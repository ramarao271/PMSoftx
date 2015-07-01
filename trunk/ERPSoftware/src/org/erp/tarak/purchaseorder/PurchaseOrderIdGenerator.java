package org.erp.tarak.purchaseorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class PurchaseOrderIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof PurchaseOrder)
			{
				PurchaseOrder purchaseOrder = (PurchaseOrder) object;
				finyear = purchaseOrder.getFinYear();
				query="SELECT MAX(purchaseOrderId) as vlaue from PurchaseOrder where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof PurchaseOrderItem)
			{
				PurchaseOrderItem purchaseOrderItem=(PurchaseOrderItem)object;
				if(purchaseOrderItem.getPurchaseOrderId()>0)
				{
					return purchaseOrderItem.getPurchaseOrderId();
				}
				finyear=purchaseOrderItem.getFinYear();
				query="SELECT MAX(purchaseOrderId) as vlaue from PurchaseOrderItem where FINANCIAL_YEAR='"
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
