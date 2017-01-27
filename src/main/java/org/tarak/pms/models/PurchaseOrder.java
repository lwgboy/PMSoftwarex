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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@IdClass(PurchaseOrderId.class)
public class PurchaseOrder implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "purchaseOrderId", strategy = "org.tarak.pms.generators.PurchaseOrderIdGenerator")
	@GeneratedValue(generator = "purchaseOrderId")
	private int purchaseOrderId;
	@Id
	private String finYear;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "PO_DATE")
	private Date purchaseOrderDate;

	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Vendor_Id")
	private Vendor vendor;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PO_POITEMS", inverseJoinColumns = {
			@JoinColumn(name = "POITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "POITEMS_purchaseOrderId", referencedColumnName = "purchaseOrderId"),
			@JoinColumn(name = "POITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "PO_purchaseOrderId", referencedColumnName = "purchaseOrderId"),@JoinColumn(name = "PO_POFinYear", referencedColumnName = "finYear") }

	)
	private List<PurchaseOrderItem> purchaseOrderItems;

	private double totalCost;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "TransportCarrier_Id")
	private TransportCarrier transportCarrier;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Warehouse_Id")
	private Warehouse transportBookingLocation;
	
	private String transportBookingAddress;
	
	private String agency;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;
	
	private int quantityTolerance;
	
	private int dateTolerance;
	@Type(type="boolean")
	private boolean processed;

	public int getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(int purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Date getPurchaseOrderDate() {
		return purchaseOrderDate;
	}

	public void setPurchaseOrderDate(Date purchaseOrderDate) {
		this.purchaseOrderDate = purchaseOrderDate;
	}

	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public List<PurchaseOrderItem> getPurchaseOrderItems() {
		return purchaseOrderItems;
	}

	public void setPurchaseOrderItems(List<PurchaseOrderItem> purchaseOrderItems) {
		this.purchaseOrderItems = purchaseOrderItems;
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


	public Warehouse getTransportBookingLocation() {
		return transportBookingLocation;
	}

	public void setTransportBookingLocation(Warehouse transportBookingLocation) {
		this.transportBookingLocation = transportBookingLocation;
	}

	public String getTransportBookingAddress() {
		return transportBookingAddress;
	}

	public void setTransportBookingAddress(String transportBookingAddress) {
		this.transportBookingAddress = transportBookingAddress;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getQuantityTolerance() {
		return quantityTolerance;
	}

	public void setQuantityTolerance(int quantityTolerance) {
		this.quantityTolerance = quantityTolerance;
	}

	public int getDateTolerance() {
		return dateTolerance;
	}

	public void setDateTolerance(int dateTolerance) {
		this.dateTolerance = dateTolerance;
	}

	public TransportCarrier getTransportCarrier() {
		return transportCarrier;
	}

	public void setTransportCarrier(TransportCarrier transportCarrier) {
		this.transportCarrier = transportCarrier;
	}
	
	
}
