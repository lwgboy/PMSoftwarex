package org.tarak.pms.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class ProductLineItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
    private Integer purchaseOrder_id;

/*    @Id
   	private String finYear;
    
    @Id
    @Column(columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int srNo;
    
    @Column(nullable = false,unique=true)
    @NotNull
    @Size(min=3,message ="Product should have minimum 3 characters")
    private String name;

    private String description;
    
    @ManyToOne
    @JoinColumn(name="Category")
    private Category category;
    
    @ManyToOne
    @JoinColumn(name="Style")
    private Style style;
    
    @ManyToOne
    @JoinColumn(name="Brand")
    private Brand brand;
    
    private int quantity;
    
    private double price;
    
    public String getName() {
        return name;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}*/

	public Integer getPurchaseOrder_id() {
		return purchaseOrder_id;
	}

	public void setPurchaseOrder_id(Integer purchaseOrder_id) {
		this.purchaseOrder_id = purchaseOrder_id;
	}
	
	
}
