package org.tarak.pms.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.tarak.pms.models.SalesOrder;
import org.tarak.pms.models.SalesOrderItem;

public class SalesOrderIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof SalesOrder)
			{
				SalesOrder salesOrder = (SalesOrder) object;
				finyear = salesOrder.getFinYear();
				query="SELECT MAX(sales_order_id) as vlaue from sales_order where fin_year='"
						+ finyear + "'";
			}
			else if(object instanceof SalesOrderItem)
			{
				SalesOrderItem salesOrderItem=(SalesOrderItem)object;
				if(salesOrderItem.getSalesOrderId()>0)
				{
					return salesOrderItem.getSalesOrderId();
				}
				finyear=salesOrderItem.getFinYear();
				query="SELECT MAX(sales_order_id) as vlaue from sales_order_item where financial_year='"
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
