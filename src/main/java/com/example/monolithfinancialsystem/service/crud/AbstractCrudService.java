package com.example.monolithfinancialsystem.service.crud;

import lombok.Setter;
import lombok.experimental.Delegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@Validated
@Transactional
public abstract class AbstractCrudService<T, ID> implements AbstractCrudServiceInterface<T, ID> {

    @Delegate(excludes = ExcludedMethods.class)
    @Setter(onMethod_={@Autowired})
    protected JpaRepository<T, ID> jpaRepository;

    public T getByIdOrThrow(ID id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found %s by id = %s.", getEntityTypeName(), id)));
    }

    public T save(T entity) {
        return jpaRepository.save(entity);
    }

    public List<T> saveAll(Iterable<T> entities) {
        return jpaRepository.saveAll(entities);
    }

    public Optional<T> findByExample(Example<T> example) {
        return jpaRepository.findOne(example);
    }

    public T getByExampleOrThrow(Example<T> example) {
        return jpaRepository.findOne(example)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Not found %s by example.", getEntityTypeName())));
    }

    @SuppressWarnings("unchecked")
    private String getEntityTypeName() {
        return ((Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
    }

    private interface ExcludedMethods<T> {
        <S extends T> S save(S entity);
        <S extends T> Iterable<S> saveAll(Iterable<S> entities);
        <S extends T> Optional<S> findOne(Example<S> example);
    }
}
