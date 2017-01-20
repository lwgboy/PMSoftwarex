package org.tarak.pms.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Variant {

    @Id
    @Column(name = "variant_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String name;
    
    private String description;
    
    @OneToOne
    @JoinColumn(name="type")
    private VariantType type;
    
    @OneToOne
    @JoinColumn(columnDefinition="integer",name="Product")
    private Product product;

    private double wspMargin;
    
    private double rspMargin;
    
    private double wspPrice;
    
    private double rspPrice;
    
    private String sku;
    
    private int quantity;
    
    @ManyToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "Variant_variantRoutes", joinColumns = @JoinColumn(name = "variant_id", referencedColumnName = "variant_id"), inverseJoinColumns = @JoinColumn(name = "variantRoute_id", referencedColumnName = "variantRoute_id"))
    private List<VariantRoute> variantRoutes;
    
    private boolean assignVariantRoute;
    
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

	public VariantType getType() {
		return type;
	}

	public void setType(VariantType type) {
		this.type = type;
	}

	public double getMargin() {
		return wspMargin;
	}

	public void setMargin(double margin) {
		this.wspMargin = margin;
	}

	public double getPrice() {
		return wspPrice;
	}

	public void setPrice(double price) {
		this.wspPrice = price;
	}

	public double getWspMargin() {
		return wspMargin;
	}

	public void setWspMargin(double wspMargin) {
		this.wspMargin = wspMargin;
	}

	public double getRspMargin() {
		return rspMargin;
	}

	public void setRspMargin(double rspMargin) {
		this.rspMargin = rspMargin;
	}

	public double getWspPrice() {
		return wspPrice;
	}

	public void setWspPrice(double wspPrice) {
		this.wspPrice = wspPrice;
	}

	public double getRspPrice() {
		return rspPrice;
	}

	public void setRspPrice(double rspPrice) {
		this.rspPrice = rspPrice;
	}

	public boolean isAssignVariantRoute() {
		return assignVariantRoute;
	}

	public void setAssignVariantRoute(boolean assignVariantRoute) {
		this.assignVariantRoute = assignVariantRoute;
	}

	public List<VariantRoute> getVariantRoutes() {
		return variantRoutes;
	}

	public void setVariantRoutes(List<VariantRoute> variantRoutes) {
		this.variantRoutes = variantRoutes;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
