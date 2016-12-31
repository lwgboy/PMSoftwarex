package org.tarak.pms.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Design {

    @Id
    @Column(name = "design_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique=true)
    @NotNull
    @Size(min=3,message ="Design should have minimum 3 characters")
    private String name;

    private String description;
    
    @ManyToOne
    @JoinColumn(name="Primary_Unit")
    private Measurement primaryUnit;
    
    @ManyToOne
    @JoinColumn(name="Secondary_Unit")
    private Measurement SecondaryUnit;
    
    @ManyToMany(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(name = "Design_Variants", joinColumns = @JoinColumn(name = "design_id", referencedColumnName = "design_id"), inverseJoinColumns = @JoinColumn(name = "variant_id", referencedColumnName = "variant_id"))
    private List<Variant> variants;
    
    @ManyToMany(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinTable(name = "Design_tags", joinColumns = @JoinColumn(name = "design_id", referencedColumnName = "design_id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
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
