package com.qst.extension.restapifinderidea.listener;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;
import com.qst.extension.restapifinderidea.utils.RestApiUtil;
import org.jetbrains.annotations.NotNull;

public class DocumentChangeListener implements  DocumentListener {
//    @Override
//    public void beforeDocumentSaving(@NotNull Document document) {
//        FileDocumentManagerListener.super.beforeDocumentSaving(document);
//        document.getModificationStamp();
//        document.getUserData(Document.psiFileKey);
//        RestApiUtil.parseFile(psiFile);
//    }

    @Override
    public void documentChanged(@NotNull DocumentEvent event) {
        DocumentListener.super.documentChanged(event);
        System.out.println("documentChanged::"+event.getDocument().getText());
    }
}
