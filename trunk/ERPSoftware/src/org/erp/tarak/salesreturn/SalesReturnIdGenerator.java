package org.erp.tarak.salesreturn;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SalesReturnIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof SalesReturn)
			{
				SalesReturn salesReturn = (SalesReturn) object;
				finyear = salesReturn.getFinYear();
				query="SELECT MAX(salesReturnId) as vlaue from SalesReturn where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof SalesReturnItem)
			{
				SalesReturnItem salesReturnItem=(SalesReturnItem)object;
				finyear=salesReturnItem.getFinYear();
				query="SELECT MAX(salesReturnId) as vlaue from SalesReturnItem where FINANCIAL_YEAR='"
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
