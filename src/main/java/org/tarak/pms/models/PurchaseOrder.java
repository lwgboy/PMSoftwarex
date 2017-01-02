package org.tarak.pms.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class PurchaseOrder {

    @Id
    @Column(name = "purchaseOrder_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @Cascade({CascadeType.ALL})
    private Vendor vendor;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date poDate;
    
    private String transportCarrier;
    
    private String transportBookingLocation;
    
    private String transportBookingAddress;
    
    private String agency;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;
    
    private int quantityTolerance;
    
    private int dateTolerance;
    
    @ManyToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "Product_Line_Items", joinColumns = @JoinColumn(name = "purchaseOrder_id", referencedColumnName = "purchaseOrder_id"), inverseJoinColumns = @JoinColumn(name = "product_line_item_id", referencedColumnName = "product_line_item_id"))
    private List<ProductLineItem> productLineItems;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Date getPoDate() {
		return poDate;
	}

	public void setPoDate(Date poDate) {
		this.poDate = poDate;
	}

	public String getTransportCarrier() {
		return transportCarrier;
	}

	public void setTransportCarrier(String transportCarrier) {
		this.transportCarrier = transportCarrier;
	}

	public String getTransportBookingLocation() {
		return transportBookingLocation;
	}

	public void setTransportBookingLocation(String transportBookingLocation) {
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

	public List<ProductLineItem> getProductLineItems() {
		return productLineItems;
	}

	public void setProductLineItems(List<ProductLineItem> productLineItems) {
		this.productLineItems = productLineItems;
	}
}
