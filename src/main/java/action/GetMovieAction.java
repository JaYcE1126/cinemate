package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;

import dataaccess.tmdb.MovieIdDao;
import dataaccess.tmdb.MovieInfoDao;
import dialog.Dialog;
import exception.CinemateException;
import utility.Constants;
import utility.Sentences;
import utility.Utilities;
import value.Movie;
import wrapper.MovieIdWrapper;

public class GetMovieAction extends Action{
	private static final Logger logger = LoggerFactory.getLogger(GetMovieAction.class);

	protected String userInput;
	protected List<MovieIdWrapper> movieIdList = new ArrayList<MovieIdWrapper>();
	protected int movieId;
	protected int movieIdIndex;
	protected Movie movie;
	protected boolean reformatWithDigit = false;
	protected Session session;

	public GetMovieAction(String userInput){
		logger.info("Entered: [userInput: {}]", userInput);
		setActionComplete(false);
		this.userInput = userInput;
		this.movieId = -1;
		this.movieIdIndex = 0;
		this.alexaResponse = new Dialog();
		if (userInput != null) this.reformatWithDigit = Utilities.doesContainNumber(userInput);
		logger.info("Exited");
	}

	public void performAction(Session session) throws CinemateException{
		//created because it's required.  This is not used.
	}
	
