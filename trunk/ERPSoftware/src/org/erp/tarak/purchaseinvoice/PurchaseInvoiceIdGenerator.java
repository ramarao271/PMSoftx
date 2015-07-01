package org.erp.tarak.purchaseinvoice;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

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
				query="SELECT MAX(purchaseInvoiceId) as vlaue from PurchaseInvoice where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof PurchaseInvoiceItem)
			{
				PurchaseInvoiceItem purchaseInvoiceItem=(PurchaseInvoiceItem)object;
				finyear=purchaseInvoiceItem.getFinYear();
				query="SELECT MAX(purchaseInvoiceId) as vlaue from PurchaseInvoiceItem where FINANCIAL_YEAR='"
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
