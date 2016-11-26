package action;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

import dataaccess.guidebox.GuideboxIdDao;
import dataaccess.guidebox.GuideboxStreamingInfoDao;
import exception.CinemateException;
import utility.CardContent;
import utility.Constants;
import utility.Sentences;
import value.Movie;

public class GetMovieSourceAction extends GetMovieAction {
	private static final Logger logger = LoggerFactory.getLogger(GetMovieAction.class);

	private int gbMovieId;
	
	public GetMovieSourceAction(String userInput){
		super(userInput);
		this.gbMovieId = -1;
	}
	
	public void performAction(Session session) throws CinemateException{
		logger.info("Entered");
		this.session = session;

		if (super.userInput==null || super.userInput.length()==0) {
			ObjectMapper mapper = new ObjectMapper();
			super.movie = mapper.convertValue(session.getAttribute(Constants.SESSION_KEY_MOVIE), Movie.class);
			logger.debug("Retrieved movie from session as [{}]", movie);
			setActionComplete(true);
			if (super.movie!=null) {
				this.movieId = Integer.parseInt(movie.getId());
				setGbMovieId();
				
			} else {
				setDialogIsAsk(Sentences.speakMovie, Sentences.speakMovieReprompt);
			}

		} else {
			setMovieId();
			if (movieId != -1) {
				setMovieInfo();
				setGbMovieId();
			}
		}
		
		if (gbMovieId != -1) setGbMovieInfo();
		
		if (alexaResponse.getInitSentence().getSsml().length()<1)
			actionSuccess();
		logger.info("Exited");
	}
	
	public void reattempt(String intentName, Session session) throws CinemateException{
		logger.info("Entered: [intentName: {}]", intentName);
		super.reattempt(intentName, session);
		
		if (movieId != -1) {
			setMovieInfo();
			setGbMovieId();
		}
		if (gbMovieId != -1) {
			setGbMovieInfo();		
			if (super.movie.getWebSources().size()>0)
				actionSuccess();
		}
		
		logger.info("Exited");
	}
	
	protected void setGbMovieId() throws CinemateException{
		logger.info("Entered");
		
		logger.debug("this.movieId: {}", this.movieId);

		if (super.movieId != -1) {		
			int daoReturnCode;
			HashMap<String, Object> responseData;
			GuideboxIdDao guideboxIdDao = new GuideboxIdDao(String.valueOf(this.movieId));
			daoReturnCode = guideboxIdDao.execute();
			
			if (daoReturnCode == 0) { //a single movie match was found
				responseData = guideboxIdDao.getData();
				gbMovieId = (int) responseData.get(Constants.GB_RESPONSE_MOVIE_ID);
				logger.debug("gbMovieId: {}",gbMovieId);
			} else if(daoReturnCode == 1){ //no movie was found
				setActionComplete(true);
				setDialogIsAsk(Sentences.noMovieSources(movie.getTitle()), Sentences.noMovieSourcesReprompt);
				
			} else {
				responseData = guideboxIdDao.getData();
				String errorMessage = (responseData.get(Constants.GB_RESPONSE_ERROR) instanceof String) ? (String) responseData.get(Constants.GB_RESPONSE_ERROR) : null;
				throw new CinemateException("GB: " + errorMessage);
			}
		} 
		logger.info("Exited: [movieId: {}]", this.movieId);
	}
	
	@SuppressWarnings("unchecked")
	protected void setGbMovieInfo() throws CinemateException{
		logger.debug("Entered");

		//logger.debug("gbMovieId: [{}]", gbMovieId);
		//if (this.gbMovieId == -1) {
		//	logger.debug("Exited: gbMovieId Not Set");
		//	return;
		//} 
		//super.setMovieInfo();
		
		int daoReturnCode;
		HashMap<String, Object> responseData;
		GuideboxStreamingInfoDao movieStreamingSource = new GuideboxStreamingInfoDao(String.valueOf(gbMovieId));
		
		daoReturnCode = movieStreamingSource.execute();
		

		if (daoReturnCode == 0) {
			responseData = movieStreamingSource.getData();
			List<String> webSubscriptionSources = (responseData.get(Constants.GB_RESPONSE_WEB_SUBSCRIPTION) instanceof List<?>) ? (List<String>) responseData.get(Constants.GB_RESPONSE_WEB_SUBSCRIPTION) : null;
			movie.setWebSources(webSubscriptionSources);
			actionSuccess();
			
		} else if(daoReturnCode == 1)	{
			setActionComplete(true);
			setDialogIsAsk(Sentences.noMovieSources(movie.getTitle()), Sentences.noMovieSourcesReprompt);
			
		} else {
			responseData = movieStreamingSource.getData();
			String errorMessage = (responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) instanceof String) ? (String) responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) : null;
			throw new CinemateException("GB: " + errorMessage);
		}
		
		//setActionComplete(true);
		//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
		//logger.debug("Added actionComplete: {} to session", getActionComplete());
		logger.info("Exited");

	}
	
	private void actionSuccess(){
		logger.info("Entered");
		
		setActionComplete(true);
		//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
		//logger.debug("Added actionComplete: [{}] to session", getActionComplete());		
		//setDialogIsAsk(Sentences.movieSources(super.movie), Sentences.movieSourcesReprompt, "", "", "");
			setDialogIsAsk(Sentences.movieSources(super.movie), Sentences.movieSourcesReprompt, movie.getTitle(), CardContent.streamingSources(movie), movie.getPosterLocation());

		
		logger.info("Exited");

	}
	
	
}
