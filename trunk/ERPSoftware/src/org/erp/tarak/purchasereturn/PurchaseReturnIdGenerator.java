package org.erp.tarak.purchasereturn;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

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
				query="SELECT MAX(purchaseReturnId) as vlaue from PurchaseReturn where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof PurchaseReturnItem)
			{
				PurchaseReturnItem purchaseReturnItem=(PurchaseReturnItem)object;
				finyear=purchaseReturnItem.getFinYear();
				query="SELECT MAX(purchaseReturnId) as vlaue from PurchaseReturnItem where FINANCIAL_YEAR='"
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
