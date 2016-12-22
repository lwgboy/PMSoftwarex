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
import org.tarak.pms.models.Measurement;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/measurement")
@Controller
public class MeasurementController {

    @Autowired
    private ServiceInterface<Measurement, Integer> measurementService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "measurement/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("measurement"))
    	{
            model.addAttribute("measurement", new Measurement());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addMeasurement(@Valid Measurement measurement, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "measurement/index";
        }
        try
        {
        	measurementService.saveAndFlush(measurement);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Measurement",measurement.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Measurement with name "+measurement.getName()+" already exists");
        	return "measurement/index";
        }
        catch(Exception e)
        {
        	String args[]={"Measurement",measurement.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "measurement/index";
        }
        model.addAttribute("measurement", new Measurement());
        return "measurement/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Measurement> listCategories()
    {
        List<Measurement> list=measurementService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteMeasurement(@PathVariable Integer id, Model model)
    {
    	
    	measurementService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editMeasurement(@PathVariable Integer id, Model model)
    {
    	Measurement measurement=measurementService.findOne(id);
    	model.addAttribute("measurement", measurement);
    	return "measurement/edit";
    }
   
}
