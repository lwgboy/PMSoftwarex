package org.tarak.pms.controllers;

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
import org.tarak.pms.models.PaymentTerms;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/paymentTerms")
@Controller
public class PaymentTermsController {

    @Autowired
    private ServiceInterface<PaymentTerms, Integer> paymentTermsService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "paymentTerms/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("paymentTerms"))
    	{
            model.addAttribute("paymentTerms", new PaymentTerms());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addPaymentTerms(@Valid PaymentTerms paymentTerms, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "paymentTerms/index";
        }
        try
        {
        	paymentTermsService.saveAndFlush(paymentTerms);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"PaymentTerms",paymentTerms.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"PaymentTerms with name "+paymentTerms.getName()+" already exists");
        	return "paymentTerms/index";
        }
        catch(Exception e)
        {
        	String args[]={"PaymentTerms",paymentTerms.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "paymentTerms/index";
        }
        model.addAttribute("paymentTerms", new PaymentTerms());
        return "paymentTerms/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<PaymentTerms> listCategories()
    {
        List<PaymentTerms> list=paymentTermsService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deletePaymentTerms(@PathVariable Integer id, Model model)
    {
    	
    	paymentTermsService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editPaymentTerms(@PathVariable Integer id, Model model)
    {
    	PaymentTerms paymentTerms=paymentTermsService.findOne(id);
    	model.addAttribute("paymentTerms", paymentTerms);
    	return "paymentTerms/edit";
    }
   
}
