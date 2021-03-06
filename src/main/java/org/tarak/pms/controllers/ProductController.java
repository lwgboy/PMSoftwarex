package org.tarak.pms.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.tarak.pms.models.Brand;
import org.tarak.pms.models.Image;
import org.tarak.pms.models.Product;
import org.tarak.pms.models.Tag;
import org.tarak.pms.models.Variant;
import org.tarak.pms.models.Vendor;
import org.tarak.pms.services.ServiceInterface;
import org.tarak.pms.services.VariantService;
import org.tarak.pms.utils.ProductUtils;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/product")
@Controller
public class ProductController {
	static Logger logger=Logger.getLogger(Product.class);
    @Autowired
    private ServiceInterface<Product, Integer> productService;

    @Autowired
    private ServiceInterface<Tag, Integer> tagService;
    
    @Autowired
    private ServiceInterface<Brand, Integer> brandService;
    
    @Autowired
    private VariantService variantService;
    
    @RequestMapping("/")
    public String index(Model model)
    {
    	prepareModel(model);
        return "product/index";
    }

    private void addProduct(Model model, boolean flag,Product p)
    {
    	/*List<Variant> variants=new ArrayList<Variant>();
    	Variant variant=new Variant();*/
    	/*VariantRoute variantRoute=new VariantRoute();
    	Route route = new Route();
    	variantRoute.setRoute(route);
    	List<VariantRoute> variantRoutes=new ArrayList<VariantRoute>();
    	variantRoutes.add(variantRoute);
    	variant.setVariantRoutes(variantRoutes);*/
    	//variants.add(variant);
		/*List<Tag> tags=new ArrayList<Tag>();
		tags.add(new Tag());
		List<Vendor> vendors=new ArrayList<Vendor>();
		vendors.add(new Vendor());*/
		Product product=new Product();
		if(!flag)
		{
			product.setCategory(p.getCategory());
			product.setDivision(p.getDivision());
			product.setSection(p.getSection());
			product.setStyle(p.getStyle());
			product.setBrand(p.getBrand());
		}
		/*product.setVariants(variants);
		product.setTags(tags);
		product.setVendors(vendors);*/
        model.addAttribute("product", product);
    }
    
    private void prepareModel(Model model) 
    {
    	if(!model.containsAttribute("product"))
    	{
    		addProduct(model,true,null);
    	}
    	if(!model.containsAttribute("variant_list"))
    	{
    		List<Variant> variants=variantService.findAll();
    		model.addAttribute("variant_list",variants);
    	}
    	if(!model.containsAttribute("tag_list"))
    	{
    		List<Tag> tags=tagService.findAll();
    		model.addAttribute("tag_list",tags);
    	}
	}

    @RequestMapping(value = "/add", params={"addVariant"}, method = RequestMethod.POST )
    public String addVariant(Product product, BindingResult result,Model model) {
    	 if(product.getVariants()==null || product.getVariants().isEmpty())
         {
         	List<Variant> variants=new LinkedList<Variant>();
         	product.setVariants(variants);
         }
    	Variant variant=new Variant();
    	/*VariantRoute variantRoute=new VariantRoute();
    	Route route = new Route();
    	variantRoute.setRoute(route);
    	List<VariantRoute> variantRoutes=new ArrayList<VariantRoute>();
    	variantRoutes.add(variantRoute);
    	variant.setVariantRoutes(variantRoutes);
    	*/product.getVariants().add(variant);
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addTag"}, method = RequestMethod.POST )
    public String addTag(Product product, BindingResult result,Model model) {
        product.getTags().add(new Tag());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"addVendor"}, method = RequestMethod.POST )
    public String addVendor(Product product, BindingResult result,Model model) {
        product.getVendors().add(new Vendor());
        return index(model);
    }
    
    @RequestMapping(value = "/add", params={"removeVariant"}, method = RequestMethod.POST )
    public String removeVariant(Product product, BindingResult result,Model model,@RequestParam int removeVariant) {
        int index=0;
    	for(Variant variant : product.getVariants())
        {
        	if(removeVariant==variant.getSrNo())
        	{
        		index=product.getVariants().indexOf(variant);
        	}
        }
    	product.getVariants().remove(index);
        return index(model);
    }

