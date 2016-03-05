package rest.test.example.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sun.jersey.spi.container.servlet.ServletContainer;

/**
 * Server for the REST service which will be tested
 * 
 * @author beverlyshill
 *
 */
public class AccessoriesServer {

	private final Server accessoriesServer;

	/**
	 * Constructor that instantiates and configures the server
	 * 
	 * @param port
	 *            is an int representing the server's listening port
	 */
	public AccessoriesServer(int port) {
		ServletHolder servletHolder = new ServletHolder(ServletContainer.class);
		servletHolder.setInitParameter("com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		servletHolder.setInitParameter("com.sun.jersey.config.property.packages", "rest.test.example.server");
		servletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

		accessoriesServer = new Server(port);
		ServletContextHandler context = new ServletContextHandler(accessoriesServer, "/",
				ServletContextHandler.SESSIONS);
		context.addServlet(servletHolder, "/");
	}

	/**
	 * Starts the server
	 */
	public void start() {
		try {
			accessoriesServer.start();
		} catch (Exception e) {
			System.out.println("There was an error starting Accessories Server. " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Listening on " + accessoriesServer.getURI());
	}

	/**
	 * Stops the server
	 */
	public void stop() {
		try {
			accessoriesServer.stop();
		} catch (Exception e) {
			System.out.println("There was an error stopping Skills Server. " + e.getMessage());
			e.printStackTrace();
		}
	}
}
