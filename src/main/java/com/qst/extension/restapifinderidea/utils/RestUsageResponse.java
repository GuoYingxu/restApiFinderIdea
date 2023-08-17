package com.qst.extension.restapifinderidea.utils;

import java.util.List;

public class RestUsageResponse {
    private Integer  code;
    private List<RestApiRef> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<RestApiRef> getData() {
        return data;
    }

    public void setData(List<RestApiRef> data) {
        this.data = data;
    }
}
