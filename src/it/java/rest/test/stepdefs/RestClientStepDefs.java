package rest.test.stepdefs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Rule;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class RestClientStepDefs {

	private ClientResponse response;
	private Properties p = new Properties(System.getProperties());

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.wireMockConfig().port(8090));

	@When("^a GET /accessories is invoked$")
	public void a_GET_accessories_is_invoked() throws Throwable {
		if (p.getProperty("useServer").equals("true")) {
			try {
				Client client = Client.create();
				WebResource webResource = client.resource("http://localhost:" + AccessoriesHooks.PORT + "/accessories");
				response = webResource.type("application/json").get(ClientResponse.class);
			} catch (RuntimeException r) {
				throw r;
			} catch (Exception e) {
				System.out.println("Exception caught attemtping to invoke GET /accessories" + e.getMessage());
			}
		} else {
			initializeWireMock();
			assertEquals(200, response.getStatus());
			assertEquals(response.getEntity(String.class),
					"[{'accessories':'Mouse','category':'computers'},{'accessories':'Mousepad','category':'computers'},{'accessories':'Keyboard','category':'computers'},{'accessories':'Cable','category':'computers'},{'accessories':'Monitor','category':'computers'}]");
		}
	}

	@Then("^a JSON resonse is returned$")
	public void a_JSON_resonse_is_returned(String expectedResponse) throws Throwable {
		initializeWireMock();
		JsonParser parser = new JsonParser();
		assertEquals("Returned JSON is not correct.", parser.parse(expectedResponse),
				parser.parse(response.getEntity(String.class)));
	}

	/**
	 * Helper method to initialize and start Wiremock
	 */
	private void initializeWireMock() {
		wireMockRule.start();
		wireMockRule.stubFor(get(urlEqualTo("/accessories")).willReturn(aResponse()
				.withBody(
						"[{'accessories':'Mouse','category':'computers'},{'accessories':'Mousepad','category':'computers'},{'accessories':'Keyboard','category':'computers'},{'accessories':'Cable','category':'computers'},{'accessories':'Monitor','category':'computers'}]")
				.withStatus(200)));
		Client client = Client.create();
		WebResource webResource = client.resource("http://localhost:" + wireMockRule.port() + "/accessories");
		response = webResource.type("application/json").get(ClientResponse.class);
	}
}
