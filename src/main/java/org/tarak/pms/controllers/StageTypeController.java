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
import org.tarak.pms.models.StageType;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/stageType")
@Controller
public class StageTypeController {

    @Autowired
    private ServiceInterface<StageType, Integer> stageTypeService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "stageType/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("stageType"))
    	{
            model.addAttribute("stageType", new StageType());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addStageType(@Valid StageType stageType, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "stageType/index";
        }
        try
        {
        	stageTypeService.saveAndFlush(stageType);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"StageType",stageType.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"StageType with name "+stageType.getName()+" already exists");
        	return "stageType/index";
        }
        catch(Exception e)
        {
        	String args[]={"StageType",stageType.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "stageType/index";
        }
        model.addAttribute("stageType", new StageType());
        return "stageType/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<StageType> listCategories()
    {
        List<StageType> list=stageTypeService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteStageType(@PathVariable Integer id, Model model)
    {
    	
    	stageTypeService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editStageType(@PathVariable Integer id, Model model)
    {
    	StageType stageType=stageTypeService.findOne(id);
    	model.addAttribute("stageType", stageType);
    	return "stageType/edit";
    }
   
}
