package rest.test.example.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import rest.test.example.server.AccessoriesServer;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/it/resources", strict = true, glue = "rest.test.stepdefs", tags = {
		"@AccessoriesInventory" }, plugin = { "pretty", "html:target/failsafe-reports-description" })
public class CucumberFeaturesAccessoriesIT {
	public static void main(String[] args) throws Exception {
		new AccessoriesServer(9988).start();
	}
}
