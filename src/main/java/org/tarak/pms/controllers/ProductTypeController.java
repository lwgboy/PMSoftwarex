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
import org.tarak.pms.models.ProductType;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/productType")
@Controller
public class ProductTypeController {

    @Autowired
    private ServiceInterface<ProductType, Integer> productTypeService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "productType/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("productType"))
    	{
            model.addAttribute("productType", new ProductType());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addProductType(@Valid ProductType productType, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "productType/index";
        }
        try
        {
        	productTypeService.saveAndFlush(productType);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"ProductType",productType.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"ProductType with name "+productType.getName()+" already exists");
        	return "productType/index";
        }
        catch(Exception e)
        {
        	String args[]={"ProductType",productType.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "productType/index";
        }
        model.addAttribute("productType", new ProductType());
        return "productType/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<ProductType> listCategories()
    {
        List<ProductType> list=productTypeService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteProductType(@PathVariable Integer id, Model model)
    {
    	
    	productTypeService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editProductType(@PathVariable Integer id, Model model)
    {
    	ProductType productType=productTypeService.findOne(id);
    	model.addAttribute("productType", productType);
    	return "productType/edit";
    }
   
}
