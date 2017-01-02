package org.tarak.pms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarak.pms.models.ProductLineItem;

/**
 * Created by Tarak on 12/7/2016.
 */
@Repository
public interface ProductLineItemRepository extends JpaRepository<ProductLineItem, Integer> {
}