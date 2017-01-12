package org.tarak.pms.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@IdClass(PurchaseInvoiceItemId.class)
public class PurchaseInvoiceItem implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;
	@Id
	/*
	 * @GenericGenerator(name = "purchaseInvoiceItemSrNo", strategy =
	 * "org.erp.tarak.purchaseorder.PurchaseInvoiceItemSrNoGenerator")
	 * 
	 * @GeneratedValue(generator = "purchaseInvoiceItemSrNo")
	 */private int srNo;
	@Id
	@GenericGenerator(name = "purchaseInvoiceId", strategy = "org.tarak.pms.generators.PurchaseInvoiceIdGenerator")
	@GeneratedValue(generator = "purchaseInvoiceId")
	private int purchaseInvoiceId;
	@Id
	@Column(name = "Financial_Year")
	private String finYear;
	@ManyToOne
	@JoinColumn(name = "Category")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "Style")
	private Style style;

	@ManyToOne
	@JoinColumn(name = "Brand")
	private Brand brand;

	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Variant")
	private Variant variant;
	
	private String description;
	private double quantity;
	@ManyToOne
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

	
}
