package org.tarak.pms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/")
@Controller
public class HomeController {

   
    @RequestMapping("/")
    public String index(Model model)
    {
        return "index";
    }

    
   
}
