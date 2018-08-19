/*
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.sys.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zlxtk.boot.framework.base.model.BaseModel;
import lombok.*;

import javax.persistence.*;

/**
 * 用途：统一系统文件管理
 * 作者: lishuyi
 * 时间: 2018/3/7  23:10
 */
@Data
@EqualsAndHashCode(callSuper = true)//equals和hashCode调研父类的方法 https://blog.csdn.net/zhanlanmg/article/details/50392266
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SysFile extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //文件名称
    @Column(nullable = false, length = 200)
    @NonNull
    private String fileName;

    //文件类型
    @Column(nullable = false, length = 20)
    @NonNull
    private String fileType;

    //文件实际存储路径
    @Column(nullable = false, length = 500)
    @NonNull
    @JsonIgnore //隐藏不对外暴露内部路径
    private String filePath;

    //文件大小
    @Column(nullable = false, length = 50)
    @NonNull
    private Long fileSize;

    //归属流程
    private String pmInsType;

    //归属流程ID
    private String pmInsId;

    //归属流程区块
    private String pmInsTypePart;

    //文件下载URL
    @Column(nullable = false, length = 500)
    @NonNull
    private String downLoadUrl;

    //专门用于标识是否跟随应用，不跟随磁盘的文件
    private Boolean isLocal;
}
