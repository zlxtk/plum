/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.util;


import com.google.common.collect.Lists;
import com.zlxtk.boot.framework.base.constants.ApplicationConstants;
import com.zlxtk.boot.framework.base.exception.Exceptions;
import com.zlxtk.boot.framework.config.AppConfig;
import com.zlxtk.boot.framework.sys.model.SysFile;
import com.zlxtk.boot.framework.sys.web.SysFileController;
import com.zlxtk.boot.framework.util.encrypt.UrlEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用途：文件工具类
 * 作者: lishuyi
 * 时间: 2018/3/12  20:45
 */
@Slf4j
@Component
public class AppFileUtil {

    private static final String UPLOAD_FILE_PATTERN =
            "(jpg|jpeg|png|gif|bmp|doc|docx|xls|xlsx|pdf|txt|rar|zip|7z)$";
    private static Pattern pattern = Pattern.compile(UPLOAD_FILE_PATTERN);

    @Autowired
    private UrlEncryptor urlEncryptor;

    @Autowired
    private AppConfig config;

    public static StoreLocation serverUploadLocation = null;

    public enum StoreLocation {disk, fastdfs, baidubos}

    @PostConstruct
    public void init() {
        serverUploadLocation = Enum.valueOf(StoreLocation.class, config.getUploadLocation());
    }

    /**
     * 判断是否允许上传
     *
     * @param fileName
     * @return
     */
    public static boolean validateUploadFileType(String fileName) {
        Matcher matcher = pattern.matcher(getFileSuffix(fileName));
        return matcher.matches();
    }

    /**
     * 根据路径返回文件名，如：http://aaa/bbb.jpg C:/aaa/abc.jpg 返回abc
     *
     * @param pathToName
     * @return
     */
    public static String getFileBaseName(String pathToName) {
        Assert.notNull(pathToName, "File name can not empty!");
        return FilenameUtils.getBaseName(pathToName);
    }

    /**
     * 根据路径返回文件后缀，如：http://aaa/bbb.jpg C:/aaa/abc.jpg 返回jpg
     *
     * @return doc
     */
    public static String getFileSuffix(String pathToName) {
        Assert.notNull(pathToName, "File name can not empty!");
        return FilenameUtils.getExtension(pathToName);
    }

    /**
     * 根据路径返回文件名，如：http://aaa/bbb.jpg C:/aaa/abc.jpg 返回abc.jpg
     *
     * @param pathToName
     * @return
     */
    public static String getFileName(String pathToName) {
        Assert.notNull(pathToName, "File name can not empty!");
        return FilenameUtils.getName(pathToName);
    }

    /**
     * 上传单个文件
     *
     * @return SysFile
     * @throws IOException
     */
    public SysFile uploadFile(String directory, MultipartFile multipartFile) throws Exception {
        Assert.notNull(multipartFile, "Upload file can not empty!");
        return uploadFiles(directory, Arrays.asList(multipartFile)).get(0);
    }

