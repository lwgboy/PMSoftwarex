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
@IdClass(GoodsReceiveChallanId.class)
public class GoodsReceiveChallan implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "goodsReceiveChallanId", strategy = "org.tarak.pms.generators.GoodsReceiveChallanIdGenerator")
	@GeneratedValue(generator = "goodsReceiveChallanId")
	private int goodsReceiveChallanId;
	
	@Id
	private String finYear;
	
	@OneToOne
	@JoinColumns(
	{
		@JoinColumn(name = "purchaseOrderId",referencedColumnName="purchaseOrderId"),
		@JoinColumn(name = "Po_fin_year",referencedColumnName="finYear")
	})
	private PurchaseOrder purchaseOrder;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "GRC_DATE")
	private Date goodsReceiveChallanDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Vendor_Id")
	private Vendor vendor;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "GRC_GRCITEMS", inverseJoinColumns = {
			@JoinColumn(name = "GRCITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "GRCITEMS_goodsReceiveChallanId", referencedColumnName = "goodsReceiveChallanId"),
			@JoinColumn(name = "GRCITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "GRC_goodsReceiveChallanId", referencedColumnName = "goodsReceiveChallanId"),@JoinColumn(name = "GRC_GRCFinYear", referencedColumnName = "finYear") }

	)
	private List<GoodsReceiveChallanItem> goodsReceiveChallanItems;

	private double totalCost;
	
	private String warehouseLocation;
	
	private String stockPoint;
	
	private String gateNo;
	
	private String lrNo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lrDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Transporter_Id")
	private Transporter transporter;

	private double frieghtAmount;
	
	private int totalBalesReceived;
	
	@Type(type="boolean")
	private boolean processed;

	@Type(type = "boolean")
	private boolean saveItemDetails;
	
	public int getGoodsReceiveChallanId() {
		return goodsReceiveChallanId;
	}

	public void setGoodsReceiveChallanId(int goodsReceiveChallanId) {
		this.goodsReceiveChallanId = goodsReceiveChallanId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Date getGoodsReceiveChallanDate() {
		return goodsReceiveChallanDate;
	}

	public void setGoodsReceiveChallanDate(Date goodsReceiveChallanDate) {
		this.goodsReceiveChallanDate = goodsReceiveChallanDate;
	}

	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<GoodsReceiveChallanItem> getGoodsReceiveChallanItems() {
		return goodsReceiveChallanItems;
	}

	public void setGoodsReceiveChallanItems(List<GoodsReceiveChallanItem> goodsReceiveChallanItems) {
		this.goodsReceiveChallanItems = goodsReceiveChallanItems;
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

	public String getWarehouseLocation() {
		return warehouseLocation;
	}

	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}

	public String getGateNo() {
		return gateNo;
	}

	public void setGateNo(String gateNo) {
		this.gateNo = gateNo;
	}

	public String getLrNo() {
		return lrNo;
	}

	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}

	public Date getLrDate() {
		return lrDate;
	}

	public void setLrDate(Date lrDate) {
		this.lrDate = lrDate;
	}

	public Transporter getTransporter() {
		return transporter;
	}

	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}

	public double getFrieghtAmount() {
		return frieghtAmount;
	}

	public void setFrieghtAmount(double frieghtAmount) {
		this.frieghtAmount = frieghtAmount;
	}

	public int getTotalBalesReceived() {
		return totalBalesReceived;
	}

	public void setTotalBalesReceived(int totalBalesReceived) {
		this.totalBalesReceived = totalBalesReceived;
	}

	public String getStockPoint() {
		return stockPoint;
	}

	public void setStockPoint(String stockPoint) {
		this.stockPoint = stockPoint;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public boolean isSaveItemDetails() {
		return saveItemDetails;
	}

	public void setSaveItemDetails(boolean saveItemDetails) {
		this.saveItemDetails = saveItemDetails;
	}
	
	
}
