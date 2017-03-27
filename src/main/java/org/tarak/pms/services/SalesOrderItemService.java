package org.tarak.pms.services;


import java.util.List;

import org.tarak.pms.models.SalesOrderItem;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface SalesOrderItemService extends ServiceInterface<SalesOrderItem, Integer> {
	public SalesOrderItem findBySalesOrderIdAndFinYearAndSrNo(int salesOrderItemId,String finYear,int srNo);
	public void deleteBySalesOrderIdAndFinYear(int salesOrderItemId, String finYear);
	public List<SalesOrderItem> findByFinYearAndForwardOrder(String finYear,boolean forwardOrderItem);
}
