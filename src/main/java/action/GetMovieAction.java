package action;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;

import dataaccess.tmdb.MovieIdDao;
import dataaccess.tmdb.MovieInfoDao;
import dialog.Dialog;
import exception.CinemateException;
import exception.TmdbApiException;
import utility.Constants;
import utility.Sentences;
import utility.Utilities;
import value.Movie;
import wrapper.MovieIdWrapper;

public class GetMovieAction extends Action{
	private static final Logger logger = LoggerFactory.getLogger(GetMovieAction.class);

	protected String userInput;
	private List<MovieIdWrapper> movieIdList;
	protected int movieId;
	private int movieIdIndex;
	protected Movie movie;

	public GetMovieAction(String userInput, Session session){
		logger.info("Entered: [userInput: {}]", userInput);
		setActionComplete(false);
		this.userInput = Utilities.formatMovieTitle(userInput);
		this.session = session;
		this.movieId = -1;
		this.movieIdIndex = 0;
		this.alexaResponse = new Dialog();
		logger.info("Exited");
	}

	public void performAction() throws TmdbApiException{
		//created because it's required.  This is not used.
	}
	
	public void reattempt(String intentName) throws TmdbApiException{
		logger.info("Entered: [intentName: {}]", intentName);
		
		if (Constants.INTENT_NO.equals(intentName)){
			this.movieIdIndex++;
			
			if (movieIdIndex >= movieIdList.size()){
				alexaResponse.setInitSentence(Sentences.confirmMovieNoneSelected());
				alexaResponse.setIsTell(false);
			} else {
			
				MovieIdWrapper miw = movieIdList.get(movieIdIndex);
				boolean isLastResult = (movieIdIndex==movieIdList.size()-1);
				
				alexaResponse.setInitSentence(Sentences.confirmMovieNextMovie(isLastResult, miw.getMovieTitle(), miw.getMovieReleaseDate()));
				alexaResponse.setRepromptSentence(Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate()));
				alexaResponse.setIsTell(false);
			}
		} else if (Constants.INTENT_YES.equals(intentName)){
			this.movieId = movieIdList.get(movieIdIndex).getMovieId();
			logger.debug("set movieId: [{}]",this.movieId);
			//setMovieInfo();
			
		} else if (Constants.INTENT_TRY_AGAIN.equals(intentName)){
			setActionComplete(true);
			session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
			setDialogIsAsk(Sentences.tryAgain, Sentences.tryAgainReprompt);
		} else if (Constants.INTENT_CUSTOM_STOP.equals(intentName) || Constants.INTENT_CANCEL.equals(intentName)
				|| Constants.INTENT_STOP.equals(intentName)) {
			setActionComplete(true);
			session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
			setDialogIsAsk(Sentences.stopAction, Sentences.stopActionReprompt);
		}else {
			
			MovieIdWrapper miw = movieIdList.get(movieIdIndex);
			String initSentence = Sentences.confirmMovieInvalidIntent + Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate());
			
			alexaResponse.setInitSentence(initSentence);
			alexaResponse.setRepromptSentence(Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate()));
			alexaResponse.setIsTell(false);
		}
		logger.info("Exited");
	}
	
	@SuppressWarnings("unchecked")
	protected void setMovieId() throws TmdbApiException{
		logger.info("Entered");
		int daoReturnCode;
		HashMap<String, Object> responseData;
		MovieIdDao movieIdDao = new MovieIdDao(userInput);
		daoReturnCode = movieIdDao.execute();
		
		if (daoReturnCode == 0) { //a single move match was found
			responseData = movieIdDao.getData();
			movieIdList = (responseData.get(Constants.TMDB_RESPONSE_ID) instanceof List<?>) ? (List<MovieIdWrapper>) responseData.get(Constants.TMDB_RESPONSE_ID) : null;

			MovieIdWrapper movieIdWrapper = movieIdList.get(0);
			movieId = movieIdWrapper.getMovieId();
			
		} else if(daoReturnCode == 1){ //no movie was found
			alexaResponse.setInitSentence(Sentences.cannotFindMovie(userInput));
			alexaResponse.setIsTell(true);
			
		} else if(daoReturnCode == 2){ //more than one movie best matched the userInput
			responseData = movieIdDao.getData();
			movieIdList = (responseData.get(Constants.TMDB_RESPONSE_ID) instanceof List<?>) ? (List<MovieIdWrapper>) responseData.get(Constants.TMDB_RESPONSE_ID) : null;
			MovieIdWrapper miw = movieIdList.get(0);
			
			session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
			session.setAttribute(Constants.SESSION_KEY_ACTION, this);

			alexaResponse.setInitSentence(Sentences.confirmMovieInit(movieIdList.size(), miw.getMovieTitle(), miw.getMovieReleaseDate()));
			alexaResponse.setRepromptSentence(Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate()));
			alexaResponse.setIsTell(false);
		} else {
			responseData = movieIdDao.getData();
			String errorMessage = (responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) instanceof String) ? (String) responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) : null;
			throw new TmdbApiException(errorMessage);
		}
		logger.info("Exited");
	}
	
	protected void setMovieInfo() throws TmdbApiException{
		logger.debug("Entered");
		int daoReturnCode;
		HashMap<String, Object> responseData;
		MovieInfoDao movieInfoDao = new MovieInfoDao(movieId);
		
		daoReturnCode = movieInfoDao.execute();
		
		try{
			if (daoReturnCode == 0) {
				responseData = movieInfoDao.getData();
				movie = new Movie(responseData);
				session.setAttribute(Constants.SESSION_KEY_MOVIE, movie);
				
				//setSuccessDialog();
				
			} else if(daoReturnCode == 1)	{
				alexaResponse.setInitSentence(Sentences.cannotFindMovie(this.userInput));
				alexaResponse.setIsTell(true);
				
				responseData = movieInfoDao.getData();
				String errorMessage = (responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) instanceof String) ? (String) responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) : null;
				throw new TmdbApiException(errorMessage);
			}
			
		}catch (CinemateException ce){
			logger.error("CinemateException ce = {}", ce.getMessage());
			logger.error("StackTrace = ",ce);
			
			alexaResponse.setInitSentence("Init Sentence");
			alexaResponse.setIsTell(true);
		}

		logger.info("Exited");
	
	}
	
	/*private void setSuccessDialog(){
		logger.info("Entered");
		alexaResponse.setInitSentence(Sentences.movieInfo(movie));
		alexaResponse.setRepromptSentence(Sentences.movieInfoReprompt);
		alexaResponse.setCardContent("", "", ""); //TODO
		alexaResponse.setIsTell(false);
		logger.info("Exited");

	}*/
	
}
