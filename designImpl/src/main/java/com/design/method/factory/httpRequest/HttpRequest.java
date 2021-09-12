package com.design.method.factory.httpRequest;



import com.design.method.api.Response.Response;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elastic")
public class HttpRequest {

    @Autowired
    private FeignService service;

    @RequestMapping("/check_Elastic_Status")
    public Response<Object> checkElasticsearchStatus() {
      feign.Response response =  service.checkStatus();
      return new Response<>().SUCCESS(new Gson().toJson(response.body().toString()));
    }

}

