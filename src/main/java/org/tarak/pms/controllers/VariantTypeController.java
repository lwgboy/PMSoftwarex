package org.tarak.pms.controllers;

import java.util.ArrayList;
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
import org.tarak.pms.models.VariantType;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/variantType")
@Controller
public class VariantTypeController {

    @Autowired
    private ServiceInterface<VariantType, Integer> variantTypeService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "variantType/index";
    }

    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("variantType"))
    	{
            model.addAttribute("variantType", new VariantType());    		
    	}
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addVariantType(@Valid VariantType variantType, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return "variantType/index";
        }
        try
        {
        	variantTypeService.saveAndFlush(variantType);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"VariantType",variantType.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"VariantType with name "+variantType.getName()+" already exists");
        	return "variantType/index";
        }
        catch(Exception e)
        {
        	String args[]={"VariantType",variantType.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return "variantType/index";
        }
        model.addAttribute("variantType", new VariantType());
        return "variantType/index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<VariantType> listCategories()
    {
        List<VariantType> list=variantTypeService.findAll();
        return list;
    }

    @RequestMapping(value = "/variantTypes", method = RequestMethod.GET )
    public @ResponseBody
    List<String> listVariantTypes()
    {
        List<VariantType> list=variantTypeService.findAll();
        List<String> variantTypes=new ArrayList<String>();
        for(VariantType vt: list)
        {
        	variantTypes.add(vt.getName());
        }
        return variantTypes;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteVariantType(@PathVariable Integer id, Model model)
    {
    	
    	variantTypeService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editVariantType(@PathVariable Integer id, Model model)
    {
    	VariantType variantType=variantTypeService.findOne(id);
    	model.addAttribute("variantType", variantType);
    	return "variantType/edit";
    }
   
}
