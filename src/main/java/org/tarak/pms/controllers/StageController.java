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
import org.tarak.pms.models.Stage;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/stage")
@Controller
public class StageController {

    @Autowired
    private ServiceInterface<Stage, Integer> stageService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "stage/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("stage"))
    	{
            model.addAttribute("stage", new Stage());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addStage(@Valid Stage stage, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "stage/index";
        }
        try
        {
        	stageService.saveAndFlush(stage);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Stage",stage.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Stage with name "+stage.getName()+" already exists");
        	return "stage/index";
        }
        catch(Exception e)
        {
        	String args[]={"Stage",stage.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "stage/index";
        }
        model.addAttribute("stage", new Stage());
        return "stage/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Stage> listCategories()
    {
        List<Stage> list=stageService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteStage(@PathVariable Integer id, Model model)
    {
    	
    	stageService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editStage(@PathVariable Integer id, Model model)
    {
    	Stage stage=stageService.findOne(id);
    	model.addAttribute("stage", stage);
    	return "stage/edit";
    }
   
}
