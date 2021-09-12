package com.design.method.factory.secondKill.util;

import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.secondKill.dto.SeckillProduct;
import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.lang.StringUtils;
import java.util.List;
import static com.design.method.api.errordict.ErrorMsgMap.*;

public class AuthUtil {
    public static RateLimiter limiter = RateLimiter.create(2);

    public static void checkAgreement(String filed, String errorMessg) throws DesignMethodFactoryException {
        if (StringUtils.isBlank(filed)) {
            throw new DesignMethodFactoryException(REQUEST_ERROR_PARAM,errorMessg);
        }
    }

    public static void checkRqeust(Object object, String errorMessg) throws DesignMethodFactoryException {
        if (object == null) {
            throw new DesignMethodFactoryException(REQUEST_ERROR_PARAM,errorMessg);
        }
    }

    public static void checkLimit(String message) throws DesignMethodFactoryException{
       if (!limiter.tryAcquire()) {
           throw new DesignMethodFactoryException(SYSTEM_MAXTHREAD_WARN, message);
       };
    }


    public static  void checkProuductId(String id, List<SeckillProduct> list, String message) throws DesignMethodFactoryException {
      list.stream()
                .filter(row -> StringUtils.equals(id,String.valueOf(row.getProductId())))
                .findAny()
                .orElseThrow(()->{
                    return new DesignMethodFactoryException(PRODUCT_NOT_EXIT,message);
                });
    }
}
