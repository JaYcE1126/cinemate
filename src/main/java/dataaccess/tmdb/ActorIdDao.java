package dataaccess.tmdb;

import java.util.HashMap;

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

public class ActorIdDao implements Dao {
	
	private static final Logger logger = LoggerFactory.getLogger(MovieIdDao.class);

	private String userInput;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	public ActorIdDao(String userInput){
		this.userInput = userInput;
	}
	
	public int execute(){
		logger.info("Entered: [userInput: {}]", this.userInput);
		int returnCode = 1;
		int totalResults = 0;
		String url;
		
		url = Constants.URL_TMDB_SEARCH_ACTOR + userInput.replaceAll(" ", "%20");
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
				JSONObject actorResult = jsonResultsArray.getJSONObject(0);
				
				int actorIdResult = actorResult.getInt(Constants.TMDB_RESPONSE_ID);
				data.put(Constants.TMDB_RESPONSE_ID, actorIdResult);
				String actorNameResult = actorResult.getString(Constants.TMDB_RESPONSE_NAME);
				data.put(Constants.TMDB_RESPONSE_NAME, actorNameResult);

				logger.trace("retrieved [actorId: {}, actorName: {}]",actorIdResult, actorNameResult);
				
				returnCode = 0;
				
			}
		} else {
			String errorMessage = jsonResponseBody.getJSONArray(Constants.TMDB_RESPONSE_ERROR_MESSAGE).getString(0);
			data.put(Constants.TMDB_RESPONSE_ERROR_MESSAGE, errorMessage);
			returnCode = -1;
		}
	
		logger.debug("Exited: [returnCode: {}]",returnCode);
		return returnCode;
	}
	
	public HashMap<String, Object> getData(){
		return this.data;
	}

}

