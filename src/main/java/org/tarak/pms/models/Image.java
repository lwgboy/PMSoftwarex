package org.tarak.pms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Image {

    @Id
    @Column(name = "image_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rawImage;

    private String image;
    
    private String description;
    
    @Transient
    private Integer productId;
    
    @Transient
    private Integer variantId; 
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


	public String getRawImage() {
		return rawImage;
	}

	public void setRawImage(String rawImage) {
		this.rawImage = rawImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getVariantId() {
		return variantId;
	}

	public void setVariantId(Integer variantId) {
		this.variantId = variantId;
	}
	
	
}
