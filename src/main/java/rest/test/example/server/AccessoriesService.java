package rest.test.example.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * REST service that will be tested
 * 
 * @author beverlyshill
 *
 */
@Path("/")
public class AccessoriesService {

	@GET
	@Path("/accessories")
	@Produces(MediaType.APPLICATION_JSON)

	/**
	 * Adds accessories to an array
	 * 
	 * @return an AccessoriesModel object
	 */
	public AccessoriesModel[] getAccessories() {
		List<AccessoriesModel> accessories = processAccessories();
		return accessories.toArray(new AccessoriesModel[] {});
	}

	/**
	 * Gets the values contained in the output file and returns a List of those
	 * values
	 * 
	 * @return a List object of the file values
	 */
	private List<AccessoriesModel> processAccessories() {
		String accessoriesValue = importAccessoriesFile();
		return compileAccessories(accessoriesValue);
	}

	/**
	 * Imports the values from the input file
	 * 
	 * @return a String of the JSON values imported from the input file
	 */
	private String importAccessoriesFile() {

		String fileName = "src/it/resources/accessories.json";

		try {
			return (String) new Scanner(new File(fileName)).useDelimiter("\\Z").next();
		} catch (FileNotFoundException e) {
			System.out.println("The accessories file could not be read. " + e.getMessage());
			return "[]";
		}
	}

	/**
	 * Returns a List of AccessoriesModel
	 * 
	 * @param accessoriesValue
	 *            is a String representing the JSON values from the input file.
	 * @return a List of AccessoriesModel
	 */
	private List<AccessoriesModel> compileAccessories(String accessoriesValue) {
		final TypeToken<List<AccessoriesModel>> typeToken = new TypeToken<List<AccessoriesModel>>() {
		};
		final Type type = typeToken.getType();
		final Gson gson = new Gson();

		return gson.fromJson(accessoriesValue, type);
	}
}
