package org.tarak.pms.beans;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxFormForDelete {

	List<String> delete_item_ids=new ArrayList<String>();

	public List<String> getDelete_item_ids() {
		return delete_item_ids;
	}

	public void setDelete_item_ids(List<String> delete_item_ids) {
		this.delete_item_ids = delete_item_ids;
	}
}
