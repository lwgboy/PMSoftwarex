package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.Variant;
import org.tarak.pms.repositories.VariantRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class VariantServiceImplementation implements VariantService
{
    @Autowired
    private VariantRepository repository;

    @Override
    public List<Variant> findAll() {
        return repository.findAll();
    }

    @Override
    public Variant saveAndFlush(Variant variant) {
        return repository.saveAndFlush(variant);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Variant findOne(Integer id) {
        return repository.findOne(id);
    }

	@Override
	public Variant findBySku(String sku) {
		return repository.findVariantBySku(sku);
	}
}
