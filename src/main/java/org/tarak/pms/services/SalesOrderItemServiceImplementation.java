package org.tarak.pms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.SalesOrderItem;
import org.tarak.pms.repositories.SalesOrderItemRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
public class SalesOrderItemServiceImplementation implements SalesOrderItemService
{
    @Autowired
    private SalesOrderItemRepository repository;

    @Override
    public List<SalesOrderItem> findAll() {
        return repository.findAll();
    }

    @Override
    public SalesOrderItem saveAndFlush(SalesOrderItem productLineItem) {
        return repository.saveAndFlush(productLineItem);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public SalesOrderItem findOne(Integer id) {
        return repository.findOne(id);
    }

	@Override
	public SalesOrderItem findBySalesOrderIdAndFinYearAndSrNo(int salesOrderItemId, String finYear,int srNo) {
		return repository.findBySalesOrderIdAndFinYearAndSrNo(salesOrderItemId, finYear,srNo);
	}

	@Override
	public void deleteBySalesOrderIdAndFinYear(int salesOrderId, String finYear) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SalesOrderItem> findByFinYearAndForwardOrder(String finYear, boolean forwardOrderItem) {
		// TODO Auto-generated method stub
		return null;
	}
 }
