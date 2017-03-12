package org.tarak.pms.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Buyer {

    @Id
    @Column(name = "buyer_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique=true)
    @NotNull
    @Size(min=3,message ="Buyer should have minimum 3 characters")
    private String name;

    private String phone;
    
    private String email;
    
    @ManyToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "Buyer_ContactPersons", joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "buyer_id"), inverseJoinColumns = @JoinColumn(name = "contactPerson_id", referencedColumnName = "contactPerson_id"))
    private List<ContactPerson> contactPersons;

    @ManyToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "Buyer_Addresses", joinColumns = @JoinColumn(name = "buyer_id", referencedColumnName = "buyer_id"), inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "address_id"))
    private List<Address> addressList;
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ContactPerson> getContactPersons() {
		return contactPersons;
	}

	public void setContactPersons(List<ContactPerson> contactPersons) {
		this.contactPersons = contactPersons;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
		
	}


}
