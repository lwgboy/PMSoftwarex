package org.tarak.pms.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Brand {

    @Id
    @Column(name = "brand_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique=true)
    @NotEmpty(message="Brand name cannot be empty")
    @Size(min=3,message ="Brand name should have minimum 3 characters")
    private String name;

    private String description;
    
    @ManyToOne
    @JoinColumn(columnDefinition="integer",name="Primary_Unit")
    private Measurement primaryUnit;
    
    @ManyToOne
    @JoinColumn(columnDefinition="integer",name="Secondary_Unit")
    private Measurement SecondaryUnit;
    
    @OneToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "Brand_Variants", joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "brand_id"), inverseJoinColumns = @JoinColumn(name = "variant_id", referencedColumnName = "variant_id"))
    private List<Variant> variants;
    
    @OneToMany
    @Cascade({CascadeType.ALL})
    @JoinTable(name = "Brand_tags", joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "brand_id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
    private List<Tag> tags;
    
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

	public Measurement getPrimaryUnit() {
		return primaryUnit;
	}

	public void setPrimaryUnit(Measurement primaryUnit) {
		this.primaryUnit = primaryUnit;
	}

	public Measurement getSecondaryUnit() {
		return SecondaryUnit;
	}

	public void setSecondaryUnit(Measurement secondaryUnit) {
		SecondaryUnit = secondaryUnit;
	}

	public List<Variant> getVariants() {
		return variants;
	}

	public void setVariants(List<Variant> variants) {
		this.variants = variants;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
}
