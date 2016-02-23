package rest.test.stepdefs;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import rest.test.example.server.AccessoriesServer;

/**
 * Cucumber hooks for before and after each scenario
 * 
 * @author beverlyshill
 *
 */
public class AccessoriesHooks {

	public static final int PORT = 8887;
	private AccessoriesServer skillsServer = new AccessoriesServer(PORT);

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
	public void startSkillsServer() {
		skillsServer.start();
	}

	@After(order = 1)
	public void stopSkillsServer() {
		skillsServer.stop();
	}
}