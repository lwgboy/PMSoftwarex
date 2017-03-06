package org.tarak.pms.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.GoodsReceiveChallanItem;
import org.tarak.pms.models.ProductItem;
import org.tarak.pms.models.PurchaseOrder;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.GoodsReceiveChallanService;
import org.tarak.pms.services.PurchaseOrderService;
import org.tarak.pms.services.ServiceInterface;
import org.tarak.pms.utils.GoodsReceiveChallanUtils;
import org.tarak.pms.utils.UserUtils;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/goodsReceiveChallan")
@Controller
public class GoodsReceiveChallanController {

    @Autowired
    private GoodsReceiveChallanService goodsReceiveChallanService;
    
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private ServiceInterface<Variant,Integer> variantService;

    @Autowired
	private HttpSession session;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "goodsReceiveChallan/index";
    }

    private void addGoodsReceiveChallan(Model model)
    {
    	GoodsReceiveChallan goodsReceiveChallan=new GoodsReceiveChallan();
        model.addAttribute("goodsReceiveChallan", goodsReceiveChallan);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("goodsReceiveChallan"))
    	{
    		addGoodsReceiveChallan(model);
    	}
	}
    
    @RequestMapping(value = "/add", params={"addItemDetails"}, method = RequestMethod.POST )
    public String addItemDetails(GoodsReceiveChallan goodsReceiveChallan, BindingResult result,Model model) {
    	GoodsReceiveChallanItem goodsReceiveChallanItem=new GoodsReceiveChallanItem();
    	List<GoodsReceiveChallanItem> goodsReceiveChallanItems=new ArrayList<GoodsReceiveChallanItem>();
    	goodsReceiveChallanItems.add(goodsReceiveChallanItem);
    	goodsReceiveChallan.setGoodsReceiveChallanItems(goodsReceiveChallanItems);
    	goodsReceiveChallan.setSaveItemDetails(true);
        return index(model);
    }

    @RequestMapping(value = "/add", params={"removeItemDetails"}, method = RequestMethod.POST )
    public String removeItemDetails(GoodsReceiveChallan goodsReceiveChallan, BindingResult result,Model model) {
    	goodsReceiveChallan.setGoodsReceiveChallanItems(null);
    	goodsReceiveChallan.setSaveItemDetails(false);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addGoodsReceiveChallanItem"}, method = RequestMethod.POST )
    public String addGoodsReceiveChallanItem(GoodsReceiveChallan goodsReceiveChallan, BindingResult result,Model model) {
    	GoodsReceiveChallanItem goodsReceiveChallanItem=new GoodsReceiveChallanItem();
    	List<GoodsReceiveChallanItem> goodsReceiveChallanItems=new ArrayList<GoodsReceiveChallanItem>();
    	goodsReceiveChallanItems.add(goodsReceiveChallanItem);
    	goodsReceiveChallan.getGoodsReceiveChallanItems().add(goodsReceiveChallanItem);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeGoodsReceiveChallanItem"}, method = RequestMethod.POST )
    public String removeGoodsReceiveChallanItem(GoodsReceiveChallan goodsReceiveChallan, BindingResult result,Model model,@RequestParam int removeGoodsReceiveChallanItem) {
        int index=0;
    	for(GoodsReceiveChallanItem goodsReceiveChallanItem : goodsReceiveChallan.getGoodsReceiveChallanItems())
        {
        	if(removeGoodsReceiveChallanItem==goodsReceiveChallanItem.getSrNo())
        	{
        		index=goodsReceiveChallan.getGoodsReceiveChallanItems().indexOf(goodsReceiveChallanItem);
        		PurchaseOrder po=purchaseOrderService.findByPurchaseOrderIdAndFinYear(goodsReceiveChallan.getPurchaseOrder().getPurchaseOrderId(), goodsReceiveChallan.getFinYear());
        		final int srNo=goodsReceiveChallanItem.getSrNo();
        		po.getPurchaseOrderItems().stream().filter(i->i.getSrNo()==srNo).findFirst().ifPresent(item->item.setProcessed(false));
        		po.setProcessed(false);
        		purchaseOrderService.saveAndFlush(po);
        	}
        }
    	goodsReceiveChallan.getGoodsReceiveChallanItems().remove(index);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addProduct"}, method = RequestMethod.POST )
    public String addProduct(GoodsReceiveChallan goodsReceiveChallan, BindingResult result,Model model,@RequestParam int addProduct) {
    	for(GoodsReceiveChallanItem goodsReceiveChallanItem : goodsReceiveChallan.getGoodsReceiveChallanItems())
        {
        	if(addProduct==goodsReceiveChallanItem.getSrNo())
        	{
        		if(goodsReceiveChallanItem.getProductItems()!=null)
        		{
        			goodsReceiveChallanItem.getProductItems().add(new ProductItem());
        		}
        		else
        		{
        			List<ProductItem> productItems=new LinkedList<ProductItem>();
        			productItems.add(new ProductItem());
        			goodsReceiveChallanItem.setProductItems(productItems);
        		}
        	}
        }	
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeProduct"}, method = RequestMethod.POST )
    public String removeProduct(GoodsReceiveChallan goodsReceiveChallan, BindingResult result,Model model,@RequestParam int removeProduct) {
    	int index=-1;
    	int mainIndex=-1;
    	for(GoodsReceiveChallanItem goodsReceiveChallanItem : goodsReceiveChallan.getGoodsReceiveChallanItems())
        {
    		for(ProductItem productItem: goodsReceiveChallanItem.getProductItems())
    		{
    			if(removeProduct==productItem.getId())
            	{
    				index=goodsReceiveChallanItem.getProductItems().indexOf(productItem);
    				break;
            	}
    		}
    		if(index>-1)
    		{
    			mainIndex=goodsReceiveChallan.getGoodsReceiveChallanItems().indexOf(goodsReceiveChallanItem);
    			break;
    		}
    		
        }
    	if(mainIndex > -1)
		{
			goodsReceiveChallan.getGoodsReceiveChallanItems().get(mainIndex).getProductItems().remove(index);
		}
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"poNo"}, method = RequestMethod.POST )
    public String removeGoodsReceiveChallanItem(GoodsReceiveChallan goodsReceiveChallan, BindingResult bindingResult,Model model) {
    	String finYear=UserUtils.getFinancialYear(session);
    	PurchaseOrder purchaseOrder=purchaseOrderService.findByPurchaseOrderIdAndFinYear(goodsReceiveChallan.getPurchaseOrder().getPurchaseOrderId(),finYear);
    	if(purchaseOrder.isProcessed())
    	{
    		bindingResult.rejectValue("purchaseOrder", "error.alreadyExists",null,"Purchase Order already used to create GRC");
    		if (bindingResult.hasErrors())
            {
        		return index(model);
            }
            
    	}
    	GoodsReceiveChallanUtils.populateGoodsReceiveChallan(purchaseOrder, goodsReceiveChallan);
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addGoodsReceiveChallan(@Valid GoodsReceiveChallan goodsReceiveChallan, BindingResult bindingResult, Model model)
    {
		if(UserUtils.getFinancialYear(session)!=null)
    	{
			String finYear=UserUtils.getFinancialYear(session);
			if(goodsReceiveChallan.getFinYear()==null || "".equals(goodsReceiveChallan.getFinYear()))
			{
				goodsReceiveChallan.setFinYear(finYear);
			}
			if(goodsReceiveChallan.getGoodsReceiveChallanItems()!=null && !goodsReceiveChallan.getGoodsReceiveChallanItems().isEmpty())
			{
				for(GoodsReceiveChallanItem item : goodsReceiveChallan.getGoodsReceiveChallanItems())
				{
					item.setFinYear(finYear);
				}
			}	
			
    	}
		else
		{
			bindingResult.rejectValue("goodsReceiveChallanItems", "error.alreadyExists",null,"Invalid session. Please login again");
			return "/";
		}
		if(goodsReceiveChallan.getGoodsReceiveChallanItems()!=null)
		{
			goodsReceiveChallan.getGoodsReceiveChallanItems().forEach(item->{
				if (item.getPoiSrNo() != 0) 
				{
					final double quantity = item.getQuantity();
					if (item.getProductItems() != null && !item.getProductItems().isEmpty()) 
					{
						if (quantity != item.getProductItems().stream().mapToDouble(o -> o.getQuantity()).sum()) 
						{
							bindingResult.rejectValue("goodsReceiveChallanItems", "error.alreadyExists", null,
									"Total quantity mismatches for item " + item.getSrNo());
						}
					}
				}
			});
		}
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	goodsReceiveChallanService.saveAndProcessPO(goodsReceiveChallan,goodsReceiveChallanService,purchaseOrderService,variantService);
        }
        catch(DataIntegrityViolationException e)
        {
        	//String args[]={"GoodsReceiveChallan",goodsReceiveChallan.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"GoodsReceiveChallan with name "+goodsReceiveChallan.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	//String args[]={"GoodsReceiveChallan",goodsReceiveChallan.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addGoodsReceiveChallan(model);
        return index(model);
    }

	@RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<GoodsReceiveChallan> listCategories()
    {
        List<GoodsReceiveChallan> list=goodsReceiveChallanService.findAll();
        return list;
    }
    @RequestMapping(value = "/delete/{goodsReceiveChallanId}", method = RequestMethod.GET )
    public String deleteGoodsReceiveChallan(@PathVariable Integer goodsReceiveChallanId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	GoodsReceiveChallan goodsReceiveChallan=goodsReceiveChallanService.findByGoodsReceiveChallanIdAndFinYear(goodsReceiveChallanId, finYear);
    	goodsReceiveChallanService.deleteByGoodsReceiveChallanIdAndFinYear(goodsReceiveChallanId, finYear);
    	PurchaseOrder po=purchaseOrderService.findByPurchaseOrderIdAndFinYear(goodsReceiveChallan.getPurchaseOrder().getPurchaseOrderId(), goodsReceiveChallan.getFinYear());
    	po.setProcessed(false);
    	GoodsReceiveChallanUtils.setProcessed(goodsReceiveChallan, false, po);
    	purchaseOrderService.saveAndFlush(po);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{goodsReceiveChallanId}", method = RequestMethod.GET)
    public String editGoodsReceiveChallan(@PathVariable Integer goodsReceiveChallanId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	GoodsReceiveChallan goodsReceiveChallan=goodsReceiveChallanService.findByGoodsReceiveChallanIdAndFinYear(goodsReceiveChallanId, finYear);
    	model.addAttribute("goodsReceiveChallan", goodsReceiveChallan);
    	prepareModel(model);
    	return "goodsReceiveChallan/edit";
    }
}
