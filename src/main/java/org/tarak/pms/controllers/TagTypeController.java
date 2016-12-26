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
import org.tarak.pms.models.TagType;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/tagType")
@Controller
public class TagTypeController {

    @Autowired
    private ServiceInterface<TagType, Integer> tagTypeService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "tagType/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("tagType"))
    	{
            model.addAttribute("tagType", new TagType());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addTagType(@Valid TagType tagType, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "tagType/index";
        }
        try
        {
        	tagTypeService.saveAndFlush(tagType);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"TagType",tagType.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"TagType with name "+tagType.getName()+" already exists");
        	return "tagType/index";
        }
        catch(Exception e)
        {
        	String args[]={"TagType",tagType.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "tagType/index";
        }
        model.addAttribute("tagType", new TagType());
        return "tagType/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<TagType> listCategories()
    {
        List<TagType> list=tagTypeService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteTagType(@PathVariable Integer id, Model model)
    {
    	
    	tagTypeService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTagType(@PathVariable Integer id, Model model)
    {
    	TagType tagType=tagTypeService.findOne(id);
    	model.addAttribute("tagType", tagType);
    	return "tagType/edit";
    }
   
}
