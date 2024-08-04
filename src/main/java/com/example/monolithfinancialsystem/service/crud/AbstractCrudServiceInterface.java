package com.example.monolithfinancialsystem.service.crud;

import org.springframework.data.domain.Example;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface AbstractCrudServiceInterface<T, ID>  {

    T getByIdOrThrow(@NotNull ID id);

    T getById(@NotNull ID id);

    Optional<T> findById(@NotNull ID id);

    void delete(@NotNull T entity);

    void deleteAll(@NotNull Iterable<? extends T> entities);

    T save(@NotNull @Valid T entity);

    List<T> saveAll(@NotNull @Valid Iterable<T> entities);

    List<T> findAllById(@NotNull Iterable<ID> ids);

    Optional<T> findByExample(@NotNull Example<T> example);

    T getByExampleOrThrow(@NotNull Example<T> example);

    List<T> findAll();
}
