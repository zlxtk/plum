package com.zlxtk.boot.framework.base.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.zlxtk.boot.framework.base.constants.ApplicationConstants;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: model 基础字段，有 状态 创建人 创建时间 修改人 修改时间；状态默认0为删除，1为正常，其他根据业务需要设置
 * @Auther: tangyake
 * @Date: 2018/7/31 12:02
 */
@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel implements Serializable, Comparable {

    @NonNull
    @Column(nullable = false)
    private Integer state = 1;

    @NonNull
    @Column(nullable = false, updatable = false)
    private String createUser;

    @CreationTimestamp
    @JsonFormat(pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @NonNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;

    @Column(length = 50)
    private String updateUser;

    @UpdateTimestamp
    @JsonFormat(pattern = ApplicationConstants.FORMAT_DATE_TIME, timezone = ApplicationConstants.FORMAT_TIME_ZONE)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column()
    private LocalDateTime updateTime;

    @Column(nullable = false)
    private Integer displayOrder;

    //分页起始页码
    @Transient
    private Integer pageIndex;

    //分页每页记录数
    @Transient
    private Integer pageSize;

    @Override
    public int compareTo(Object o) {
        return CompareToBuilder.reflectionCompare(this, o);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
