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
import org.tarak.pms.models.Vendor;
import org.tarak.pms.models.Measurement;
import org.tarak.pms.models.Tag;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/vendor")
@Controller
public class VendorController {

    @Autowired
    private ServiceInterface<Vendor, Integer> vendorService;

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
        return "vendor/index";
    }

    private void addVendor(Model model)
    {
    	List<Variant> variants=new ArrayList<Variant>();
		variants.add(new Variant());
		List<Tag> tags=new ArrayList<Tag>();
		tags.add(new Tag());
		Vendor vendor=new Vendor();
		/*vendor.setVariants(variants);
		vendor.setTags(tags);*/
        model.addAttribute("vendor", vendor);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("vendor"))
    	{
    		addVendor(model);
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
    public String addVariant(Vendor vendor, BindingResult result,Model model) {
        //vendor.getVariants(add(new Variant());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addTag"}, method = RequestMethod.POST )
    public String addTag(Vendor vendor, BindingResult result,Model model) {
        //vendor.getTags().add(new Tag());
        return index(model);
    }

    @RequestMapping(value = "/add", params={"removeVariant"}, method = RequestMethod.POST )
    public String removeVariant(Vendor vendor, BindingResult result,Model model) {
        //vendor.getVariants().add(new Variant());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addVendor(@Valid Vendor vendor, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	vendorService.saveAndFlush(vendor);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Vendor",vendor.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Vendor with name "+vendor.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	String args[]={"Vendor",vendor.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addVendor(model);
        return index(model);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Vendor> listCategories()
    {
        List<Vendor> list=vendorService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteVendor(@PathVariable Integer id, Model model)
    {
    	
    	vendorService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editVendor(@PathVariable Integer id, Model model)
    {
    	Vendor vendor=vendorService.findOne(id);
    	model.addAttribute("vendor", vendor);
    	prepareModel(model);
    	return "/vendor/edit";
    }
   
}
