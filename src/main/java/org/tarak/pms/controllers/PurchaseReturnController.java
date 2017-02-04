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
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarak.pms.models.PurchaseInvoice;
import org.tarak.pms.models.PurchaseReturn;
import org.tarak.pms.models.PurchaseReturnItem;
import org.tarak.pms.services.PurchaseInvoiceService;
import org.tarak.pms.services.PurchaseReturnService;
import org.tarak.pms.utils.PurchaseReturnUtils;
import org.tarak.pms.utils.UserUtils;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/purchaseReturn")
@Controller
public class PurchaseReturnController {

    @Autowired
    private PurchaseReturnService purchaseReturnService;
    
    @Autowired
    private PurchaseInvoiceService purchaseInvoiceService;


    @Autowired
	private HttpSession session;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "purchaseReturn/index";
    }

    private void addPurchaseReturn(Model model)
    {
    	PurchaseReturnItem purchaseReturnItem=new PurchaseReturnItem();
    	List<PurchaseReturnItem> purchaseReturnItems=new ArrayList<PurchaseReturnItem>();
    	purchaseReturnItems.add(purchaseReturnItem);
		PurchaseReturn purchaseReturn=new PurchaseReturn();
		purchaseReturn.setPurchaseReturnItems(purchaseReturnItems);
		
        model.addAttribute("purchaseReturn", purchaseReturn);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("purchaseReturn"))
    	{
    		addPurchaseReturn(model);
    	}
	}

    @RequestMapping(value = "/add", params={"addPurchaseReturnItem"}, method = RequestMethod.POST )
    public String addPurchaseReturnItem(PurchaseReturn purchaseReturn, BindingResult result,Model model) {
    	PurchaseReturnItem purchaseReturnItem=new PurchaseReturnItem();
    	List<PurchaseReturnItem> purchaseReturnItems=new ArrayList<PurchaseReturnItem>();
    	purchaseReturnItems.add(purchaseReturnItem);
    	purchaseReturn.getPurchaseReturnItems().add(purchaseReturnItem);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"poNo"}, method = RequestMethod.POST )
    public String removePurchaseReturnItem(PurchaseReturn purchaseReturn, BindingResult result,Model model) {
    	String finYear=UserUtils.getFinancialYear(session);
    	PurchaseInvoice purchaseInvoice=purchaseInvoiceService.findByPurchaseInvoiceIdAndFinYear(purchaseReturn.getPurchaseInvoice().getPurchaseInvoiceId(),finYear);
    	PurchaseReturnUtils.populatePurchaseReturn(purchaseInvoice, purchaseReturn);
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addPurchaseReturn(@Valid PurchaseReturn purchaseReturn, BindingResult bindingResult, Model model)
    {
		if(UserUtils.getFinancialYear(session)!=null)
    	{
			if(purchaseReturn.getFinYear()==null || "".equals(purchaseReturn.getFinYear()))
			{
				String finYear=UserUtils.getFinancialYear(session);
				purchaseReturn.setFinYear(finYear);
				for(PurchaseReturnItem item : purchaseReturn.getPurchaseReturnItems())
				{
					item.setFinYear(finYear);
				}
			}
    	}
		else
		{
			bindingResult.rejectValue("name", "error.alreadyExists",null,"Invalid session. Please login again");
			return "/";
		}
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	purchaseReturnService.saveAndFlush(purchaseReturn);
        }
        catch(DataIntegrityViolationException e)
        {
        	//String args[]={"PurchaseReturn",purchaseReturn.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"PurchaseReturn with name "+purchaseReturn.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	//String args[]={"PurchaseReturn",purchaseReturn.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addPurchaseReturn(model);
        return index(model);
    }

	@RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<PurchaseReturn> listCategories()
    {
        List<PurchaseReturn> list=purchaseReturnService.findAll();
        return list;
    }
    @RequestMapping(value = "/delete/{purchaseReturnId}", method = RequestMethod.GET )
    public String deletePurchaseReturn(@PathVariable Integer purchaseReturnId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	purchaseReturnService.deleteByPurchaseReturnIdAndFinYear(purchaseReturnId, finYear);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{purchaseReturnId}", method = RequestMethod.GET)
    public String editPurchaseReturn(@PathVariable Integer purchaseReturnId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	PurchaseReturn purchaseReturn=purchaseReturnService.findByPurchaseReturnIdAndFinYear(purchaseReturnId, finYear);
    	model.addAttribute("purchaseReturn", purchaseReturn);
    	prepareModel(model);
    	return "purchaseReturn/edit";
    }
   
}
