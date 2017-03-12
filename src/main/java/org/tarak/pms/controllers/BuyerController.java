package org.tarak.pms.controllers;

import java.util.ArrayList;
import java.util.LinkedList;
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
import org.tarak.pms.models.Buyer;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/buyer")
@Controller
public class BuyerController {

    @Autowired
    private ServiceInterface<Buyer, Integer> buyerService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "buyer/index";
    }

    private void addBuyer(Model model)
    {
    	List<ContactPerson> contactPersons=new ArrayList<ContactPerson>();
    	contactPersons.add(new ContactPerson());
		List<Address> addresses=new ArrayList<Address>();
		addresses.add(new Address());
		Buyer buyer=new Buyer();
		buyer.setContactPersons(contactPersons);
		buyer.setAddressList(addresses);
        model.addAttribute("buyer", buyer);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("buyer"))
    	{
    		addBuyer(model);
    	}
	}
    @RequestMapping(value = "/add", params={"addContactPerson"}, method = RequestMethod.POST )
    public String addVariant(Buyer buyer, BindingResult result,Model model) {
        buyer.getContactPersons().add(new ContactPerson());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addAddresses"}, method = RequestMethod.POST )
    public String addTag(Buyer buyer, BindingResult result,Model model) {
        buyer.getAddressList().add(new Address());
        return index(model);
    }

    @RequestMapping(value = "/add", params={"removeVariant"}, method = RequestMethod.POST )
    public String removeVariant(Buyer buyer, BindingResult result,Model model) {
        //buyer.getVariants().add(new Variant());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addBuyer(@Valid Buyer buyer, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	buyerService.saveAndFlush(buyer);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Buyer",buyer.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Buyer with name "+buyer.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	String args[]={"Buyer",buyer.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addBuyer(model);
        return index(model);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Buyer> listCategories()
    {
        List<Buyer> list=buyerService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteBuyer(@PathVariable Integer id, Model model)
    {
    	
    	buyerService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editBuyer(@PathVariable Integer id, Model model)
    {
    	Buyer buyer=buyerService.findOne(id);
    	model.addAttribute("buyer", buyer);
    	prepareModel(model);
    	return "buyer/edit";
    }
    
    @RequestMapping(value = "/firm/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<String> buyerFirm(@PathVariable Integer id, Model model)
    {
    	Buyer buyer=buyerService.findOne(id);
    	List<String> list=new LinkedList<String>();
    	if(buyer!=null && buyer.getAddressList()!=null && buyer.getAddressList().size()>0)
    	{
    		for(Address address :buyer.getAddressList())
    		{
    			list.add(address.getFirm());
    		}
    	}
    	return list;
    }
}
