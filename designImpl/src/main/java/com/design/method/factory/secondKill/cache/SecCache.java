package com.design.method.factory.secondKill.cache;

import com.design.method.api.errordict.DesignMethodFactoryException;
import org.redisson.api.RLock;

import java.util.List;

public interface SecCache<T> {

    public void update(String key,T t);

    public void delete(String id, Class<T> type);

    public List<T>  select(String id, Class<T> type) throws DesignMethodFactoryException;

    public void  insert(String key,T t);

    public RLock getLock(String keyLock);


}
