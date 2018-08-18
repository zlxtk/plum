/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.util;

import com.zlxtk.boot.framework.base.constants.ApplicationConstants;
import com.zlxtk.boot.framework.base.exception.Exceptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 用途：分布式FastDfs https://github.com/happyfish100/fastdfs
 * 作者: lishuyi
 * 时间: 2018/8/2  20:43
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.file.upload.location", havingValue = "fastdfs")
public class FastDfsClient {
    private static TrackerClient tracker = null;
    private static TrackerServer trackerServer = null;
    private static StorageClient storageClient = null;

    private static FastDfsClient client;

    @PostConstruct
    public void init() {
        try {
            BufferedReader bufferedReader = BootAppFileReader.getClasspathFile("fastdfs-client.properties");
            Properties props = new Properties();
            props.load(bufferedReader);
            ClientGlobal.initByProperties(props);
            if (tracker == null) {
                tracker = new TrackerClient();
            }
            if (trackerServer == null) {
                trackerServer = tracker.getConnection();
            }
            storageClient = new StorageClient(trackerServer, null);
        } catch (Exception ex) {
            Exceptions.printException(ex);
        }
        client = this;
        client.tracker = this.tracker;
        client.trackerServer = this.trackerServer;
        client.storageClient = this. storageClient;
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileName 文件全路径
     * @param extName 文件扩展名，不包含（.）
     * @param metas 文件扩展信息
     * @return
     * @throws Exception
     */
    public static String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
        String[] result = storageClient.upload_file(fileName, extName, metas);
        String group = result[0];
        String filePath = result[1];
        return group + ApplicationConstants.SLASH + filePath;
    }

    public static String uploadFile(String fileName) throws Exception {
        return uploadFile(fileName, null, null);
    }

    public static String uploadFile(String fileName, String extName) throws Exception {
        return uploadFile(fileName, extName, null);
    }

    /**
     * 上传文件方法
     * <p>Title: uploadFile</p>
     * <p>Description: </p>
     * @param fileContent 文件的内容，字节数组
     * @param extName 文件扩展名，不包含（.）
     * @param metas 文件扩展信息
     * @return
     * @throws Exception
     */
    public static String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
        String[] result = storageClient.upload_file(fileContent, extName, metas);
        String group = result[0];
        String filePath = result[1];
        return group + ApplicationConstants.SLASH + filePath;
    }

    public static String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }

    public static String uploadFile(byte[] fileContent, String extName) throws Exception {
        return uploadFile(fileContent, extName, null);
    }

    public static Integer deleteFile(String storagePath) throws IOException, MyException {
        String group = StringUtils.substringBefore(storagePath, "/");
        String filename = StringUtils.substringAfter(storagePath, "/");
        log.warn("Want remove file at group {}, filepath {}", group, filename);
        return storageClient.delete_file(group, filename);
    }
}
