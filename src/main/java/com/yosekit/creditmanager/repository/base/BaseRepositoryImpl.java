package com.yosekit.creditmanager.repository.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class BaseRepositoryImpl<T, ID> implements BaseRepository<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public BaseRepositoryImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public T findById(ID id) {
        return entityManager.find(persistentClass, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager
                .createQuery("SELECT e FROM " + persistentClass.getSimpleName() + " e", persistentClass)
                .getResultList();
    }

    @Override
    public List<T> findByColumns(Map<String, Object> criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(persistentClass);
        Root<T> root = query.from(persistentClass);
        Map<String, Join<?, ?>> joins = new HashMap<>();

        List<Predicate> predicates = new ArrayList<>();

        for (Map.Entry<String, Object> entry : criteria.entrySet()) {
            String column = entry.getKey();
            Object value = entry.getValue();

            if (column.contains(".")) {
                String[] parts = column.split("\\.");
                Join<?, ?> join = getOrCreateJoin(root, joins, parts[0]);
                predicates.add(buildPredicate(builder, join.get(parts[1]), value));
            } else {
                predicates.add(buildPredicate(builder, root.get(column), value));
            }
        }

        query.where(builder.and(predicates.toArray(Predicate[]::new)));
        return entityManager.createQuery(query).getResultList();
    }

    private Predicate buildPredicate(CriteriaBuilder builder, Path<?> path, Object value) {
        if (value instanceof String) {
            String searchString = "%" + ((String) value).toLowerCase() + "%";  // Добавляем % для поиска подстроки
            return builder.like(builder.lower(path.as(String.class)), searchString);
        } else {
            return builder.equal(path, value);
        }
    }

    private Join<?, ?> getOrCreateJoin(Root<T> root, Map<String, Join<?, ?>> joins, String entityField) {
        return joins.computeIfAbsent(entityField, root::join);
    }

    @Override
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity)
                ? entity
                : entityManager.merge(entity));
    }
}
