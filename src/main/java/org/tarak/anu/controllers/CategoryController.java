package org.tarak.anu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarak.anu.models.Category;
import org.tarak.anu.services.ServiceInterface;

import javax.validation.Valid;
import java.util.List;

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
    public String deleteCategory(@RequestParam List<Integer> ids)
    {
        for(int id: ids)
        {
            categoryService.delete(id);
        }
        return "category/index";
    }
}
