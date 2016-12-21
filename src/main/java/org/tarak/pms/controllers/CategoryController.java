package org.tarak.pms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.dao.DataIntegrityViolationException;
import org.tarak.pms.models.Category;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/category")
@Controller
public class CategoryController {

    @Autowired
    private ServiceInterface<Category, Integer> categoryService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "category/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("category"))
    	{
            model.addAttribute("category", new Category());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addCategory(@Valid Category category, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
        	//prepareModel(model);
    		return "category/index";
        }
        try
        {
        	categoryService.saveAndFlush(category);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Category",category.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Category with name "+category.getName()+" already exists");
        	return "category/index";
        }
        catch(Exception e)
        {
        	String args[]={"Category",category.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "category/index";
        }
        model.addAttribute("category", new Category());
        return "category/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Category> listCategories()
    {
        List<Category> list=categoryService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteCategory(@PathVariable Integer id, Model model)
    {
    	
    	categoryService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editCategory(@PathVariable Integer id, Model model)
    {
    	Category category=categoryService.findOne(id);
    	model.addAttribute("category", category);
    	return "category/edit";
    }
   
}
