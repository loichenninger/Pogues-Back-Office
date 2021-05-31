package fr.insee.pogues.transforms;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.insee.pogues.persistence.service.QuestionnairesService;
import fr.insee.pogues.webservice.rest.PoguesException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class LunaticJSONToUriStromaeV2Imp implements LunaticJSONToUriStromaeV2{
	
	@Autowired
	private QuestionnairesService questionnaireService;
	
	@Value("${fr.insee.pogues.api.host}")// localhost:8080
	private String apiHost;
	
	@Value("${fr.insee.pogues.api.name}")// /rmes-pogfo/pogues
	private String apiName;
	
	@Value("${fr.insee.pogues.api.scheme}")// http
	private String apiScheme;
	
	@Value("${fr.insee.pogues.api.remote.stromaev2.vis.url}")
	private String uriStromaeV2;

	@Override
	public void transform(InputStream input, OutputStream output, Map<String, Object> params, String surveyName)
			throws Exception {
		throw new NotImplementedException();
		
	}

	@Override
	public String transform(InputStream input, Map<String, Object> params, String surveyName) throws Exception {
		throw new NotImplementedException();
	}

	@Override
	public String transform(String input, Map<String, Object> params, String surveyName) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject jsonContent = (JSONObject) parser.parse(input);
		String id  = (String) jsonContent.get("id");
		try {
			questionnaireService.createJsonLunatic(jsonContent);
		} catch (PoguesException e) {
			questionnaireService.updateJsonLunatic(id, jsonContent);
        } catch (Exception e) {
            throw new Exception(String.format("%s:%s", getClass().getName(), e.getMessage()));
        }
		String urlGetJsonLunatic = String.format("%s://%s%s/persistence/questionnaire/json-lunatic/%s",apiScheme,apiHost,apiName,id);
		String uriVisuStromaeV2 = String.format("%s%s", uriStromaeV2, URLEncoder.encode(urlGetJsonLunatic, "UTF-8"));
		return uriVisuStromaeV2;
	}





}
