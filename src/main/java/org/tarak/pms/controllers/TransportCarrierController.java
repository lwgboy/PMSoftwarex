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
import org.tarak.pms.models.TransportCarrier;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/transportCarrier")
@Controller
public class TransportCarrierController {

    @Autowired
    private ServiceInterface<TransportCarrier, Integer> transportCarrierService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "transportCarrier/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("transportCarrier"))
    	{
            model.addAttribute("transportCarrier", new TransportCarrier());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addTransportCarrier(@Valid TransportCarrier transportCarrier, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "transportCarrier/index";
        }
        try
        {
        	transportCarrierService.saveAndFlush(transportCarrier);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"TransportCarrier",transportCarrier.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"TransportCarrier with name "+transportCarrier.getName()+" already exists");
        	return "transportCarrier/index";
        }
        catch(Exception e)
        {
        	String args[]={"TransportCarrier",transportCarrier.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "transportCarrier/index";
        }
        model.addAttribute("transportCarrier", new TransportCarrier());
        return "transportCarrier/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<TransportCarrier> listCategories()
    {
        List<TransportCarrier> list=transportCarrierService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteTransportCarrier(@PathVariable Integer id, Model model)
    {
    	
    	transportCarrierService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTransportCarrier(@PathVariable Integer id, Model model)
    {
    	TransportCarrier transportCarrier=transportCarrierService.findOne(id);
    	model.addAttribute("transportCarrier", transportCarrier);
    	return "transportCarrier/edit";
    }
   
}
