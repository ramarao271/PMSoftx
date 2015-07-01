package org.erp.tarak.purchasePayment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class PurchasePaymentIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof PurchasePayment)
			{
				PurchasePayment purchasePayment = (PurchasePayment) object;
				finyear = purchasePayment.getFinYear();
				query="SELECT MAX(purchasePaymentId) as vlaue from PurchasePayment where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof PurchasePaymentItem)
			{
				PurchasePaymentItem purchasePaymentItem=(PurchasePaymentItem)object;
				finyear=purchasePaymentItem.getFinYear();
				query="SELECT MAX(purchasePaymentId) as vlaue from PurchasePaymentItem where FINANCIAL_YEAR='"
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
