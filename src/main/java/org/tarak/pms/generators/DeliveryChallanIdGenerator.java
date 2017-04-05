package org.tarak.pms.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.tarak.pms.models.DeliveryChallan;
import org.tarak.pms.models.DeliveryChallanItem;

public class DeliveryChallanIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof DeliveryChallan)
			{
				DeliveryChallan deliveryChallan = (DeliveryChallan) object;
				finyear = deliveryChallan.getFinYear();
				query="SELECT MAX(goods_receive_challan_id) as vlaue from goods_receive_challan where fin_year='"
						+ finyear + "'";
			}
			else if(object instanceof DeliveryChallanItem)
			{
				DeliveryChallanItem deliveryChallanItem=(DeliveryChallanItem)object;
				if(deliveryChallanItem.getDeliveryChallanId()>0)
				{
					return deliveryChallanItem.getDeliveryChallanId();
				}
				finyear=deliveryChallanItem.getFinYear();
				query="SELECT MAX(goods_receive_challan_id) as vlaue from goods_receive_challan_item where financial_year='"
				+ finyear + "'";
			}
			PreparedStatement ps = connection
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
