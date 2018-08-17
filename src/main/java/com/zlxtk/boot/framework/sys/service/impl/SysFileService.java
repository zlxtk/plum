package com.zlxtk.boot.framework.sys.service.impl;

import com.google.common.collect.Lists;
import com.zlxtk.boot.framework.base.constants.ApplicationConstants;
import com.zlxtk.boot.framework.base.exception.Exceptions;
import com.zlxtk.boot.framework.base.service.impl.BaseService;
import com.zlxtk.boot.framework.sys.model.SysFile;
import com.zlxtk.boot.framework.sys.model.UploadFileResponse;
import com.zlxtk.boot.framework.sys.repository.SysFileRepository;
import com.zlxtk.boot.framework.sys.service.ISysFileService;
import com.zlxtk.boot.framework.util.AppFileUtil;
import com.zlxtk.boot.framework.util.office.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 用途：统一系统文件管理逻辑层
 * 作者: lishuyi
 * 时间: 2018/2/23  10:14
 */
@Slf4j
@Service
@DependsOn(value = {"appFileUtil"})
public class SysFileService extends BaseService<SysFile, String> implements ISysFileService {

    @Autowired
    private SysFileRepository repository;

    @Autowired
    private AppFileUtil appFileUtil;

    @Autowired
    public SysFileService(SysFileRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SysFile uploadProcessFile(MultipartFile multipartFile, String pmInsType, String pmInsId, String pmInsTypePart) {
        List<SysFile> fileList = uploadProcessFiles(Arrays.asList(multipartFile), pmInsType, pmInsId, pmInsTypePart);
        return fileList.isEmpty() ? null : fileList.get(0);
    }

    @Override
    @Transactional
    public List<SysFile> uploadProcessFiles(Collection<MultipartFile> multipartFiles, String pmInsType, String pmInsId, String pmInsTypePart) {
        List<SysFile> sysFileList = Lists.newArrayList();
        try {
            sysFileList = appFileUtil.uploadFiles(pmInsType + ApplicationConstants.SLASH + pmInsTypePart, multipartFiles);
            for(SysFile sysFile : sysFileList){
                sysFile = super.insert(sysFile); //先保存文件获取ID
                sysFile.setDownLoadUrl(sysFile.getDownLoadUrl().concat("?id="+sysFile.getId())); //修改下载URL，追加ID
                sysFile.setPmInsType(pmInsType);
                sysFile.setPmInsId(pmInsId);
                sysFile.setPmInsTypePart(pmInsTypePart);
            }
        } catch (IOException e) {
            Exceptions.printException(e);
        } catch (Exception e) {
            Exceptions.printException(e);
        }
        return sysFileList;
    }

    @Override
    @Transactional
    public <T> UploadFileResponse importExcel(MultipartFile multipartFile, String pmInsType, String pmInsId, String pmInsTypePart, Class<T> clazz, String sheetName) {
        SysFile sysFile = uploadProcessFile(multipartFile, pmInsType, pmInsId, pmInsTypePart);
        if (sysFile != null) {
            ExcelUtil<T> importUtil = new ExcelUtil<>(clazz);
            File tempFile = AppFileUtil.createTempFile();
            try {
                multipartFile.transferTo(tempFile);
                List<T> listData = importUtil.importExcel(sheetName, new FileInputStream(tempFile));
                UploadFileResponse<T> uploadFileResponse = new UploadFileResponse<>();
                uploadFileResponse.setListData(listData);
                uploadFileResponse.setSysFiles(Arrays.asList(sysFile));
                return uploadFileResponse;
            } catch (IOException e) {
                Exceptions.printException(e);
            }
        }
        return null;
    }

    @Override
    public File getRealFileById(String id) {
        SysFile sysFile = this.findById(id);
        return appFileUtil.getFileFromSystem(sysFile.getFilePath());
    }

    @Override
    @Transactional
    public void deleteById ( String id ) {
        SysFile sysFile = this.findById(id);
        String filePath = sysFile.getFilePath();
        super.deleteById(id);
        int result = appFileUtil.deleteFile(filePath);
        log.warn("Delete file result is {}", result);
    }
}
