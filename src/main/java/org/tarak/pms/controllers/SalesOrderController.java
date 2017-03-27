package org.tarak.pms.controllers;

import java.util.ArrayList;
import java.util.Date;
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
import org.tarak.pms.models.Brand;
import org.tarak.pms.models.PickList;
import org.tarak.pms.models.Product;
import org.tarak.pms.models.SalesOrder;
import org.tarak.pms.models.SalesOrderItem;
import org.tarak.pms.models.Style;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.PickListService;
import org.tarak.pms.services.SalesOrderItemService;
import org.tarak.pms.services.SalesOrderService;
import org.tarak.pms.services.VariantService;
import org.tarak.pms.utils.PMSoftwareConstants;
import org.tarak.pms.utils.UserUtils;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/salesOrder")
@Controller
public class SalesOrderController {

    @Autowired
    private SalesOrderService salesOrderService;

    @Autowired
    private SalesOrderItemService salesOrderItemService;

    @Autowired
    private PickListService pickListService;

    @Autowired
    private VariantService variantService;
    
    @Autowired
	private HttpSession session;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "salesOrder/index";
    }

    private void addSalesOrder(Model model)
    {
    	SalesOrderItem salesOrderItem=new SalesOrderItem();
    	List<SalesOrderItem> salesOrderItems=new ArrayList<SalesOrderItem>();
    	salesOrderItems.add(salesOrderItem);
		SalesOrder salesOrder=new SalesOrder();
		salesOrder.setSalesOrderItems(salesOrderItems);
		
        model.addAttribute("salesOrder", salesOrder);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("salesOrder"))
    	{
    		addSalesOrder(model);
    	}
	}

    @RequestMapping(value = "/add", params={"addSalesOrderItem"}, method = RequestMethod.POST )
    public String addSalesOrderItem(SalesOrder salesOrder, BindingResult result,Model model) {
    	SalesOrderItem salesOrderItem=new SalesOrderItem();
    	List<SalesOrderItem> salesOrderItems=new ArrayList<SalesOrderItem>();
    	salesOrderItems.add(salesOrderItem);
    	salesOrder.getSalesOrderItems().add(salesOrderItem);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addProduct"}, method = RequestMethod.POST )
    public String addProduct(SalesOrder salesOrder, BindingResult result,Model model,@RequestParam int addProduct) {
    	for(SalesOrderItem salesOrderItem : salesOrder.getSalesOrderItems())
        {
        	if(addProduct==salesOrderItem.getSrNo())
        	{
        		salesOrderItem.setProduct(new Product());
        		salesOrderItem.setVariant(new Variant());
        		salesOrderItem.setBrand(null);
        		salesOrderItem.setStyle(null);
        	}
        }	
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeProduct"}, method = RequestMethod.POST )
    public String removeProduct(SalesOrder salesOrder, BindingResult result,Model model,@RequestParam int removeProduct) {
    	for(SalesOrderItem salesOrderItem : salesOrder.getSalesOrderItems())
        {
        	if(removeProduct==salesOrderItem.getSrNo())
        	{
        		salesOrderItem.setProduct(null);
        		salesOrderItem.setVariant(null);
        		salesOrderItem.setBrand(new Brand());
        		salesOrderItem.setStyle(new Style());
        	}
        }	
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeSalesOrderItem"}, method = RequestMethod.POST )
    public String removeSalesOrderItem(SalesOrder salesOrder, BindingResult result,Model model,@RequestParam int removeSalesOrderItem) {
        int index=0;
    	for(SalesOrderItem salesOrderItem : salesOrder.getSalesOrderItems())
        {
        	if(removeSalesOrderItem==salesOrderItem.getSrNo())
        	{
        		index=salesOrder.getSalesOrderItems().indexOf(salesOrderItem);
        	}
        }
    	salesOrder.getSalesOrderItems().remove(index);
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addSalesOrder(@Valid SalesOrder salesOrder, BindingResult bindingResult, Model model)
    {
		if(UserUtils.getFinancialYear(session)!=null)
    	{
			String finYear=UserUtils.getFinancialYear(session);
			salesOrder.setFinYear(finYear);
			for(SalesOrderItem item : salesOrder.getSalesOrderItems())
			{
				item.setFinYear(finYear);
			}
			if(salesOrder.getEmployeeAttended()!=null && salesOrder.getEmployeeAttended().getId()==null)
			{
				salesOrder.setEmployeeAttended(null);
			}
    	}
		else
		{
			bindingResult.rejectValue("buyer", "error.alreadyExists",null,"Invalid session. Please login again");
			return "/index";
		}
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	salesOrderService.saveAndFlush(salesOrder);
        }
        catch(DataIntegrityViolationException e)
        {
        	//String args[]={"SalesOrder",salesOrder.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"SalesOrder with name "+salesOrder.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	//String args[]={"SalesOrder",salesOrder.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addSalesOrder(model);
        return index(model);
    }

	@RequestMapping(value = "/add", params={"allocate"}, method = RequestMethod.POST )
    public String allocateSalesOrder(@Valid SalesOrder salesOrder, BindingResult bindingResult, Model model)
    {
		SalesOrder so=salesOrderService.findBySalesOrderIdAndFinYear(salesOrder.getSalesOrderId(), salesOrder.getFinYear());
		so.getSalesOrderItems().forEach(item->{
			final int id=item.getSrNo();
			salesOrder.getSalesOrderItems().forEach(i->{
				if(id==i.getSrNo())
				{
					item.setForwardOrder(i.isForwardOrder()?true:false);
					double diff=i.getAllocated()-item.getAllocated();
					item.setAllocated(i.getAllocated());
					if(item.getVariant()!=null)
					{
						Variant variant=variantService.findOne(item.getVariant().getId());
						variant.setAllocated(variant.getAllocated()+diff);
						variantService.saveAndFlush(variant);	
					}
				}
		/*		if(i.getAllocated()==i.getQuantity())
				{
					item.setProcessed(true);
				}
				else
				{
					item.setProcessed(false);
				}
		*/	});
		});
		if(so.getSalesOrderItems().stream().filter(item->item.isForwardOrder()==true).findFirst().isPresent())
		{
			so.setForwardOrder(true);
		}
		else
		{
			so.setForwardOrder(false);
		}
		/*long count=so.getSalesOrderItems().stream().filter(item->item.isProcessed()).count();
		if(so.getSalesOrderItems().size()==count)
		{
			so.setStage(PMSoftwareConstants.FULLFILLED);
		}
		else if(count>=1)
		{
			so.setStage(PMSoftwareConstants.PARTIALLYFULLFILLED);
		}
		else
		{
			so.setStage(PMSoftwareConstants.UNFULLFILLED);
		}
		*/salesOrderService.saveAndFlush(so);
		return "salesOrder/unAllocated";
    }
	
	@RequestMapping(value = "/add", params={"close"}, method = RequestMethod.POST )
    public String closeSalesOrder(@Valid SalesOrder salesOrder, BindingResult bindingResult, Model model)
    {
		salesOrder.setStage(PMSoftwareConstants.CLOSED);
		salesOrderService.saveAndFlush(salesOrder);
	    addSalesOrder(model);
		return index(model);
    }
	
	@RequestMapping(value = "/add", params={"generate_picklist"}, method = RequestMethod.POST )
    public String printSalesOrderItems(@Valid SalesOrder salesOrder, BindingResult bindingResult, Model model)
    {
		PickList pi=new PickList();
		List<SalesOrderItem> salesOrderItems=new LinkedList<SalesOrderItem>();
		pi.setSalesOrderItems(salesOrderItems);
		salesOrder.getSalesOrderItems().forEach(item->{
			SalesOrderItem soi=salesOrderItemService.findBySalesOrderIdAndFinYearAndSrNo(item.getSalesOrderId(),item.getFinYear(),item.getSrNo());
			item.setProduct(soi.getProduct());
			item.setVariant(soi.getVariant());
			item.setQuantity(soi.getQuantity());
			pi.getSalesOrderItems().add(soi);
		});
		Date date=new Date(System.currentTimeMillis());
		pi.setPickListDate(date);
		String finYear=UserUtils.getFinancialYear(session);
		pi.setFinYear(finYear);
		pickListService.saveAndFlush(pi);
		model.addAttribute("pickList", pi);
		return "salesOrder/printPickList";
    }
	
	@RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<SalesOrder> listCategories()
    {
        List<SalesOrder> list=salesOrderService.findAll();
        return list;
    }
	
	@RequestMapping(value = "/pickList", method = RequestMethod.GET )
    public @ResponseBody
    List<PickList> pickList()
    {
        List<PickList> list=pickListService.findAll();
        return list;
    }
	
	@RequestMapping(value = "/unAllocatedList", method = RequestMethod.GET )
    public @ResponseBody
    List<SalesOrder> listUnfullfilled()
    {
		String finYear=UserUtils.getFinancialYear(session);
        List<SalesOrder> list=salesOrderService.findByFinYearAndStage(finYear,PMSoftwareConstants.UNFULLFILLED);
        return list;
    }
	
	@RequestMapping(value = "/unAllocated", method = RequestMethod.GET)
    public String unfulfilledSalesOrder()
    {
    	return "salesOrder/unAllocated";
    }
	
	@RequestMapping(value = "/pickListItemList/{pickListId}", method = RequestMethod.GET )
    public @ResponseBody
    List<SalesOrderItem> listPickListItems(@PathVariable Integer pickListId)
    {
		String finYear=UserUtils.getFinancialYear(session);
		PickList pl=pickListService.findByPickListIdAndFinYear(pickListId, finYear);
		return pl.getSalesOrderItems();
    }
	
	@RequestMapping(value = "/allocatedList", method = RequestMethod.GET )
    public @ResponseBody
    List<SalesOrderItem> listAllocated()
    {
		String finYear=UserUtils.getFinancialYear(session);
        List<SalesOrder> list=salesOrderService.findByFinYearAndStage(finYear,PMSoftwareConstants.UNFULLFILLED);
        List<SalesOrderItem> items=new LinkedList<SalesOrderItem>();
        list.forEach(order->{
        	order.getSalesOrderItems().removeIf(item->item.getAllocated()!=item.getQuantity());
        	order.getSalesOrderItems().forEach(item->{
        	});
        	items.addAll(order.getSalesOrderItems());
        });
        return items;
    }
	
	@RequestMapping(value = "/allocated", method = RequestMethod.GET)
    public String allocatedSalesOrder(Model model)
    {
		addSalesOrder(model);
    	return "salesOrder/allocated";
    }
	
    @RequestMapping(value = "/delete/{salesOrderId}", method = RequestMethod.GET )
    public String deleteSalesOrder(@PathVariable Integer salesOrderId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	salesOrderService.deleteBySalesOrderIdAndFinYear(salesOrderId, finYear);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{salesOrderId}", method = RequestMethod.GET)
    public String editSalesOrder(@PathVariable Integer salesOrderId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	SalesOrder salesOrder=salesOrderService.findBySalesOrderIdAndFinYear(salesOrderId,finYear);
    	model.addAttribute("salesOrder", salesOrder);
    	prepareModel(model);
    	return "salesOrder/edit";
    }
    
    @RequestMapping(value = "/print/{pickListId}", method = RequestMethod.GET)
    public String printPickList(@PathVariable Integer pickListId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	PickList pl=pickListService.findByPickListIdAndFinYear(pickListId, finYear);
    	model.addAttribute("pickList", pl);
    	prepareModel(model);
    	return "salesOrder/printPickList";
    }
    
    
    @RequestMapping(value = "/allocate_link/{salesOrderId}", method = RequestMethod.GET)
    public String allocateSalesOrder(@PathVariable Integer salesOrderId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	SalesOrder salesOrder=salesOrderService.findBySalesOrderIdAndFinYear(salesOrderId,finYear);
    	salesOrder.getSalesOrderItems().forEach(item->{
    		if(item.getVariant()!=null)
    		{
    			item.getVariant().setUnallocated(item.getVariant().getQuantity()-item.getVariant().getAllocated());
    		}
    	});
    	model.addAttribute("salesOrder", salesOrder);
    	prepareModel(model);
    	return "salesOrder/allocate";
    }
    
    @RequestMapping(value = "/forwardOrder", method = RequestMethod.GET)
    public String forwardSalesOrder()
    {
    	return "salesOrder/forwardOrder";
    }
    
    @RequestMapping(value = "/forwardOrderList", method = RequestMethod.GET)
    public @ResponseBody
    List<SalesOrderItem> forwardSalesOrderList()
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	List<SalesOrder> salesOrders=salesOrderService.findByFinYearAndForwardOrder(finYear, true);
    	List<SalesOrderItem> items=new LinkedList<SalesOrderItem>();
    	salesOrders.forEach(order->{
    		if(order.isForwardOrder())
    		{
    			order.getSalesOrderItems().forEach(item->{
        			if(item.isForwardOrder())
        			{
        				item.setSalesOrderId(order.getSalesOrderId());
            			item.setForwardOrderQuantity(item.getQuantity()-item.getAllocated());
            			if(item.getProduct()==null || item.getVariant()==null)
            			{
            				Product product=new Product();
            				product.setName(item.getStyle().getName());
            				Variant variant=new Variant();
            				variant.setName(item.getBrand().getName());
            				item.setProduct(product);
            				item.setVariant(variant);
            			}
            			items.add(item);
            			
        			}
        		});
    		}
    	});
    	return items;
    }
}
