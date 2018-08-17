package com.zlxtk.boot.framework.sys.service;

import com.zlxtk.boot.framework.base.service.IBaseService;
import com.zlxtk.boot.framework.sys.model.SysFile;
import com.zlxtk.boot.framework.sys.model.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * 用途：统一系统文件管理逻辑层
 * 作者: lishuyi
 * 时间: 2018/2/23  10:14
 */
public interface ISysFileService extends IBaseService<SysFile, String> {

    /**
     * 上传并保存单个文件
     * @param multipartFile 上传文件
     * @param pmInsType 流程类型
     * @param pmInsId 流程ID
     * @param pmInsTypePart 流程区块
     * @return
     */
    SysFile uploadProcessFile(MultipartFile multipartFile, String pmInsType, String pmInsId, String pmInsTypePart);

    /**
     * 上传并保存多个文件
     * @param multipartFiles 上传文件
     * @param pmInsType 流程类型
     * @param pmInsId 流程ID
     * @param pmInsTypePart 流程区块
     * @return
     */
    List<SysFile> uploadProcessFiles(Collection<MultipartFile> multipartFiles, String pmInsType, String pmInsId, String pmInsTypePart);

    /**
     * 导入Excel文件
     * @param multipartFile 上传文件
     * @param pmInsType 流程类型
     * @param pmInsId 流程ID
     * @param pmInsTypePart 流程区块
     * @param clazz 导入对象类
     * @param sheetName 导入sheet页
     * @param <T>
     * @return
     */
    <T> UploadFileResponse importExcel(MultipartFile multipartFile, String pmInsType, String pmInsId, String pmInsTypePart, Class<T> clazz, String sheetName);

    /**
     * 通过SysFile的ID获取实际文件
     * @param id
     * @return
     */
    File getRealFileById(String id);
}
