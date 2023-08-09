package com.qst.extension.restapifinderidea.utils;

import com.qst.extension.restapifinderidea.model.RestApiModel;

import javax.swing.*;

public class DataCenter {
    public static DefaultListModel<RestApiModel> LIST_MODEL = new DefaultListModel();



    public static void add(RestApiModel data) {
        LIST_MODEL.addElement(data);
    }
    public  static void remove(RestApiModel data) {
        LIST_MODEL.removeElement(data);
    }
    public static void reset() {
        LIST_MODEL.clear();
    }
    public static void update(int index, RestApiModel data) {
        LIST_MODEL.set(index, data);
    }
}
