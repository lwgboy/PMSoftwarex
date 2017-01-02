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
import org.tarak.pms.models.Route;
import org.tarak.pms.models.VariantRoute;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/variantRoute")
@Controller
public class VariantRouteController {

    @Autowired
    private ServiceInterface<VariantRoute, Integer> variantRouteService;

    @Autowired
    private ServiceInterface<Route, Integer> routeService;
    
    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "variantRoute/index";
    }

    private void addVariantRoute(Model model)
    {
    	List<Route> routes=new ArrayList<Route>();
		routes.add(new Route());
		VariantRoute variantRoute=new VariantRoute();
		//variantRoute.setRoutes(routes);
        model.addAttribute("variantRoute", variantRoute);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("variantRoute"))
    	{
    		addVariantRoute(model);
    	}
    	if(!model.containsAttribute("route_list"))
    	{
    		List<Route> routes=routeService.findAll();
    		model.addAttribute("route_list",routes);
    	}
	}

    @RequestMapping(value = "/add", params={"addRoute"}, method = RequestMethod.POST )
    public String addRoute(VariantRoute variantRoute, BindingResult result,Model model) {
        //variantRoute.getRoutes().add(new Route());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeRoute"}, method = RequestMethod.POST )
    public String removeRoute(VariantRoute variantRoute, BindingResult result,Model model) {
        //variantRoute.getRoutes().add(new Route());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addVariantRoute(@Valid VariantRoute variantRoute, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	variantRouteService.saveAndFlush(variantRoute);
        }
        catch(DataIntegrityViolationException e)
        {
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"VariantRoute with name "+variantRoute.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	//bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addVariantRoute(model);
        return index(model);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<VariantRoute> listCategories()
    {
        List<VariantRoute> list=variantRouteService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteVariantRoute(@PathVariable Integer id, Model model)
    {
    	
    	variantRouteService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editVariantRoute(@PathVariable Integer id, Model model)
    {
    	VariantRoute variantRoute=variantRouteService.findOne(id);
    	model.addAttribute("variantRoute", variantRoute);
    	prepareModel(model);
    	return "/variantRoute/edit";
    }
   
}
