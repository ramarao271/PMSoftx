package org.erp.tarak.salesPayment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SalesPaymentIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof SalesPayment)
			{
				SalesPayment salesPayment = (SalesPayment) object;
				finyear = salesPayment.getFinYear();
				query="SELECT MAX(salesPaymentId) as vlaue from SalesPayment where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof SalesPaymentItem)
			{
				SalesPaymentItem salesPaymentItem=(SalesPaymentItem)object;
				finyear=salesPaymentItem.getFinYear();
				query="SELECT MAX(salesPaymentId) as vlaue from SalesPaymentItem where FINANCIAL_YEAR='"
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
