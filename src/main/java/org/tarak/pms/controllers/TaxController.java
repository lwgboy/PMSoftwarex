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
import org.tarak.pms.models.Tax;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/tax")
@Controller
public class TaxController {

    @Autowired
    private ServiceInterface<Tax, Integer> taxService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "tax/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("tax"))
    	{
            model.addAttribute("tax", new Tax());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addTax(@Valid Tax tax, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "tax/index";
        }
        try
        {
        	taxService.saveAndFlush(tax);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Tax",tax.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Tax with name "+tax.getName()+" already exists");
        	return "tax/index";
        }
        catch(Exception e)
        {
        	String args[]={"Tax",tax.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "tax/index";
        }
        model.addAttribute("tax", new Tax());
        return "tax/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Tax> listCategories()
    {
        List<Tax> list=taxService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteTax(@PathVariable Integer id, Model model)
    {
    	
    	taxService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTax(@PathVariable Integer id, Model model)
    {
    	Tax tax=taxService.findOne(id);
    	model.addAttribute("tax", tax);
    	return "tax/edit";
    }
   
}
