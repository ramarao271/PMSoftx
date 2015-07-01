package org.erp.tarak.balanceSheet;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class BalanceSheetIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor HiSession, Object object)
			throws HibernateException {
		Connection connection = HiSession.connection();
		try {
			String finyear="";
			String query="";
			if(object instanceof BalanceSheet)
			{
				BalanceSheet balanceSheet = (BalanceSheet) object;
				finyear = balanceSheet.getFinYear();
				query="SELECT MAX(balanceSheetId) as vlaue from BalanceSheet where finYear='"
						+ finyear + "'";
			}
			else if(object instanceof BalanceSheetItem)
			{
				BalanceSheetItem balanceSheetItem=(BalanceSheetItem)object;
				if(balanceSheetItem.getBalanceSheetId()>0)
				{
					return balanceSheetItem.getBalanceSheetId();
				}
				finyear=balanceSheetItem.getFinYear();
				query="SELECT MAX(balanceSheetId) as vlaue from BalanceSheetItem where FINANCIAL_YEAR='"
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
