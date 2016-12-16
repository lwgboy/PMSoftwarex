package org.tarak.pms.beans;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class CheckBoxFormForDelete {

	@NotEmpty(message="Select atleast one record")
	List<String> delete_item_ids;

	public List<String> getDelete_item_ids() {
		return delete_item_ids;
	}

	public void setDelete_item_ids(List<String> delete_item_ids) {
		this.delete_item_ids = delete_item_ids;
	}
}
