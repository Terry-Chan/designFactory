package com.design.method.factory.annotation.impl;

import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.annotation.PreCheck;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;

import static com.design.method.api.errordict.ErrorMsgMap.INDEX_CREATE_ERROR;
import static com.design.method.api.errordict.ErrorMsgMap.REQUEST_INDEX_ERROR;

@Aspect
@Component
public class PreCheckImpl {

    @Autowired
    private RestHighLevelClient client;


    @Pointcut("@annotation(com.design.method.factory.annotation.PreCheck)")
    private void PreCheck(){

    }

    //在目标方法执行前调用
    @Before("PreCheck()")
    public void begin(JoinPoint joinPoint) throws DesignMethodFactoryException{
        try {
            PreCheck preCheck = getDeclaredAnnotation(joinPoint);
            createIndex(client,preCheck.indexName());
        }catch (Exception e) {
            throw new DesignMethodFactoryException(REQUEST_INDEX_ERROR);
        }

    }

    public  boolean checkIndexExits(RestHighLevelClient client, String indexName) throws DesignMethodFactoryException {

        GetIndexRequest gir = new GetIndexRequest(indexName);
        try {
            return client.indices().exists(gir, RequestOptions.DEFAULT);
        }catch (IOException e) {
            throw new DesignMethodFactoryException(REQUEST_INDEX_ERROR);
        }
    }


    public  void createIndex(RestHighLevelClient client, String indexName) throws DesignMethodFactoryException{

        if (checkIndexExits(client,indexName)){
            return;
        } else {
            // 创建索引请求
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            try {
                // 执行创建索引请求
                CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
                if (!response.isAcknowledged()) {
                    throw new DesignMethodFactoryException(INDEX_CREATE_ERROR, "创建索引失败");
                };
            }catch (IOException e) {
                throw  new DesignMethodFactoryException(INDEX_CREATE_ERROR);
            }
        }
    }

    public PreCheck getDeclaredAnnotation(JoinPoint joinPoint)throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        PreCheck annotation = objMethod.getDeclaredAnnotation(PreCheck.class);
        // 返回
        return annotation;
    }


}
