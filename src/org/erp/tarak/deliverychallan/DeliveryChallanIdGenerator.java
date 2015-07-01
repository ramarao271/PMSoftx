package org.erp.tarak.deliverychallan;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

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
				query="SELECT MAX(deliveryChallanId) as vlaue from DeliveryChallan where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof DeliveryChallanItem)
			{
				DeliveryChallanItem deliveryChallanItem=(DeliveryChallanItem)object;
				finyear=deliveryChallanItem.getFinYear();
				query="SELECT MAX(deliveryChallanId) as vlaue from DeliveryChallanItem where FINANCIAL_YEAR='"
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
