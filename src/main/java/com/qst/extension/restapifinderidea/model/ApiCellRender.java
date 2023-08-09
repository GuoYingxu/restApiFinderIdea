package com.qst.extension.restapifinderidea.model;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

public class ApiCellRender extends JLabel implements ListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        RestApiModel celldata = (RestApiModel) value;
        try{
        ClassLoader classLoader = ApiCellRender.class.getClassLoader();
        ImageIcon icon  = new FlatSVGIcon(getIconPath(celldata.getMethod()),20,20,classLoader);
        this.setIcon(icon);
        this.setText(celldata.getMethod()+" "+celldata.getPath());
        }catch (Exception e){
            e.printStackTrace();
            this.setIcon(null);
            this.setText("error");
        }
        System.out.println("getListCellRendererComponent"+ this.getText() + " " + this.getIcon());
        return this;
    }

    public String getIconPath(String method){
        String iconPath = "";
        switch (method){
            case "GET":
                iconPath = "icons/GET.svg";
                break;
            case "POST":
                iconPath = "icons/POST.svg";
                break;
            case "PUT":
                iconPath = "icons/PUT.svg";
                break;
            case "DELETE":
                iconPath = "icons/DEL.svg";
                break;
            case "PATCH":
                iconPath = "icons/PAT.svg";
                break;
            default:
                iconPath = "icons/GET.svg";
                break;
        }
        return iconPath;
    }
}
