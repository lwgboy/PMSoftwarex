package org.tarak.pms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Category;
import org.tarak.pms.repositories.CategoryRepository;

import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class CategoryServiceImplementation implements ServiceInterface<Category, Integer>
{
    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category saveAndFlush(Category category) {
        return repository.saveAndFlush(category);
    }

    @Override
    public Category getOne(Integer id) {
        return repository.getOne(id);
    }
    
    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Category findOne(Integer id) {
        return repository.findOne(id);
    }
}
