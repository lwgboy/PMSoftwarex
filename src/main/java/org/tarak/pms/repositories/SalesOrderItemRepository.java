package org.tarak.pms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarak.pms.models.SalesOrder;
import org.tarak.pms.models.SalesOrderItem;

/**
 * Created by Tarak on 12/7/2016.
 */
@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Integer> {

	public SalesOrderItem findBySalesOrderIdAndFinYearAndSrNo(int salesOrderId, String finYear,int srNo);
	public void deleteBySalesOrderIdAndFinYear(int salesOrderId, String finYear);
	public List<SalesOrder> findByFinYearAndForwardOrder(String finYear,boolean forwardOrder);

}