package com.yosekit.creditmanager.repository.base;

import java.util.List;
import java.util.Map;

public interface BaseRepository<T, ID> {
    T findById(ID id);
    List<T> findAll();
    List<T> findAll(List<String> columns);
    List<T> findByColumns(Map<String, Object> criteria);
    void save(T entity);
    void update(T entity);
    void delete(T entity);
}
