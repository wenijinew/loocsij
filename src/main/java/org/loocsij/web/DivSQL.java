package org.loocsij.web;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
�ṩ�����ķ�ҳ���ܣ�pl/sql��ִ�в�ѯ�ͻ�ȡ��������Ҫ��ͬ�Ķ�����ɣ���ΪStatementÿִ��һ�β�ѯ��ǰ��Ĳ�ѯ�������
eg:
Statement stmt1 = con.createStatement();
DivSQL ds1 = new DivSQL(sql,start,count,stmt1);
Result rs = ds1.doQuery();
Statement stmt2 = con.createStatement();
DivSQL ds2 = new DivSQL(sql,start,count,stmt2);
int recordTotal = 0;
int pageTotal = 0;
int currPage = 0;
if(rs!=null){
	recordTotal = ds2.getTotal();
	pageTotal = ds2.getPageTotal();
	currPage = ds2.getCurrPageNum();
}
*/
public class DivSQL{
	private static String head= "select * from(select s.*,rownum as row_num from(";
	private static String tail=")s) a where a.row_num between ";
	private static String total="select count(*) as TOTAL from ";
	private Statement stmt;
	private String sql;
	private int start;
	private int count;
	private int recordTotal;
	/*
	���������ض���ѯ�ġ�ָ����ʼ��������ѯ������ִ��SQL��Statement�ķ�ҳ����
	*/
	public DivSQL(String sql,int start,int count,Statement stmt){
		this.stmt=stmt;
		this.sql =sql;
		this.start = start;
		this.count=count;
	}
	/*ִ�в�ѯ�����ؽ����¼��*/
	public ResultSet doQuery() throws SQLException{
		String control =  start + " and " + (start+count-1);
		String sqlFinal = head + sql + tail + control ; 
		System.out.println(sqlFinal);
		ResultSet rs = stmt.executeQuery(sqlFinal);  
		//while(rs.next()){
		//	System.out.println(rs.getString("type"));
		//}
		return rs;
	} 
	/**
	 * ��ȡ������
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getTotal() throws SQLException{
		String sqlFinal = total + "("+this.sql+")";
		ResultSet rs = stmt.executeQuery(sqlFinal);
		if(rs!=null && rs.next()){
			this.recordTotal = rs.getInt("TOTAL");
		}
		return this.recordTotal;
	}
	/**
	 * ��ȡ��ҳ��
	 * @return
	 */
	public int getPageTotal(){
		if(this.recordTotal==0 || this.count==0){
			return 0;
		}
		int pageTotal = 0;
		if(this.recordTotal%this.count==0){
			pageTotal = this.recordTotal/this.count;
		}else{
			pageTotal = this.recordTotal/this.count+1;
		}
		return pageTotal;
	}
	/**
	 * ��ȡ��ǰҳ��
	 * @return
	 */
	public int getCurrPageNum(){
		if(this.recordTotal == 0){
			return 0;
		}
		if(start <= count){
			return 1;
		}
		return (start/count)+1;
	}
}

