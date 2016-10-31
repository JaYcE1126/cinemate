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


public class MovieInfoDao implements Dao {
	private static final Logger logger = LoggerFactory.getLogger(MovieIdDao.class);

	private String userInput;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	
	public MovieInfoDao(int userInput){
		this.userInput = "" + userInput;
		data.put(Constants.TMDB_RESPONSE_ID, userInput);
	}

	public int execute(){
		logger.info("Entered: [userInput: {}]", this.userInput);
		int returnCode = 1;		
		String url;
		
		url = Constants.URL_TMDB_MOVIE_INFO.replaceAll("###", userInput);
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
			
			List<String> originalMusicComposers = new ArrayList<String>();
			List<String> directors = new ArrayList<String>();
			List<String> writers = new ArrayList<String>();
			List<String> producers = new ArrayList<String>();
			List<String> cast = new ArrayList<String>();
			List<String> castRoles = new ArrayList<String>();

			String title = jsonResponseBody.getString(Constants.TMDB_RESPONSE_TITLE).replaceAll("&", "and");
			data.put(Constants.TMDB_RESPONSE_TITLE, title);
			logger.debug("title = {}",title);
			
			String plot = jsonResponseBody.getString(Constants.TMDB_RESPONSE_PLOT);
			data.put(Constants.TMDB_RESPONSE_PLOT, plot);
			logger.debug("plot = {}",plot);
			
			String posterLocation = jsonResponseBody.getString(Constants.TMDB_RESPONSE_POSTER_PATH);
			data.put(Constants.TMDB_RESPONSE_POSTER_PATH, posterLocation);
			logger.debug("posterLocation = {}",posterLocation);
			
			String releaseDate = jsonResponseBody.getString(Constants.TMDB_RESPONSE_RELEASE_DATE);
			data.put(Constants.TMDB_RESPONSE_RELEASE_DATE, releaseDate);
			logger.debug("releaseDate = {}",releaseDate);
			
			String runtime = "" + jsonResponseBody.getInt(Constants.TMDB_RESPONSE_RUNTIME);
			data.put(Constants.TMDB_RESPONSE_RUNTIME, runtime);
			logger.debug("runtime = {}",runtime);
			
			JSONObject jsonResponseCredits = jsonResponseBody.getJSONObject(Constants.TMDB_RESPONSE_CREDITS);
			JSONArray jsonResponseCastArray = jsonResponseCredits.getJSONArray(Constants.TMDB_RESPONSE_CREDITS_CAST);
			JSONArray jsonResponseCrewArray = jsonResponseCredits.getJSONArray(Constants.TMDB_RESPONSE_CREDITS_CREW);
			JSONObject jsonCrewObject;
			
			for (int i=0; i < jsonResponseCrewArray.length(); i++){
				jsonCrewObject = jsonResponseCrewArray.getJSONObject(i);

				if (Constants.TMDB_RESPONSE_CREDITS_DIRECTOR.equals(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_JOB))){
					directors.add(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_NAME));
					
				} else if (Constants.TMDB_RESPONSE_CREDITS_PRODUCER.equals(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_JOB))){
					producers.add(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_NAME));

				} else if (Constants.TMDB_RESPONSE_CREDITS_SCREENPLAY.equals(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_JOB)) 
						|| Constants.TMDB_RESPONSE_CREDITS_WRITER.equals(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_JOB))){
					writers.add(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_NAME));

				}else if (Constants.TMDB_RESPONSE_CREDITS_COMPOSER.equals(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_JOB))
						|| Constants.TMDB_RESPONSE_CREDITS_MUSIC.equals(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_JOB))){
					originalMusicComposers.add(jsonCrewObject.getString(Constants.TMDB_RESPONSE_CREDITS_NAME));

				}
			}
	
			//add List to hashMap, then cast Object as List
			data.put(Constants.TMDB_RESPONSE_CREDITS_DIRECTOR, directors);
			logger.debug("directors = {}",directors);
			data.put(Constants.TMDB_RESPONSE_CREDITS_PRODUCER, producers);
			logger.debug("producers = {}",producers);
			data.put(Constants.TMDB_RESPONSE_CREDITS_WRITER, writers);
			logger.debug("writers = {}",directors);
			data.put(Constants.TMDB_RESPONSE_CREDITS_COMPOSER, originalMusicComposers);
			logger.debug("originalMusicComposers = {}",originalMusicComposers);

			JSONObject jsonCastObject;
			for (int i=0; i < jsonResponseCastArray.length(); i++){
				jsonCastObject = jsonResponseCastArray.getJSONObject(i);
				
				if (jsonCastObject.getString(Constants.TMDB_RESPONSE_CREDITS_NAME).length()==0){
					cast.add(Constants.UNKNOWN_ACTOR);
				} else {
					cast.add(jsonCastObject.getString(Constants.TMDB_RESPONSE_CREDITS_NAME));
				}
				
				if ("".equals(jsonCastObject.getString(Constants.TMDB_RESPONSE_CREDITS_CHARACTER))){
					castRoles.add(Constants.UNKNOWN_CHARACTER);
				} else {
					castRoles.add(jsonCastObject.getString(Constants.TMDB_RESPONSE_CREDITS_CHARACTER));
				}

			}
			data.put(Constants.TMDB_RESPONSE_CREDITS_CAST, cast);
			data.put(Constants.TMDB_RESPONSE_CREDITS_CAST_ROLES, castRoles);
			returnCode = 0;
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

