package org.tarak.pms.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@IdClass(PurchaseInvoiceId.class)
public class PurchaseInvoice implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "purchaseInvoiceId", strategy = "org.tarak.pms.generators.PurchaseInvoiceIdGenerator")
	@GeneratedValue(generator = "purchaseInvoiceId")
	private int purchaseInvoiceId;
	
	@Id
	private String finYear;
	
	@OneToOne
	@JoinColumns(
	{
		@JoinColumn(name = "goodsReceiveChallanId",referencedColumnName="goodsReceiveChallanId"),
		@JoinColumn(name = "GRC_fin_year",referencedColumnName="finYear")
	})
	private GoodsReceiveChallan goodsReceiveChallan;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date purchaseInvoiceDate;

	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Vendor_Id")
	private Vendor vendor;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PI_PIITEMS", inverseJoinColumns = {
			@JoinColumn(name = "PIITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "PIITEMS_purchaseInvoiceId", referencedColumnName = "purchaseInvoiceId"),
			@JoinColumn(name = "PIITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "PI_purchaseInvoiceId", referencedColumnName = "purchaseInvoiceId"),@JoinColumn(name = "PI_PIFinYear", referencedColumnName = "finYear") }

	)
	private List<PurchaseInvoiceItem> purchaseInvoiceItems;

	private double totalCost;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date supplierInvoiceDate;
	
	private String supplierInvoiceNo;
	
	
	@Type(type="boolean")
	private boolean processed;

	public int getPurchaseInvoiceId() {
		return purchaseInvoiceId;
	}

	public void setPurchaseInvoiceId(int purchaseInvoiceId) {
		this.purchaseInvoiceId = purchaseInvoiceId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Date getPurchaseInvoiceDate() {
		return purchaseInvoiceDate;
	}

	public void setPurchaseInvoiceDate(Date purchaseInvoiceDate) {
		this.purchaseInvoiceDate = purchaseInvoiceDate;
	}

	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<PurchaseInvoiceItem> getPurchaseInvoiceItems() {
		return purchaseInvoiceItems;
	}

	public void setPurchaseInvoiceItems(List<PurchaseInvoiceItem> purchaseInvoiceItems) {
		this.purchaseInvoiceItems = purchaseInvoiceItems;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	

	public GoodsReceiveChallan getGoodsReceiveChallan() {
		return goodsReceiveChallan;
	}

	public void setGoodsReceiveChallan(GoodsReceiveChallan goodsReceiveChallan) {
		this.goodsReceiveChallan = goodsReceiveChallan;
	}

	public Date getSupplierInvoiceDate() {
		return supplierInvoiceDate;
	}

	public void setSupplierInvoiceDate(Date supplierInvoiceDate) {
		this.supplierInvoiceDate = supplierInvoiceDate;
	}

	public String getSupplierInvoiceNo() {
		return supplierInvoiceNo;
	}

	public void setSupplierInvoiceNo(String supplierInvoiceNo) {
		this.supplierInvoiceNo = supplierInvoiceNo;
	}
	
}
