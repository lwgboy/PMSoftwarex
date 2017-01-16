package org.tarak.pms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarak.pms.models.GoodsReceiveChallan;

/**
 * Created by Tarak on 12/7/2016.
 */
@Repository
public interface GoodsReceiveChallanRepository extends JpaRepository<GoodsReceiveChallan, Integer> {
	public GoodsReceiveChallan findByGoodsReceiveChallanIdAndFinYear(int goodsReceiveChallanId, String finYear);
	public void deleteByGoodsReceiveChallanIdAndFinYear(int goodsReceiveChallanId, String finYear);
}