package org.tarak.pms.services;


import org.tarak.pms.models.DeliveryChallan;
import org.tarak.pms.models.Variant;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface DeliveryChallanService extends ServiceInterface<DeliveryChallan, Integer> 
{
	public DeliveryChallan findByDeliveryChallanIdAndFinYear(int deliveryChallanId,String finYear);
	public void deleteByDeliveryChallanIdAndFinYear(int deliveryChallanId, String finYear);
	public void saveAndProcessPO(DeliveryChallan deliveryChallan, DeliveryChallanService deliveryChallanService, SalesOrderService SalesOrderService,ServiceInterface<Variant,Integer> variantService);
}
