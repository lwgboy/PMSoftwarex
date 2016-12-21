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
import org.tarak.pms.models.Brand;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/brand")
@Controller
public class BrandController {

    @Autowired
    private ServiceInterface<Brand, Integer> brandService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "brand/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("brand"))
    	{
            model.addAttribute("brand", new Brand());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addBrand(@Valid Brand brand, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "brand/index";
        }
        try
        {
        	brandService.saveAndFlush(brand);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Brand",brand.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Brand with name "+brand.getName()+" already exists");
        	return "brand/index";
        }
        catch(Exception e)
        {
        	String args[]={"Brand",brand.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "brand/index";
        }
        model.addAttribute("brand", new Brand());
        return "brand/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Brand> listCategories()
    {
        List<Brand> list=brandService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteBrand(@PathVariable Integer id, Model model)
    {
    	
    	brandService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBrand(@PathVariable Integer id, Model model)
    {
    	Brand brand=brandService.findOne(id);
    	model.addAttribute("brand", brand);
    	return "brand/edit";
    }
   
}
