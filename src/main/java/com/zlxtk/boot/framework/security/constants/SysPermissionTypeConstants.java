
package com.zlxtk.boot.framework.security.constants;

/**
 * 用途：权限类型常量
 */
public class SysPermissionTypeConstants {

    //根，只有一个/,下级全是module或admin_module
    public static final String ROOT = "ROOT";
    //前台模块，每个系统模块有前台和后台两个权限模块，比如shop系统模块，在权限中有/shop(前台)和/shop/admin(后台)两个权限模块数据
    public static final String MODULE = "MODULE";
    //后台模块
    public static final String ADMIN_MODULE = "ADMIN_MODULE";
    //模块下的菜单页面。
    public static final String MENU = "MENU";
    //不是菜单的页面
    public static final String PAGE = "PAGE";
    //页面中的功能，没有页面
    public static final String FUNCTION = "FUNCTION";
}
