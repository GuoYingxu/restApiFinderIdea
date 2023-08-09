//package com.qst.extension.restapifinderidea.listener;
//
//import com.intellij.openapi.editor.Document;
//import com.intellij.openapi.editor.Editor;
//import com.intellij.openapi.fileEditor.FileEditorManager;
//import com.intellij.openapi.project.Project;
//import com.intellij.openapi.wm.ToolWindow;
//import com.intellij.openapi.wm.ToolWindowManager;
//import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
//import com.intellij.psi.PsiClass;
//import com.intellij.psi.PsiDocumentManager;
//import com.intellij.psi.PsiFile;
//import com.intellij.psi.PsiManager;
//import com.intellij.psi.util.PsiTreeUtil;
//import com.qst.extension.restapifinderidea.model.RestApiModel;
//import com.qst.extension.restapifinderidea.utils.DataCenter;
//import com.qst.extension.restapifinderidea.utils.RestApiUtil;
//import com.qst.extension.restapifinderidea.window.ApiListWindow;
//import org.jetbrains.annotations.NotNull;
//import com.intellij.psi.impl.PsiTreeChangeEventImpl;
//import javax.swing.*;
//import java.util.ArrayList;
//import java.util.List;
//public class FileApiWindowListener implements ToolWindowManagerListener {
//    private final Project project;
//    public FileApiWindowListener(Project project) {
//        this.project = project;
////        this.toolWindowShown(ToolWindowManager.getInstance(project));
//        PsiManager.getInstance(project).addPsiTreeChangeListener(new FileChangeListener(project), project);
//
//    }
//
//    @Override
//    public void toolWindowShown(@NotNull ToolWindow toolWindow) {
//        ToolWindowManagerListener.super.toolWindowShown(toolWindow);
//        if(toolWindow.getStripeTitle().equals("api")) {
//            System.out.println("toolWindowShown");
////            Document currentDoc = FileEditorManager.getInstance(project).getSelectedTextEditor().getDocument();
////            PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(currentDoc);
//////            List<RestApiModel>  restApiModelList = new ArrayList<>();
////            PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class).forEach(psiClass -> {
////                List<RestApiModel> models = RestApiUtil.parse(psiClass);
////                models.forEach(model -> {
////                    DataCenter.add(model);
////                });
//////                restApiModelList.addAll(models);
////            });
//
//
//        }
//    }
//
//
//    @Override
//    public void stateChanged(ToolWindowManager toolWindowManager) {
////        ToolWindowManager
////                .getInstance(project)
////                .getToolWindow("Api List")
////                .getContentManager().removeAllContents(true);
////        System.out.println("stateChanged"+ toolWindowManager.getToolWindow("api").getStripeTitle());
////        Document currentDoc = FileEditorManager.getInstance(project).getSelectedTextEditor().getDocument();
////        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(currentDoc);
////        System.out.println("psiFile"+ psiFile);
////        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(currentDoc);
////        restApiModelList = new ArrayList<>();
////        PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class).forEach(psiClass -> {
////            List<RestApiModel> models = RestApiUtil.parse(psiClass);
////            restApiModelList.addAll(models);
////        });
//    }
//
//}
