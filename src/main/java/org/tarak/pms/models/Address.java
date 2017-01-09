package org.tarak.pms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Address {

    @Id
    @Column(name = "address_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message="Address cannot be empty")
    @NotNull(message="Address cannot be null")
    @Size(min=3,message ="Adress line should have minimum 3 characters")
    private String addressLine;
    
    @Size(min=3,message ="Street should have minimum 3 characters")
    private String street;
    
    @Size(min=3,message ="City should have minimum 3 characters")
    private String city;

    @Size(min=3,message ="District should have minimum 3 characters")
    private String District;
    
    @Size(min=3,message ="Pincode should have minimum 3 characters")
    private String pincode;
    
    @Size(min=3,message ="State should have minimum 3 characters")
    private String state;
    
    @Size(min=3,message ="Country should have minimum 3 characters")
    private String country;
    
    private String tinNo;
    
    private String cstNo;
    
    public Integer getId() {
        return id;
    }

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return District;
	}

	public void setDistrict(String district) {
		District = district;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTinNo() {
		return tinNo;
	}

	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}

	public String getCstNo() {
		return cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}
    
}
