package org.tarak.pms.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by Tarak on 12/3/2016.
 */
@Entity
public class Stage {

    @Id
    @Column(name = "stage_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String duration;

    @ManyToOne
    @JoinColumn(columnDefinition="integer",name="type")
    private StageType type;
    
    private String description;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public StageType getType() {
		return type;
	}

	public void setType(StageType type) {
		this.type = type;
	}
	
	
}
