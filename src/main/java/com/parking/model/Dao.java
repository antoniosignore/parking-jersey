package com.parking.model;

import com.parking.entity.Entity;

import java.util.List;


public interface Dao<T extends Entity, I> {

    List<T> findAll();


    T find(I id);


    T save(T entity);


    void delete(I id);

}