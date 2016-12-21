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
import org.tarak.pms.models.Style;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/style")
@Controller
public class StyleController {

    @Autowired
    private ServiceInterface<Style, Integer> styleService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "style/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("style"))
    	{
            model.addAttribute("style", new Style());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addStyle(@Valid Style style, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "style/index";
        }
        try
        {
        	styleService.saveAndFlush(style);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Style",style.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Style with name "+style.getName()+" already exists");
        	return "style/index";
        }
        catch(Exception e)
        {
        	String args[]={"Style",style.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "style/index";
        }
        model.addAttribute("style", new Style());
        return "style/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Style> listCategories()
    {
        List<Style> list=styleService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteStyle(@PathVariable Integer id, Model model)
    {
    	
    	styleService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editStyle(@PathVariable Integer id, Model model)
    {
    	Style style=styleService.findOne(id);
    	model.addAttribute("style", style);
    	return "style/edit";
    }
   
}
