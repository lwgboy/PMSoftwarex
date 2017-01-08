package org.tarak.pms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;


/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
@Table(name="user_info")
public class User {

    @Id
    @Column(name = "user_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique=true)
    @NotNull(message ="Username cannot be null")
    @NotEmpty(message ="Username cannot be empty")
    @Size(min=3,message ="Username should have minimum 3 characters")
    private String username;
    
    @NotNull(message ="Password cannot be null")
    @NotEmpty(message ="Password cannot be empty")
    private String password;
    
    private String financialYear;

    
    public Integer getId() {
        return id;
    }


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getFinancialYear() {
		return financialYear;
	}


	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}


	public void setId(Integer id) {
		this.id = id;
	}
    
    
}
