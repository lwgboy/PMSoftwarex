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
@IdClass(SalesOrderId.class)
public class SalesOrder implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "salesOrderId", strategy = "org.tarak.pms.generators.SalesOrderIdGenerator")
	@GeneratedValue(generator = "salesOrderId")
	private int salesOrderId;
	@Id
	private String finYear;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "SO_DATE")
	private Date salesOrderDate;

	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Buyer_Id")
	private Buyer buyer;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "SO_SOITEMS", inverseJoinColumns = {
			@JoinColumn(name = "SOITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "SOITEMS_salesOrderId", referencedColumnName = "salesOrderId"),
			@JoinColumn(name = "SOITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "SO_salesOrderId", referencedColumnName = "salesOrderId"),@JoinColumn(name = "SO_SOFinYear", referencedColumnName = "finYear") }

	)
	private List<SalesOrderItem> salesOrderItems;

	private double totalCost;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "TransportCarrier_Id")
	private TransportCarrier transportCarrier;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Warehouse_Id")
	private Warehouse transportBookingLocation;
	
	private String agency;
	
	private String contactPerson;
	
	private String priceType;
	
	private double totalDiscount;
	
	private String orderType;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date deliveryDate;
	
	private int quantityTolerance;
	
	private int dateTolerance;
	@Type(type="boolean")
	private boolean processed;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Employee_Id")
	private Employee employeeAttended;

	public int getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(int salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Date getSalesOrderDate() {
		return salesOrderDate;
	}

	public void setSalesOrderDate(Date salesOrderDate) {
		this.salesOrderDate = salesOrderDate;
	}

	
	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public List<SalesOrderItem> getSalesOrderItems() {
		return salesOrderItems;
	}

	public void setSalesOrderItems(List<SalesOrderItem> salesOrderItems) {
		this.salesOrderItems = salesOrderItems;
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

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public double getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(double totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public Employee getEmployeeAttended() {
		return employeeAttended;
	}

	public void setEmployeeAttended(Employee employeeAttended) {
		employeeAttended = employeeAttended;
	}
	
	
}
