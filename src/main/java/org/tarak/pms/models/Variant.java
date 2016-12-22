package org.tarak.pms.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Variant {

    @Id
    @Column(name = "variant_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique=true)
    @NotNull
    @Size(min=3,message ="VariantType should have minimum 3 characters")
    private String name;
    
    @ManyToOne
    @JoinColumn(name="type")
    private VariantType type;

    private String description;
    
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
