package org.erp.tarak.salesinvoice;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SalesInvoiceIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof SalesInvoice)
			{
				SalesInvoice salesInvoice = (SalesInvoice) object;
				finyear = salesInvoice.getFinYear();
				query="SELECT MAX(salesInvoiceId) as vlaue from SalesInvoice where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof SalesInvoiceItem)
			{
				SalesInvoiceItem salesInvoiceItem=(SalesInvoiceItem)object;
				finyear=salesInvoiceItem.getFinYear();
				query="SELECT MAX(salesInvoiceId) as vlaue from SalesInvoiceItem where FINANCIAL_YEAR='"
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
