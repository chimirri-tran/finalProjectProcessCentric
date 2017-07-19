package introsde.rest.ehealth.resources;

import java.io.IOException;
import java.net.URI;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
@Path("/internal")
public class InternalProcessCentric {
	
	static String serverCheckLogin = "http://localhost:5701/sdelab/checkLoginBusiness";
	static String serverNewGoal = "http://localhost:5701/sdelab/newGoalBusiness";
	static String serverCheckGoal =  "http://localhost:5701/sdelab/checkGoalBusiness";
	static String serverPeopleStorage =  "http://localhost:5801/sdelab/peopleStorage";
	
	public static Response makeRequest(String server, String path, String mediaType, 
			String method, JSONObject obj){

		String serverUri = "";
		if(server.equals("serverCheckLogin")){
			serverUri=serverCheckLogin;
		} else if(server.equals("serverNewGoal")){
			serverUri=serverNewGoal;
		} else if(server.equals("serverCheckGoal")){
			serverUri=serverCheckGoal;}
		else {
			serverUri=serverPeopleStorage;
		}
		URI uri = UriBuilder.fromUri(serverUri).build();
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(uri);
		
		Response response = null;	

		if(server.equals("serverCheckGoal")){
			response = service.path(path)
					.queryParam("username", obj.get("username"))
					.queryParam("password",  obj.get("password"))
					.queryParam("height",  obj.get("height"))
					.queryParam("idperson",  obj.get("idperson"))
					.queryParam("sleephours",  obj.get("sleephours"))
					.queryParam("maxbloodpressure",  obj.get("maxbloodpressure"))
					.queryParam("minbloodpressure",  obj.get("minbloodpressure"))
					.queryParam("heartrate",  obj.get("heartrate"))
					.queryParam("weight",  obj.get("weight"))
					.queryParam("steps",  obj.get("steps"))
					.request(mediaType)
					.accept(mediaType)
					.get(Response.class);
		}
		else if(server.equals("serverNewGoal")){
			response = service.path(path)
					.queryParam("username", obj.get("username"))
					.queryParam("password",  obj.get("password"))
					.queryParam("height",  obj.get("height"))
					.queryParam("idperson",  obj.get("idperson"))
					.queryParam("sleephours",  obj.get("sleephours"))
					.queryParam("maxbloodpressure",  obj.get("maxbloodpressure"))
					.queryParam("minbloodpressure",  obj.get("minbloodpressure"))
					.queryParam("heartrate",  obj.get("heartrate"))
					.queryParam("weight",  obj.get("weight"))
					.queryParam("steps",  obj.get("steps"))
					.request(mediaType)
					.accept(mediaType)
					.get(Response.class);
		}
		else if(server.equals("serverCheckLogin")){
			response = service.path(path)
				.queryParam("username", obj.get("username"))
				.queryParam("password",  obj.get("password"))
				.queryParam("height",  obj.get("height"))
				.queryParam("sleephours",  obj.get("sleephours"))
				.queryParam("maxbloodpressure",  obj.get("maxbloodpressure"))
				.queryParam("minbloodpressure",  obj.get("minbloodpressure"))
				.queryParam("heartrate",  obj.get("heartrate"))
				.queryParam("weight",  obj.get("weight"))
				.queryParam("steps",  obj.get("steps"))
				.request(mediaType)
				.accept(mediaType)
				.get(Response.class);
		} else if(server.equals("serverPeopleStorage")){
			if (obj.get("saveperson").equals("yes")){
				response = service.path(path)
						.queryParam("username",obj.get("username"))
						.queryParam("password",obj.get("password"))
						.queryParam("saveperson",obj.get("yes"))
						.request(mediaType)
						.accept(mediaType)
						.get(Response.class);
			} else {
				response = service.path(path)
						.queryParam("idperson",obj.get("idperson"))
						.queryParam("password",obj.get("password"))
						.queryParam("saveperson",obj.get("no"))
						.request(mediaType)
						.accept(mediaType)
						.get(Response.class);
			}
		}
		/*
		
		WebTarget wt =  service.path(path);
	    for (Object key : obj.keySet()) {
	        String keyStr = (String)key;
	        String keyvalue = (String) obj.get(keyStr);
	        System.out.println(keyStr+keyvalue);
			wt.queryParam(keyStr, keyvalue);			
		}
		Response response = wt
				.request(mediaType)
				.accept(mediaType)
				.get(Response.class);
		*/

		return response;

	}
	
	
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public String getCalories(
    		@DefaultValue("registration") @QueryParam("action") String action,
    		@DefaultValue("") @QueryParam("steps") String steps,
    		@DefaultValue("") @QueryParam("heartrate") String heartrate,
    		@DefaultValue("") @QueryParam("weight") String weight,
    		@DefaultValue("") @QueryParam("sleephours") String sleephours,
    		@DefaultValue("") @QueryParam("minbloodpressure") String minbloodpressure,
    		@DefaultValue("") @QueryParam("maxbloodpressure") String maxbloodpressure,
    		@DefaultValue("") @QueryParam("username") String username,
    		@DefaultValue("") @QueryParam("password") String password,
    		@DefaultValue("") @QueryParam("height") String height,
    		@DefaultValue("") @QueryParam("idperson") String idperson  		
    		)
    		throws ParserConfigurationException, SAXException, IOException {
	
		String path = "";
		String method = "get";
        System.out.println(this.getClass()+"pre");	

		JSONObject params = new JSONObject();
		params.put("steps",steps);
		params.put("heartrate",heartrate);
		params.put("weight",weight);
		params.put("initialweight",weight);
		params.put("sleephours",sleephours);
		params.put("minbloodpressure",minbloodpressure);
		params.put("maxbloodpressure",maxbloodpressure);
		params.put("username",username);
		params.put("password",password);
		params.put("height",height);
		params.put("idperson",idperson);
		
		if (action.equals("registration")){
	        Response response = makeRequest(//ok
	        		"serverCheckLogin",
	        		path, 
	        		MediaType.APPLICATION_JSON,
	        		method,	        		
	        		params);
	        System.out.println("fields checked");
	        if (response.readEntity(String.class).equals("ok")){
	        	System.out.println("fields are ok");
	        	params.put("saveperson", "yes");
		        response = makeRequest(//ok
		        		"serverPeopleStorage",
		        		path, 
		        		MediaType.APPLICATION_JSON,
		        		method,	        		
		        		params);
		        System.out.println("person saved");
		        String idPerson = response.readEntity(String.class);
		        params.put("idperson", idPerson);
		        response = makeRequest(//ok
		        		"serverNewGoal",
		        		path, 
		        		MediaType.APPLICATION_JSON,
		        		method,	        		
		        		params);
		        System.out.println("goal saved");
		        String newGoal = response.readEntity(String.class);
				JSONObject registrationResponse = new JSONObject();
				registrationResponse.put("acoomplished", "no");
				registrationResponse.put("ok","ok");
				registrationResponse.put("newGoal",newGoal);
				registrationResponse.put("idperson",idPerson);
				return registrationResponse.toString();
	        }
	        else {
	        	System.out.println("fields are NOT ok");
				JSONObject registrationResponse = new JSONObject();
				registrationResponse.put("ok","error");
				return registrationResponse.toString();
	        }
		} else if (action.equals("login")){
        	params.put("saveperson", "no");
	        Response response = makeRequest(//ok
	        		"serverPeopleStorage",
	        		path, 
	        		MediaType.APPLICATION_JSON,
	        		method,	        		
	        		params);	
	        String responseString = response.readEntity(String.class);
			JSONObject loginResponse = new JSONObject();
	        if(responseString.equals("-1")){
	        	loginResponse.put("ok","error");    	
	        } else {
	        	loginResponse.put("ok","ok");
	        	loginResponse.put("idperson",responseString);
	        }
			return loginResponse.toString();	    
		} else if (action.equals("daily")){
	        Response response = makeRequest(//ok
	        		"serverCheckGoal",
	        		path, 
	        		MediaType.APPLICATION_JSON,
	        		method,	        		
	        		params);

	        JSONObject objCheckGoal = new JSONObject(response);

	        if(objCheckGoal.get("acoomplished").equals("yes")){
		        response = makeRequest(//ok
		        		"serverNewGoal",
		        		path, 
		        		MediaType.APPLICATION_JSON,
		        		method,	        		
		        		params);

		        JSONObject objNewGoal = new JSONObject(response);
		        
		        objNewGoal.put("newGoal", response);
		        objNewGoal.put("acoomplished", "yes");
		        
		        return response.readEntity(String.class);
	        } else {
	        	return objCheckGoal.toString();//////////////////
	        }
		} else return "";
    }

}
