package org.tarak.anu.services;

import org.tarak.anu.models.Category;

import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface CategoryService
{
    List<Category> findAll();
    Category findOne(Long id);
    Category create(Category category);
    Category edit(Category category);
    void deleteById(Long id);
}
