package com.qst.extension.restapifinderidea.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.util.PsiTreeUtil;
import com.qst.extension.restapifinderidea.model.RestApiModel;
import com.qst.extension.restapifinderidea.model.ParameterModel;
import okhttp3.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class RestApiUtil {
    private static final String ANN_PATH_VARIABLE = "org.springframework.web.bind.annotation.PathVariable";
    public static String ANN_CONTROLLER = "org.springframework.web.bind.annotation.RestController";
    public static String ANN_REQUEST = "org.springframework.web.bind.annotation.RequestMapping";
    public static String ANN_POST = "org.springframework.web.bind.annotation.PostMapping";
    public static String ANN_GET = "org.springframework.web.bind.annotation.GetMapping";
    public static String ANN_PUT = "org.springframework.web.bind.annotation.PutMapping";
    public static String ANN_DELETE = "org.springframework.web.bind.annotation.DeleteMapping";
    public static String ANN_PATCH = "org.springframework.web.bind.annotation.PatchMapping";
    public static String ANN_REQUEST_PARAM = "org.springframework.web.bind.annotation.RequestParam";
    public static String ANN_REQUEST_BODY = "org.springframework.web.bind.annotation.RequestBody";
    public static String ANN_REQUEST_HEADER = "org.springframework.web.bind.annotation.RequestHeader";
    public static String ANN_REQUEST_COOKIE = "org.springframework.web.bind.annotation.CookieValue";
    public static String ANN_REQUEST_PATH = "org.springframework.web.bind.annotation.PathVariable";
    public static String ANN_REQUEST_PART = "org.springframework.web.bind.annotation.RequestPart";
    public static String ANN_REQUEST_ATTRIBUTE = "org.springframework.web.bind.annotation.RequestAttribute";
    public static String ANN_REQUEST_METHOD = "org.springframework.web.bind.annotation.RequestMethod";
    public static String ANN_REQUEST_METHOD_POST = "org.springframework.web.bind.annotation.RequestMethod.POST";
    public static String ANN_REQUEST_METHOD_GET = "org.springframework.web.bind.annotation.RequestMethod.GET";
    public static String ANN_REQUEST_METHOD_PUT = "org.springframework.web.bind.annotation.RequestMethod.PUT";
    public static String ANN_REQUEST_METHOD_DELETE = "org.springframework.web.bind.annotation.RequestMethod.DELETE";
    public static String ANN_REQUEST_METHOD_PATCH = "org.springframework.web.bind.annotation.RequestMethod.PATCH";
    public static String ANN_REQUEST_METHOD_HEAD = "org.springframework.web.bind.annotation.RequestMethod.HEAD";
    public static String ANN_REQUEST_METHOD_OPTIONS = "org.springframework.web.bind.annotation.RequestMethod.OPTIONS";

    public static String Method_GET = "GET";
    public static String Method_POST = "POST";
    public static String Method_PUT = "PUT";
    public static String Method_DELETE = "DELETE";
    public static String Method_PATCH = "PATCH";
    public static String Method_HEAD = "HEAD";
    public static String Method_OPTIONS = "OPTIONS";

    public static List<RestApiModel> parse(PsiClass psiClass) {
        if(isController(psiClass)) {
            return parseController(psiClass);
        }else {
            System.out.println("not controller");
        }

        return new ArrayList<>();
    }
    public static boolean isController(PsiClass psiClass) {
       return psiClass.hasAnnotation(ANN_CONTROLLER);
    }

    public static  List<RestApiModel> parseController(PsiClass psiClass) {
        List<RestApiModel> models = new ArrayList<>();
        PsiAnnotation ann = psiClass.getAnnotation(ANN_REQUEST);
        String baseUrl = parseUrl(ann,ANN_REQUEST);
//        System.out.println(baseUrl);
        PsiMethod[] methods = psiClass.getMethods();
        for(PsiMethod method : methods) {
          RestApiModel m =  parseMethod(method,baseUrl);
          if(m!=null) {
              models.add(m);
          }
        }
        return models;
    }

    public static RestApiModel parseMethod(PsiMethod psiMethod,String baseUrl) {
        if(psiMethod.hasAnnotation(ANN_POST)) {
            return parsePost(psiMethod,baseUrl);
        }
        if(psiMethod.hasAnnotation(ANN_GET)) {
            return parseGet(psiMethod,baseUrl);
        }
        if(psiMethod.hasAnnotation(ANN_PUT)) {
            return parsePut(psiMethod,baseUrl);
        }
        if(psiMethod.hasAnnotation(ANN_DELETE)) {
            return parseDelete(psiMethod,baseUrl);
        }
        if(psiMethod.hasAnnotation(ANN_PATCH)) {
            return parsePatch(psiMethod,baseUrl);
        }
        if(psiMethod.hasAnnotation(ANN_REQUEST)) {
            return parseRequest(psiMethod,baseUrl);
        }
        return null;

    }
    public static Boolean isRestElement(PsiElement element) {

        return false;
    }
    public static RestApiModel parsePost(PsiMethod psiMethod, String baseUrl) {
        RestApiModel model = new RestApiModel();
        String url = parseUrl(psiMethod.getAnnotation(ANN_POST),ANN_POST);
        model.setPath(joinPath(baseUrl,url));
        String method = Method_POST;
        model.setMethod(method);
        String descriptions = parseDescriptions(psiMethod);
        model.setDescription(descriptions);
        List<ParameterModel> parameters = parseParameters(psiMethod);
        model.setParameters(parameters);
        return model;
    }
    public static RestApiModel parseGet(PsiMethod psiMethod,String baseUrl) {
        RestApiModel model = new RestApiModel();

        String url = parseUrl(psiMethod.getAnnotation(ANN_GET),ANN_GET);
        model.setPath(joinPath(baseUrl,url));
        String method = Method_GET;
        model.setMethod(method);
        String descriptions = parseDescriptions(psiMethod);
        model.setDescription(descriptions);
        List<ParameterModel> parameters = parseParameters(psiMethod);
        model.setParameters(parameters);
        return model;
    }
    public static RestApiModel parsePut(PsiMethod psiMethod,String baseUrl) {
        RestApiModel model = new RestApiModel();
        String url = parseUrl(psiMethod.getAnnotation(ANN_PUT),ANN_PUT);
        model.setPath(joinPath(baseUrl,url));
        model.setMethod(Method_PUT);
        String descriptions = parseDescriptions(psiMethod);
        model.setDescription(descriptions);
        List<ParameterModel> parameters = parseParameters(psiMethod);
        model.setParameters(parameters);
        return model;
    }
    public static RestApiModel parseDelete(PsiMethod psiMethod,String baseUrl) {
        RestApiModel model = new RestApiModel();
        String url = parseUrl(psiMethod.getAnnotation(ANN_DELETE),ANN_DELETE);
        model.setPath(joinPath(baseUrl,url));
        model.setMethod(Method_DELETE);
        String descriptions = parseDescriptions(psiMethod);
        model.setDescription(descriptions);
        List<ParameterModel> parameters = parseParameters(psiMethod);
        model.setParameters(parameters);
        return model;
    }
    public static RestApiModel parsePatch(PsiMethod psiMethod,String baseUrl) {
        RestApiModel model = new RestApiModel();
        String url = parseUrl(psiMethod.getAnnotation(ANN_PATCH),ANN_PATCH);
        model.setPath(joinPath(baseUrl,url));
        model.setMethod(Method_PATCH);
        String descriptions = parseDescriptions(psiMethod);
        model.setDescription(descriptions);
        List<ParameterModel> parameters = parseParameters(psiMethod);
        model.setParameters(parameters);
        return model;
    }
    public static RestApiModel parseRequest(PsiMethod psiMethod,String baseUrl) {
        RestApiModel model = new RestApiModel();
        String url = parseUrl(psiMethod.getAnnotation(ANN_REQUEST),ANN_REQUEST);
        model.setPath(joinPath(baseUrl,url));
        String method = parseRequestMethod(psiMethod.getAnnotation(ANN_REQUEST));
        model.setMethod(method);
        String descriptions = parseDescriptions(psiMethod);
        model.setDescription(descriptions);
        List<ParameterModel> parameters = parseParameters(psiMethod);
        model.setParameters(parameters);
        return model;
    }

    private static String parseRequestMethod(PsiAnnotation annotation) {
        if(annotation.hasQualifiedName(ANN_REQUEST)) {
            PsiAnnotationMemberValue methodvalue = annotation.findAttributeValue("method");
            return methodvalue.getLastChild().getText();
        }else {
            System.out.println("not found"+ ANN_REQUEST);
        }
        return Method_GET;
    }

    /**
     * 解析url
     * @param psiAnnotation
     * @param qualifier
     * @return
     */
    public static String parseUrl(PsiAnnotation psiAnnotation, String qualifier) {
        String text = psiAnnotation.getText();
        if(psiAnnotation.hasQualifiedName(qualifier)) {
            PsiAnnotationMemberValue value= psiAnnotation.findAttributeValue("value");
            if(value != null && value.getLastChild() != null) {
                text = value.getLastChild().getText();
            }
            PsiAnnotationMemberValue name= psiAnnotation.findAttributeValue("name");
            if(name != null && name.getLastChild() != null) {
                text = name.getLastChild().getText();
            }
            String[] split = text.split("\"");
            if(split.length > 1) {
                return split[1];
            }
        }else {
            System.out.println("not found"+ qualifier);
        }
        return "";
    }

    /**
     * 拼接路径
     * @param root
     * @param path
     * @return
     */
    public static String joinPath(String root,String path) {
        if(root.endsWith("/") && path.startsWith("/")) {
            return root + path.substring(1);
        }
        if(!root.endsWith("/") && !path.startsWith("/")) {
            return root + "/" + path;
        }
        return root + path ;
    }

    /**
     * 解析描述
     * @param psiMethod
     * @return
     */
    public static String parseDescriptions(PsiMethod psiMethod) {
        PsiDocComment docComment = psiMethod.getDocComment();
        if (docComment != null) {
            PsiElement[] elements = docComment.getDescriptionElements();
            if (elements.length > 0) {
                return elements[0].getText();
            }
        }
        return "";
    }

    /**
     * 解析参数
     * @param method
     * @return
     */
    public static List<ParameterModel> parseParameters(PsiMethod method){
        List<ParameterModel> list = new ArrayList<>();
        PsiParameterList parameterList = method.getParameterList();
        // 解析doc注释
        PsiDocComment docs =  method.getDocComment();
        Map<String,String> paramDocMap = new HashMap<>();
        PsiDocTag[] docTags = docs.getTags();
        for (PsiDocTag docTag : docTags) {
            if("param".equals(docTag.getName())) {
                List<PsiElement> dataElements = Arrays.stream(docTag.getDataElements()).collect(Collectors.toList());
                if (!dataElements.isEmpty()) {
                    dataElements.remove(0);
                    String description = String.join("", dataElements.stream().map(PsiElement::getText).collect(Collectors.toList()));
                    if(docTag.getValueElement()!= null) {
                        paramDocMap.put(docTag.getValueElement().getText(), description);
                    }
                }
            }
        }

        for (PsiParameter parameter : parameterList.getParameters()) {
            ParameterModel model = new ParameterModel();
            PsiTypeElement typeElement = parameter.getTypeElement();
            PsiType type = typeElement.getType();
            String typeName = type.getPresentableText();
            model.setName(parameter.getName());
            model.setSchema("{type:"+ typeName +"}");

            // 获取是否必选
            if(parameter.hasAnnotation(ANN_REQUEST_PARAM)) {
                PsiAnnotation ann = parameter.getAnnotation(ANN_REQUEST_PARAM);
                model.setIn(ParameterModel.PARAM_IN_QUERY);
                String required = ann.findAttributeValue("required").getText();
                model.setRequired("true".equals(required)?true:false);
            }
            if(parameter.hasAnnotation(ANN_PATH_VARIABLE)) {
                model.setIn(ParameterModel.PARAM_IN_PATH);
                model.setRequired(true);
            }

            if(paramDocMap.containsKey(parameter.getName())) {
                model.setDescription(paramDocMap.get(parameter.getName()));
            }
            list.add(model);
        }
        return list;
    }

    public static void parseFile(PsiFile psiFile) {
        DataCenter.reset();
        System.out.println("parseFile::"+psiFile.getName());
        PsiTreeUtil.findChildrenOfType(psiFile, PsiClass.class).forEach(psiClass -> {
            List<RestApiModel> models = RestApiUtil.parse(psiClass);
            models.forEach(model -> {
                DataCenter.add(model);
            });
        });
    }

    public static String parseUsage(RestApiModel model) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        Map<String,String> params = new HashMap<>();
        params.put("method",model.getMethod());
        params.put("url",model.getPath());
        params.put("moduleName",model.getModuleName());
        String body = JSON.toJSONString(params);
        System.out.println(body);
        RequestBody requestBody =  RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
        Request request = new Request.Builder()
                .url("http://localhost:3000/apimanage/api_call_infomations")
                .post(requestBody)
                .build();
        try {

            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            System.out.println(result);
            RestUsageResponse res = JSON.parseObject(result, RestUsageResponse.class);
            if(res.getCode() == 200) {
                List<RestApiRef> refs = res.getData();
                if(refs.size() == 0) return "[未查到调用信息]";
                StringBuilder sb = new StringBuilder();
                 refs.stream().map(ref -> {
                    return "["+ ref.getClientName() +"调用"+ ref.getFiles().split(",").length + "次]";
                }).forEach(usage -> {
                    sb.append(usage);
                });
                 return sb.toString();
            }
            return "requestError";
        }catch (IOException e) {
            e.printStackTrace();
            return "requestError";
        }
    }

}
