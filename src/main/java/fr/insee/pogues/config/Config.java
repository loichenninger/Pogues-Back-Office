package fr.insee.pogues.config;

import org.springframework.core.env.Environment;

public class Config {


	public static String APP_HOST = "";
	
	public static String ENV = "";
	public static boolean REQUIRES_SSL = false;
	

	private Config() {
		throw new IllegalStateException("Utility class");
	}
	
	public static void setConfig(Environment env) {
//		System.out.println(env.getProperty("fr.insee.rmes.bauhaus.sesame.gestion.sesameServer"));
//		Config.APP_HOST = env.getProperty("fr.insee.rmes.bauhaus.concepts.appHost");
		
		Config.ENV = "dev";

		
	}

//	private static void readConfigForStructures(Environment env) {
//		Config.DSDS_GRAPH = env.getProperty("fr.insee.rmes.bauhaus.dsds.graph");
//		Config.DSDS_BASE_URI = env.getProperty("fr.insee.rmes.bauhaus.dsds.baseURI");
//		Config.STRUCTURES_COMPONENTS_GRAPH = env.getProperty("fr.insee.rmes.bauhaus.structures.components.graph");
//	}
}