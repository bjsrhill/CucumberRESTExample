package rest.test.stepdefs;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import rest.test.example.server.AccessoriesServer;

/**
 * Cucumber hooks before the first step of each scenario
 * and after the last step of each scenario
 * 
 * @author beverlyshill
 *
 */
public class AccessoriesHooks {

	public static final int PORT = 8887;
	private AccessoriesServer skillsServer = new AccessoriesServer(PORT);
	private Properties p = new Properties(System.getProperties());
	private static ClientResponse responseWireMock;
	private static ClientResponse responseServer;
	private Client client = Client.create();
	private WireMockServer wireMockServer;

	@Before(order = 1)
	public void deleteDataFile() {
		Path path = FileSystems.getDefault().getPath("skills.json");
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			System.out.println("The skills.json file cannot be deleted. " + e.getMessage());
		}
	}

	@Before(order = 2)
	public void start() {
		if (p.getProperty("useServer").equals("true")) {
			skillsServer.start();
			getServerResponse();
		} else {
			getWireMockResponse();
		}
	}

	@After(order = 1)
	public void stop() {
		if (p.getProperty("useServer").equals("true")) {
			skillsServer.stop();
		} else {
			wireMockServer.stop();
		}
	}

	/**
	 * Gets Wiremock response from GET /accessories
	 */
	private void getWireMockResponse() {
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8090)); 
		wireMockServer.start();
		wireMockServer.stubFor(get(urlEqualTo("/accessories")).willReturn(aResponse()
				.withBody(
						"[{'accessories':'Mouse','category':'computers'},{'accessories':'Mousepad','category':'computers'},{'accessories':'Keyboard','category':'computers'},{'accessories':'Cable','category':'computers'},{'accessories':'Monitor','category':'computers'}]")
				.withStatus(200)));
		WebResource webResource = client.resource("http://localhost:" + wireMockServer.port() + "/accessories");
		responseWireMock = webResource.type("application/json").get(ClientResponse.class);
	}

	/**
	 * Gets server response from GET /accessories
	 */
	private void getServerResponse() {
		try {
			WebResource webResource = client.resource("http://localhost:" + AccessoriesHooks.PORT + "/accessories");
			responseServer = webResource.type("application/json").get(ClientResponse.class);
		} catch (RuntimeException r) {
			throw r;
		} catch (Exception e) {
			System.out.println("Exception caught attemtping to invoke GET /accessories" + e.getMessage());
		}
	}

	public static ClientResponse getResponseWireMock() {
		return responseWireMock;
	}

	public static ClientResponse getResponseServer() {
		return responseServer;
	}
}
