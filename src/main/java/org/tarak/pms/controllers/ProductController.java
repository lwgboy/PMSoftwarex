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
import org.tarak.pms.models.Product;
import org.tarak.pms.models.Measurement;
import org.tarak.pms.models.Tag;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/product")
@Controller
public class ProductController {

    @Autowired
    private ServiceInterface<Product, Integer> productService;

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
        return "product/index";
    }

    private void addProduct(Model model)
    {
    	List<Variant> variants=new ArrayList<Variant>();
		variants.add(new Variant());
		List<Tag> tags=new ArrayList<Tag>();
		tags.add(new Tag());
		Product product=new Product();
		product.setVariants(variants);
		product.setTags(tags);
        model.addAttribute("product", product);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("product"))
    	{
    		addProduct(model);
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
    public String addVariant(Product product, BindingResult result,Model model) {
        product.getVariants().add(new Variant());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addTag"}, method = RequestMethod.POST )
    public String addTag(Product product, BindingResult result,Model model) {
        product.getTags().add(new Tag());
        return index(model);
    }

    @RequestMapping(value = "/add", params={"removeVariant"}, method = RequestMethod.POST )
    public String removeVariant(Product product, BindingResult result,Model model) {
        product.getVariants().add(new Variant());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addProduct(@Valid Product product, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	productService.saveAndFlush(product);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Product",product.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Product with name "+product.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	String args[]={"Product",product.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addProduct(model);
        return index(model);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Product> listCategories()
    {
        List<Product> list=productService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteProduct(@PathVariable Integer id, Model model)
    {
    	
    	productService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editProduct(@PathVariable Integer id, Model model)
    {
    	Product product=productService.findOne(id);
    	model.addAttribute("product", product);
    	prepareModel(model);
    	return "/product/edit";
    }
   
}
