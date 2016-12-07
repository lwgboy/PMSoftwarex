package org.tarak.anu.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tarak.anu.models.Category;
import org.tarak.anu.services.ServiceInterface;

import javax.validation.Valid;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/category")
@Controller
public class CategoryController {

    @Autowired
    private ServiceInterface<Category, Long> categoryService;

    @RequestMapping("/")
    public String index(Model model)
    {
        model.addAttribute("category", new Category());
        return "category/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST )
    public String bookGroupSubmit(@Valid Category category, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
            return "category/index";
        }
        categoryService.saveAndFlush(category);
        return "category/index";
    }
}
