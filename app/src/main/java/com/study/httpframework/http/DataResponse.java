package com.study.httpframework.http;

public class DataResponse<T> {

    public int success;
    public String msg;
    public T data;

    @Override
    public String toString() {
        return "DataResponse{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}