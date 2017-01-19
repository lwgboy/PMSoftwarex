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
import org.tarak.pms.models.Warehouse;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/warehouse")
@Controller
public class WarehouseController {

    @Autowired
    private ServiceInterface<Warehouse, Integer> warehouseService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "warehouse/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("warehouse"))
    	{
            model.addAttribute("warehouse", new Warehouse());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addWarehouse(@Valid Warehouse warehouse, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "warehouse/index";
        }
        try
        {
        	warehouseService.saveAndFlush(warehouse);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Warehouse",warehouse.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Warehouse with name "+warehouse.getName()+" already exists");
        	return "warehouse/index";
        }
        catch(Exception e)
        {
        	String args[]={"Warehouse",warehouse.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "warehouse/index";
        }
        model.addAttribute("warehouse", new Warehouse());
        return "warehouse/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Warehouse> listCategories()
    {
        List<Warehouse> list=warehouseService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteWarehouse(@PathVariable Integer id, Model model)
    {
    	
    	warehouseService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editWarehouse(@PathVariable Integer id, Model model)
    {
    	Warehouse warehouse=warehouseService.findOne(id);
    	model.addAttribute("warehouse", warehouse);
    	return "warehouse/edit";
    }
   
}
