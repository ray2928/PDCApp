package edu.missouri.cf.pdcapp.dbconnect;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;

public class Pools {

	public enum Names {
		PROJEX
	}

	//private static Logger logger = LoggerFactory.getLogger(Pools.class);

	private static JDBCConnectionPool projexConnectionPool = null;
	
	public final static boolean useProduction = false;

	public static String projex4ConnectionString;

	static {

//		if (logger.isTraceEnabled()) {
//			logger.trace("Creating connection pool to Projex 4 Server");
//		}

		if (useProduction) {
				
//			if(logger.isTraceEnabled()) {
//				logger.trace("Using production server connection");
//			}
				
			projex4ConnectionString = "jdbc:oracle:thin:@//mu-cf-projexdb.col.missouri.edu:1521/prj4.col.missouri.edu";

				
		} else {
			
//			if(logger.isTraceEnabled()) {
//				logger.trace("Using test server connection");
//			}
			projex4ConnectionString = "jdbc:oracle:thin:@//128.206.190.72:1521/projex4.projex4db.cf.missouri.edu";// develop
			//projex4ConnectionString = "jdbc:oracle:thin:@//128.206.191.213:1521/projex4.p4proddb.cf.missouri.edu";// prod
			//projex4ConnectionString = "jdbc:oracle:thin:@//128.206.191.45:1521/projex4testdb.cf.missouri.edu";// new test
			//projex4ConnectionString = "jdbc:oracle:thin:@//128.206.191.211:1521/projex4testdb.cf.missouri.edu";// new test
			System.out.println(projex4ConnectionString);
		}
			
		try {
			projexConnectionPool = new SimpleJDBCConnectionPool("oracle.jdbc.OracleDriver", projex4ConnectionString, "projex4", "prj4_user", 20, 50);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// projexConnectionPool = new HikariConnectionPool("oracle.jdbc.OracleDriver", projex4ConnectionString, "projex4", "prj4_user", 8, 80);

	
	}

	public Pools() {

	}

	/**
	 * 
	 * Universal method for getting the various connection pools. If you call
	 * use this method, you should use JDBCCloser.release to return the
	 * connection to the pool.
	 * 
	 * @param pool
	 * @return
	 */
	public static JDBCConnectionPool getConnectionPool(Pools.Names pool) {
		switch (pool) {
		case PROJEX:
			return projexConnectionPool;
		}
		return null;
	}

	public static Connection getConnection(Pools.Names pool) throws SQLException {

		try {
//			if (logger != null && logger.isTraceEnabled()) {
//				logger.trace("Checking out connection from {}", pool);
//			}
			return getConnectionPool(pool).reserveConnection();
		} catch (SQLException e) {
//			if (logger.isErrorEnabled()) {
//				logger.error("Error getting connection from {}", pool, e);
//				throw e;
//			}

		}
		return null;
	}

	public static void releaseConnection(Pools.Names pool, Connection conn) {
		if (conn != null) {
//			if (logger.isTraceEnabled()) {
//				logger.trace("Releasing connection back to " + pool);
//			}
			getConnectionPool(pool).releaseConnection(conn);
		}
	}

	private static void close(ResultSet rs) {
		if (rs != null)
			try {
//				if (logger.isTraceEnabled()) {
//					logger.trace("Closing ResultSet");
//				}
				rs.close();
			} catch (SQLException e) {
			//	logger.error("Error closing ResultSet", e);
			}
	}

	private static void close(Statement stmt) {
		if (stmt != null)
			try {
//				if (logger.isTraceEnabled()) {
//					logger.trace("Closing Statement");
//				}
				stmt.close();
			} catch (SQLException e) {
				//logger.error("Error closing Statement", e);
			}
	}

	/**
	 * Close all sql objects. Order probably matters, but it has not been
	 * tested. To be sure, close all derived objects before closing their
	 * deriver.
	 * 
	 * @param objects
	 */
	public static void close(Object... objects) {
		for (Object o : objects) {
			if (o instanceof java.sql.Statement) {
				close((Statement) o);
			} else if (o instanceof java.sql.ResultSet) {
				close((ResultSet) o);
			} else if (o != null) {

				Exception e = new Exception();
				//logger.error("Invalid call to close : o is of type {}", o.getClass().getName(), e);

			}
		}
	}

	@Deprecated
	public static PreparedStatement getPreparedStatement(Connection conn, String query, String... values) throws SQLException {
		if (values == null) {
			throw new IllegalArgumentException("values array cannot be null");
		}
		DBObject[] list = new DBObject[values.length];
		for (int i = 0; i < values.length; i++) {
			list[i] = new DBObject(values[i], Types.VARCHAR);
		}
		return getPreparedStatement(conn, query, list);
	}

	/**
	 * Provides the ability to create a prepared statement for null values and
	 * other data types.
	 * <p>
	 * More data types will need to be added in later.
	 * 
	 * @param conn
	 * @param query
	 * @param values
	 *            - ie. array of DBObject(Object value, Types.VARCHAR);
	 * @return
	 * @throws SQLException
	 */
	public static PreparedStatement getPreparedStatement(Connection conn, String query, DBObject... values) throws SQLException {

		if (conn == null) {
			throw new IllegalArgumentException("connection cannot be null");
		}
		if (query == null) {
			throw new IllegalArgumentException("query cannot be null");
		}
		if (values == null) {
			throw new IllegalArgumentException("values array cannot be null");
		}

		PreparedStatement stmt = conn.prepareStatement(query);
		for (int i = 0; i < values.length; i++) {
			int type = values[i].getType();
			Object value = values[i].getValue();
			if (value == null) {
				stmt.setNull(i + 1, type);
			} else {
				switch (type) {
				case Types.VARCHAR:
					stmt.setString(i + 1, value.toString());
					break;
				case Types.INTEGER:
					stmt.setInt(i + 1, (int) value);
					break;
				case Types.NUMERIC:
					if (value instanceof Integer) {
						stmt.setInt(i + 1, (int) value);
					} else if (value instanceof BigDecimal) {
						stmt.setBigDecimal(i + 1, (BigDecimal) value);
					} else {
						throw new IllegalArgumentException("Class " + value.getClass() + " is not handled yet");
					}
					break;
				default:
					throw new IllegalArgumentException("Class " + value.getClass() + " is not handled yet");

				}
			}
		}

		return stmt;
	}

	public static class DBObject {

		private Object value;
		private int type;

		public DBObject(Object value, int type) {
			this.value = value;
			this.type = type;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
	}

	public static String generateInClause(String[] strings) {
		String query = "";
		if (strings == null || strings.length == 0) {
			throw new IllegalArgumentException("in list cannot be null or empty");
		}
		if (strings.length == 1) {
			query = " = ?";
		} else {
			query = "in (";
			for (int i = 0; i < strings.length; i++) {
				query += "?,";
			}
			query = query.substring(0, query.length() - 1);
			query += ")";
		}
//		if (logger.isTraceEnabled()) {
//			logger.trace("in query = {}", query);
//		}
		return query;
	}

}
