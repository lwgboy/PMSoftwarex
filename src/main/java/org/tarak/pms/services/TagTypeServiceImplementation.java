package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.TagType;
import org.tarak.pms.repositories.TagTypeRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class TagTypeServiceImplementation implements ServiceInterface<TagType, Integer>
{
    @Autowired
    private TagTypeRepository repository;

    @Override
    public List<TagType> findAll() {
        return repository.findAll();
    }

    @Override
    public TagType saveAndFlush(TagType tagType) {
        return repository.saveAndFlush(tagType);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public TagType findOne(Integer id) {
        return repository.findOne(id);
    }
}
