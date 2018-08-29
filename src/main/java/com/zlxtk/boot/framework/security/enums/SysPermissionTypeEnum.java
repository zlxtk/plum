/**
 * 版权所有 © 北京晟壁科技有限公司 2008-2027。保留一切权利!
 */
package com.zlxtk.boot.framework.security.enums;

import com.zlxtk.boot.framework.base.enums.BaseEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 用途：系统权限类型
 * 作者: lishuyi 
 * 时间: 2018/1/31  14:39 
 */
public enum SysPermissionTypeEnum implements BaseEnum {

    ROOT("根"),  MENU("访问路径"), BUTTON("按钮"), METHOD("执行方法"),MODULE("功能模块");

    @Setter @Getter
    private String value;

    SysPermissionTypeEnum(String value) {
        this.value = value;
    }

}
