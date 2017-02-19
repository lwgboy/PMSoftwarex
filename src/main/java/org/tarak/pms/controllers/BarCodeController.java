package org.tarak.pms.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarak.pms.barCode.BarCode;
import org.tarak.pms.barCode.BarCodeUtils;
import org.tarak.pms.barCode.BarCodes;
import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.GoodsReceiveChallanItem;
import org.tarak.pms.models.Product;
import org.tarak.pms.models.ProductItem;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.GoodsReceiveChallanService;
import org.tarak.pms.services.ServiceInterface;
import org.tarak.pms.utils.UserUtils;

import com.itextpdf.text.DocumentException;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/barCode")
@Controller
public class BarCodeController {

	static Logger logger=Logger.getLogger(BarCodes.class);
    @Autowired
    private ServiceInterface<Product, Integer> productService;
    
    @Autowired
    private GoodsReceiveChallanService goodsReceiveChallanService;

    @Autowired
  	private HttpSession session;
    
    @RequestMapping("/")
    public String index(Model model)
    {
    	model.addAttribute(new BarCode());
        return "barCode/index";
    }
    
    @RequestMapping("/grc")
    public String grc(Model model)
    {
    	model.addAttribute(new BarCode());
        return "barCode/grc";
    }


	@RequestMapping(value = "/print", method = RequestMethod.POST,produces = "application/pdf" )
	@ResponseBody
	public FileSystemResource addBarCode(@Valid BarCode barCode, BindingResult bindingResult, Model model) throws IOException, DocumentException
    {
		String userName=UserUtils.getUserName(session);
		BarCodeUtils.processBarCodes(barCode,userName);
		logger.info(barCode.getFileName());
		return new FileSystemResource(barCode.getFileName());
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Variant> listCategories()
    {
        List<Product> list=productService.findAll();
        List<Variant> variants=BarCodeUtils.listVariants(list);
        return variants;
    }
    
    
    @RequestMapping(value = "/list/{goodsReceiveChallanId}", method = RequestMethod.GET )
    public @ResponseBody
    List<Variant> listVariants(@PathVariable Integer goodsReceiveChallanId)
    {
    	List<Variant> variants=new LinkedList<Variant>();
    	if(goodsReceiveChallanId>0)
    	{
    		String finYear=UserUtils.getFinancialYear(session);
        	GoodsReceiveChallan goodsReceiveChallan=goodsReceiveChallanService.findByGoodsReceiveChallanIdAndFinYear(goodsReceiveChallanId, finYear);
        	if(goodsReceiveChallan.getGoodsReceiveChallanItems()!=null && !goodsReceiveChallan.getGoodsReceiveChallanItems().isEmpty())
        	{
        		for(GoodsReceiveChallanItem item: goodsReceiveChallan.getGoodsReceiveChallanItems())
        		{
        			for(ProductItem pi : item.getProductItems())
        			{
        				pi.getVariant().setProductName(pi.getProduct().getName());
        				String barCode=pi.getVariant().getSku()+"_"+goodsReceiveChallan.getGoodsReceiveChallanId();
        				pi.getVariant().setBarCode(barCode);
        				variants.add(pi.getVariant());
        			}
        		}
        	}
    	}
    	return variants;
    }
    
    @RequestMapping(value = "/print", method = RequestMethod.POST,params={"grcId"} )
    public String getVariantsForGRC(@Valid BarCode barCode, BindingResult bindingResult, Model model) throws IOException, DocumentException
    {
		return "barCode/grc";
    }
}
