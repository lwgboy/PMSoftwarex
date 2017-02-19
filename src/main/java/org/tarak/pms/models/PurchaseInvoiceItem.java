package org.tarak.pms.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@IdClass(PurchaseInvoiceItemId.class)
public class PurchaseInvoiceItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	private int srNo;
	
	@Id
	@GenericGenerator(name = "purchaseInvoiceId", strategy = "org.tarak.pms.generators.PurchaseInvoiceIdGenerator")
	@GeneratedValue(generator = "purchaseInvoiceId")
	private int purchaseInvoiceId;
	
	@Id
	@Column(name = "Financial_Year")
	private String finYear;

	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Style")
	private Style style;

	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Brand")
	private Brand brand;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PI_PRODUCT_ITEM", 
		joinColumns = {
			@JoinColumn(name = "PIITEMS_SrNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "PIITEMS_purchaseInvoiceId", referencedColumnName = "purchaseInvoiceId"),
			@JoinColumn(name = "PIITEMS_Financial_Year", referencedColumnName = "Financial_Year") 
			}, 
		inverseJoinColumns = { 
			@JoinColumn(name = "PI_ProductItemId", referencedColumnName = "Product_Item_Id") 
			}

	)
	private List<ProductItem> productItems;

	private String description;
	
	private double quantity;
	
	@ManyToOne
	@JoinColumn(name = "Measurement")
	private Measurement measurement;
	
	private double rate;
	
	private double totalCost;
	
	private double discount;
	
	@Type(type = "boolean")
	private boolean processed;

	@Type(type = "boolean")
	private boolean approved;

	@Type(type = "boolean")
	private boolean defective;

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public int getPurchaseInvoiceId() {
		return purchaseInvoiceId;
	}

	public void setPurchaseInvoiceId(int purchaseInvoiceId) {
		this.purchaseInvoiceId = purchaseInvoiceId;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
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

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	public List<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isDefective() {
		return defective;
	}

	public void setDefective(boolean defective) {
		this.defective = defective;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	
}
