package org.tarak.pms.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarak.pms.barCode.BarCode;
import org.tarak.pms.barCode.BarCodes;
import org.tarak.pms.models.Product;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.ServiceInterface;

import com.itextpdf.text.DocumentException;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/barCode")
@Controller
public class BarCodeController {

    @Autowired
    private ServiceInterface<Product, Integer> productService;

    @Autowired
    ServletContext context;
    
    @RequestMapping("/")
    public String index(Model model)
    {
    	model.addAttribute(new BarCode());
        return "barCode/index";
    }


	@RequestMapping(value = "/print", method = RequestMethod.POST )
    public String addBarCode(@Valid BarCode barCode, BindingResult bindingResult, Model model) throws IOException, DocumentException
    {
		BarCodes.createPdf("src/main/resources/public/pdf/pdf.pdf", barCode.getSelect());
		if(barCode.getSelect()!=null && !barCode.getSelect().isEmpty())
		{
			barCode.setShowLink(true);
		}
		return "barCode/index";
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Variant> listCategories()
    {
        List<Product> list=productService.findAll();
        List<Variant> variants=new LinkedList<Variant>();
        for(Product product : list)
        {
        	for(Variant variant: product.getVariants())
        	{
        		variant.setProductName(product.getName());
        		variants.add(variant);
        	}
        }
        return variants;
    }
}
