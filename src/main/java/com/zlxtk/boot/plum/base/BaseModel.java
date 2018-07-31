package com.zlxtk.boot.plum.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @Description: model 基础字段，有 状态 创建人 创建时间 修改人 修改时间；状态默认0为删除，1为正常，其他根据业务需要设置
 *
 * @Auther: tangyake
 * @Date: 2018/7/31 12:02
 */
@MappedSuperclass
public class BaseModel {

    @NonNull
    @Column(nullable = false)
    private Integer enabled = 1;

    @NonNull
    @Column(nullable = false, updatable = false)
    private String createUser;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @NonNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(length = 50)
    private String updateUser;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column()
    private LocalDateTime updateTime;
}
