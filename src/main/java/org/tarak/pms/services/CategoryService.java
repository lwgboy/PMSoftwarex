package org.tarak.pms.services;

import java.util.List;

import org.tarak.pms.models.Category;

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
