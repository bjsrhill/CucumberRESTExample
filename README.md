# CucumberRESTExample
Example of using Cucumber in REST test automation.

This test automation example uses Cucumber JVM to test a simple REST service or a service stub using WireMock.

The test automation example features the following technology:

Java, Cucumber-JVM (Cucumber for Java), Maven, Jersey, WireMock

Requirements for running the example:

Java 1.6 or higher  
Maven 3  
Git  

To run the example after cloning locally:

cd CucumberRESTExample  
mvn clean install

src/it/resources/config.properties useServer can be set to false to use the WireMock stub or true to run the example web server. 
