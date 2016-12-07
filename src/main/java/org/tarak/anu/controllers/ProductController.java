package org.tarak.anu.controllers;

/**
 * Created by Tarak on 12/1/2016.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/product")
@Controller
public class ProductController {
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
}