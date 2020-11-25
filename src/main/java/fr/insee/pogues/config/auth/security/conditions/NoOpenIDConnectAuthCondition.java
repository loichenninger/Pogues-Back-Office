package fr.insee.pogues.config.auth.security.conditions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

@Component
public class NoOpenIDConnectAuthCondition implements Condition {
	
	private static final Logger logger = LoggerFactory.getLogger(NoOpenIDConnectAuthCondition.class);

	private Boolean authentication;


	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		try {
			getEnvironmentProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("[NoOpenIDConnectAuthCondition] Authentication : " + authentication);
		return !authentication;
	}
	
	private void getEnvironmentProperties() throws IOException {
		Properties props = this.getProperties();
		this.authentication = Boolean.valueOf(props.getProperty("fr.insee.pogues.authentication"));
	}

	private Properties getProperties() throws IOException {
        Properties props = new Properties();
        String env = System.getProperty("fr.insee.pogues.env");
        if(null == env) {
            env = "dev";
        }
        String propsPath = String.format("env/%s/pogues-bo.properties", env);
        props.load(getClass()
                .getClassLoader()
                .getResourceAsStream(propsPath));
        File f = new File(String.format("%s/webapps/%s", System.getProperty("catalina.base"), "pogues-bo.properties"));
        if(f.exists() && !f.isDirectory()) {
            FileReader r = new FileReader(f);
            props.load(r);
            r.close();
        }
        File f2 = new File(String.format("%s/webapps/%s", System.getProperty("catalina.base"), "rmspogfo.properties"));
        if(f2.exists() && !f2.isDirectory()) {
            FileReader r2 = new FileReader(f2);
            props.load(r2);
            r2.close();
        }
        File f3 = new File(String.format("%s/webapps/%s", System.getProperty("catalina.base"), "rmespogfo.properties"));
        if(f3.exists() && !f3.isDirectory()) {
            FileReader r3 = new FileReader(f3);
            props.load(r3);
            r3.close();
        }
        return props;
    }

}

