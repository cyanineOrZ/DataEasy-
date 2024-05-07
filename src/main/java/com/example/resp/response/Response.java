package com.example.resp.response;

import java.util.List;

public class Response<T> {
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Response() {
    }

    public Response(T data) {
        this.data = data;
    }

    // 默认成功，返回code = 0和msg = 成功，没有data
    public static Response success() {
        Response result = new Response<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    // 成功时，返回code = 0 msg = 成功 和 data
    public static <T> Response<T> success(T data) {
        Response<T> result = new Response<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }

    // 成功时， 返回code = 0， 自定义msg 和 data
    public static <T> Response<T> success(T data,String msg) {
        Response<T> result = new Response<>(data);
        result.setCode("0");
        result.setMsg(msg);
        return result;
    }

    public static Response error(String code, String msg) {
        Response result = new Response();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
