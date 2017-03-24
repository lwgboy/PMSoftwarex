package org.tarak.pms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarak.pms.models.SalesOrder;

/**
 * Created by Tarak on 12/7/2016.
 */
@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
	public SalesOrder findBySalesOrderIdAndFinYear(int salesOrderId, String finYear);
	public void deleteBySalesOrderIdAndFinYear(int salesOrderId, String finYear);
	public List<SalesOrder> findByFinYearAndForwardOrder(String finYear,boolean forwardOrder);
	
}