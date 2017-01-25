package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Style;
import org.tarak.pms.repositories.StyleRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class StyleServiceImplementation implements ServiceInterface<Style, Integer>
{
    @Autowired
    private StyleRepository repository;

    @Override
    public List<Style> findAll() {
        return repository.findAll();
    }

    @Override
    public Style saveAndFlush(Style style) {
        return repository.saveAndFlush(style);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Style findOne(Integer id) {
        return repository.findOne(id);
    }
}
