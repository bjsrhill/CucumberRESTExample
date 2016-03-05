package rest.test.stepdefs;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import com.google.gson.JsonParser;
import com.sun.jersey.api.client.ClientResponse;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RestClientStepDefs {

	private Properties p = new Properties(System.getProperties());
	private ClientResponse responseWireMock = AccessoriesHooks.getResponseWireMock();
	private ClientResponse responseServer = AccessoriesHooks.getResponseServer();
	private int statusServer;
	private String bodyServer;
	private int statusWireMock;
	private String bodyWireMock;
	private int statusValue = 200;

	@When("^a GET /accessories is invoked$")
	public void a_GET_accessories_is_invoked() throws Throwable {
		if (p.getProperty("useServer").equals("true")) {
			statusServer = responseServer.getStatus();
			bodyServer = responseServer.getEntity(String.class);
			assertEquals(statusValue, statusServer);
		} else {
			statusWireMock = responseWireMock.getStatus();
			bodyWireMock = responseWireMock.getEntity(String.class);
			assertEquals(statusValue, statusWireMock);
		}
	}

	@Then("^a JSON resonse is returned$")
	public void a_JSON_resonse_is_returned(String expectedResponse) throws Throwable {
		JsonParser parser = new JsonParser();
		if (p.getProperty("useServer").equals("true")) {
			 assertEquals("Returned JSON is not correct.",
			 parser.parse(expectedResponse), parser.parse(bodyServer));
		} else {
			assertEquals("Returned JSON is not correct.", parser.parse(expectedResponse), parser.parse(bodyWireMock));
		}
	}
}
