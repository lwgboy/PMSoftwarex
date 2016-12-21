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
import org.tarak.pms.models.Section;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/section")
@Controller
public class SectionController {

    @Autowired
    private ServiceInterface<Section, Integer> sectionService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "section/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("section"))
    	{
            model.addAttribute("section", new Section());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addSection(@Valid Section section, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "section/index";
        }
        try
        {
        	sectionService.saveAndFlush(section);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Section",section.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Section with name "+section.getName()+" already exists");
        	return "section/index";
        }
        catch(Exception e)
        {
        	String args[]={"Section",section.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "section/index";
        }
        model.addAttribute("section", new Section());
        return "section/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Section> listCategories()
    {
        List<Section> list=sectionService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteSection(@PathVariable Integer id, Model model)
    {
    	
    	sectionService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editSection(@PathVariable Integer id, Model model)
    {
    	Section section=sectionService.findOne(id);
    	model.addAttribute("section", section);
    	return "section/edit";
    }
   
}
