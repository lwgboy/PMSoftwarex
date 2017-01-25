package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Tag;
import org.tarak.pms.repositories.TagRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class TagServiceImplementation implements ServiceInterface<Tag, Integer>
{
    @Autowired
    private TagRepository repository;

    @Override
    public List<Tag> findAll() {
        return repository.findAll();
    }

    @Override
    public Tag saveAndFlush(Tag tag) {
        return repository.saveAndFlush(tag);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Tag findOne(Integer id) {
        return repository.findOne(id);
    }
}
