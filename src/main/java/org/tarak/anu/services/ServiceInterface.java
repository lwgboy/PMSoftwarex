package org.tarak.anu.services;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface ServiceInterface<T, ID extends Serializable> {
    List<T> findAll();

    T saveAndFlush(T var1);

    void delete(ID var1);

    T getOne(ID var1);

    T findOne(ID var1);
}
