package com.shoescompany.application.services.interfaces;

import java.util.List;

public interface IBaseService<T, R> {

    List<T> findAll();
    T findById(Long id) throws Exception;
    T save(R r) throws Exception;
    void update(Long id, R r) throws Exception;

    void delete(Long id) throws Exception;

    void activate(Long id) throws Exception;

}
