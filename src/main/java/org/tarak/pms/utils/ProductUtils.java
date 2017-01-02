package org.tarak.pms.utils;

import org.tarak.pms.models.Product;
import org.tarak.pms.models.Variant;

public class ProductUtils {

	public static void processSKU(Product product)
    {
    	if(product.getVariants()!=null && product.getVariants().size()>0)
    	{
    		String prefix=product.getCategory().getName().substring(0,2)+product.getDivision().getName().substring(0, 2)+product.getSection().getName().substring(0, 2)+product.getStyle().getName().substring(0, 2)+product.getBrand().getName().substring(0,2)+product.getName().substring(0, 2);
    		for(Variant variant : product.getVariants())
    		{
    			int suffix=1;
    			if(variant.getSku()==null || "".equals(variant.getSku()))
    			{
    				suffix=skuSuffix(product,prefix+variant.getName().substring(0,2),suffix);
    				variant.setSku(prefix+variant.getName().substring(0,2)+suffix);
    			}
    		}
    	}
    }

	public static int skuSuffix(Product product,String sku,int suffix) 
	{
		if(product.getVariants()!=null && product.getVariants().size()>0)
    	{
    		for(Variant variant : product.getVariants())
    		{
    			if(variant.getSku()!=null && !"".equals(variant.getSku()))
    			{
    				if((sku+suffix).equals(variant.getSku()))
        			{
        				return skuSuffix(product,sku,suffix+1);
        			}
    			}
    		}
    	}
		return suffix;
	}

}
