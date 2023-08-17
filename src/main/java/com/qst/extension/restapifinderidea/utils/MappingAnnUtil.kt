package com.qst.extension.restapifinderidea.utils

import com.intellij.openapi.module.ModuleManager
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiMethod
import com.intellij.psi.util.PsiTreeUtil
import com.qst.extension.restapifinderidea.model.RestApiModel

class MappingAnnUtil {
    companion object {
        fun getMappingAnnName(annName: String): String {
            return when (annName) {
                "GetMapping" -> "GET"
                "PostMapping" -> "POST"
                "PutMapping" -> "PUT"
                "DeleteMapping" -> "DELETE"
                "PatchMapping" -> "PATCH"
                else -> ""
            }
        }

        fun parseUrl(annotation: PsiAnnotation): String {
            val psiAnnotationMemberValue = annotation.findDeclaredAttributeValue("value")
            if (psiAnnotationMemberValue != null) {
                return psiAnnotationMemberValue.text
            }
            return ""
        }
        fun parseBaseUrl(psiClass: PsiClass):String {
            val annotation = psiClass.annotations.find { it.qualifiedName?.contains("RequestMapping") == true }
            if(annotation == null) {
                return ""
            }else {
                val psiAnnotationMemberValue = annotation.findDeclaredAttributeValue("value")
                if (psiAnnotationMemberValue != null) {
                    return psiAnnotationMemberValue.text
                }
                return ""
            }
        }
        fun parseMethod(annotation: PsiAnnotation): String {
            if(annotation.qualifiedName?.contains("Mapping") == true) {
                if (annotation.qualifiedName?.contains("RequestMapping") == true) {
                    val psiAnnotationMemberValue = annotation.findDeclaredAttributeValue("method")
                    if (psiAnnotationMemberValue != null) {
                        return psiAnnotationMemberValue.text
                    }
                    return ""
                } else {
                    return getMappingAnnName(annotation.qualifiedName?.split(".")?.last() ?: "")
                }
            }
            return ""
        }

        fun parseApi(psiMethd:PsiMethod,module:String):RestApiModel {
            val baseUrl = parseBaseUrl(PsiTreeUtil.getParentOfType(psiMethd, PsiClass::class.java)!!).replace("\"","")
            var mapAnn = psiMethd.annotations.find { it.qualifiedName?.contains("Mapping") == true }
            if(mapAnn!= null) {
                val url = parseUrl(mapAnn).replace("\"","")
                val method = parseMethod(mapAnn)
                println("method:"+method)
                return RestApiModel(method, joinPath(baseUrl,url), module)
            }
            return null!!
        }

        fun joinPath( baseurl:String,url:String) :String {

            if(baseurl.isEmpty()) return url?:"";
            if(url.isEmpty()) return baseurl?:"";
            if(baseurl.endsWith("/") && url.startsWith("/")) {
                return baseurl+url.substring(1)
            }else if(!baseurl.endsWith("/") && !url.startsWith("/")) {
                return baseurl+"/"+url
            }else {
                return baseurl+url
            }
        }
    }
}