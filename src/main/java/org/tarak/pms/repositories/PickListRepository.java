package org.tarak.pms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tarak.pms.models.PickList;

/**
 * Created by Tarak on 12/7/2016.
 */
@Repository
public interface PickListRepository extends JpaRepository<PickList, Integer> {
	public PickList findByPickListIdAndFinYear(int pickListId, String finYear);
	public void deleteByPickListIdAndFinYear(int pickListId, String finYear);
}