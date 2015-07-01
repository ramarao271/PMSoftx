package org.erp.tarak.productioninvoice;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ProductionInvoiceIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof ProductionInvoice)
			{
				ProductionInvoice productionInvoice = (ProductionInvoice) object;
				finyear = productionInvoice.getFinYear();
				query="SELECT MAX(productionInvoiceId) as vlaue from ProductionInvoice where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof ProductionInvoiceItem)
			{
				ProductionInvoiceItem productionInvoiceItem=(ProductionInvoiceItem)object;
				if(productionInvoiceItem.getProductionInvoiceId()>0)
				{
					return productionInvoiceItem.getProductionInvoiceId();
				}
				finyear=productionInvoiceItem.getFinYear();
				query="SELECT MAX(productionInvoiceId) as vlaue from ProductionInvoiceItem where FINANCIAL_YEAR='"
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
