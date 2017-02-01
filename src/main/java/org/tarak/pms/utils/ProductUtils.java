package org.tarak.pms.utils;

import java.util.LinkedHashSet;
import java.util.Set;

import org.tarak.pms.models.Product;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.VariantService;

public class ProductUtils {
	
	public static void processSKU(Product product, VariantService variantService)
    {
    	if(product.getVariants()!=null && product.getVariants().size()>0)
    	{
    		String prefix=product.getStyle().getName().substring(0, 3)+returnThreeDigitCode(product.getBrand().getId())+product.getName().substring(0,3);
    		int suffix=1;
    		Set<String> prefixSet=new LinkedHashSet<String>();
    		for(Variant variant : product.getVariants())
    		{
    			if(variant.getSku()==null || "".equals(variant.getSku()))
    			{
    				String vprefix=prefix+variant.getName().substring(0,3);
    				if(!prefixSet.contains(vprefix))
    				{
    					prefixSet.add(vprefix);
    					suffix=1;
    				}
    				else
    				{
    					suffix++;
    				}
    				String suffString=skuSuffix(vprefix,suffix,variantService);
    				String sku=vprefix+suffString;
    				variant.setSku(sku);
    			}
    		}
    	}
    }

	public static String skuSuffix(String sku,int suffix,VariantService variantService) 
	{
		String threeDigits=returnThreeDigitCode(suffix);
		Variant variant=variantService.findBySku(sku+threeDigits);
		if(variant!=null)
		{
			return skuSuffix(sku,suffix+1,variantService);
    		
		}
		return threeDigits;
	}
	
	public static String returnThreeDigitCode(int code)
	{
		String c="";
		if(code/10<1)
		{
			c="00"+code;
		}
		else if(((code/10)>1) && ((code/10)<10))
		{
			c="0"+code;
		}
		else if((code/10>10) && (code/10<100))
		{
			return code+"";
		}
		return c;
	}
}
