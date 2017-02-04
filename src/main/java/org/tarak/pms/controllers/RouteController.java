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
import org.tarak.pms.models.Stage;
import org.tarak.pms.services.ServiceInterface;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/route")
@Controller
public class RouteController {

    @Autowired
    private ServiceInterface<Route, Integer> routeService;

    @Autowired
    private ServiceInterface<Stage, Integer> stageService;
    
    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "route/index";
    }

    private void addRoute(Model model)
    {
    	List<Stage> stages=new ArrayList<Stage>();
		stages.add(new Stage());
		Route route=new Route();
		route.setStages(stages);
        model.addAttribute("route", route);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("route"))
    	{
    		addRoute(model);
    	}
    	if(!model.containsAttribute("stage_list"))
    	{
    		List<Stage> stages=stageService.findAll();
    		model.addAttribute("stage_list",stages);
    	}
	}

    @RequestMapping(value = "/add", params={"addStage"}, method = RequestMethod.POST )
    public String addStage(Route route, BindingResult result,Model model) {
        route.getStages().add(new Stage());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeStage"}, method = RequestMethod.POST )
    public String removeStage(Route route, BindingResult result,Model model) {
        route.getStages().add(new Stage());
        return index(model);
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.POST )
    public String addRoute(@Valid Route route, BindingResult bindingResult, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	routeService.saveAndFlush(route);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Route",route.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Route with name "+route.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	String args[]={"Route",route.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addRoute(model);
        return index(model);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Route> listCategories()
    {
        List<Route> list=routeService.findAll();
        return list;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteRoute(@PathVariable Integer id, Model model)
    {
    	
    	routeService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editRoute(@PathVariable Integer id, Model model)
    {
    	Route route=routeService.findOne(id);
    	model.addAttribute("route", route);
    	prepareModel(model);
    	return "route/edit";
    }
   
}
