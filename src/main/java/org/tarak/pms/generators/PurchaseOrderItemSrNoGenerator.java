package org.tarak.pms.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.tarak.pms.models.PurchaseOrderItem;

public class PurchaseOrderItemSrNoGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof PurchaseOrderItem)
			{
				PurchaseOrderItem purchaseOrderItem=(PurchaseOrderItem)object;
				finyear=purchaseOrderItem.getFinYear();
				if(purchaseOrderItem.getPurchaseOrderId()>0)
				{
					query="SELECT MAX(srN_no) as vlaue from purchase_order_item where FINANCIAL_YEAR='"
							+ finyear + "' and purchase_order_id="+ purchaseOrderItem.getPurchaseOrderId();
				}
				else
				{
					query="SELECT MAX(sr_no) as vlaue from purchase_order_item where FINANCIAL_YEAR='"
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
