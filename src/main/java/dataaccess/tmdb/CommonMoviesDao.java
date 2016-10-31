package dataaccess.tmdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dataaccess.Dao;
import utility.Constants;
import value.Actor;


public class CommonMoviesDao implements Dao {
	private static final Logger logger = LoggerFactory.getLogger(MovieIdDao.class);

	private List<Actor> actorList = new ArrayList<Actor>();
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	public CommonMoviesDao(List<Actor> actorList){
		this.actorList = actorList;
	}

	public int execute(){
		logger.info("Entered: [actorList: {}]", this.actorList);
		int returnCode = 1;		
		int totalResults = 0;
		List<String> commonMovies = new ArrayList<String>();
		String url;
		
		String with_cast = "" + actorList.get(0).getId();
		for (int i=1; i<actorList.size();i++){
			with_cast += "," + actorList.get(i).getId();
		}
		
		url = Constants.URL_TMDB_DISCOVER_COMMON_MOVIES + with_cast;
		logger.info("url: {}", url);
		
		Client client = ClientBuilder.newClient();
		Response response = client.target(url)
		  .request(MediaType.TEXT_PLAIN_TYPE)
		  .header("Accept", "application/json")
		  .get();
		
		int httpsStatusCode = response.getStatus();
		logger.info("httpStatusCode: {}", httpsStatusCode);
		
		String responseHeader = response.getHeaders().toString();
		logger.debug("responseHeader: {}", responseHeader);
		
		String responseBody = response.readEntity(String.class); //Created this String to log body more efficiently
		JSONObject jsonResponseBody = new JSONObject(responseBody);
		logger.debug("responseBody = {}", responseBody);
		
		if (Constants.SUCCESSFUL_HTTPS_STATUS_CODE == httpsStatusCode){
			totalResults = jsonResponseBody.getInt(Constants.TMDB_RESPONSE_TOTAL_RESULTS);
			
			if (totalResults > 0) {
				JSONArray jsonResultsArray = jsonResponseBody.getJSONArray(Constants.TMDB_RESPONSE_RESULTS);
				
				for(int i = 0; i<jsonResultsArray.length(); i++){
					commonMovies.add(jsonResultsArray.getJSONObject(i).getString(Constants.TMDB_RESPONSE_TITLE));
				}
				data.put(Constants.DAO_RESPONSE_COMMON_MOVIES, commonMovies);
				returnCode = 0;
			}
			
		} else {
			returnCode = -1;
			data.put(Constants.TMDB_RESPONSE_ERROR_MESSAGE, jsonResponseBody.getString(Constants.TMDB_RESPONSE_STATUS_MESSAGE));
		}
	
		logger.debug("Exited: [returnCode: {}]",returnCode);
		return returnCode;
	}
	
	public HashMap<String, Object> getData(){
		return this.data;
	}

}

