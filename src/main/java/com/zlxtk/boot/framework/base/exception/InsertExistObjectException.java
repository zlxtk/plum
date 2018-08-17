
package com.zlxtk.boot.framework.base.exception;

import lombok.NoArgsConstructor;

/**
 * 用途：新增创建已存在的对象异常
 * 作者: lishuyi
 * 时间: 2018/6/7  20:03
 */
@NoArgsConstructor
public class InsertExistObjectException extends RuntimeException {

    /**
     * Constructs an <code>InsertExistObjectException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public InsertExistObjectException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>InsertExistObjectException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public InsertExistObjectException(String msg, Throwable t) {
        super(msg, t);
    }
}
