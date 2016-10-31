package dataaccess.guidebox;

import java.util.HashMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dataaccess.Dao;
import dataaccess.tmdb.MovieIdDao;
import utility.Constants;

public class GuideboxIdDao implements Dao{
	private static final Logger logger = LoggerFactory.getLogger(MovieIdDao.class);

	private String userInput;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	public GuideboxIdDao(String userInput){
		this.userInput = userInput;
	}
	
	public int execute(){
		logger.info("Entered: [userInput: {}]", this.userInput);
		int returnCode = 1;
		String url;
		
		url = Constants.URL_GB_SEARCH_ID + userInput;
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
			
			if (jsonResponseBody.has(Constants.GB_RESPONSE_MOVIE_ID)){
				int gbMovieId = jsonResponseBody.getInt(Constants.GB_RESPONSE_MOVIE_ID);
				data.put(Constants.GB_RESPONSE_MOVIE_ID, gbMovieId);
				returnCode = 0;
			} 
		} else {
			returnCode = -1;
			String errorMessage = jsonResponseBody.getString(Constants.GB_RESPONSE_ERROR);
			logger.debug("errorMessage: {}", errorMessage);
			data.put(Constants.GB_RESPONSE_ERROR, errorMessage);
		}

		logger.info("Exited: [returnCode = {}]", returnCode);
		return returnCode;
	}
	
	public HashMap<String, Object> getData(){
		return this.data;
	}


}
