/**
 * 
 *//*

package org.loocsij._3party.dbcp;

import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.logging.log4j.Logger;

import org.loocsij.logger.Log;
import org.loocsij.util.EncodingUtil;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;
*/
/**
 * Connect configured database.
 * 
 * @author wengm
 * 
 *//*

public class DatabaseConnector {
	private static Logger log = Log.getLogger(EncodingUtil.class);
	private static DatabaseConnector dbc = null;

	private DataSource bds = null;
	*/
/**
	 * 
	 * @return
	 *//*

	public Connection get() {
		try {
			return bds.getConnection();
		} catch (SQLException e) {
			log.error("Fail - can not get connection:", e);
		}
		return null;
	}

	*/
/**
	 * read configuration information and initialize basic data source.
	 *//*

	private DatabaseConnector(Properties conf) {
		if(conf==null) {
			throw new IllegalArgumentException("Invalid DBCP configuration!");
		}
		try {
			bds = BasicDataSourceFactory.createDataSource(conf);
		} catch (Exception e) {
			log.error("Fail - can not create data source:", e);
		}
	}
	
	*/
/**
	 * 
	 * @param conf
	 * @return
	 *//*

	public DatabaseConnector getInstance(Properties conf) {
		if(dbc!=null) {
			return dbc;
		}
		dbc = new DatabaseConnector(conf);
		return dbc;
	}

	*/
/**
	 * @param args
	 *//*

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
*/
