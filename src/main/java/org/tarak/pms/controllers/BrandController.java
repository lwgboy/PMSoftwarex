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
import org.tarak.pms.models.Brand;
import org.tarak.pms.models.Measurement;
import org.tarak.pms.models.Tag;
import org.tarak.pms.models.Variant;
import org.tarak.pms.models.VariantRoute;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/brand")
@Controller
public class BrandController {

    @Autowired
    private ServiceInterface<Brand, Integer> brandService;

    @Autowired
    private ServiceInterface<Variant, Integer> variantService;
    
    @Autowired
    private ServiceInterface<Tag, Integer> tagService;
    
    @Autowired
    private ServiceInterface<Measurement, Integer> measurementService;
    
    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "brand/index";
    }

    private void addBrand(Model model)
    {
    	List<Variant> variants=new ArrayList<Variant>();
		variants.add(new Variant());
		List<Tag> tags=new ArrayList<Tag>();
		tags.add(new Tag());
		Brand brand=new Brand();
		brand.setVariants(variants);
		brand.setTags(tags);
        model.addAttribute("brand", brand);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("brand"))
    	{
    		addBrand(model);
    	}
    	if(!model.containsAttribute("variant_list"))
    	{
    		List<Variant> variants=variantService.findAll();
    		model.addAttribute("variant_list",variants);
    	}
    	if(!model.containsAttribute("tag_list"))
    	{
    		List<Tag> tags=tagService.findAll();
    		model.addAttribute("tag_list",tags);
    	}
    	if(!model.containsAttribute("measurement_list"))
    	{
    		List<Measurement> measurements=measurementService.findAll();
    		model.addAttribute("measurement_list",measurements);
    	}
	}

    @RequestMapping(value = "/add", params={"addVariant"}, method = RequestMethod.POST )
    public String addVariant(Brand brand, BindingResult result,Model model) {
        brand.getVariants().add(new Variant());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addTag"}, method = RequestMethod.POST )
    public String addTag(Brand brand, BindingResult result,Model model) {
        brand.getTags().add(new Tag());
        return index(model);
    }

    @RequestMapping(value = "/add", params={"removeVariant"}, method = RequestMethod.POST )
    public String removeVariant(Brand brand, BindingResult result,Model model) {
        brand.getVariants().add(new Variant());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addBrand(@Valid Brand brand, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	/*List<Variant> variants=new ArrayList<Variant>();
        	for(Variant variant : brand.getVariants())
        	{
        		if(variant.getVariantRoute()==null)
            	{
            		variant.setVariantRoute(new VariantRoute());
            	}
        	}
        	brand.setVariants(variants);*/
        	brandService.saveAndFlush(brand);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Brand",brand.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Brand with name "+brand.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	String args[]={"Brand",brand.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addBrand(model);
        return index(model);
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
    	prepareModel(model);
    	return "/brand/edit";
    }
   
}
