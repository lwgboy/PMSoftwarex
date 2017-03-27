package org.tarak.pms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.PickList;
import org.tarak.pms.repositories.PickListRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
@Transactional
public class PickListServiceImplementation implements PickListService
{
    @Autowired
    private PickListRepository repository;

    @Override
    public List<PickList> findAll() {
        return repository.findAll();
    }

    @Override
    public PickList saveAndFlush(PickList pickList) {
        return repository.saveAndFlush(pickList);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public PickList findOne(Integer id) {
        return repository.findOne(id);
    }

	@Override
	public PickList findByPickListIdAndFinYear(int pickListId, String finYear) {
		return repository.findByPickListIdAndFinYear(pickListId, finYear);
	}

	@Override
	public void deleteByPickListIdAndFinYear(int pickListId, String finYear) {
        repository.deleteByPickListIdAndFinYear(pickListId, finYear);		
	}
 }
