package org.tarak.pms.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarak.pms.beans.CheckBoxFormForDelete;
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
        model.addAttribute("category", new Category());
        return "category/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addCategory(@Valid Category category, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
            return "category/index";
        }
        categoryService.saveAndFlush(category);
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

    @RequestMapping(value = "/delete", method = RequestMethod.POST )
    public String deleteCategory(@Valid CheckBoxFormForDelete form, BindingResult bindingResult, Model model)
    {
    	List<String> ids=form.getDelete_item_ids();
    	if(ids!=null && ids.size()>0)
    	{
    		for(String id: ids)
            {
                if(id!=null && !"".equals(id))
                {
                	int i=Integer.parseInt(id);
                	categoryService.delete(i);
                }
            }
    	}
    	return "redirect:/category/";
    }
}
