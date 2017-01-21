package org.tarak.pms.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@IdClass(PurchaseOrderItemId.class)
public class PurchaseOrderItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@GenericGenerator(name = "purchaseOrderItemSrNo", strategy ="org.tarak.pms.generators.PurchaseOrderItemSrNoGenerator")
	@GeneratedValue(generator = "purchaseOrderItemSrNo")
	private int srNo;
	
	@Id
	@GenericGenerator(name = "purchaseOrderId", strategy = "org.tarak.pms.generators.PurchaseOrderIdGenerator")
	@GeneratedValue(generator = "purchaseOrderId")
	private int purchaseOrderId;
	
	@Id
	@Column(name = "Financial_Year")
	private String finYear;
	
	@OneToOne
	@JoinColumn(columnDefinition="integer",name = "Style")
	private Style style;

	@OneToOne
	@JoinColumn(columnDefinition="integer",name = "Brand")
	private Brand brand;

	@OneToOne
	@JoinColumn(columnDefinition="integer",name = "Variant")
	private Variant variant;
	
	@OneToOne
	@JoinColumn(columnDefinition="integer",name = "Product")
	private Product product;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private String description;
	
	private double quantity;
	
	@OneToOne
	@JoinColumn(name = "Measurement")
	private Measurement measurement;
	
	private double rate;
	
	private double totalCost;
	
	@Type(type = "boolean")
	private boolean processed;

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

	public int getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(int purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
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

	public Variant getVariant() {
		return variant;
	}

	public void setVariant(Variant variant) {
		this.variant = variant;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	
}
