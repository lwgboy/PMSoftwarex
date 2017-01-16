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
import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.PurchaseInvoice;
import org.tarak.pms.models.PurchaseInvoiceItem;
import org.tarak.pms.services.GoodsReceiveChallanService;
import org.tarak.pms.services.PurchaseInvoiceService;
import org.tarak.pms.utils.PurchaseInvoiceUtils;
import org.tarak.pms.utils.UserUtils;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/purchaseInvoice")
@Controller
public class PurchaseInvoiceController {

    @Autowired
    private PurchaseInvoiceService purchaseInvoiceService;
    
    @Autowired
    private GoodsReceiveChallanService goodsReceiveChallanService;


    @Autowired
	private HttpSession session;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "purchaseInvoice/index";
    }

    private void addPurchaseInvoice(Model model)
    {
    	PurchaseInvoiceItem purchaseInvoiceItem=new PurchaseInvoiceItem();
    	List<PurchaseInvoiceItem> purchaseInvoiceItems=new ArrayList<PurchaseInvoiceItem>();
    	purchaseInvoiceItems.add(purchaseInvoiceItem);
		PurchaseInvoice purchaseInvoice=new PurchaseInvoice();
		purchaseInvoice.setPurchaseInvoiceItems(purchaseInvoiceItems);
		
        model.addAttribute("purchaseInvoice", purchaseInvoice);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("purchaseInvoice"))
    	{
    		addPurchaseInvoice(model);
    	}
	}

    @RequestMapping(value = "/add", params={"addPurchaseInvoiceItem"}, method = RequestMethod.POST )
    public String addPurchaseInvoiceItem(PurchaseInvoice purchaseInvoice, BindingResult result,Model model) {
    	PurchaseInvoiceItem purchaseInvoiceItem=new PurchaseInvoiceItem();
    	List<PurchaseInvoiceItem> purchaseInvoiceItems=new ArrayList<PurchaseInvoiceItem>();
    	purchaseInvoiceItems.add(purchaseInvoiceItem);
    	purchaseInvoice.getPurchaseInvoiceItems().add(purchaseInvoiceItem);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removePurchaseInvoiceItem"}, method = RequestMethod.POST )
    public String removePurchaseInvoiceItem(PurchaseInvoice purchaseInvoice, BindingResult result,Model model,@RequestParam int removePurchaseInvoiceItem) {
        int index=0;
    	for(PurchaseInvoiceItem purchaseInvoiceItem : purchaseInvoice.getPurchaseInvoiceItems())
        {
        	if(removePurchaseInvoiceItem==purchaseInvoiceItem.getSrNo())
        	{
        		index=purchaseInvoice.getPurchaseInvoiceItems().indexOf(purchaseInvoiceItem);
        	}
        }
    	purchaseInvoice.getPurchaseInvoiceItems().remove(index);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"poNo"}, method = RequestMethod.POST )
    public String removePurchaseInvoiceItem(PurchaseInvoice purchaseInvoice, BindingResult result,Model model) {
    	String finYear=UserUtils.getFinancialYear(session);
    	GoodsReceiveChallan goodsReceiveChallan=goodsReceiveChallanService.findByGoodsReceiveChallanIdAndFinYear(purchaseInvoice.getGoodsReceiveChallan().getGoodsReceiveChallanId(),finYear);
    	PurchaseInvoiceUtils.populatePurchaseInvoice(goodsReceiveChallan, purchaseInvoice);
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addPurchaseInvoice(@Valid PurchaseInvoice purchaseInvoice, BindingResult bindingResult, Model model)
    {
		if(UserUtils.getFinancialYear(session)!=null)
    	{
			if(purchaseInvoice.getFinYear()==null || "".equals(purchaseInvoice.getFinYear()))
			{
				String finYear=UserUtils.getFinancialYear(session);
				purchaseInvoice.setFinYear(finYear);
				for(PurchaseInvoiceItem item : purchaseInvoice.getPurchaseInvoiceItems())
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
        	purchaseInvoiceService.saveAndFlush(purchaseInvoice);
        }
        catch(DataIntegrityViolationException e)
        {
        	//String args[]={"PurchaseInvoice",purchaseInvoice.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"PurchaseInvoice with name "+purchaseInvoice.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	//String args[]={"PurchaseInvoice",purchaseInvoice.getName()};
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addPurchaseInvoice(model);
        return index(model);
    }

	@RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<PurchaseInvoice> listCategories()
    {
        List<PurchaseInvoice> list=purchaseInvoiceService.findAll();
        return list;
    }
    @RequestMapping(value = "/delete/{purchaseInvoiceId}", method = RequestMethod.GET )
    public String deletePurchaseInvoice(@PathVariable Integer id, Model model)
    {
    	
    	purchaseInvoiceService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{purchaseInvoiceId}", method = RequestMethod.GET)
    public String editPurchaseInvoice(@PathVariable Integer purchaseInvoiceId, Model model)
    {
    	String finYear=UserUtils.getFinancialYear(session);
    	PurchaseInvoice purchaseInvoice=purchaseInvoiceService.findByPurchaseInvoiceIdAndFinYear(purchaseInvoiceId, finYear);
    	model.addAttribute("purchaseInvoice", purchaseInvoice);
    	prepareModel(model);
    	return "/purchaseInvoice/edit";
    }
   
}
