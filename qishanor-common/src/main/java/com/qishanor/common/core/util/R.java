
package com.qishanor.common.core.util;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.qishanor.common.core.constant.CommonConstant;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 响应信息主体
 *
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @JsonPropertyDescription(value = "调用成功返回0，调用失败返回1")
    private int code;

    @Getter
    @Setter
    @JsonPropertyDescription(value = "返回具体的信息")
    private String msg;

    @Getter
    @Setter
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, CommonConstant.SUCCESS, null);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonConstant.SUCCESS, null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonConstant.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, CommonConstant.FAIL, null);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonConstant.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, CommonConstant.FAIL, null);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, CommonConstant.FAIL, msg);
    }

    static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public boolean isOk() {
        return this.code == CommonConstant.SUCCESS;
    }

}
