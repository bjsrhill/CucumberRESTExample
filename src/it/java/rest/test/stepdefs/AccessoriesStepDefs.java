package rest.test.stepdefs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cucumber.api.java.en.Given;
import gherkin.deps.com.google.gson.Gson;
import rest.test.example.server.AccessoriesModel;

/**
 * Cucumber step definition
 * 
 * @author beverlyshill
 *
 */
public class AccessoriesStepDefs {

	@Given("^a list of accessories is available for category$")
	public void a_list_of_accessories_is_available_for_category(List<AccessoriesModel> accessories) throws Throwable {
		try {
			Gson content = new Gson();
			File file = new File("src/it/resources/accessories.json");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(content.toJson(accessories));
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("There was an error writing the accessories file. " + e.getMessage());
			e.printStackTrace();
		}
	}
}
