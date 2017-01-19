package org.tarak.pms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarak.pms.models.Warehouse;

/**
 * Created by Tarak on 12/7/2016.
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}