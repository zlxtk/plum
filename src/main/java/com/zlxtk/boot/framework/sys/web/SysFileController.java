/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.sys.web;

import com.zlxtk.boot.framework.base.web.controller.BaseController;
import com.zlxtk.boot.framework.base.web.response.JsonResponse;
import com.zlxtk.boot.framework.config.AppConfig;
import com.zlxtk.boot.framework.sys.model.SysFile;
import com.zlxtk.boot.framework.sys.model.UploadFileResponse;
import com.zlxtk.boot.framework.sys.service.ISysFileService;
import com.zlxtk.boot.framework.util.AppFileUtil;
import com.zlxtk.boot.framework.util.encrypt.UrlEncryptor;
import com.zlxtk.boot.framework.util.encrypt.WebOffice3Des;
import com.zlxtk.boot.framework.util.json.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 用途：统一系统文件管理控制器
 * 作者: lishuyi https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
 * 时间: 2018/2/23  10:14
 */
@Slf4j
@Controller
public class SysFileController extends BaseController<SysFile, Long> {

    public final static String UPLOAD_PROCESS_FILES_URL = "/sys/file/uploadProcessFiles";
    public final static String UPLOAD_PROCESS_FILES_URL_SSO = "/sys/file/uploadProcessFiles/sso";
    public final static String DOWNLOAD_URL = "/sys/file/download";
    public final static String DOWNLOAD_URL_SSO = "/sys/file/download";
    public final static String OPEN_URL = "/sys/file/open";
    public final static String OPEN_URL_SSO = "/sys/file/open";

    @Autowired
    private ISysFileService fileService;

    @Autowired
    private UrlEncryptor urlEncryptor;

    @Autowired
    private AppFileUtil appFileUtil;

    @Autowired
    private AppConfig config;

    @Autowired
    public SysFileController(ISysFileService fileService) {
        super(fileService);
        this.fileService = fileService;
    }
//

    /**
     * 上传文件，支持IE8及以上版本浏览器，支持同时上传多个附件
     * @param request
     * @param response
     * @throws Exception
     */
    @PostMapping(value = {UPLOAD_PROCESS_FILES_URL, UPLOAD_PROCESS_FILES_URL_SSO})
    public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        MultipartHttpServletRequest mureq = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> multipartFiles = mureq.getFileMap();
        List<SysFile> sysFiles = fileService.uploadProcessFiles(multipartFiles.values(), request.getParameter("pmInsType"), request.getParameter("pmInsId"),
                request.getParameter("pmInsTypePart"));
        UploadFileResponse uploadFileResponse = new UploadFileResponse();
        uploadFileResponse.setSysFiles(sysFiles);
        JsonResponse jsonResponse = JsonResponse.success(uploadFileResponse);
        String result = "<script type=\"text/javascript\">parent.result="+ JacksonUtils.obj2json(jsonResponse)+"</script>";
        out.println(result);
        out.close();
    }

    /**
     * 下载文件
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    @GetMapping(value = {DOWNLOAD_URL, DOWNLOAD_URL_SSO})
    public ResponseEntity<?> download(@RequestParam("id") String id) throws FileNotFoundException {
        SysFile sysFile = fileService.findById(Long.parseLong(id));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // headers.setContentType(MediaType.parseMediaType(sysFile.getFileType()));
        // String downloadFileName = new String(sysFile.getFileName().getBytes("UTF-8"), "ISO-8859-1");

        ContentDisposition cd = ContentDisposition.builder("attachment")
                .filename(sysFile.getFileName(), StandardCharsets.UTF_8) // 防止文件名乱码，需指定文件名编码
                .size(sysFile.getFileSize())
                .build();
        headers.setContentDisposition(cd);
        File realFile = fileService.getRealFileById(Long.parseLong(id));
        Resource resource = new InputStreamResource(new FileInputStream(realFile));
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    /**
     * 在线预览文件，仅适用于保存在FastDfs环境中的文件
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping(value = {OPEN_URL, OPEN_URL_SSO})
    public String open(@RequestParam("id") String id) throws Exception{
        SysFile sysFile = fileService.findById(Long.parseLong(id));
        log.debug("Want access file online url is {}", sysFile.getFilePath());
        String redirectUrl = config.getAppHostPort()+"/webOffice/?furl="+ WebOffice3Des.encode(appFileUtil.getFileUrlFromFastDfs(sysFile.getFilePath()));
        log.warn("webOfficeUrl is :"+redirectUrl);
        return "redirect:"+redirectUrl;
    }

    @PostMapping(value = "/sys/file/deleteById")
    @ResponseBody
    public JsonResponse deleteById(@RequestParam("id") String id){
        fileService.deleteById(Long.parseLong(id));
        return JsonResponse.defaultSuccessResponse();
    }

    /**
     * 涉及到具体对象的操作，所以不直接暴露接口
     *
     * @param uploadfile
     * @param pmInsType
     * @param pmInsId
     * @param pmInsTypePart
     * @param clazz
     * @param sheetName
     * @param <T>
     * @throws IOException
     * @rn
     */
    private <T> JsonResponse importExcel(MultipartFile uploadfile,
                                         String pmInsType,
                                         String pmInsId, //起草阶段上传文件，可不填写业务单据ID
                                         String pmInsTypePart,
                                         Class<T> clazz, String sheetName) throws IOException {
        UploadFileResponse uploadFileResponse = fileService.importExcel(uploadfile, pmInsType, pmInsId, pmInsTypePart, clazz, sheetName);
        if (null != uploadFileResponse) {
            return JsonResponse.success(uploadFileResponse);
        } else {
            return JsonResponse.defaultErrorResponse();
        }
    }
}
