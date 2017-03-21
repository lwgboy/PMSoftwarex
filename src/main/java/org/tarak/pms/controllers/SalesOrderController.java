package org.tarak.pms.controllers;

import java.util.ArrayList;
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
import org.tarak.pms.models.Product;
import org.tarak.pms.models.SalesOrder;
import org.tarak.pms.models.SalesOrderItem;
import org.tarak.pms.models.Style;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.SalesOrderService;
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

	@RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<SalesOrder> listCategories()
    {
        List<SalesOrder> list=salesOrderService.findAll();
        return list;
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
    
    @RequestMapping(value = "/allocate_link/{salesOrderId}", method = RequestMethod.GET)
    public String allocateSalesOrder(@PathVariable Integer salesOrderId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	SalesOrder salesOrder=salesOrderService.findBySalesOrderIdAndFinYear(salesOrderId,finYear);
    	model.addAttribute("salesOrder", salesOrder);
    	prepareModel(model);
    	return "salesOrder/allocate";
    }

}
