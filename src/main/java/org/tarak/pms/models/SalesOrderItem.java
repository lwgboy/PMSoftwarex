package org.tarak.pms.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@IdClass(SalesOrderItemId.class)
public class SalesOrderItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	private int srNo;
	
	@Id
	@GenericGenerator(name = "salesOrderId", strategy = "org.tarak.pms.generators.SalesOrderIdGenerator")
	@GeneratedValue(generator = "salesOrderId")
	private int salesOrderId;
	
	@Id
	@Column(name = "Financial_Year")
	private String finYear;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Style")
	private Style style;

	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Brand")
	private Brand brand;

	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Variant")
	private Variant variant;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Product")
	private Product product;
	
	private double discount;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;
	
	private String description;
	
	private double quantity;
	
	private double allocated;
	
	@Transient
	private double forwardOrderQuantity;
	
	@OneToOne
	@JoinColumn(name = "Measurement")
	private Measurement measurement;
	
	private double rate;
	
	private double totalCost;
	
	@Type(type = "boolean")
	private boolean processed;
	
	private boolean forwardOrder;
	
	@ElementCollection
	private List<String> barCodes;
	
	public List<String> getBarCodes() {
		return barCodes;
	}

	public void setBarCodes(List<String> barCodes) {
		this.barCodes = barCodes;
	}

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

	public int getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(int salesOrderId) {
		this.salesOrderId = salesOrderId;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public double getAllocated() {
		return allocated;
	}

	public void setAllocated(double allocated) {
		this.allocated = allocated;
	}

	public boolean isForwardOrder() {
		return forwardOrder;
	}

	public void setForwardOrder(boolean forwardOrder) {
		this.forwardOrder = forwardOrder;
	}

	public double getForwardOrderQuantity() {
		return forwardOrderQuantity;
	}

	public void setForwardOrderQuantity(double forwardOrderQuantity) {
		this.forwardOrderQuantity = forwardOrderQuantity;
	}
	
	
}
