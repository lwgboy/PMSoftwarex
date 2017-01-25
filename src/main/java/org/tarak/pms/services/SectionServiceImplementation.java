package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Section;
import org.tarak.pms.repositories.SectionRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class SectionServiceImplementation implements ServiceInterface<Section, Integer>
{
    @Autowired
    private SectionRepository repository;

    @Override
    public List<Section> findAll() {
        return repository.findAll();
    }

    @Override
    public Section saveAndFlush(Section section) {
        return repository.saveAndFlush(section);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Section findOne(Integer id) {
        return repository.findOne(id);
    }
}
