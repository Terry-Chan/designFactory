package com.design.method.factory.elasticsearch.util;


import com.alibaba.druid.support.json.JSONUtils;
import com.design.method.api.errordict.DesignMethodFactoryException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mysql.cj.xdevapi.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;

import static com.design.method.api.errordict.ErrorMsgMap.*;

@Slf4j
public class ElasticSerachUtil {

    private static final Gson gson = new Gson();

    private static final Integer SUCCESS_CODE = 200;

    private static final Integer CREATED_CODE = 201;

    private  ElasticSerachUtil(){
        throw new IllegalArgumentException();
    }

    public static boolean checkIndexExits(RestHighLevelClient client, String indexName) throws DesignMethodFactoryException {

        GetIndexRequest  gir = new GetIndexRequest(indexName);
        try {
              return client.indices().exists(gir, RequestOptions.DEFAULT);
        }catch (IOException e) {
            throw new DesignMethodFactoryException(REQUEST_INDEX_ERROR);
        }
    }

    public static void createIndex(RestHighLevelClient client, String indexName) throws DesignMethodFactoryException{

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

    //删除索引
    public static void deleteIndex(RestHighLevelClient client, String indexName) throws DesignMethodFactoryException{

        if (!checkIndexExits(client,indexName)){
            throw new DesignMethodFactoryException(INDEX_NOT_EXISTS);
        } else {

            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            try {
                // 执行创建索引请求
                AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
                if (!response.isAcknowledged()) {
                    throw new DesignMethodFactoryException(DELETEE_INDEX_FAIL);
                };
            }catch (IOException e) {
                throw  new DesignMethodFactoryException(DELETEE_INDEX_ERROR);
            }
        }
    }

    //查询
    public static <T> List<T> getResult(RestHighLevelClient client, String indexName, Map<String,Object> map, Class<T> tClass) throws DesignMethodFactoryException{
        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types("doc");
        // 构建搜索条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        map.forEach((key,value)->{
            boolQueryBuilder.should(QueryBuilders.matchQuery(key,value));
        });
        builder.query(boolQueryBuilder);
        searchRequest.source(builder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            List<T> result  = new ArrayList<>();
            Arrays.stream(searchResponse.getHits().getHits()).forEach(hit->{
                result.add(gson.fromJson(hit.getSourceAsString(),tClass));
            });
            return result;
        }catch (IOException e) {
            throw new DesignMethodFactoryException(SELECT_DATA_ERROR);
        }

    }

    //插入数据
    public static <T> void addDocument(RestHighLevelClient client, String indexName, String id, T t) throws DesignMethodFactoryException {

        IndexRequest indexRequest = new IndexRequest(indexName,"doc",id);
        indexRequest.source(gson.toJson(t), XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            log.info("新增数据==Id="  + id + indexResponse.status().getStatus());
            int resultCode = indexResponse.status().getStatus();
            if ( !(resultCode == SUCCESS_CODE || resultCode == CREATED_CODE) ) {
                throw new DesignMethodFactoryException(INSERT_DATA_FAIL);
            };
        }catch (IOException e) {
             throw new DesignMethodFactoryException(INSERT_DATA_ERROR);
        }

    }


    //删除数据
    public static  void deleteData(RestHighLevelClient client, String indexName, String id) throws DesignMethodFactoryException {

        DeleteRequest deleteRequest = new DeleteRequest(indexName,"doc",id);
        try {
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            if (deleteResponse.status().getStatus() != SUCCESS_CODE) {
                throw new DesignMethodFactoryException(INSERT_DATA_FAIL);
            };
        }catch (IOException e) {
            throw new DesignMethodFactoryException(INSERT_DATA_ERROR);
        }

    }

    //更新数据
    public static <T> void updateDate(RestHighLevelClient client, String indexName, T t, String id) throws DesignMethodFactoryException {
        UpdateRequest updateRequest = new UpdateRequest(indexName,"doc",id);
        updateRequest.doc(gson.toJson(t),XContentType.JSON);

        try {
            UpdateResponse updateResponse = client.update(updateRequest,RequestOptions.DEFAULT);
            if (updateResponse.status().getStatus() != SUCCESS_CODE) {
                throw new DesignMethodFactoryException(UPDATE_DATE_FAIL);
            }
        } catch (IOException e) {
                throw new DesignMethodFactoryException(UPDATE_DATE_FAIL);
        }



    }


}
