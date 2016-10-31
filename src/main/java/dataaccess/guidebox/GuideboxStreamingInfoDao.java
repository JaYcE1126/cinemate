package dataaccess.guidebox;

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
import dataaccess.tmdb.MovieIdDao;
import utility.Constants;

public class GuideboxStreamingInfoDao implements Dao{
	private static final Logger logger = LoggerFactory.getLogger(MovieIdDao.class);

	private String userInput;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	public GuideboxStreamingInfoDao(String userInput){
		this.userInput = userInput;
	}
	
	public int execute(){
		logger.info("Entered: [userInput: {}]", this.userInput);
		List<String> webSubscriptionSrc = new ArrayList<String>();
		String url;
		String responseHeader = "";
		int httpsStatusCode;
		int returnCode = 1;
		
		url = Constants.URL_GB_MOVIE_INFOL + userInput;
		logger.info("url: {}",url);

		Client client = ClientBuilder.newClient();
		Response response = client.target(url)
			  .request(MediaType.TEXT_PLAIN_TYPE)
			  .header("Accept", "application/json")
			  .get();
		
		httpsStatusCode = response.getStatus();
		logger.info("httpStatusCode = {}", httpsStatusCode);
		
		responseHeader = response.getHeaders().toString();
		logger.debug("responseHeader = {}", responseHeader);
		
		String responseBody = response.readEntity(String.class); //Created this String to log body more efficiently
		JSONObject jsonResponseBody = new JSONObject(responseBody);
		logger.debug("responseBody = {}", responseBody);
		
		if (Constants.SUCCESSFUL_HTTPS_STATUS_CODE == httpsStatusCode){
			JSONArray jsonWebSubscription = jsonResponseBody.getJSONArray(Constants.GB_RESPONSE_WEB_SUBSCRIPTION);
			
			JSONObject source;
			for (int i=0; i<jsonWebSubscription.length(); i++){
				source = jsonWebSubscription.getJSONObject(i);
				webSubscriptionSrc.add(source.getString(Constants.GB_RESPONSE_SOURCE_NAME));
				logger.debug("added {} to web subscription source list.",webSubscriptionSrc.get(i));
			}

			data.put(Constants.GB_RESPONSE_WEB_SUBSCRIPTION, webSubscriptionSrc);
			if (jsonWebSubscription.length()>0) returnCode = 0;
			
		} else {
			returnCode = -1;
			String errorMessage = jsonResponseBody.getString(Constants.GB_RESPONSE_ERROR);
			logger.debug("errorMessage = {}", errorMessage);
			data.put(Constants.GB_RESPONSE_ERROR, errorMessage);
		}
		logger.info("Exited: [returnCode = {}]", returnCode);
	
	return returnCode;
	}
	
	public HashMap<String, Object> getData(){
		return this.data;
	}


}
