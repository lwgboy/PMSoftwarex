package org.tarak.pms.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Product implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "product_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique=true)
    @NotEmpty(message="Product name cannot be empty")
    @Size(min=3,message ="Product should have minimum 3 characters")
    private String name;

    private String description;
    
    @Valid
    @ManyToOne
    @NotNull(message="Primary Category cannot be empty")
    @JoinColumn(name="Category")
    private Category category;
    
    @Valid
    @ManyToOne
    @NotNull(message="Primary Division cannot be empty")
    @JoinColumn(name="Division")
    private Division division;
    
    @Valid
    @ManyToOne
    @NotNull(message="Primary Section cannot be empty")
    @JoinColumn(name="Section")
    private Section section;
    
    @ManyToOne
    @JoinColumn(name="Style")
    private Style style;
    
    @ManyToOne
    @JoinColumn(name="Brand")
    private Brand brand;
    
    @Valid
    @OneToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "Product_Variants", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"), inverseJoinColumns = @JoinColumn(name = "variant_id", referencedColumnName = "variant_id"))
    private List<Variant> variants;
    
    @OneToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "Product_tags", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
    private List<Tag> tags;

    private boolean trackInventory=true;
    
    private boolean active=true;
    
    @Valid
    @ManyToOne
    @NotNull(message="Product Type cannot be empty")
    @JoinColumn(name="Product_Type")
    private ProductType productType;
    
    @ManyToMany
    @JoinTable(name = "Product_Vendors", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"), inverseJoinColumns = @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id"))
    private List<Vendor> vendors;
    
    @Cascade({CascadeType.ALL})
    @OneToMany
    @JoinTable(name = "Product_Images", joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"), inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "image_id"))
    private List<Image> images;
    
    private double indicatoryCost;
    
    private boolean taxable;
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Variant> getVariants() {
		return variants;
	}

	public void setVariants(List<Variant> variants) {
		this.variants = variants;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public boolean isTrackInventory() {
		return trackInventory;
	}

	public void setTrackInventory(boolean trackInventory) {
		this.trackInventory = trackInventory;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendors) {
		this.vendors = vendors;
	}

	public double getIndicatoryCost() {
		return indicatoryCost;
	}

	public void setIndicatoryCost(double indicatoryCost) {
		this.indicatoryCost = indicatoryCost;
	}

	public boolean isTaxable() {
		return taxable;
	}

	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	
}
