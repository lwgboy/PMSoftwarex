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
import javax.validation.Valid;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@IdClass(DeliveryChallanId.class)
public class DeliveryChallan implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "deliveryChallanId", strategy = "org.tarak.pms.generators.DeliveryChallanIdGenerator")
	@GeneratedValue(generator = "deliveryChallanId")
	private int deliveryChallanId;
	
	@Id
	private String finYear;
	@Valid
	@OneToOne
	@JoinColumns(
	{
		@JoinColumn(name = "SalesOrderId",referencedColumnName="SalesOrderId"),
		@JoinColumn(name = "Po_fin_year",referencedColumnName="finYear")
	})
	private SalesOrder SalesOrder;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DC_DATE")
	@Valid
	private Date deliveryChallanDate;

	@ManyToOne
	@Valid
	@JoinColumn(columnDefinition="integer",name = "Buyer_Id")
	private Buyer buyer;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "DC_DCITEMS", inverseJoinColumns = {
			@JoinColumn(name = "DCITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "DCITEMS_deliveryChallanId", referencedColumnName = "deliveryChallanId"),
			@JoinColumn(name = "DCITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "DC_deliveryChallanId", referencedColumnName = "deliveryChallanId"),@JoinColumn(name = "DC_DCFinYear", referencedColumnName = "finYear") }

	)
	private List<DeliveryChallanItem> deliveryChallanItems;

	private double totalCost;

	@ManyToOne
	@Valid
	@JoinColumn(columnDefinition="integer",name = "Warehouse_Id")
	private Warehouse warehouseLocation;
	
	@ManyToOne
	@Valid
	@JoinColumn(columnDefinition="integer",name = "StockPoint_Id")
	private StockPoint stockPoint;
	
	private String gateNo;
	
	private String lrNo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date lrDate;
	
	@ManyToOne
	@JoinColumn(name = "Transporter_Id")
	@Valid
	private Transporter transporter;

	private double frieghtAmount;
	
	private int totalBalesReceived;
	
	@Type(type="boolean")
	private boolean processed;

	@Type(type = "boolean")
	private boolean saveItemDetails;

	@Type(type = "boolean")
	private boolean verified;

	public int getDeliveryChallanId() {
		return deliveryChallanId;
	}

	public void setDeliveryChallanId(int deliveryChallanId) {
		this.deliveryChallanId = deliveryChallanId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Date getDeliveryChallanDate() {
		return deliveryChallanDate;
	}

	public void setDeliveryChallanDate(Date deliveryChallanDate) {
		this.deliveryChallanDate = deliveryChallanDate;
	}

	
	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public List<DeliveryChallanItem> getDeliveryChallanItems() {
		return deliveryChallanItems;
	}

	public void setDeliveryChallanItems(List<DeliveryChallanItem> deliveryChallanItems) {
		this.deliveryChallanItems = deliveryChallanItems;
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

	
	
	public StockPoint getStockPoint() {
		return stockPoint;
	}

	public void setStockPoint(StockPoint stockPoint) {
		this.stockPoint = stockPoint;
	}

	public SalesOrder getSalesOrder() {
		return SalesOrder;
	}

	public void setSalesOrder(SalesOrder SalesOrder) {
		this.SalesOrder = SalesOrder;
	}

	public boolean isSaveItemDetails() {
		return saveItemDetails;
	}

	public void setSaveItemDetails(boolean saveItemDetails) {
		this.saveItemDetails = saveItemDetails;
	}

	public Warehouse getWarehouseLocation() {
		return warehouseLocation;
	}

	public void setWarehouseLocation(Warehouse warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	
}
