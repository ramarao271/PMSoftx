package org.erp.tarak.productionorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class ProductionOrderIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof ProductionOrder)
			{
				ProductionOrder productionOrder = (ProductionOrder) object;
				finyear = productionOrder.getFinYear();
				query="SELECT MAX(productionOrderId) as vlaue from ProductionOrder where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof ProductionOrderItem)
			{
				ProductionOrderItem productionOrderItem=(ProductionOrderItem)object;
				if(productionOrderItem.getProductionOrderId()>0)
				{
					return productionOrderItem.getProductionOrderId();
				}
				finyear=productionOrderItem.getFinYear();
				query="SELECT MAX(productionOrderId) as vlaue from ProductionOrderItem where FINANCIAL_YEAR='"
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
