package com.qst.extension.restapifinderidea.listener;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;

import com.intellij.psi.util.PsiTreeUtil;
import com.qst.extension.restapifinderidea.model.RestApiModel;
import com.qst.extension.restapifinderidea.utils.DataCenter;
import com.qst.extension.restapifinderidea.utils.RestApiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FileChangeListener implements FileEditorManagerListener {
    @Override
    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
        FileEditorManagerListener.super.fileOpened(source, file);
//        System.out.println("fileOpened::"+file.getName());
//        Editor editor = source.getSelectedTextEditor();
//        if(editor == null) {
//            return;
//        }
//        if(editor.getProject() == null) {
//            return;
//        }
//        PsiFileFactory.getInstance(editor.getProject()).
//        if(editor.)
//        Document currentDoc = editor.getDocument();
//        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(currentDoc);
//        restApiModelList = new ArrayList<>();
//        PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class).forEach(psiClass -> {
//            List<RestApiModel> models = RestApiUtil.parse(psiClass);
//            restApiModelList.addAll(models);
//        });
//        apiList.setListData(restApiModelList.toArray());
//        apiList.setCellRenderer(new ApiCellRender());
    }

    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {

        FileEditorManagerListener.super.selectionChanged(event);
        System.out.println("selectionChanged::"+event.getNewFile().getName());
        Editor editor = event.getManager().getSelectedTextEditor();
        if(editor == null) {
            return;
        }
        if(editor.getProject() == null) {
            return;
        }
        Project project = editor.getProject();
        DumbService.getInstance(project).runWhenSmart(() -> {
            Document currentDoc = editor.getDocument();
            PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(currentDoc);
            parseFile(psiFile);
        });

    }

    private void parseFile(PsiFile psiFile) {
        DataCenter.reset();
        PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class).forEach(psiClass -> {
            List<RestApiModel> models = RestApiUtil.parse(psiClass);
            models.forEach(model -> {
                DataCenter.add(model);
            });
        });
    }
}
