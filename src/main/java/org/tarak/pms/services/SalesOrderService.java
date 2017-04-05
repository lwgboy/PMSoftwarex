package org.tarak.pms.services;


import java.util.List;

import org.tarak.pms.models.SalesOrder;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface SalesOrderService extends ServiceInterface<SalesOrder, Integer> {
	public SalesOrder findBySalesOrderIdAndFinYear(int salesOrderId,String finYear);
	public void deleteBySalesOrderIdAndFinYear(int salesOrderId, String finYear);
	public List<SalesOrder> findByFinYearAndForwardOrder(String finYear,boolean forwardOrder);
	public List<SalesOrder> findByFinYearAndStageIn(String finYear,List<String> stage);
}
