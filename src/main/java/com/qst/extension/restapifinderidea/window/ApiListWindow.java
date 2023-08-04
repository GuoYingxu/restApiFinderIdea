package com.qst.extension.restapifinderidea.window;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.qst.extension.restapifinderidea.model.RestApiModel;
import com.qst.extension.restapifinderidea.utils.RestApiUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiListWindow {
    private JPanel panel1;
    private JList apiList;
    private  Project project;


    private List<RestApiModel> restApiModelList;

    public JPanel getContentPanel() {
        return panel1;
    }
    public ApiListWindow(Project project, ToolWindow window) {
        apiList.setListData(new String[]{"1","2","3"});
        this.project = project;
         PsiManager.getInstance(project).addPsiTreeChangeListener(new PsiTreeChangeAdapter() {
             @Override
             public void childAdded(@NotNull PsiTreeChangeEvent event) {
                 super.childAdded(event);
                 init();
             }

             @Override
             public void childRemoved(@NotNull PsiTreeChangeEvent event) {
                 super.childRemoved(event);
                 init();
             }

             @Override
             public void childReplaced(@NotNull PsiTreeChangeEvent event) {
                 super.childReplaced(event);
                 init();
             }

             @Override
             public void childrenChanged(@NotNull PsiTreeChangeEvent event) {
                 super.childrenChanged(event);
                 init();
             }

             @Override
             public void childMoved(@NotNull PsiTreeChangeEvent event) {
                 super.childMoved(event);
                 init();
             }

             @Override
             public void propertyChanged(@NotNull PsiTreeChangeEvent event) {
                 super.propertyChanged(event);
                 init();
             }
         });

    }


    public void init() {
//        Document currentDoc = FileEditorManager.getInstance(project).getSelectedTextEditor().getDocument();
//        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(currentDoc);
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if(editor == null) {
            return;
        }
        Document currentDoc = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(currentDoc);
        restApiModelList = new ArrayList<>();
        PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class).forEach(psiClass -> {
            List<RestApiModel> models = RestApiUtil.parse(psiClass);
            restApiModelList.addAll(models);
        });
        apiList.setListData(restApiModelList.toArray());
//        apiList.setCellRenderer(new ApiCellRender());
    }
}