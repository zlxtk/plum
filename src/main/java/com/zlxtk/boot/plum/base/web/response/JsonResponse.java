package com.zlxtk.boot.plum.base.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class JsonResponse {
    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = -1;
    public static final int SUCCESS_STATUS = 200;
    public static final int ERROR_STATUS = 500;

    //状态码，请求正常返回为0
    @NonNull
    private Integer errcode;

    //时间戳
    @CreationTimestamp// 创建时自动更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime timestamp;

    //Http请求状态码
    private int status;

    //Http请求内部错误
    private String error;

    //Http请求提示信息
    private String message;

    //Http请求路径
    private String path;

    //业务数据
    private Object data;

    /**
     *
     * @return 默认成功输出
     */
    public static JsonResponse defaultSuccessResponse() {
        return JsonResponse.builder().errcode(SUCCESS_CODE).timestamp(LocalDateTime.now())
                .status(SUCCESS_STATUS).build();
    }

    /**
     *
     * @return 默认失败输出
     */
    public static JsonResponse defaultErrorResponse() {
        return JsonResponse.builder().errcode(ERROR_CODE).timestamp(LocalDateTime.now())
                .status(ERROR_STATUS).build();
    }

    /**
     *
     * @param obj 成功数据
     * @return 输出成功数据
     */
    public static JsonResponse success(Object obj) {
        JsonResponse response = defaultSuccessResponse();
        response.setData(obj);
        return response;
    }

    /**
     *
     * @param obj 失败数据
     * @return 输出失败数据
     */
    public static JsonResponse fail(Object obj) {
        JsonResponse response = defaultErrorResponse();
        response.setData(obj);
        return response;
    }

    /**
     *
     * @param obj 成功数据
     * @param message 提示信息
     * @return 输出成功数据
     */
    public static JsonResponse success(Object obj, String message) {
        JsonResponse response = success(obj);
        response.setMessage(message);
        return response;
    }

    /**
     *
     * @param obj 失败数据
     * @param message 提示信息
     * @return 输出失败数据
     */
    public static JsonResponse fail(Object obj, String message) {
        JsonResponse response = fail(obj);
        response.setMessage(message);
        return response;
    }

    /**
     *
     * @param obj 失败数据
     * @param message 提示信息
     * @param errcode 错误代码  详见 ErrorCodeConstants
     * @return
     */
    public static JsonResponse fail(Object obj, String message, Integer errcode) {
        JsonResponse response = fail(obj);
        response.setMessage(message);
        return response;
    }
}
