package com.example.howtodoinjava.hellodocker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class PostgresFetchDataPKFK {
	
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject allPKFKData = null;
		try{
		String webServiceJson="{\r\n" + 
					"\"webservice\" : {\"host\":\"ec2-23-23-50-231.compute-1.amazonaws.com\",\"databasename\":\"dfchgt5o680aok\",\"username\":\"ubb3mhh56quq28\",\"password\":\"p40b3519da1cda5a8064ad44a89f19aee833c231d97b5996ebd63ce3d059e8bbd\",\"port\":\"5432\",\"webservicetype\":\"database\",\"databasetype\":\"postgres\",\"schema\":\"salesforce\"},\r\n" + 
					"\"PK\":{\"field\":\"sfid\", \"object\":\"propstrength__application_booking__c\",\"value\":\"a10O0000004iDtLIAU\"},\r\n" + 
					"\"FK\":[\r\n" + 
					"{\"field\":\"sfid\", \"object\":\"propstrength__projects__c\",\"pkfield\":\"propstrength__project__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n" + 
					"{\"field\":\"sfid\", \"object\":\"contact\",\"pkfield\":\"propstrength__primary_customer__c\",\"pkobject\":\"propstrength__application_booking__c\"}\r\n" + 
					"]\r\n" + 
					"}";
		//System.out.println(webServiceJson);
		JSONObject objAllJson = new JSONObject(webServiceJson);
		JSONObject objWebServiceJson = objAllJson.getJSONObject("webservice");
		if(objWebServiceJson.has("webservicetype") & objWebServiceJson.getString("webservicetype").equals("database") & objWebServiceJson.getString("databasetype").equals("postgres")){
		Connection conn = PostgresFetchDataPKFK.getPostGresConnection(objWebServiceJson.getString("host"), 
				objWebServiceJson.getString("databasename"), objWebServiceJson.getString("username"), objWebServiceJson.getString("password"), objWebServiceJson.getString("port"));
		String schema = "";
		String schemaTable = "";
		JSONObject pkObj = objAllJson.getJSONObject("PK");
		if(objWebServiceJson.has("schema")){
			if(!objWebServiceJson.getString("schema").equals("")){
			schema = objWebServiceJson.getString("schema")+"."+pkObj.getString("object");
			schemaTable = objWebServiceJson.getString("schema");
			}else{
				schema = pkObj.getString("object");
			}
		}else{
			schema = pkObj.getString("object");
		}
		  allPKFKData = PostgresFetchDataPKFK.getPKData(conn, pkObj.getString("field"), schema, pkObj.getString("value"));
		  allPKFKData = PostgresFetchDataPKFK.getFkData(conn, allPKFKData, objAllJson.getJSONArray("FK"), schemaTable);
		  System.out.println("alldata :: " + allPKFKData);
		}
		}catch(Exception e){
			
		}
	}*/
	
	public Connection getPostGresConnection(String host,String databasename,String username,String password,String port){
		Connection conn = null;
		try{
		 conn = DriverManager.getConnection(
				"jdbc:postgresql://"+host+":"+port+"/"+databasename, username,
				password);
		 //System.out.println(conn);
		}catch(Exception e){
			System.out.println("getPostGresConnection()  :: "+e.getMessage());
		}
		return conn;
	}
	
	public JSONObject getPKData(Connection conn,String primaryKey,String primaryObject,String primaryValue){
		JSONObject pkData = new JSONObject();
		try{
			String SQL_SELECT = "select * from "+primaryObject+" where "+primaryKey+" ='"+primaryValue+"'";
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (conn != null) {
				//System.out.println("Connected to the database!");
				ResultSetMetaData meta = resultSet.getMetaData();
				 int columnCount = meta.getColumnCount();
				 
				while (resultSet.next()) {
					 for (int column = 1; column <= columnCount; column++) 
					{
						Object value = resultSet.getObject(column);
						String columnName = meta.getColumnName(column);

						if (value != null) {
							if(!pkData.has(columnName)){
								pkData.put(columnName, value.toString());
							}
							//System.out.println("column :: " + columnName + " : value :: " + value.toString());

						} else {
							if(!pkData.has(columnName)){
								pkData.put(columnName, "");
							}
							//System.out.println("column :: " + columnName + " : value :: ");
						}
					}
					// System.out.println(applnBooking);
				}
				System.out.println("getPKData  :: "+pkData);

			} else {
				System.out.println("getPKData()  :: Failed to make connection!");
			}

		}catch(Exception e){
			System.out.println("getPKData()  :: "+e.getMessage());
		}
		return pkData;
	}

	public JSONObject getFkData(Connection conn,JSONObject allPKFKData,JSONArray fkKeySchema,String schemaTable){
		
		try{
			if (conn != null) {
			for(int i=0;i<fkKeySchema.length();i++){
				JSONObject fkObject = fkKeySchema.getJSONObject(i); 
//				System.out.println(fkObject.getString("field"));
//				System.out.println(fkObject.getString("pkfield"));
//				System.out.println(fkObject.getString("object"));
				String fkSchemaObject = "";
				if(!schemaTable.equals("")){
					fkSchemaObject = schemaTable + "." +fkObject.getString("object");
				}else{
					fkSchemaObject = fkObject.getString("object");
				}
				//System.out.println(fkSchemaObject);
				if(allPKFKData.has(fkObject.getString("pkfield"))){
					//System.out.println(allPKFKData.get(fkObject.getString("pkfield")).toString());
					String SQL_SELECT = "select * from "+fkSchemaObject+" where "+fkObject.getString("field")+" ='"+allPKFKData.get(fkObject.getString("pkfield")).toString()+"'";
					PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
					ResultSet resultSet = preparedStatement.executeQuery();

						//System.out.println("Connected to the database!");
						ResultSetMetaData meta = resultSet.getMetaData();
						 int columnCount = meta.getColumnCount();
						 
						while (resultSet.next()) {
							 for (int column = 1; column <= columnCount; column++) 
							{
								Object value = resultSet.getObject(column);
								String columnName = meta.getColumnName(column);

								if (value != null) {
									if(!allPKFKData.has(columnName)){
										allPKFKData.put(columnName, value.toString());
									}
									//System.out.println("column :: " + columnName + " : value :: " + value.toString());

								} else {
									if(!allPKFKData.has(columnName)){
										allPKFKData.put(columnName, "");
									}
									//System.out.println("column :: " + columnName + " : value :: ");
								}
							}
							// System.out.println(applnBooking);
						}
						

					
				}
			}
			System.out.println("getFkData()  :: "+allPKFKData);
			} else {
				System.out.println("getFkData()  :: Failed to make connection!");
			}
		}catch(Exception e){
			System.out.println("getFkData()  :: "+e.getMessage());
		}
		return allPKFKData;
	}

}
