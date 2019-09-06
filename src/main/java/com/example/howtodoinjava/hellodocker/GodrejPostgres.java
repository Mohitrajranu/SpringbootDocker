package com.example.howtodoinjava.hellodocker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GodrejPostgres {

	public Connection getPostGresConnection(){
		Connection conn = null;
		try{
			Class.forName("org.postgresql.Driver");
		 conn = DriverManager.getConnection(
				"jdbc:postgresql://ec2-23-23-50-231.compute-1.amazonaws.com:5432/dfchgt5o680aok", "ubb3mhh56quq28",
				"p40b3519da1cda5a8064ad44a89f19aee833c231d97b5996ebd63ce3d059e8bbd");
		}catch(Exception e){
			System.out.println("getPostGresConnection()  :: "+e.getMessage());
		}
		return conn;
	}
	// appln booking sfid - a10O0000004iUGPIA2
	public  JSONObject getApplnBooking(String sfId,Connection conn){
		JSONObject applnBooking = null;
		try{
			applnBooking = new JSONObject();
			sfId = "'"+sfId+"'";
			String SQL_SELECT = "select * from salesforce.propstrength__application_booking__c where sfid ="+sfId;
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (conn != null) {
				System.out.println("Connected to the database!");
				ResultSetMetaData meta = resultSet.getMetaData();
				 int columnCount = meta.getColumnCount();
				 
				while (resultSet.next()) {
					 for (int column = 1; column <= columnCount; column++) 
					{
						Object value = resultSet.getObject(column);
						String columnName = meta.getColumnName(column);

						if (value != null) {
							applnBooking.put(columnName, value.toString());
							//System.out.println("column :: " + columnName + " : value :: " + value.toString());

						} else {
							applnBooking.put(columnName, "");
							//System.out.println("column :: " + columnName + " : value :: ");
						}
					}
					// System.out.println(applnBooking);
				}
				

			} else {
				System.out.println("getApplnBooking()  :: Failed to make connection!");
			}

		}catch(Exception e){
			System.out.println("getApplnBooking()  :: "+e.getMessage());
		}
		return applnBooking;
	}
	
	public  JSONObject getProperty(String sfId,Connection conn,JSONObject property){
		try{
			//salesforce.propstrength__property__c
			sfId = "'"+sfId+"'";
			String SQL_SELECT = "select * from salesforce.propstrength__property__c where sfid ="+sfId;
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (conn != null) {
				System.out.println("Connected to the database!");
				ResultSetMetaData meta = resultSet.getMetaData();
				 int columnCount = meta.getColumnCount();
				 
				while (resultSet.next()) {
					 for (int column = 1; column <= columnCount; column++) 
					{
						Object value = resultSet.getObject(column);
						String columnName = meta.getColumnName(column);

						if (value != null) {
							if(!property.has(columnName)){
							property.put(columnName, value.toString());
							}
							//System.out.println("column :: " + columnName + " : value :: " + value.toString());

						} else {
							if(!property.has(columnName)){
							property.put(columnName, "");
							}
							//System.out.println("column :: " + columnName + " : value :: ");
						}
					}
					// System.out.println(applnBooking);
				}
				

			} else {
				System.out.println("getProperty()  :: Failed to make connection!");
			}

		}catch(Exception e){
			System.out.println("getProperty()  :: "+e.getMessage());
		}
		return property;
	}
	
	public  JSONObject getProject(String sfId,Connection conn,JSONObject project){
		//JSONObject project = null;
		try{
			//project = new JSONObject();
			sfId = "'"+sfId+"'";
			String SQL_SELECT = "select * from salesforce.propstrength__projects__c where sfid ="+sfId;
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (conn != null) {
				System.out.println("Connected to the database!");
				ResultSetMetaData meta = resultSet.getMetaData();
				 int columnCount = meta.getColumnCount();
				 
				while (resultSet.next()) {
					 for (int column = 1; column <= columnCount; column++) 
					{
						Object value = resultSet.getObject(column);
						String columnName = meta.getColumnName(column);

						if (value != null) {
							if(!project.has(columnName)){
							project.put(columnName, value.toString());
							}
							//System.out.println("column :: " + columnName + " : value :: " + value.toString());

						} else {
							if(!project.has(columnName)){
							project.put(columnName, "");
							}
							//System.out.println("column :: " + columnName + " : value :: ");
						}
					}
					// System.out.println(applnBooking);
				}
				

			} else {
				System.out.println("getProject()  :: Failed to make connection!");
			}

		}catch(Exception e){
			System.out.println("getProject()  :: "+e.getMessage());
		}
		return project;
	}
	
	public  JSONObject getContact(String sfId,Connection conn,JSONObject contact){
		//JSONObject contact = null;
		try{
			//contact = new JSONObject();
			sfId = "'"+sfId+"'";
			String SQL_SELECT = "select * from salesforce.contact where sfid ="+sfId;
			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (conn != null) {
				System.out.println("Connected to the database!");
				ResultSetMetaData meta = resultSet.getMetaData();
				 int columnCount = meta.getColumnCount();
				 
				while (resultSet.next()) {
					 for (int column = 1; column <= columnCount; column++) 
					{
						Object value = resultSet.getObject(column);
						String columnName = meta.getColumnName(column);

						if (value != null) {
							if(!contact.has(columnName)){
							contact.put(columnName, value.toString());
							}
							//System.out.println("column :: " + columnName + " : value :: " + value.toString());

						} else {
							if(!contact.has(columnName)){
							contact.put(columnName, "");
							}
							//System.out.println("column :: " + columnName + " : value :: ");
						}
					}
					// System.out.println(applnBooking);
				}
				

			} else {
				System.out.println("getContact()  :: Failed to make connection!");
			}

		}catch(Exception e){
			System.out.println("getContact()  :: "+e.getMessage());
		}
		return contact;
	}
	
	public static void main(String[] args) {/*

		try {
			System.out.println (System.getProperty ("java.class.path"));
			JSONObject mainJson = new JSONObject();
			String sfId = "a10O0000004iUGPIA2";
			String sfIdProject = "";
			String sfIdProperty = "";
			String sfIdContact = "";
			Connection conn = GodrejPostgres.getPostGresConnection();
			mainJson = GodrejPostgres.getApplnBooking(sfId, conn);
			if(mainJson.has("propstrength__project__c")){
				sfIdProject = mainJson.getString("propstrength__project__c");
			}
			if(mainJson.has("propstrength__property__c")){
				sfIdProperty = mainJson.getString("propstrength__property__c");
			}
			if(mainJson.has("propstrength__primary_customer__c")){
				sfIdContact = mainJson.getString("propstrength__primary_customer__c");
			}
			mainJson = GodrejPostgres.getProject(sfIdProject, conn,mainJson);
			mainJson = GodrejPostgres.getProperty(sfIdProperty, conn,mainJson);
			mainJson = GodrejPostgres.getContact(sfIdContact, conn,mainJson);
			
			System.out.println(mainJson);
//			String SQL_SELECT = "select * from salesforce.contact limit 20";
//			Connection conn = DriverManager.getConnection(
//					"jdbc:postgresql://ec2-23-23-50-231.compute-1.amazonaws.com:5432/dfchgt5o680aok", "ubb3mhh56quq28",
//					"p40b3519da1cda5a8064ad44a89f19aee833c231d97b5996ebd63ce3d059e8bbd");
//			String SQL_SELECT = "select * from salesforce.contact where sfid = '003O000001DE48WIAT'";
//			PreparedStatement preparedStatement = conn.prepareStatement(SQL_SELECT);
//			ResultSet resultSet = preparedStatement.executeQuery();
//
//			if (conn != null) {
//				System.out.println("Connected to the database!");
//				ResultSetMetaData meta = resultSet.getMetaData();
//				 int columnCount = meta.getColumnCount();
//				 
//				while (resultSet.next()) {
//					 for (int column = 1; column <= columnCount; column++) 
//					{
//						Object value = resultSet.getObject(column);
//						String columnName = meta.getColumnName(column);
//
//						if (value != null) {
//							mainJson.put(columnName, value.toString());
//							//System.out.println("column :: " + columnName + " : value :: " + value.toString());
//
//						} else {
//							mainJson.put(columnName, "");
//							//System.out.println("column :: " + columnName + " : value :: ");
//						}
//					}
//					 System.out.println(mainJson);
//					//System.out.println(resultSet.get);
//					/*String email = resultSet.getString("email");
//					String firstname = resultSet.getString("firstname");
//					String lastname = resultSet.getString("lastname");
//					String mobile__c = resultSet.getString("mobile__c");
//					String mobile_no__c = resultSet.getString("mobile_no__c");
//					String mobile_nonew__c = resultSet.getString("mobile_nonew__c");
//					String mobile_number__c = resultSet.getString("mobile_number__c");
//					String mobilephone = resultSet.getString("mobilephone");
//					Date birthdate = resultSet.getDate("birthdate");
//					Date birthday__c = resultSet.getDate("birthday__c");
//					String age_a__c = resultSet.getString("age_a__c");
//
//					System.out.println("email :: " + email);
//					System.out.println("firstname :: " + firstname);
//					System.out.println("lastname :: " + lastname);
//					System.out.println("mobile__c :: " + mobile__c);
//					System.out.println("mobile_no__c :: " + mobile_no__c);
//					System.out.println("mobile_nonew__c :: " + mobile_nonew__c);
//					System.out.println("mobile_number__c :: " + mobile_number__c);
//					System.out.println("mobilephone :: " + mobilephone);
//					System.out.println("birthdate :: " + birthdate);
//					System.out.println("birthday__c :: " + birthday__c);
//					System.out.println("age_a__c :: " + age_a__c);*/
//				}
//				
//
//			} else {
//				System.out.println("Failed to make connection!");
//			}

		/*}  catch (Exception e) {
			e.printStackTrace();
		}*/

}
}