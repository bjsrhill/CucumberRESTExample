package rest.test.stepdefs;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

import java.net.HttpURLConnection;

import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClientStepDefs {
	
	private ClientResponse response;
	
	@When("^a GET /accessories is invoked$")
	public void a_GET_accessories_is_invoked() throws Throwable {
		try {

            Client client = Client.create();

            WebResource webResource = client
                    .resource("http://localhost:" + AccessoriesHooks.PORT + "/accessories");

            response = webResource.type("application/json")
                    .get(ClientResponse.class);
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {
            System.out.println("Exception caught attemtping to invoke GET /accessories" + e.getMessage());
        }
        assertEquals("Response was not OK: ", 
            HttpURLConnection.HTTP_OK, response.getStatus());
	}

	@Then("^a JSON resonse is returned$")
	public void a_JSON_resonse_is_returned(String expectedResponse) throws Throwable {
		JsonParser parser = new JsonParser();
        assertEquals("Returned JSON is not correct.", 
            parser.parse(expectedResponse), parser.parse(response.getEntity(String.class)));
	}


}
