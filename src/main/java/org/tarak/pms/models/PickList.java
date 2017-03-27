package org.tarak.pms.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@IdClass(PickListId.class)
public class PickList implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GenericGenerator(name = "pickListId", strategy = "org.tarak.pms.generators.PickListIdGenerator")
	@GeneratedValue(generator = "pickListId")
	private int pickListId;
	@Id
	private String finYear;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "SO_DATE")
	private Date pickListDate;
	
	@OneToMany
	@Fetch(value = FetchMode.SELECT)
	@JoinTable(name = "PL_SOITEMS", inverseJoinColumns = {
			@JoinColumn(name = "SOITEMS_srNo", referencedColumnName = "srNo"),
			@JoinColumn(name = "SOITEMS_pickListId", referencedColumnName = "salesOrderId"),
			@JoinColumn(name = "SOITEMS_Financial_Year", referencedColumnName = "Financial_Year") }, joinColumns = { @JoinColumn(name = "SO_pickListId", referencedColumnName = "pickListId"),@JoinColumn(name = "SO_SOFinYear", referencedColumnName = "finYear") }

	)
	private List<SalesOrderItem> salesOrderItems;

	private double totalCost;
	
	@ManyToOne
	@JoinColumn(columnDefinition="integer",name = "Warehouse_Id")
	private Warehouse transportBookingLocation;
	
	@Type(type="boolean")
	private boolean processed;

	public int getPickListId() {
		return pickListId;
	}

	public void setPickListId(int pickListId) {
		this.pickListId = pickListId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public Date getPickListDate() {
		return pickListDate;
	}

	public void setPickListDate(Date pickListDate) {
		this.pickListDate = pickListDate;
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
	
}
