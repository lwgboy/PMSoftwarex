package org.tarak.pms.models;

import java.io.Serializable;
import java.util.Date;
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
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@IdClass(DeliveryChallanItemId.class)
public class DeliveryChallanItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	private int srNo;
	
	private int poiSrNo;
	
	@Id
	@GenericGenerator(name = "deliveryChallanId", strategy = "org.tarak.pms.generators.DeliveryChallanIdGenerator")
	@GeneratedValue(generator = "deliveryChallanId")
	private int deliveryChallanId;
	
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
	@JoinTable(name = "DC_PRODUCT_ITEM", 
		joinColumns = {
			@JoinColumn(name = "DCITEMS_SrNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "DCITEMS_deliveryChallanId", referencedColumnName = "deliveryChallanId"),
			@JoinColumn(name = "DCITEMS_Financial_Year", referencedColumnName = "Financial_Year") 
			}, 
		inverseJoinColumns = { 
			@JoinColumn(name = "DC_ProductItemId", referencedColumnName = "Product_Item_Id") 
			}

	)
	private List<ProductItem> productItems;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;
	
	private String description;
	
	private double quantity;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Measurement")
	private Measurement measurement;
	
	private double rate;
	
	private double totalCost;
	
	@Type(type = "boolean")
	private boolean processed;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date poDate;

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

	public int getDeliveryChallanId() {
		return deliveryChallanId;
	}

	public void setDeliveryChallanId(int deliveryChallanId) {
		this.deliveryChallanId = deliveryChallanId;
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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public List<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public int getPoiSrNo() {
		return poiSrNo;
	}

	public void setPoiSrNo(int poiSrNo) {
		this.poiSrNo = poiSrNo;
	}

	
}
