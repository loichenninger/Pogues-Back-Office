package fr.insee.pogues.search.repository;

import fr.insee.pogues.search.model.DDIItem;
import fr.insee.pogues.search.model.DataCollectionContext;
import fr.insee.pogues.search.model.PoguesQuery;
import fr.insee.pogues.search.model.ResponseSearchItem;
import fr.insee.pogues.search.model.SolrResult;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Component(value="PoguesItemRepositoryImpl")
public class PoguesItemRepositoryImpl implements PoguesItemRepository {

    @Value("${fr.insee.pogues.api.remote.metadata.url}")
    String serviceUrl;


    @Autowired
    RestTemplate restTemplate;


    @Override
    public List<ResponseSearchItem> findByLabel(PoguesQuery query, MultiValueMap<String, String> params) throws Exception {
        String url = String.format("%s/search", serviceUrl);
        url = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).toUriString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("Content-type", ContentType.APPLICATION_JSON.getMimeType());
        HttpEntity<PoguesQuery> request = new HttpEntity<>(query, headers);
        ResponseEntity<ResponseSearchItem[]> response = restTemplate
                .exchange(url, HttpMethod.POST, request, ResponseSearchItem[].class);
        if(null == response.getBody()) {
            return new ArrayList();
        }
        return Arrays.asList(response.getBody());
    }

    @Override
    public List<DDIItem> getSubGroups() throws Exception {
        String url = String.format("%s/search/series", serviceUrl);
        ResponseEntity<DDIItem[]> response = restTemplate
                .exchange(url, HttpMethod.GET, null, DDIItem[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public List<DDIItem> getStudyUnits(String subgGroupId) throws Exception {
        String url = String.format("%s/search/series/%s/operations", serviceUrl, subgGroupId);
        ResponseEntity<DDIItem[]> response = restTemplate
                .exchange(url, HttpMethod.GET, null, DDIItem[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public List<DDIItem> getDataCollections(String studyUnitId) throws Exception {
        String url = String.format("%s/search/operations/%s/data-collection", serviceUrl, studyUnitId);
        ResponseEntity<DDIItem[]> response = restTemplate
                .exchange(url, HttpMethod.GET, null, DDIItem[].class);
        return Arrays.asList(response.getBody());
    }
    
    @Override
	public DataCollectionContext getDataCollectionContext(String dataCollectionId) throws Exception {
    	String url = String.format("%s/search/context/data-collection/%s", serviceUrl, dataCollectionId);
        ResponseEntity<DataCollectionContext> response = restTemplate
                .exchange(url, HttpMethod.GET, null, DataCollectionContext.class);
        return response.getBody();
	}
    
    @Override
	public List<SolrResult> getSolrResults(String label) throws Exception {
    	String url = String.format("%s/search/items/%s", serviceUrl, label);
        ResponseEntity<SolrResult[]> response = restTemplate
                .exchange(url, HttpMethod.GET, null, SolrResult[].class);
        return Arrays.asList(response.getBody());
	}

}
