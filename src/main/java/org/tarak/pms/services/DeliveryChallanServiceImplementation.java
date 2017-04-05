package org.tarak.pms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.DeliveryChallan;
import org.tarak.pms.models.SalesOrder;
import org.tarak.pms.models.Variant;
import org.tarak.pms.repositories.DeliveryChallanRepository;
import org.tarak.pms.utils.DeliveryChallanUtils;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
@Transactional
public class DeliveryChallanServiceImplementation implements DeliveryChallanService
{
    @Autowired
    private DeliveryChallanRepository repository;

    @Override
    public List<DeliveryChallan> findAll() {
        return repository.findAll();
    }

    @Override
    public DeliveryChallan saveAndFlush(DeliveryChallan deliveryChallan) {
        return repository.saveAndFlush(deliveryChallan);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public DeliveryChallan findOne(Integer id) {
        return repository.findOne(id);
    }
    
	@Override
	public DeliveryChallan findByDeliveryChallanIdAndFinYear(int id, String finYear) {
		return repository.findByDeliveryChallanIdAndFinYear(id, finYear);
	}

	@Override
	public void deleteByDeliveryChallanIdAndFinYear(int deliveryChallanId, String finYear) {
		repository.deleteByDeliveryChallanIdAndFinYear(deliveryChallanId, finYear);
	}
    @Override
	public void saveAndProcessPO(DeliveryChallan deliveryChallan, DeliveryChallanService deliveryChallanService, SalesOrderService SalesOrderService,ServiceInterface<Variant,Integer> variantService)
	{
		deliveryChallanService.saveAndFlush(deliveryChallan);
		if(deliveryChallan.getDeliveryChallanItems()!=null && !deliveryChallan.getDeliveryChallanItems().isEmpty())
    	{
			SalesOrder po=SalesOrderService.findBySalesOrderIdAndFinYear(deliveryChallan.getSalesOrder().getSalesOrderId(), deliveryChallan.getFinYear());
			DeliveryChallanUtils.setProcessed(deliveryChallan, true, po);
			if(!po.getSalesOrderItems().stream().filter(i->!i.isProcessed()).findAny().isPresent())
			{
				po.setProcessed(true);
			}
			else
			{
				po.setProcessed(false);
			}
			SalesOrderService.saveAndFlush(po);
    	}	
	}
 }
