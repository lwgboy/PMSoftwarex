package org.tarak.pms.services;


import org.tarak.pms.models.Variant;

/**
 * Created by Tarak on 12/7/2016.
 */
public interface VariantService extends ServiceInterface<Variant, Integer> {
	public Variant findBySku(String sku);
}
