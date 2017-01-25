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
public class VariantRoute {

    @Id
    @Column(name = "variantRoute_id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(columnDefinition="integer",name="type")
    private Route route;
    
    @ManyToOne
    @JoinColumn(columnDefinition="integer",name="current_stage")
    private Stage currentStage;
    
	private int count;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}


}
