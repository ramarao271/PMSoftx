package org.erp.tarak.otherPayment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class OtherPaymentIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof OtherPayment)
			{
				OtherPayment otherPayment = (OtherPayment) object;
				finyear = otherPayment.getFinYear();
				query="SELECT MAX(otherPaymentId) as vlaue from OtherPayment where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof OtherPaymentItem)
			{
				OtherPaymentItem otherPaymentItem=(OtherPaymentItem)object;
				finyear=otherPaymentItem.getFinYear();
				query="SELECT MAX(otherPaymentId) as vlaue from OtherPaymentItem where FINANCIAL_YEAR='"
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
