package com.design.method.factory.secondKill.cache.mysql;

import com.design.method.factory.secondKill.cache.SecCache;
import com.design.method.factory.secondKill.cache.mysql.service.PayOrderServiceImpl;
import com.design.method.factory.secondKill.cache.mysql.service.SeckillServiceImpl;
import com.design.method.factory.secondKill.dto.PayOrder;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 数据库层面的实现--mysql
 * @param <T>
 */
@Component("mysqlCache")
public class MysqlCache<T>  implements SecCache<T> {

    @Autowired
    private SeckillServiceImpl seckillService;

    @Autowired
    private PayOrderServiceImpl  payOrderService;

    @Override
    public void update(String key,T t) {
        payOrderService.update((PayOrder)t);
    }

    @Override
    public void delete(String id, Class<T> type) {
    }

    @Override
    public List<T> select(String productId, Class<T> type) {
        return (List<T>)seckillService.getList(productId);
    }


    @Override
    public void insert(String key,T t) {
        payOrderService.insert((PayOrder) t);
    }

    @Override
    public RLock getLock(String keyLock) {

        //todo 可以通过数据库来实现获取锁的过程
        return null;
    }

}
