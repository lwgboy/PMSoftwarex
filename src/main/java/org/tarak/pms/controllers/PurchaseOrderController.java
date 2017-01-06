package org.tarak.pms.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarak.pms.models.PurchaseOrderItem;
import org.tarak.pms.models.PurchaseOrder;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/purchaseOrder")
@Controller
public class PurchaseOrderController {

    @Autowired
    private ServiceInterface<PurchaseOrder, Integer> purchaseOrderService;

    
    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "purchaseOrder/index";
    }

    private void addPurchaseOrder(Model model)
    {
    	PurchaseOrderItem purchaseOrderItem=new PurchaseOrderItem();
    	List<PurchaseOrderItem> purchaseOrderItems=new ArrayList<PurchaseOrderItem>();
    	purchaseOrderItems.add(purchaseOrderItem);
		PurchaseOrder purchaseOrder=new PurchaseOrder();
		purchaseOrder.setPurchaseOrderItems(purchaseOrderItems);
		
        model.addAttribute("purchaseOrder", purchaseOrder);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("purchaseOrder"))
    	{
    		addPurchaseOrder(model);
    	}
	}

    @RequestMapping(value = "/add", params={"addPurchaseOrderItem"}, method = RequestMethod.POST )
    public String addPurchaseOrderItem(PurchaseOrder purchaseOrder, BindingResult result,Model model) {
    	PurchaseOrderItem purchaseOrderItem=new PurchaseOrderItem();
    	List<PurchaseOrderItem> purchaseOrderItems=new ArrayList<PurchaseOrderItem>();
    	purchaseOrderItems.add(purchaseOrderItem);
    	//purchaseOrder.getPurchaseOrderItems().add(purchaseOrderItem);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removePurchaseOrderItem"}, method = RequestMethod.POST )
    public String removePurchaseOrderItem(PurchaseOrder purchaseOrder, BindingResult result,Model model) {
        //purchaseOrder.getPurchaseOrderItems().add(new PurchaseOrderItem());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addPurchaseOrder(@Valid PurchaseOrder purchaseOrder, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	purchaseOrderService.saveAndFlush(purchaseOrder);
        }
        catch(DataIntegrityViolationException e)
        {
        	//String args[]={"PurchaseOrder",purchaseOrder.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"PurchaseOrder with name "+purchaseOrder.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	//String args[]={"PurchaseOrder",purchaseOrder.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addPurchaseOrder(model);
        return index(model);
    }

	@RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<PurchaseOrder> listCategories()
    {
        List<PurchaseOrder> list=purchaseOrderService.findAll();
        return list;
    }
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deletePurchaseOrder(@PathVariable Integer id, Model model)
    {
    	
    	purchaseOrderService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editPurchaseOrder(@PathVariable Integer id, Model model)
    {
    	PurchaseOrder purchaseOrder=purchaseOrderService.findOne(id);
    	model.addAttribute("purchaseOrder", purchaseOrder);
    	prepareModel(model);
    	return "/purchaseOrder/edit";
    }
   
}