	@RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBrandVariants(Product product, Model model)
    {
		if(product.getBrand()!=null && product.getBrand().getId()!=null)
		{
			Brand brand=brandService.findOne(product.getBrand().getId());
			if(brand!=null)
			{
				product.setVariants(brand.getVariants());
				product.setTags(brand.getTags());
				product.getVariants().stream().forEach(variant -> variant.setId(null));
				product.getTags().stream().forEach(tag -> tag.setId(null));
			}
		}
		return index(model);
    }
    
    
	@RequestMapping(value = "/add", method = RequestMethod.POST,params={"save"})
    public String addProduct(@Valid Product product, BindingResult bindingResult,@RequestParam("imageFiles") MultipartFile[] uploadFiles, Model model)
    {
        if (bindingResult.hasErrors())
        {
    		return index(model);
        }
        try
        {
        	List<Tag> emptyTags=product.getTags().stream().filter(tag->tag.getType()==null).collect(Collectors.toList());
        	product.getTags().removeAll(emptyTags);
        	List<Vendor> emptyVendors=product.getVendors().stream().filter(vendor->vendor.getId()==null).collect(Collectors.toList());
        	product.getVendors().removeAll(emptyVendors);
        	ProductUtils.processSKU(product,variantService);
        	productService.saveAndFlush(product);
        	int count=1;
        	List<Image> images=new LinkedList<Image>();
        	for (MultipartFile multipartFile : uploadFiles) 
        	{
        		if(StringUtils.isEmpty(multipartFile.getOriginalFilename())) continue;
        		String imageName=product.getId()+"_"+count++ +multipartFile.getOriginalFilename();
        		String dir_path="src/main/resources/public/product_images/"+product.getId()+"/"+imageName;
        		File f=new File(dir_path);
                if (f.getParentFile() != null) 
                {
                	logger.info("parent direcotory exists ");
                	f.getParentFile().mkdirs();
                }
                Image image=new Image();
                try
                {
                	boolean compareResult = false;
                	if(f.exists())
                    {
                    	logger.info("File Exists");
                    	compareResult = FileUtils.contentEquals(f,ProductUtils.convert(multipartFile));
                    }
                	if(!compareResult)
                	{
                		BufferedOutputStream stream =
                    	          new BufferedOutputStream(new FileOutputStream(f));
                    	      stream.write(multipartFile.getBytes());
                    	      stream.close();
                	}

                } catch (IOException e) 
                {
                    e.printStackTrace();
                }
                image.setImage("/product_images/"+product.getId()+"/"+imageName);
                images.add(image);
            }
        	if(product.getImages()!=null)
        	{
        		product.getImages().addAll(images);
        	}
        	else
        	{
        		product.setImages(images);
        	}
        	productService.saveAndFlush(product);
        }
        catch(DataIntegrityViolationException e)
        {
        	String args[]={"Product",product.getName()};
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Product with name "+product.getName()+" already exists");
        	return index(model);
        }
        catch(Exception e)
        {
        	String args[]={"Product",product.getName()};
        	e.printStackTrace();
        	bindingResult.rejectValue("name", "error.alreadyExists",args ,"Unknown error! Please contact Administrator");
        	return index(model);
        }
        addProduct(model,false,product);
        return index(model);
    }

	@RequestMapping(value = "/list", method = RequestMethod.GET )
    public @ResponseBody
    List<Product> listCategories()
    {
        List<Product> list=productService.findAll();
        return list;
    }

	@RequestMapping(value = "/images/{id}", method = RequestMethod.GET )
    public @ResponseBody
    List<Image> listImages(@PathVariable Integer id)
    {
		List<Image> list=new LinkedList<Image>();
		if(id!=null)
		{
			Product product=productService.findOne(id);
			if(product!=null)
			{
				list=product.getImages();
			}
		}
        return list;
    }
	
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET )
    public String deleteProduct(@PathVariable Integer id, Model model)
    {
    	
    	productService.delete(id);
    	return index(model);
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editProduct(@PathVariable Integer id, Model model)
    {
    	Product product=productService.findOne(id);
    	model.addAttribute("product", product);
    	prepareModel(model);
    	return "product/edit";
    }

    @RequestMapping(value = "/clone/{id}", method = RequestMethod.GET)
    public String cloneProduct(@PathVariable Integer id, Model model)
    {
    	Product product=productService.findOne(id);
    	product.setId(null);
    	for(Variant variant : product.getVariants())
    	{
    		variant.setId(null);
    	}
    	for(Tag tag : product.getTags())
    	{
    		tag.setId(null);
    	}
    	for(Image image : product.getImages())
    	{
    		image.setId(null);
    	}
    	
    	model.addAttribute("product", product);
    	prepareModel(model);
    	return "product/index";
    }
    
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String upload(@Valid Product product, BindingResult bindingResult,
        @RequestParam("images") MultipartFile[] uploadFiles) throws Exception     
    {
    	String voidResponse = "{}";
    	return voidResponse;
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody String delete(@Valid Product product, BindingResult bindingResult,
        @RequestParam("images") MultipartFile[] uploadFiles) throws Exception     
    {
    	String voidResponse = "{}";
    	return voidResponse;
    }
    
    @RequestMapping(value = "/images/", method = RequestMethod.GET)
    public String images(Model model)
    {
    	List<Product> products=productService.findAll();
    	List<Image> images=new LinkedList<Image>();
    	products.forEach(product->{
    		product.getImages().forEach(image->{
    			image.setProductId(product.getId());
    		});
    		images.addAll(product.getImages());
    	});
    	model.addAttribute("images", images);
    	prepareModel(model);
    	return "product/images";
    }
}
