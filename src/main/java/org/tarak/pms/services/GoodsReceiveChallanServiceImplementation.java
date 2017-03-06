package org.tarak.pms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.PurchaseOrder;
import org.tarak.pms.models.Variant;
import org.tarak.pms.repositories.GoodsReceiveChallanRepository;
import org.tarak.pms.utils.GoodsReceiveChallanUtils;

/**
 * Created by Tarak on 12/7/2016.
 */

@Service
@Transactional
public class GoodsReceiveChallanServiceImplementation implements GoodsReceiveChallanService
{
    @Autowired
    private GoodsReceiveChallanRepository repository;

    @Override
    public List<GoodsReceiveChallan> findAll() {
        return repository.findAll();
    }

    @Override
    public GoodsReceiveChallan saveAndFlush(GoodsReceiveChallan goodsReceiveChallan) {
        return repository.saveAndFlush(goodsReceiveChallan);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public GoodsReceiveChallan findOne(Integer id) {
        return repository.findOne(id);
    }
    
	@Override
	public GoodsReceiveChallan findByGoodsReceiveChallanIdAndFinYear(int id, String finYear) {
		return repository.findByGoodsReceiveChallanIdAndFinYear(id, finYear);
	}

	@Override
	public void deleteByGoodsReceiveChallanIdAndFinYear(int goodsReceiveChallanId, String finYear) {
		repository.deleteByGoodsReceiveChallanIdAndFinYear(goodsReceiveChallanId, finYear);
	}
    @Override
	public void saveAndProcessPO(GoodsReceiveChallan goodsReceiveChallan, GoodsReceiveChallanService goodsReceiveChallanService, PurchaseOrderService purchaseOrderService,ServiceInterface<Variant,Integer> variantService)
	{
		goodsReceiveChallanService.saveAndFlush(goodsReceiveChallan);
		if(goodsReceiveChallan.getGoodsReceiveChallanItems()!=null && !goodsReceiveChallan.getGoodsReceiveChallanItems().isEmpty())
    	{
			PurchaseOrder po=purchaseOrderService.findByPurchaseOrderIdAndFinYear(goodsReceiveChallan.getPurchaseOrder().getPurchaseOrderId(), goodsReceiveChallan.getFinYear());
			GoodsReceiveChallanUtils.setProcessed(goodsReceiveChallan, true, po);
			if(!po.getPurchaseOrderItems().stream().filter(i->!i.isProcessed()).findAny().isPresent())
			{
				po.setProcessed(true);
			}
			else
			{
				po.setProcessed(false);
			}
			purchaseOrderService.saveAndFlush(po);
    	}	
	}
 }
