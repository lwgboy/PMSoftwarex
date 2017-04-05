package org.tarak.pms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarak.pms.models.DeliveryChallan;

/**
 * Created by Tarak on 12/7/2016.
 */
@Repository
public interface DeliveryChallanRepository extends JpaRepository<DeliveryChallan, Integer> {
	public DeliveryChallan findByDeliveryChallanIdAndFinYear(int deliveryChallanId, String finYear);
	public void deleteByDeliveryChallanIdAndFinYear(int deliveryChallanId, String finYear);
}