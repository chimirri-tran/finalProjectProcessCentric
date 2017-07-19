package introsde.rest.ehealth.resources;

import java.io.IOException;
import java.net.URI;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.ParserConfigurationException;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.SAXException;

@Stateless
@LocalBean
@Path("/external")
public class ExternalProcessCentric {
	
	static String serverCalories = "http://localhost:5701/sdelab/caloriesBusiness";
	static String serverExercize = "http://localhost:5702/sdelab/exercizeStorage";
	static String serverMotivation =  "http://localhost:5702/sdelab/motivationStorage";
	
	public static Response makeRequest(String path, String mediaType, String method, 
			String paramName, String paramValue, String serverName){

		String serverUri = "";
		if(serverName.equals("calories")){serverUri=serverCalories;}
		else if(serverName.equals("exercize")){serverUri=serverExercize;}
		else {serverUri=serverMotivation;}
		URI server = UriBuilder.fromUri(serverUri).build();
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(server);
		Response response = null;
		if(method.equals("get") && !paramName.equals("") && !paramValue.equals(""))
		response = service.path(path)
				.queryParam(paramName, paramValue)
				.request(mediaType).accept(mediaType)
				.get(Response.class);
		else if(method.equals("get"))
			response = service.path(path)
			.request(mediaType).accept(mediaType)
			.get(Response.class);

		return response;

	}
	
	public String responseToString(Response response){
		
		String content = response.readEntity(String.class);
		
		/*if too many calories than exercize*/
		JSONObject obj = new JSONObject(content);
		JSONArray arr = (JSONArray) obj.get("introsdeExercize");
		
		if(arr.get(0).equals("yes")) {
			
			String path = "";
	        System.out.println("Getting exercize");	
	        Response responseExercize = makeRequest(path, MediaType.APPLICATION_JSON, "get",
	        		"", "","exercize");
	        String contentExercize = responseExercize.readEntity(String.class);
	        JSONObject objExercize = new JSONObject(contentExercize);
	        obj.append("exercize", ((JSONArray) objExercize.get("exercize")).get(0).toString());
		} else {
	        obj.append("exercize", "NO");
		}
		
		return obj.toString();
	}
	
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public String getCalories(
    		@DefaultValue("calories") @QueryParam("server") String server,
    		@DefaultValue("apple") @QueryParam("food") String food)
    		throws ParserConfigurationException, SAXException, IOException {
	
		String paramName = "food";		
		String paramValue = food;		
		String path = "";
        System.out.println(this.getClass()+food);	
        Response response = makeRequest(path, MediaType.APPLICATION_JSON, "get",
        		paramName, paramValue, server);	
        if(server.equals("calories")){
        	String caloriesString = responseToString(response);
        	System.out.println(this.getClass()+caloriesString);
            return caloriesString;
        }
        else return response.readEntity(String.class);

    }

}
