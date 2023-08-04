package com.qst.extension.restapifinderidea.model;

public class ParameterModel {

    public static String PARAM_IN_PATH = "path";
    public static String PARAM_IN_QUERY = "query";
    public static String PARAM_TYPE_BODY = "body";
    public static String PARAM_TYPE_HEADER = "header";

    private String name;
    private String in;
    private String description;
    private Boolean required;
    private String example;
    private String schema;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String toString() {
    	return name;
    }
    public String toJsonString() {
    	return "{\"name\":\"" + name + "\",\"in\":\"" + in + "\",\"description\":\"" + description + "\",\"required\":\"" + required + "\",\"example\":\"" + example + "\",\"schema\":\"" + schema + "\"}";
    }
}
