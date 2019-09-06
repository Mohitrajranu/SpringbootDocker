package com.example.howtodoinjava.hellodocker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PostgresFetchTableAndColumn {
	
	public static void main(String[] args) {/*
		// TODO Auto-generated method stub
		try{
		String webServiceJson = "{\"host\":\"ec2-23-23-50-231.compute-1.amazonaws.com\",\"databasename\":\"dfchgt5o680aok\","
				+ "\"username\":\"ubb3mhh56quq28\",\"password\":\"p40b3519da1cda5a8064ad44a89f19aee833c231d97b5996ebd63ce3d059e8bbd\","
				+ "\"port\":\"5432\",\"webservicetype\":\"database\",\"databasetype\":\"postgres\""
				+ "}";
		System.out.println(webServiceJson);
		JSONObject objWebServiceJson = new JSONObject(webServiceJson);
		if(objWebServiceJson.has("webservicetype") & objWebServiceJson.getString("webservicetype").equals("database") & objWebServiceJson.getString("databasetype").equals("postgres")){
		Connection conn = PostgresFetchTableAndColumn.getPostGresConnection(objWebServiceJson.getString("host"), 
				objWebServiceJson.getString("databasename"), objWebServiceJson.getString("username"), objWebServiceJson.getString("password"), objWebServiceJson.getString("port"));
		JSONObject objTableColumn = PostgresFetchTableAndColumn.getTableAndSchema(conn);
		System.out.println(objTableColumn);
		}
		}catch(Exception e){
			
		}
	*/}
	
	public  Connection getPostGresConnection(String host,String databasename,String username,String password,String port){
		Connection conn = null;
		try{
		 conn = DriverManager.getConnection(
				"jdbc:postgresql://"+host+":"+port+"/"+databasename, username,
				password);
		}catch(Exception e){
			System.out.println("getPostGresConnection()  :: "+e.getMessage());
		}
		return conn;
	}
	
	public JSONObject getTableAndSchema(Connection conn,String schema){
		JSONObject tableJson = new JSONObject();
		try{
			//String SQL_SELECT = "select table_name from information_schema.tables where table_schema = 'salesforce' ORDER BY table_name";
			String SQL_SELECT = "";
			if(!schema.equals("")){
				SQL_SELECT = "select table_name from information_schema.tables where table_schema = '"+schema+"' ORDER BY table_name";
			}else{
				SQL_SELECT = "select table_name from information_schema.tables ORDER BY table_name";
			}
			
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (conn != null) {
				while (resultSet.next()) {
					//System.out.println(resultSet.getString("table_name"));
					String table_name = "'" + resultSet.getString("table_name") + "'";
					String SQL_SELECT_COL = "select column_name from information_schema.columns where table_name = "+table_name+" ORDER BY column_name";
					PreparedStatement preparedStatementCol = conn.prepareStatement(SQL_SELECT_COL);
					ResultSet resultSetCol = preparedStatementCol.executeQuery();
					JSONArray columnArr = new JSONArray();
					while (resultSetCol.next()) {
							//System.out.println(resultSetCol.getString("column_name"));
							columnArr.put(resultSetCol.getString("column_name"));
						}
					tableJson.put(resultSet.getString("table_name"), columnArr);
					//System.out.println(tableJson);
				}
			}
//			/System.out.println(tableJson);
		}catch(Exception e){
			
		}
		return tableJson;
	}

}
