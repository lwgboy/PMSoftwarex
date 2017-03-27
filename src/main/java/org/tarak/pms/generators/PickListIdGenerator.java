package org.tarak.pms.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.tarak.pms.models.PickList;
//import org.tarak.pms.models.PickListItem;

public class PickListIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof PickList)
			{
				PickList pickList = (PickList) object;
				finyear = pickList.getFinYear();
				query="SELECT MAX(sales_order_id) as vlaue from sales_order where fin_year='"
						+ finyear + "'";
			}
	/*		else if(object instanceof PickListItem)
			{
				PickListItem pickListItem=(PickListItem)object;
				if(pickListItem.getPickListId()>0)
				{
					return pickListItem.getPickListId();
				}
				finyear=pickListItem.getFinYear();
				query="SELECT MAX(sales_order_id) as vlaue from sales_order_item where financial_year='"
				+ finyear + "'";
			}
	*/		PreparedStatement ps = connection
					.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("vlaue");
				id = id + 1;
				return id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
