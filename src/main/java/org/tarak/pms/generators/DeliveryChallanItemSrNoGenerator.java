package org.tarak.pms.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.tarak.pms.models.DeliveryChallanItem;

public class DeliveryChallanItemSrNoGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof DeliveryChallanItem)
			{
				DeliveryChallanItem deliveryChallanItem=(DeliveryChallanItem)object;
				finyear=deliveryChallanItem.getFinYear();
				if(deliveryChallanItem.getDeliveryChallanId()>0)
				{
					query="SELECT MAX(sr_no) as vlaue from goods_receive_challan_item where FINANCIAL_YEAR='"
							+ finyear + "' and deliveryChallanId="+ deliveryChallanItem.getDeliveryChallanId();
				}
				else
				{
					query="SELECT MAX(sr_no) as vlaue from goods_receive_challan_item where FINANCIAL_YEAR='"
							+ finyear + "'";
				}
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
