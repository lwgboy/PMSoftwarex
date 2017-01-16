package org.tarak.pms.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.tarak.pms.models.PurchaseReturn;
import org.tarak.pms.models.PurchaseReturnItem;

public class PurchaseReturnIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof PurchaseReturn)
			{
				PurchaseReturn purchaseReturn = (PurchaseReturn) object;
				finyear = purchaseReturn.getFinYear();
				query="SELECT MAX(purchase_invoice_id) as vlaue from purchase_invoice where fin_year='"
						+ finyear + "'";
			}
			else if(object instanceof PurchaseReturnItem)
			{
				PurchaseReturnItem purchaseReturnItem=(PurchaseReturnItem)object;
				if(purchaseReturnItem.getPurchaseReturnId()>0)
				{
					return purchaseReturnItem.getPurchaseReturnId();
				}
				finyear=purchaseReturnItem.getFinYear();
				query="SELECT MAX(purchase_invoice_id) as vlaue from purchase_invoice_item where financial_year='"
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
