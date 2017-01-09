package org.tarak.pms.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.tarak.pms.models.GoodsReceiveChallan;
import org.tarak.pms.models.GoodsReceiveChallanItem;

public class GoodsReceiveChallanIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof GoodsReceiveChallan)
			{
				GoodsReceiveChallan goodsReceiveChallan = (GoodsReceiveChallan) object;
				finyear = goodsReceiveChallan.getFinYear();
				query="SELECT MAX(goods_receive_challan_id) as vlaue from goods_receive_challan where fin_year='"
						+ finyear + "'";
			}
			else if(object instanceof GoodsReceiveChallanItem)
			{
				GoodsReceiveChallanItem goodsReceiveChallanItem=(GoodsReceiveChallanItem)object;
				if(goodsReceiveChallanItem.getGoodsReceiveChallanId()>0)
				{
					return goodsReceiveChallanItem.getGoodsReceiveChallanId();
				}
				finyear=goodsReceiveChallanItem.getFinYear();
				query="SELECT MAX(goods_receive_challan_id) as vlaue from goods_receive_challan_item where financial_year='"
				+ finyear + "'";
			}
			PreparedStatement ps = connection
					.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				long id = rs.getLong("vlaue");
				id = id + 1;
				return id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
