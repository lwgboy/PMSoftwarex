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
@IdClass(PurchaseReturnId.class)
public class PurchaseReturn implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "purchaseReturnId", strategy = "org.tarak.pms.generators.PurchaseReturnIdGenerator")
	@GeneratedValue(generator = "purchaseReturnId")
	private int purchaseReturnId;
	
	@Id
	private String finYear;
	
	@OneToOne
	@JoinColumns(
	{
		@JoinColumn(name = "purchaseInvoiceId",referencedColumnName="purchaseInvoiceId"),
		@JoinColumn(name = "GRC_fin_year",referencedColumnName="finYear")
	})
	private PurchaseInvoice purchaseInvoice;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date purchaseReturnDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Vendor_Id")
	private Vendor vendor;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PR_PRITEMS", inverseJoinColumns = {
			@JoinColumn(name = "PRITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "PRITEMS_purchaseReturnId", referencedColumnName = "purchaseReturnId"),
			@JoinColumn(name = "PRITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "PR_purchaseReturnId", referencedColumnName = "purchaseReturnId"),@JoinColumn(name = "PR_PRFinYear", referencedColumnName = "finYear") }

	)
	private List<PurchaseReturnItem> purchaseReturnItems;

	private double totalCost;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date supplierInvoiceDate;
	
	private String supplierInvoiceNo;
	
	
	@Type(type="boolean")
	private boolean processed;

	public int getPurchaseReturnId() {
		return purchaseReturnId;
	}

	public void setPurchaseReturnId(int purchaseReturnId) {
		this.purchaseReturnId = purchaseReturnId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Date getPurchaseReturnDate() {
		return purchaseReturnDate;
	}

	public void setPurchaseReturnDate(Date purchaseReturnDate) {
		this.purchaseReturnDate = purchaseReturnDate;
	}

	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<PurchaseReturnItem> getPurchaseReturnItems() {
		return purchaseReturnItems;
	}

	public void setPurchaseReturnItems(List<PurchaseReturnItem> purchaseReturnItems) {
		this.purchaseReturnItems = purchaseReturnItems;
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

	

	public PurchaseInvoice getPurchaseInvoice() {
		return purchaseInvoice;
	}

	public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		this.purchaseInvoice = purchaseInvoice;
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
