package org.tarak.pms.services;


import org.tarak.pms.models.GoodsReceiveChallan;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface GoodsReceiveChallanService extends ServiceInterface<GoodsReceiveChallan, Integer> {
	public GoodsReceiveChallan findByGoodsReceiveChallanIdAndFinYear(int id,String finYear);
}
