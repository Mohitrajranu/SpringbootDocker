package com.example.howtodoinjava.hellodocker;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HelloDockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloDockerApplication.class, args);
	}
}

@RestController
class HelloDockerRestController {
	
	@Autowired
	GodrejPostgres godrejPostgres;
	@Autowired
	PostgresFetchTableAndColumn postgresFetchTableAndColumn;
	@Autowired
	PostgresFetchDataPKFK postgresFetchDataPKFK;
	
	@RequestMapping("/hello/{name}")
	public String helloDocker(@PathVariable(value = "name") String name) {
		String response = "Hello " + name + " Response received on : " + new Date();
		System.out.println(response);
		return response;

	}
	@RequestMapping(value="/getPostgresPkFkDet" ,method = RequestMethod.POST)
	public Map<String, Object> save(@RequestBody String json){
		 Map<String, Object> jsonOut = new HashMap<>();
		 JSONObject allPKFKData = null;
		try {
			//JSONObject jsonObj=new JSONObject(json);
			

			String webServiceJson="{\r\n" + 
						"\"webservice\" : {\"host\":\"ec2-23-23-50-231.compute-1.amazonaws.com\",\"databasename\":\"dfchgt5o680aok\",\"username\":\"ubb3mhh56quq28\",\"password\":\"p40b3519da1cda5a8064ad44a89f19aee833c231d97b5996ebd63ce3d059e8bbd\",\"port\":\"5432\",\"webservicetype\":\"database\",\"databasetype\":\"postgres\",\"schema\":\"salesforce\"},\r\n" + 
						"\"PK\":{\"field\":\"sfid\", \"object\":\"propstrength__application_booking__c\",\"value\":\"a10O0000004iDtLIAU\"},\r\n" + 
						"\"FK\":[\r\n" + 
						"{\"field\":\"sfid\", \"object\":\"propstrength__projects__c\",\"pkfield\":\"propstrength__project__c\",\"pkobject\":\"propstrength__application_booking__c\"},\r\n" + 
						"{\"field\":\"sfid\", \"object\":\"contact\",\"pkfield\":\"propstrength__primary_customer__c\",\"pkobject\":\"propstrength__application_booking__c\"}\r\n" + 
						"]\r\n" + 
						"}";
			//System.out.println(webServiceJson);
			JSONObject objAllJson = new JSONObject(json);
			JSONObject objWebServiceJson = objAllJson.getJSONObject("webservice");
			if(objWebServiceJson.has("webservicetype") & objWebServiceJson.getString("webservicetype").equals("database") & objWebServiceJson.getString("databasetype").equals("postgres")){
			Connection conn = postgresFetchDataPKFK.getPostGresConnection(objWebServiceJson.getString("host"), 
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
			  allPKFKData = postgresFetchDataPKFK.getPKData(conn, pkObj.getString("field"), schema, pkObj.getString("value"));
			  allPKFKData = postgresFetchDataPKFK.getFkData(conn, allPKFKData, objAllJson.getJSONArray("FK"), schemaTable);
			  System.out.println("alldata :: " + allPKFKData);
			}
			
			
			
			
			jsonOut.put("outputdata", allPKFKData.toString());
		//	System.out.println(jsonObj);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonOut;

		}
	
	@RequestMapping(value="/getPostgresTableData" ,method = RequestMethod.POST)
	public  Map<String, Object> generatePostgresTable(@RequestBody DatabaseModel databaseModel){
	
		 Map<String, Object> json = new HashMap<>();
		 JSONObject objTableColumn = null;
		try{
		objTableColumn = new JSONObject();
		//System.out.println(databaseModel);
		if(databaseModel.getWebservicetype() != null && databaseModel.getWebservicetype().equals("database") && databaseModel.getDatabasetype().equals("postgres")){
		Connection conn = postgresFetchTableAndColumn.getPostGresConnection(databaseModel.getHost(), 
				databaseModel.getDatabasename(), databaseModel.getUsername(), databaseModel.getPassword(), databaseModel.getPort());
		String schema = "";
		if(databaseModel.getSchema() != null){
			schema = databaseModel.getSchema();
		}
		objTableColumn = postgresFetchTableAndColumn.getTableAndSchema(conn,schema);
		json.put("outputdata", objTableColumn.toString());
		//System.out.println(objTableColumn);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return json;
	}
	
	
	@RequestMapping(value="/getPostgresData" ,method = RequestMethod.GET)
	public  Map<String, Object> generatePostgresData(@RequestParam String sfId){
		/*ResponseEntity<JSONObject> responseEntity = null;
		HttpHeaders headers = null;*/
		 JSONObject mainJson = null;
		 Map<String, Object> json = new HashMap<>();
			try {
				mainJson = new JSONObject();
				//String sfId = "a10O0000004iUGPIA2";
				String sfIdProject = "";
				String sfIdProperty = "";
				String sfIdContact = "";
				Connection conn = godrejPostgres.getPostGresConnection();
				mainJson = godrejPostgres.getApplnBooking(sfId, conn);
				if(mainJson.has("propstrength__project__c")){
					sfIdProject = mainJson.getString("propstrength__project__c");
				}
				if(mainJson.has("propstrength__property__c")){
					sfIdProperty = mainJson.getString("propstrength__property__c");
				}
				if(mainJson.has("propstrength__primary_customer__c")){
					sfIdContact = mainJson.getString("propstrength__primary_customer__c");
				}
				mainJson = godrejPostgres.getProject(sfIdProject, conn,mainJson);
				mainJson = godrejPostgres.getProperty(sfIdProperty, conn,mainJson);
				mainJson = godrejPostgres.getContact(sfIdContact, conn,mainJson);
				
				//System.out.println(mainJson);
				/*headers = new HttpHeaders();
	        	headers.setContentType(MediaType.APPLICATION_JSON);
				 responseEntity = new ResponseEntity<JSONObject>(mainJson,
			                HttpStatus.OK);*/
				 json.put("outputdata", mainJson.toString());
			}  catch (Exception e) {
				e.printStackTrace();
			}
			return json;
	}
}
