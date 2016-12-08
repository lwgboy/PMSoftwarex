package org.tarak.anu.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.anu.models.Category;
import org.tarak.anu.repositories.CategoryRepository;
import java.util.List;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class CategoryServiceImplementation implements ServiceInterface<Category, Long>
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
    public Category getOne(Long id) {
        return repository.getOne(id);
    }
    
    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public Category findOne(Long id) {
        return repository.findOne(id);
    }
}
