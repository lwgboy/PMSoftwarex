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
import org.tarak.pms.models.ProductLineItem;
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

    @Autowired
    private ServiceInterface<ProductLineItem, Integer> productLineItemService;
    
    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "purchaseOrder/index";
    }

    private void addPurchaseOrder(Model model)
    {
    	ProductLineItem productLineItem=new ProductLineItem();
    	List<ProductLineItem> productLineItems=new ArrayList<ProductLineItem>();
    	productLineItems.add(productLineItem);
		PurchaseOrder purchaseOrder=new PurchaseOrder();
		purchaseOrder.setProductLineItems(productLineItems);
        model.addAttribute("purchaseOrder", purchaseOrder);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("purchaseOrder"))
    	{
    		addPurchaseOrder(model);
    	}
    	if(!model.containsAttribute("variant_list"))
    	{
    		List<ProductLineItem> variants=productLineItemService.findAll();
    		model.addAttribute("variant_list",variants);
    	}
	}

    @RequestMapping(value = "/add", params={"addProductLineItem"}, method = RequestMethod.POST )
    public String addProductLineItem(PurchaseOrder purchaseOrder, BindingResult result,Model model) {
    	ProductLineItem productLineItem=new ProductLineItem();
    	List<ProductLineItem> productLineItems=new ArrayList<ProductLineItem>();
    	productLineItems.add(productLineItem);
    	purchaseOrder.getProductLineItems().add(productLineItem);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeProductLineItem"}, method = RequestMethod.POST )
    public String removeProductLineItem(PurchaseOrder purchaseOrder, BindingResult result,Model model) {
        purchaseOrder.getProductLineItems().add(new ProductLineItem());
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
