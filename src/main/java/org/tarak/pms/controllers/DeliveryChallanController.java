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
import org.tarak.pms.models.DeliveryChallan;
import org.tarak.pms.models.DeliveryChallanItem;
import org.tarak.pms.models.ProductItem;
import org.tarak.pms.models.SalesOrder;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.DeliveryChallanService;
import org.tarak.pms.services.SalesOrderService;
import org.tarak.pms.services.ServiceInterface;
import org.tarak.pms.utils.DeliveryChallanUtils;
import org.tarak.pms.utils.UserUtils;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/deliveryChallan")
@Controller
public class DeliveryChallanController {

    @Autowired
    private DeliveryChallanService deliveryChallanService;
    
    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private ServiceInterface<Variant,Integer> variantService;

    @Autowired
	private HttpSession session;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "deliveryChallan/index";
    }

    private void addDeliveryChallan(Model model)
    {
    	DeliveryChallan deliveryChallan=new DeliveryChallan();
        model.addAttribute("deliveryChallan", deliveryChallan);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("deliveryChallan"))
    	{
    		addDeliveryChallan(model);
    	}
	}
    
    @RequestMapping(value = "/add", params={"addItemDetails"}, method = RequestMethod.POST )
    public String addItemDetails(DeliveryChallan deliveryChallan, BindingResult result,Model model) {
    	DeliveryChallanItem deliveryChallanItem=new DeliveryChallanItem();
    	List<DeliveryChallanItem> deliveryChallanItems=new ArrayList<DeliveryChallanItem>();
    	deliveryChallanItems.add(deliveryChallanItem);
    	deliveryChallan.setDeliveryChallanItems(deliveryChallanItems);
    	deliveryChallan.setSaveItemDetails(true);
        return index(model);
    }

    @RequestMapping(value = "/add", params={"removeItemDetails"}, method = RequestMethod.POST )
    public String removeItemDetails(DeliveryChallan deliveryChallan, BindingResult result,Model model) {
    	deliveryChallan.setDeliveryChallanItems(null);
    	deliveryChallan.setSaveItemDetails(false);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addDeliveryChallanItem"}, method = RequestMethod.POST )
    public String addDeliveryChallanItem(DeliveryChallan deliveryChallan, BindingResult result,Model model) {
    	DeliveryChallanItem deliveryChallanItem=new DeliveryChallanItem();
    	List<DeliveryChallanItem> deliveryChallanItems=new ArrayList<DeliveryChallanItem>();
    	deliveryChallanItems.add(deliveryChallanItem);
    	deliveryChallan.getDeliveryChallanItems().add(deliveryChallanItem);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeDeliveryChallanItem"}, method = RequestMethod.POST )
    public String removeDeliveryChallanItem(DeliveryChallan deliveryChallan, BindingResult result,Model model,@RequestParam int removeDeliveryChallanItem) {
        int index=0;
    	for(DeliveryChallanItem deliveryChallanItem : deliveryChallan.getDeliveryChallanItems())
        {
        	if(removeDeliveryChallanItem==deliveryChallanItem.getSrNo())
        	{
        		index=deliveryChallan.getDeliveryChallanItems().indexOf(deliveryChallanItem);
        		SalesOrder po=salesOrderService.findBySalesOrderIdAndFinYear(deliveryChallan.getSalesOrder().getSalesOrderId(), deliveryChallan.getFinYear());
        		final int srNo=deliveryChallanItem.getSrNo();
        		po.getSalesOrderItems().stream().filter(i->i.getSrNo()==srNo).findFirst().ifPresent(item->item.setProcessed(false));
        		po.setProcessed(false);
        		salesOrderService.saveAndFlush(po);
        	}
        }
    	deliveryChallan.getDeliveryChallanItems().remove(index);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addProduct"}, method = RequestMethod.POST )
    public String addProduct(DeliveryChallan deliveryChallan, BindingResult result,Model model,@RequestParam int addProduct) {
    	for(DeliveryChallanItem deliveryChallanItem : deliveryChallan.getDeliveryChallanItems())
        {
        	if(addProduct==deliveryChallanItem.getSrNo())
        	{
        		if(deliveryChallanItem.getProductItems()!=null)
        		{
        			deliveryChallanItem.getProductItems().add(new ProductItem());
        		}
        		else
        		{
        			List<ProductItem> productItems=new LinkedList<ProductItem>();
        			productItems.add(new ProductItem());
        			deliveryChallanItem.setProductItems(productItems);
        		}
        	}
        }	
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeProduct"}, method = RequestMethod.POST )
    public String removeProduct(DeliveryChallan deliveryChallan, BindingResult result,Model model,@RequestParam int removeProduct) {
    	int index=-1;
    	int mainIndex=-1;
    	for(DeliveryChallanItem deliveryChallanItem : deliveryChallan.getDeliveryChallanItems())
        {
    		for(ProductItem productItem: deliveryChallanItem.getProductItems())
    		{
    			if(removeProduct==productItem.getId())
            	{
    				index=deliveryChallanItem.getProductItems().indexOf(productItem);
    				break;
            	}
    		}
    		if(index>-1)
    		{
    			mainIndex=deliveryChallan.getDeliveryChallanItems().indexOf(deliveryChallanItem);
    			break;
    		}
    		
        }
    	if(mainIndex > -1)
		{
			deliveryChallan.getDeliveryChallanItems().get(mainIndex).getProductItems().remove(index);
		}
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"poNo"}, method = RequestMethod.POST )
    public String removeDeliveryChallanItem(DeliveryChallan deliveryChallan, BindingResult bindingResult,Model model) {
    	String finYear=UserUtils.getFinancialYear(session);
    	SalesOrder salesOrder=salesOrderService.findBySalesOrderIdAndFinYear(deliveryChallan.getSalesOrder().getSalesOrderId(),finYear);
    	if(salesOrder.isProcessed())
    	{
    		bindingResult.rejectValue("salesOrder", "error.alreadyExists",null,"Purchase Order already used to create GRC");
    		if (bindingResult.hasErrors())
            {
        		return index(model);
            }
            
    	}
    	DeliveryChallanUtils.populateDeliveryChallan(salesOrder, deliveryChallan);
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addDeliveryChallan(@Valid DeliveryChallan deliveryChallan, BindingResult bindingResult, Model model)
    {
		if(UserUtils.getFinancialYear(session)!=null)
    	{
			String finYear=UserUtils.getFinancialYear(session);
			if(deliveryChallan.getFinYear()==null || "".equals(deliveryChallan.getFinYear()))
			{
				deliveryChallan.setFinYear(finYear);
			}
			if(deliveryChallan.getDeliveryChallanItems()!=null && !deliveryChallan.getDeliveryChallanItems().isEmpty())
			{
				for(DeliveryChallanItem item : deliveryChallan.getDeliveryChallanItems())
				{
					item.setFinYear(finYear);
				}
			}	
			
    	}
		else
		{
			bindingResult.rejectValue("deliveryChallanItems", "error.alreadyExists",null,"Invalid session. Please login again");
			return "/";
		}
		if(deliveryChallan.getDeliveryChallanItems()!=null)
		{
			deliveryChallan.getDeliveryChallanItems().forEach(item->{
				if (item.getPoiSrNo() != 0) 
				{
					final double quantity = item.getQuantity();
					if (item.getProductItems() != null && !item.getProductItems().isEmpty()) 
					{
						if (quantity != item.getProductItems().stream().mapToDouble(o -> o.getQuantity()).sum()) 
						{
							bindingResult.rejectValue("deliveryChallanItems", "error.alreadyExists", null,
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
        	deliveryChallanService.saveAndProcessPO(deliveryChallan,deliveryChallanService,salesOrderService,variantService);
        }
        catch(DataIntegrityViolationException e)
        {
        	//String args[]={"DeliveryChallan",deliveryChallan.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"DeliveryChallan with name "+deliveryChallan.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	//String args[]={"DeliveryChallan",deliveryChallan.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addDeliveryChallan(model);
        return index(model);
    }

	@RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<DeliveryChallan> listCategories()
    {
        List<DeliveryChallan> list=deliveryChallanService.findAll();
        return list;
    }
    @RequestMapping(value = "/delete/{deliveryChallanId}", method = RequestMethod.GET )
    public String deleteDeliveryChallan(@PathVariable Integer deliveryChallanId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	DeliveryChallan deliveryChallan=deliveryChallanService.findByDeliveryChallanIdAndFinYear(deliveryChallanId, finYear);
    	deliveryChallanService.deleteByDeliveryChallanIdAndFinYear(deliveryChallanId, finYear);
    	SalesOrder po=salesOrderService.findBySalesOrderIdAndFinYear(deliveryChallan.getSalesOrder().getSalesOrderId(), deliveryChallan.getFinYear());
    	po.setProcessed(false);
    	DeliveryChallanUtils.setProcessed(deliveryChallan, false, po);
    	salesOrderService.saveAndFlush(po);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{deliveryChallanId}", method = RequestMethod.GET)
    public String editDeliveryChallan(@PathVariable Integer deliveryChallanId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	DeliveryChallan deliveryChallan=deliveryChallanService.findByDeliveryChallanIdAndFinYear(deliveryChallanId, finYear);
    	model.addAttribute("deliveryChallan", deliveryChallan);
    	prepareModel(model);
    	return "deliveryChallan/edit";
    }
}
