package org.tarak.pms.services;


import org.tarak.pms.models.PickList;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface PickListService extends ServiceInterface<PickList, Integer> {
	public PickList findByPickListIdAndFinYear(int salesOrderId,String finYear);
	public void deleteByPickListIdAndFinYear(int salesOrderId, String finYear);
}
