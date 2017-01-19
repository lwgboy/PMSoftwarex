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
import org.tarak.pms.models.StockPoint;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/stockPoint")
@Controller
public class StockPointController {

    @Autowired
    private ServiceInterface<StockPoint, Integer> stockPointService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "stockPoint/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("stockPoint"))
    	{
            model.addAttribute("stockPoint", new StockPoint());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addStockPoint(@Valid StockPoint stockPoint, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "stockPoint/index";
        }
        try
        {
        	stockPointService.saveAndFlush(stockPoint);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"StockPoint",stockPoint.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"StockPoint with name "+stockPoint.getName()+" already exists");
        	return "stockPoint/index";
        }
        catch(Exception e)
        {
        	String args[]={"StockPoint",stockPoint.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "stockPoint/index";
        }
        model.addAttribute("stockPoint", new StockPoint());
        return "stockPoint/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<StockPoint> listCategories()
    {
        List<StockPoint> list=stockPointService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteStockPoint(@PathVariable Integer id, Model model)
    {
    	
    	stockPointService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editStockPoint(@PathVariable Integer id, Model model)
    {
    	StockPoint stockPoint=stockPointService.findOne(id);
    	model.addAttribute("stockPoint", stockPoint);
    	return "stockPoint/edit";
    }
   
}
