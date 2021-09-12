package com.design.method.factory.secondKill.cache;

import com.design.method.api.errordict.DesignMethodFactoryException;
import com.design.method.factory.secondKill.cache.mysql.MysqlCache;
import com.design.method.factory.secondKill.cache.redis.RedisCache;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.design.method.api.errordict.ErrorMsgMap.PRODUCT_NOT_EXIT;

@Service("secCacheImpl")
public class SecCacheImpl<T> implements SecCache<T>{

    @Autowired
    private MysqlCache<T> mysqlCache;

    @Autowired
    private RedisCache<T> redisCache;


    private static final String PAY_ORDER_FIX = "PAY_ORDER=";


    @Override
    public void update(String key,T t) {
         insert(key,t);
    }

    @Override
    public void delete(String id, Class<T> type) {

    }

    @Override
    public List<T> select(String id, Class<T> type) throws DesignMethodFactoryException {
       if (id.startsWith(PAY_ORDER_FIX)) {
           return getFromRedis(id, type);
       }

        List<T>  result = redisCache.select(id, type);
      if (result == null || result.size() <= 0) {
          result =  mysqlCache.select(id.split("=")[1], type);
      } else {
          return result;
      }

      if (result == null || result.size() <= 0) {
          throw new DesignMethodFactoryException(PRODUCT_NOT_EXIT,"该商品不参与抢购");
      } else {
          insert(id,result.get(0));
      }
      return result;
    }

    @Override
    public void insert(String key,T t) {
        redisCache.insert(key, t);

    }

    @Override
    public RLock getLock(String keyLock) {
        return redisCache.getLock(keyLock);
    }

    private List getFromRedis(String key, Class<T> type) {
        return  redisCache.select(key, type);
    }



}
