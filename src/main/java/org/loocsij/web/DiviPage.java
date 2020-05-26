/**
 * 
 */
package org.loocsij.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * @author wengm
 *
 */
public abstract class DiviPage {
	private String table;
	private int rowCount;
	private int pageCount;
	private int rowPerPage;
	
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	
	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getRowCount() {
		return rowCount;
	}
	 
	public DiviPage() {
		super();
	}

	/**
	 * 
	 */
	public DiviPage(String table,int rowPerPage) {
		super();
		if(table == null){
			throw new RuntimeException("table must not be NULL!");
		}
		this.table = table;
		this.rowPerPage = rowPerPage;
		try{
			init();
			calculate();
		}catch(SQLException sqle){
			throw new RuntimeException("DB Error,"+sqle.getMessage());
		}
	}
	
	public void process(){
		try{
			init();
			calculate();
		}catch(SQLException sqle){
			throw new RuntimeException("DB Error,"+sqle.getMessage());
		}
	}
	
	private void init() throws SQLException{
		if(con != null){
			stmt = con.createStatement();
		}
	}
	
	private void calculate() throws SQLException{
		String sql = "select count(*) as num from " + table;
		rs = stmt.executeQuery(sql);
		if (rs.next()) {
			rowCount = rs.getInt("num");
		}
		if(rowCount == 0){
			return;
		}
		if(rowCount%rowPerPage == 0){
			pageCount = rowCount/rowPerPage;
		}else{
			pageCount = rowCount/rowPerPage + 1;
		}
	}
	
	
	public abstract Collection getRecords();
}
