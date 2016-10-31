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
import utility.Utilities;
import wrapper.MovieIdWrapper;

public class MovieIdDao implements Dao{
	private static final Logger logger = LoggerFactory.getLogger(MovieIdDao.class);

	private String userInput;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	public MovieIdDao(String userInput){
		this.userInput = userInput;
	}
	
	public int execute(){
		logger.info("Entered: [userInput: {}]", this.userInput);
		int returnCode = 1;
		int totalResults = 0;
		String url;
		List<MovieIdWrapper> movieIdList = new ArrayList<MovieIdWrapper>();
		
		url = Constants.URL_TMDB_SEARCH_MOVIE + userInput.replaceAll(" ", "%20");
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
				MovieIdWrapper movieIdWrapper;
				int lowestDistance = 1000;
				
				for (int i =0; i < jsonResultsArray.length() && i < 10; i++){
					
					JSONObject movieResult = jsonResultsArray.getJSONObject(i);
					int movieIdResult = movieResult.getInt(Constants.TMDB_RESPONSE_ID);
					String movieTitleResult = movieResult.getString(Constants.TMDB_RESPONSE_TITLE);
					String movieReleaseDateResults = movieResult.getString(Constants.TMDB_RESPONSE_RELEASE_DATE);
										
					int distance = Utilities.getDistance(this.userInput, movieTitleResult);
					logger.trace("distance between {} and {}: {}",this.userInput, movieTitleResult, distance);
					
					if (distance < lowestDistance) {
						lowestDistance = distance;
						movieIdWrapper = new MovieIdWrapper(movieIdResult, movieTitleResult, movieReleaseDateResults);
						movieIdList = new ArrayList<MovieIdWrapper>();
						movieIdList.add(movieIdWrapper);

					} else if (distance == lowestDistance){
						movieIdWrapper = new MovieIdWrapper(movieIdResult, movieTitleResult, movieReleaseDateResults);
						movieIdList.add(movieIdWrapper);
					}
					
				} 
				logger.trace("movieIdList: {}",movieIdList);
				data.put(Constants.TMDB_RESPONSE_ID, movieIdList);
				
				if (movieIdList.size()>1) {
					returnCode = 2;
				} else {
					returnCode = 0;
				}
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
