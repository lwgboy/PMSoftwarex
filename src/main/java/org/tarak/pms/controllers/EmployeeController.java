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
import org.tarak.pms.models.Employee;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/employee")
@Controller
public class EmployeeController {

    @Autowired
    private ServiceInterface<Employee, Integer> employeeService;

    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "employee/index";
    }

    private void addEmployee(Model model)
    {
    	List<ContactPerson> contactPersons=new ArrayList<ContactPerson>();
    	contactPersons.add(new ContactPerson());
		List<Address> addresses=new ArrayList<Address>();
		addresses.add(new Address());
		Employee employee=new Employee();
		employee.setAddressList(addresses);
        model.addAttribute("employee", employee);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("employee"))
    	{
    		addEmployee(model);
    	}
	}
    
    @RequestMapping(value = "/add", params={"addAddresses"}, method = RequestMethod.POST )
    public String addTag(Employee employee, BindingResult result,Model model) {
        employee.getAddressList().add(new Address());
        return index(model);
    }

    @RequestMapping(value = "/add", params={"removeVariant"}, method = RequestMethod.POST )
    public String removeVariant(Employee employee, BindingResult result,Model model) {
        //employee.getVariants().add(new Variant());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addEmployee(@Valid Employee employee, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	employeeService.saveAndFlush(employee);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Employee",employee.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Employee with name "+employee.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	String args[]={"Employee",employee.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addEmployee(model);
        return index(model);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Employee> listCategories()
    {
        List<Employee> list=employeeService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteEmployee(@PathVariable Integer id, Model model)
    {
    	
    	employeeService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editEmployee(@PathVariable Integer id, Model model)
    {
    	Employee employee=employeeService.findOne(id);
    	model.addAttribute("employee", employee);
    	prepareModel(model);
    	return "employee/edit";
    }
}
