package org.tarak.pms.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.tarak.pms.models.PurchaseInvoice;
import org.tarak.pms.models.PurchaseInvoiceItem;

public class PurchaseInvoiceIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof PurchaseInvoice)
			{
				PurchaseInvoice purchaseInvoice = (PurchaseInvoice) object;
				finyear = purchaseInvoice.getFinYear();
				query="SELECT MAX(purchase_invoice_id) as vlaue from purchase_invoice where fin_year='"
						+ finyear + "'";
			}
			else if(object instanceof PurchaseInvoiceItem)
			{
				PurchaseInvoiceItem purchaseInvoiceItem=(PurchaseInvoiceItem)object;
				if(purchaseInvoiceItem.getPurchaseInvoiceId()>0)
				{
					return purchaseInvoiceItem.getPurchaseInvoiceId();
				}
				finyear=purchaseInvoiceItem.getFinYear();
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
