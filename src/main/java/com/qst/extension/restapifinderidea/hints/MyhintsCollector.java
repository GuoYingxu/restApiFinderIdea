package com.qst.extension.restapifinderidea.hints;

import com.intellij.codeInsight.hints.*;
import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.impl.PresentationFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import  com.intellij.openapi.editor.BlockInlayPriority;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import static com.intellij.debugger.InstanceFilter.create;

public class MyhintsCollector extends  FactoryInlayHintsCollector{
        public MyhintsCollector(Editor editor) {
            super(editor);
        }

        @Override
        public boolean collect(@NotNull PsiElement psiElement, @NotNull Editor editor, @NotNull InlayHintsSink inlayHintsSink) {
            if (psiElement instanceof PsiMethod) {
                PsiElement psiParent = psiElement.getParent();
                if(psiParent instanceof PsiClass) {
                    if(((PsiClass) psiParent).hasAnnotation("org.springframework.web.bind.annotation.RestController")) {
                        PsiMethod psiMethod = (PsiMethod) psiElement;
                        PsiAnnotation[] psiAnnotations = psiMethod.getAnnotations();
                        PsiAnnotation first = Arrays.stream(psiAnnotations).findFirst().orElse(null);
                        if(first!=null) {
                            Integer line = editor.getDocument().getLineNumber(first.getTextOffset());
                            Integer lineStart = editor.getDocument().getLineStartOffset(line);
                            inlayHintsSink.addBlockElement(lineStart, true, true, BlockInlayPriority.ANNOTATIONS, getFactory().smallText("get"));
                        }
                    }
                }
            }
            return true;
        }
}
