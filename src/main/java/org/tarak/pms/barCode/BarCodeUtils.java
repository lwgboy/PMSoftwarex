package org.tarak.pms.barCode;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.tarak.pms.models.Product;
import org.tarak.pms.models.Variant;

import com.itextpdf.text.DocumentException;

public class BarCodeUtils 
{ 
	public static List<Variant> listVariants(List<Product> list)
	{
		List<Variant> variants=new LinkedList<Variant>();
        for(Product product : list)
        {
        	List<Variant> variantList=product.getVariants();
        	if(product.getVariants()!=null && !product.getVariants().isEmpty())
        	{
        		variants.addAll(populateProduct(variantList,product));
        	}
        }
        return variants;
	}
	
	public static List<Variant> populateProduct(List<Variant> variantList,Product product)
	{
		List<Variant> variants=new LinkedList<Variant>();
		for(Variant variant: variantList)
    	{
    		variant.setProductName(product.getName());
    		variants.add(variant);
    	}
		return variants;
	}
	
	public static void processBarCodes(BarCode barCode) throws IOException, DocumentException
	{
		BarCodes.createPdf("src/main/resources/public/pdf/pdf.pdf", barCode.getSelect());
		if(barCode.getSelect()!=null && !barCode.getSelect().isEmpty())
		{
			barCode.setShowLink(true);
		}
	}
}
