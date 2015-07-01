package org.erp.tarak.salesorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class SalesOrderIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof SalesOrder)
			{
				SalesOrder salesOrder = (SalesOrder) object;
				finyear = salesOrder.getFinYear();
				query="SELECT MAX(salesOrderId) as vlaue from SalesOrder where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof SalesOrderItem)
			{
				SalesOrderItem salesOrderItem=(SalesOrderItem)object;
				if(salesOrderItem.getSalesOrderId()>0)
				{
					return salesOrderItem.getSalesOrderId();
				}
				finyear=salesOrderItem.getFinYear();
				query="SELECT MAX(salesOrderId) as vlaue from SalesOrderItem where FINANCIAL_YEAR='"
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
