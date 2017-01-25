package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Division;
import org.tarak.pms.repositories.DivisionRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class DivisionServiceImplementation implements ServiceInterface<Division, Integer>
{
    @Autowired
    private DivisionRepository repository;

    @Override
    public List<Division> findAll() {
        return repository.findAll();
    }

    @Override
    public Division saveAndFlush(Division division) {
        return repository.saveAndFlush(division);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Division findOne(Integer id) {
        return repository.findOne(id);
    }
}
