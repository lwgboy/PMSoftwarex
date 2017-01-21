package org.tarak.pms.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.tarak.pms.models.Product;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/variant")
@Controller
public class VariantController {

    @Autowired
    private ServiceInterface<Variant, Integer> variantService;

    @Autowired
    private ServiceInterface<Product, Integer> productService;

    
    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "variant/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("variant"))
    	{
            model.addAttribute("variant", new Variant());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addVariant(@Valid Variant variant, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "variant/index";
        }
        try
        {
        	variantService.saveAndFlush(variant);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Variant",variant.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Variant with name "+variant.getName()+" already exists");
        	return "variant/index";
        }
        catch(Exception e)
        {
        	String args[]={"Variant",variant.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "variant/index";
        }
        model.addAttribute("variant", new Variant());
        return "variant/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Variant> listVariants()
    {
        List<Variant> list=variantService.findAll();
        return list;
    }

    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET )
    public @ResponseBody
    List<Variant> listVariantsByProduct(@PathVariable int id)
    {
    	Product product=productService.findOne(id);
        List<Variant> list=product.getVariants();
        return list;
    }

    
    @RequestMapping(value = "/variants", method = RequestMethod.GET )
    public @ResponseBody
    Map<Integer, String> listVariantsJson()
    {
        List<Variant> list=variantService.findAll();
        Map<Integer,String> variants=new LinkedHashMap<Integer,String>();
        for(Variant vt: list)
        {
        	variants.put(vt.getId(),vt.getName());
        }
        return variants;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteVariant(@PathVariable Integer id, Model model)
    {
    	
    	variantService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editVariant(@PathVariable Integer id, Model model)
    {
    	Variant variant=variantService.findOne(id);
    	model.addAttribute("variant", variant);
    	return "variant/edit";
    }
   
}
