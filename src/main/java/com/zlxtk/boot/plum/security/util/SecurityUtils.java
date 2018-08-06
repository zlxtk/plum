
package com.zlxtk.boot.plum.security.util;

import com.zlxtk.boot.plum.base.constants.AuthoritiesConstants;
import com.zlxtk.boot.plum.security.model.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用途：安全工具类
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static SysUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof SysUser) {
                return (SysUser) authentication.getPrincipal();
            } else
                return null;
        }
        return null;
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static String getCurrentUserName() {
        String userName = null;
        SysUser authUser = getCurrentUser();
        if(null != authUser){
            userName = authUser.getUsername();
        }
        return userName;
    }


    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities()
                    .stream()
                    .noneMatch(grantedAuthority -> grantedAuthority.getAuthority()
                            .equals(AuthoritiesConstants.ANONYMOUS));
        }
        return false;
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean hasPermission(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities()
                    .stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority()
                            .equals(authority));
        }
        return false;
    }
}
