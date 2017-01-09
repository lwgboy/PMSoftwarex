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
import org.tarak.pms.models.Address;
import org.tarak.pms.models.ContactPerson;
import org.tarak.pms.models.Transporter;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/transporter")
@Controller
public class TransporterController {

    @Autowired
    private ServiceInterface<Transporter, Integer> transporterService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "transporter/index";
    }

    private void addTransporter(Model model)
    {
    	List<ContactPerson> contactPersons=new ArrayList<ContactPerson>();
    	contactPersons.add(new ContactPerson());
		List<Address> addresses=new ArrayList<Address>();
		addresses.add(new Address());
		Transporter transporter=new Transporter();
		transporter.setContactPersons(contactPersons);
		transporter.setAddressList(addresses);
        model.addAttribute("transporter", transporter);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("transporter"))
    	{
    		addTransporter(model);
    	}
	}
    @RequestMapping(value = "/add", params={"addContactPerson"}, method = RequestMethod.POST )
    public String addVariant(Transporter transporter, BindingResult result,Model model) {
        transporter.getContactPersons().add(new ContactPerson());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addAddresses"}, method = RequestMethod.POST )
    public String addTag(Transporter transporter, BindingResult result,Model model) {
        transporter.getAddressList().add(new Address());
        return index(model);
    }

    @RequestMapping(value = "/add", params={"removeVariant"}, method = RequestMethod.POST )
    public String removeVariant(Transporter transporter, BindingResult result,Model model) {
        //transporter.getVariants().add(new Variant());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addTransporter(@Valid Transporter transporter, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	transporterService.saveAndFlush(transporter);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Transporter",transporter.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Transporter with name "+transporter.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	String args[]={"Transporter",transporter.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addTransporter(model);
        return index(model);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Transporter> listCategories()
    {
        List<Transporter> list=transporterService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteTransporter(@PathVariable Integer id, Model model)
    {
    	
    	transporterService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTransporter(@PathVariable Integer id, Model model)
    {
    	Transporter transporter=transporterService.findOne(id);
    	model.addAttribute("transporter", transporter);
    	prepareModel(model);
    	return "/transporter/edit";
    }
}
