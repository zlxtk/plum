/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.sys.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用途：返回上传文件响应对象
 * 作者: lishuyi
 * 时间: 2018/7/21  10:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponse<T> {

    List<SysFile> sysFiles;

    List<T> listData;

}
