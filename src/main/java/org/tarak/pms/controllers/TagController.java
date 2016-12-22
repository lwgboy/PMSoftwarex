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
import org.tarak.pms.models.Tag;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/tag")
@Controller
public class TagController {

    @Autowired
    private ServiceInterface<Tag, Integer> tagService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "tag/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("tag"))
    	{
            model.addAttribute("tag", new Tag());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addTag(@Valid Tag tag, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "tag/index";
        }
        try
        {
        	tagService.saveAndFlush(tag);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Tag",tag.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Tag with name "+tag.getName()+" already exists");
        	return "tag/index";
        }
        catch(Exception e)
        {
        	String args[]={"Tag",tag.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "tag/index";
        }
        model.addAttribute("tag", new Tag());
        return "tag/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Tag> listCategories()
    {
        List<Tag> list=tagService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteTag(@PathVariable Integer id, Model model)
    {
    	
    	tagService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTag(@PathVariable Integer id, Model model)
    {
    	Tag tag=tagService.findOne(id);
    	model.addAttribute("tag", tag);
    	return "tag/edit";
    }
   
}
