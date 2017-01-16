package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.PurchaseReturn;
import org.tarak.pms.repositories.PurchaseReturnRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class PurchaseReturnServiceImplementation implements PurchaseReturnService
{
    @Autowired
    private PurchaseReturnRepository repository;

    @Override
    public List<PurchaseReturn> findAll() {
        return repository.findAll();
    }

    @Override
    public PurchaseReturn saveAndFlush(PurchaseReturn purchaseReturn) {
        return repository.saveAndFlush(purchaseReturn);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public PurchaseReturn findOne(Integer id) {
        return repository.findOne(id);
    }
    
	@Override
	public PurchaseReturn findByPurchaseReturnIdAndFinYear(int id, String finYear) {
		return repository.findByPurchaseReturnIdAndFinYear(id, finYear);
	}

	@Override
	public void deleteByPurchaseReturnIdAndFinYear(int purchaseReturnId, String finYear) {
	    repository.deleteByPurchaseReturnIdAndFinYear(purchaseReturnId, finYear);
	}
    
 }
