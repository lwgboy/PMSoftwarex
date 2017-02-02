package org.tarak.pms.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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
        return "barCode/index";
    }


	@RequestMapping(value = "/print", method = RequestMethod.POST )
    public String addBarCode(@Valid BarCode barCode, BindingResult bindingResult, Model model) throws IOException, DocumentException
    {
		BarCodes.createPdf("/public/pdf.pdf", barCode.getSelect());
		return "redirect : /barCode/pdf/pdf.pdf";
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
    
    @RequestMapping("/pdf/{fileName:.+}")
    public void downloader(HttpServletRequest request, HttpServletResponse response,@PathVariable("fileName") String fileName) throws IOException {
     
     System.out.println("Downloading file :- " + fileName);

     String downloadFolder = context.getRealPath("/public/");
     ClassLoader classloader = Thread.currentThread().getContextClassLoader();
     File file = new File(classloader.getResource(fileName).getFile());

     //Path file = Paths.get(downloadFolder, fileName);
     // Check if file exists
     if (file.exists()) {
      // set content type 
      response.setContentType("application/pdf");
      // add response header
      response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
      
      /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
          while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
      response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));

       
      /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
      //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
       
      response.setContentLength((int)file.length());

      InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

      //Copy bytes from source to destination(outputstream in this example), closes both streams.
      FileCopyUtils.copy(inputStream, response.getOutputStream());
     } else {
      System.out.println("Sorry File not found!!!!");
     }
    }
}
