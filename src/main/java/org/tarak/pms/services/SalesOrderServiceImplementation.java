package org.tarak.pms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.SalesOrder;
import org.tarak.pms.repositories.SalesOrderRepository;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
@Transactional
public class SalesOrderServiceImplementation implements SalesOrderService
{
    @Autowired
    private SalesOrderRepository repository;

    @Override
    public List<SalesOrder> findAll() {
        return repository.findAll();
    }

    @Override
    public SalesOrder saveAndFlush(SalesOrder salesOrder) {
        return repository.saveAndFlush(salesOrder);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public SalesOrder findOne(Integer id) {
        return repository.findOne(id);
    }

	@Override
	public SalesOrder findBySalesOrderIdAndFinYear(int salesOrderId, String finYear) {
		return repository.findBySalesOrderIdAndFinYear(salesOrderId, finYear);
	}

	@Override
	public void deleteBySalesOrderIdAndFinYear(int salesOrderId, String finYear) {
        repository.deleteBySalesOrderIdAndFinYear(salesOrderId, finYear);		
	}

	@Override
	public List<SalesOrder> findByFinYearAndForwardOrder(String finYear, boolean forwardOrder) {
		return repository.findByFinYearAndForwardOrder(finYear,forwardOrder);
		
	}

	@Override
	public List<SalesOrder> findByFinYearAndStage(String finYear, String stage) {
		return repository.findByFinYearAndStage(finYear,stage);
	}
 }
