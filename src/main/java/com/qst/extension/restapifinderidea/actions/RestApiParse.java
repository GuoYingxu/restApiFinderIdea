package com.qst.extension.restapifinderidea.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtil;
import com.qst.extension.restapifinderidea.model.RestApiModel;
import com.qst.extension.restapifinderidea.utils.DataCenter;
import com.qst.extension.restapifinderidea.utils.RestApiUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;

public class RestApiParse extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        System.out.println(psiFile.getLanguage());
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if(editor == null) {
            System.out.println("editor is null");
            return;
        }
        PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class).forEach(psiClass -> {
           List<RestApiModel> models =  RestApiUtil.parse(psiClass);
           models.forEach(model -> {
               System.out.println(model);
               DataCenter.add(model);
           });
        });
//        psiFile.accept(new JavaRecursiveElementVisitor() {
//            @Override
//            public void visitAnnotation(@NotNull PsiAnnotation annotation) {
//                super.visitAnnotation(annotation);
//                System.out.println(annotation.getText());
//            }
//        });
    }
}
