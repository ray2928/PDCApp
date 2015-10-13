package edu.missouri.cf.pdcapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

import edu.missouri.cf.pdcapp.dbconnect.Pools;



public class ListItems extends TableQuery{
	static HashMap<String, BeanContainer<String, ListBean>> cache = new HashMap<String, BeanContainer<String, ListBean>>();

	public ListItems(String tableName, JDBCConnectionPool connectionPool) {
		super(tableName, connectionPool);
		// TODO Auto-generated constructor stub
	}
	
	private static PreparedStatement getPreparedStatementForListForCampus(Connection conn, String listName, String campusId) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("select " 
													+"li.ID, "
													+"li.DESCRIPTION, "
													+"li.VALUE, "
													+"li.SYSTEMVALUE, "
													+"li.ISDEFAULT "
													+"from listitems li "
													+"inner join lists l on l.ID = li.LISTID "
													+"where l.DESCRIPTION = 'Country Codes' "
													+"order by li.DESCRIPTION");
		System.out.println();				
		return stmt;
	}
	
	private static PreparedStatement getPreparedStatementForList(Connection conn, String listName) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("select " 
													+"li.ID, "
													+"li.DESCRIPTION, "
													+"li.VALUE, "
													+"li.SYSTEMVALUE, "
													+"li.ISDEFAULT "
													+"from listitems li "
													+"inner join lists l on l.ID = li.LISTID "
													+"where l.DESCRIPTION = ? "
													+"order by li.DESCRIPTION");
		stmt.setString(1, listName);
		return stmt;
	}
	
	public static BeanContainer<String, ListBean> getBeanContainer(String listName, String campusId) {
		BeanContainer<String, ListBean> bc;
		
		//Content load from cache
		if(cache.get(listName)!=null) {
			return cache.get(listName);
		}
		
		
		//Content load from database
		bc = new BeanContainer<String, ListBean>(ListBean.class);
		bc.setBeanIdProperty("id");
		

		Connection conn = null;
		
		try {

			conn = Pools.getConnection(Pools.Names.PROJEX);
			
			PreparedStatement stmt = getPreparedStatementForList(conn, listName);
			try(ResultSet rs = stmt.executeQuery();){
				while (rs.next()) {
					ListBean newBean = new ListBean(rs.getString("VALUE"),new OracleString(rs.getString("SYSTEMVALUE")), new OracleString(rs.getString("VALUE")), rs.getBoolean("ISDEFAULT"), new OracleString(rs.getString("DESCRIPTION")));
					bc.addBean(newBean);
				}
			}
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		} finally {
			Pools.releaseConnection(Pools.Names.PROJEX, conn);
		}	
		
		//put into cache
		cache.put(listName, bc);
		
		return bc;
	}
}