	public void reattempt(String intentName, Session session) throws CinemateException{
		logger.info("Entered: [intentName: {}, session: {}]", intentName, session);
		this.session = session;

		if (Constants.INTENT_NO.equals(intentName)){
			this.movieIdIndex++;
			
			if (movieIdIndex >= movieIdList.size()){
				setDialogIsAsk(Sentences.confirmMovieNoneSelected,Sentences.confirmMovieNoneSelectedReprompt);
				
			} else {
			
				MovieIdWrapper miw = movieIdList.get(movieIdIndex);
				boolean isLastResult = (movieIdIndex==movieIdList.size()-1);
				
				setDialogIsAsk(Sentences.confirmMovieNextMovie(isLastResult, miw.getMovieTitle(), miw.getMovieReleaseDate()),
					Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate()));
			}
		} else if (Constants.INTENT_YES.equals(intentName)){
			this.movieId = movieIdList.get(movieIdIndex).getMovieId();
			logger.debug("set movieId: [{}]",this.movieId);
			//setMovieInfo();
			
		} else if (Constants.INTENT_TRY_AGAIN.equals(intentName)){
			setActionComplete(true);
			//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
			//logger.debug("Added actionComplete: [{}] to session", getActionComplete());		

			setDialogIsAsk(Sentences.tryAgain, Sentences.tryAgainReprompt);
		} else if (Constants.INTENT_CUSTOM_STOP.equals(intentName) || Constants.INTENT_CANCEL.equals(intentName)
				|| Constants.INTENT_STOP.equals(intentName)) {
			setActionComplete(true);
			//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
			//logger.debug("Added actionComplete: [{}] to session", getActionComplete());		

			setDialogIsAsk(Sentences.stopAction, Sentences.stopActionReprompt);
		}else {
			
			logger.debug("movieIdList: [{}]", movieIdList);
			logger.debug("movieIdIndex: [{}]", movieIdIndex);
			MovieIdWrapper miw = movieIdList.get(movieIdIndex);
			String initSentence = Sentences.confirmMovieInvalidIntent + Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate());
			setDialogIsAsk(initSentence, Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate()));

		}
		logger.info("Exited");
	}
	
	@SuppressWarnings("unchecked")
	protected void setMovieId() throws CinemateException{
		logger.info("Entered");
		int daoReturnCode;
		HashMap<String, Object> responseData;
		MovieIdDao movieIdDao = new MovieIdDao(userInput);
		daoReturnCode = movieIdDao.execute();
		
/*		if (daoReturnCode == 0) { //a single move match was found
			responseData = movieIdDao.getData();
			//movieIdList = (responseData.get(Constants.TMDB_RESPONSE_ID) instanceof List<?>) ? (List<MovieIdWrapper>) responseData.get(Constants.TMDB_RESPONSE_ID) : null;
			movieIdList.addAll((List<MovieIdWrapper>) responseData.get(Constants.TMDB_RESPONSE_ID));

			//MovieIdWrapper miw = movieIdList.get(0);
			
			//movieId = miw.getMovieId();
			
//		} else if(daoReturnCode == 1){ //no movie was found
//			setDialogIsTell(Sentences.cannotFindMovie(this.userInput));
//			logger.info("Exited");
//			return;
			
		} else if(daoReturnCode == 2){ //more than one movie best matched the userInput
			responseData = movieIdDao.getData();
			//movieIdList = (responseData.get(Constants.TMDB_RESPONSE_ID) instanceof List<?>) ? (List<MovieIdWrapper>) responseData.get(Constants.TMDB_RESPONSE_ID) : null;
			movieIdList.addAll((List<MovieIdWrapper>) responseData.get(Constants.TMDB_RESPONSE_ID));

			//MovieIdWrapper miw = movieIdList.get(0);
			
			//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
			//session.setAttribute(Constants.SESSION_KEY_ACTION, this);
			
			//setDialogIsAsk(Sentences.confirmMovieInit(movieIdList.size(), miw.getMovieTitle(), miw.getMovieReleaseDate()),
			//		Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate()));
			
		}
*/ 	
		if (daoReturnCode == -1){
			responseData = movieIdDao.getData();
			String errorMessage = (responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) instanceof String) ? (String) responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) : null;
			throw new CinemateException("TMDB: " + errorMessage);
		} else if (daoReturnCode == 0 || daoReturnCode == 2) {
			responseData = movieIdDao.getData();
			movieIdList.addAll((List<MovieIdWrapper>) responseData.get(Constants.TMDB_RESPONSE_ID));
		}
		
		if (reformatWithDigit) {
			reformatWithDigit = false;
			this.userInput = Utilities.reformatTitle(this.userInput);
			this.setMovieId();
		} else{
			
			if (movieIdList.size() > 1) {
				MovieIdWrapper miw = movieIdList.get(0);
				//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
				//logger.debug("Added actionComplete: [{}] to session", getActionComplete());		

				//session.setAttribute(Constants.SESSION_KEY_ACTION, this);
				
				setDialogIsAsk(Sentences.confirmMovieInit(movieIdList.size(), miw.getMovieTitle(), miw.getMovieReleaseDate()),
						Sentences.confirmMovieReprompt(miw.getMovieTitle(), miw.getMovieReleaseDate()));
			} else if (movieIdList.size() == 1) {
				MovieIdWrapper miw = movieIdList.get(0);
				movieId = miw.getMovieId();
			} else {
				setActionComplete(true);
				setDialogIsAsk(Sentences.cannotFindMovie(this.userInput), Sentences.cannotFindMovieReprompt);
			}
		}
		
		logger.info("Exited");
	}
	
	protected void setMovieInfo() throws CinemateException{
		logger.debug("Entered");
		int daoReturnCode;
		HashMap<String, Object> responseData;
		MovieInfoDao movieInfoDao = new MovieInfoDao(movieId);
		
		daoReturnCode = movieInfoDao.execute();
		
		if (daoReturnCode == 0) {
			responseData = movieInfoDao.getData();
			movie = new Movie(responseData);
			
			this.session.setAttribute(Constants.SESSION_KEY_MOVIE, movie);
			logger.debug("Added movie [{}] to session.", movie.getTitle());
			
			logger.debug("Object Type: [{}]", session.getAttribute(Constants.SESSION_KEY_MOVIE).getClass().getName());
			logger.debug("Instance of Movie: [{}]", session.getAttribute(Constants.SESSION_KEY_MOVIE) instanceof Movie);

			//setSuccessDialog();
			
		} else if(daoReturnCode == 1)	{
			setDialogIsTell(Sentences.cannotFindMovie(this.userInput));

			responseData = movieInfoDao.getData();
			String errorMessage = (responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) instanceof String) ? (String) responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) : null;
			throw new CinemateException("TMDB: " + errorMessage);
		}
			
		logger.info("Exited");
	
	}
	
	/*private void setSuccessDialog(){
		logger.info("Entered");
		alexaResponse.setInitSentence(Sentences.movieInfo(movie));
		alexaResponse.setRepromptSentence(Sentences.movieInfoReprompt);
		alexaResponse.setCardContent("", "", ""); 
		alexaResponse.setIsTell(false);
		logger.info("Exited");

	}*/
	
}