    /**
     * 上传多个文件
     *
     * @return UploadFileModel
     * @throws IOException
     */
    public List<SysFile> uploadFiles(String directory, Collection<MultipartFile> multipartFiles) throws Exception {
        Assert.notEmpty(multipartFiles, "Upload file can not empty!");
        List<SysFile> fileModels = Lists.newArrayList();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            } else if (multipartFile.getSize() == 0L){
                continue;
            }
            String filePath = null;
            log.debug("Will upload file {} to {}", multipartFile.getOriginalFilename(), serverUploadLocation);
            switch (serverUploadLocation) {
                case disk:
                    byte[] bytes = multipartFile.getBytes();
                    String storePath = config.getUploadPath()  + ApplicationConstants.SLASH + DateUtil.getCurrentStr()
                            + ApplicationConstants.SLASH + directory + ApplicationConstants.SLASH
                            + CodeGenerator.randomInt(4);
                    File targetFileDirectory = new File(storePath);
                    if (!targetFileDirectory.exists()) {
                        FileUtils.forceMkdir(targetFileDirectory);
                        log.debug("Directory {} is not exist, force create direcorty....", storePath);
                    }
                    Path path = Paths.get(targetFileDirectory.getPath() + ApplicationConstants.SLASH + multipartFile.getOriginalFilename());
                    Files.write(path, bytes);
                    filePath = path.toString();
                    break;
                case fastdfs:
                    filePath = FastDfsClient.uploadFile(IOUtils.toByteArray(multipartFile.getInputStream()), getFileSuffix(multipartFile.getOriginalFilename()));
                    break;
            }
            SysFile sysFile = SysFile.builder().fileName(multipartFile.getOriginalFilename()).fileType(getFileSuffix(multipartFile.getOriginalFilename()))
                    .filePath(filePath).fileSize(multipartFile.getSize()).downLoadUrl(SysFileController.DOWNLOAD_URL).
                            build();
            log.debug("Upload save file is {}", sysFile.toString());
            fileModels.add(sysFile);
        }
        return fileModels;
    }

    /**
     * 创建无后缀临时文件
     * @return
     */
    public static File createTempFile(){
        return createTempFile(CodeGenerator.randomChar(4));
    }

    /**
     * 创建带后缀临时文件
     * @param suffix
     * @return
     */
    public static File createTempFile(String suffix){
        File tempFile = null;
        try {
            tempFile = File.createTempFile(CodeGenerator.randomChar(4), ApplicationConstants.DOT + suffix);
        } catch (IOException e) {
            Exceptions.printException(e);
        }
        log.debug("Create temp file absolute path is {}", tempFile.getAbsolutePath());
        return tempFile;
    }

    /**
     * 从系统中下载文件
     * @param filePath
     * @return
     */
    public File getFileFromSystem(String filePath){
        File realFile = null;
        log.debug("Want to get file {} from {}", filePath, serverUploadLocation);
        switch (serverUploadLocation) {
            case disk:
                realFile = new File(filePath);
                break;
            case fastdfs:
                realFile = downloadFromUrl(config.getAppHostPort() + ApplicationConstants.SLASH + filePath);
                break;
        }
        return realFile;
    }

    /**
     * 获取保存在FastDfs中的文件访问路径
     * @param filePath
     * @return
     */
    public String getFileUrlFromFastDfs(String filePath){
        return config.getAppHostPort() + ApplicationConstants.SLASH + filePath;
    }

    /**
     * 根据远程文件的url下载文件
     * @param fileUrl
     * @return
     */
    public File downloadFromUrl(String fileUrl) {
        File targetFile = createTempFile(getFileSuffix(fileUrl));
        HttpURLConnection conn = null;
        try {
            log.debug("Download origal url is {}", fileUrl);
            String urlStr = FilenameUtils.getFullPath(fileUrl) + urlEncryptor.encrypt(getFileName(fileUrl));
            log.debug("Download real url is {}", urlStr);
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod(ApplicationConstants.HTTPGET);
            conn.connect();
            if (conn.getContentLengthLong() != 0) {
                FileUtils.copyURLToFile(url, targetFile);
                conn.disconnect();
                log.debug("Downloaded file from url: {}, and save at: {}", fileUrl, targetFile.getAbsolutePath());
            } else {
                log.error("Want to download file from {}, but get nothing......", fileUrl);
            }
        } catch (Exception e) {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
            Exceptions.printException(e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return targetFile;
    }


    public int deleteFile(String filePath) {
        int result = 0;
        log.debug("Will remove file at {}, and filePath is {}", serverUploadLocation, filePath);
        if (StringUtils.isNotEmpty(filePath)) {
            switch (serverUploadLocation) {
                case disk:
                    try {
                        FileUtils.forceDelete(new File(filePath));
                    } catch (Exception e) {
                        result = -1;
                        Exceptions.printException(e);
                    }
                    break;
                case fastdfs:
                    try {
                        FastDfsClient.deleteFile(filePath);
                    } catch (Exception e) {
                        result = -1;
                        Exceptions.printException(e);
                    }
                    break;
                default:
                    break;
            }
        }
        return result;
    }
}
