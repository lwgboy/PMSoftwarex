package org.tarak.pms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.tarak.pms.models.Product;
import org.tarak.pms.models.Variant;
import org.tarak.pms.services.VariantService;

public class ProductUtils {
	
    @Autowired
    private static VariantService variantService;

	public static void processSKU(Product product)
    {
    	if(product.getVariants()!=null && product.getVariants().size()>0)
    	{
    		String prefix=product.getStyle().getName().substring(0, 3)+returnThreeDigitCode(product.getBrand().getId())+returnThreeDigitCode(product.getId());
    		for(Variant variant : product.getVariants())
    		{
    			int suffix=001;
    			if(variant.getSku()==null || "".equals(variant.getSku()))
    			{
    				prefix+=variant.getName().substring(0,3);
    				String sku=prefix+"001";
    				Variant variantDb=variantService.findBySku(sku);
    				if(variantDb!=null)
    				{
    					suffix=skuSuffix(prefix,suffix);
        				sku=prefix+suffix;
    					
    				}
    				variant.setSku(sku);
    			}
    		}
    	}
    }

	public static int skuSuffix(String sku,int suffix) 
	{
		sku+=returnThreeDigitCode(suffix);
		Variant variant=variantService.findBySku(sku);
		if(variant!=null)
		{
			return skuSuffix(sku,suffix+1);
    		
		}
		return suffix;
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
