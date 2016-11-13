package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;

import dataaccess.Dao;
import dataaccess.tmdb.ActorIdDao;
import dataaccess.tmdb.CommonMoviesDao;
import dialog.Dialog;
import exception.CinemateException;
import utility.CardContent;
import utility.Constants;
import utility.Sentences;
import value.Actor;

public class GetCommonMoviesAction extends Action{
	private static final Logger logger = LoggerFactory.getLogger(GetCommonMoviesAction.class);

	private List<String> userInput = new ArrayList<String>();
	private List<Actor> actorList = new ArrayList<Actor>();
	private List<String> commonMoviesResponse;

	
	public GetCommonMoviesAction(List<String> userInput){
		logger.info("Entered: [userInput: {}]", userInput);
		setActionComplete(false);
		this.userInput = userInput;
		this.alexaResponse = new Dialog();
		logger.info("Exited");
	}
	
	public void performAction(Session session) throws CinemateException{
		logger.info("Entered");

		for (String userInput : this.userInput){
			logger.debug("userInput: [{}]", userInput);
			if (userInput == null || userInput.length()==0){
				setDialogIsAsk(Sentences.speakActor, Sentences.speakActorReprompt);
				setActionComplete(true);
				//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
				//logger.debug("Added actionComplete: [{}] to session", getActionComplete());		

				logger.info("Exited");
				return;
			}
			setActor(userInput);
			if (getActionComplete()) {
				logger.info("Exited");
				return;
			}
		}
		setCommonMoviesList();
		if (commonMoviesResponse != null) actionSuccess();
		logger.debug("alexaResponse: [{}]", getDialog().toString());
		logger.info("Exited");
	}
	
	public void reattempt(String intentName, Session session) throws CinemateException {
		//no reattempt for this action.
	}
	
	private void setActor(String userInput) throws CinemateException{
		logger.info("Entered: [userInput: {}]",userInput);
		
		Dao actorIdDao = new ActorIdDao(userInput);
		int daoReturnCode = actorIdDao.execute();
		HashMap<String, Object> responseData;
		
		if (daoReturnCode == 0) { //a single move match was found
			responseData = actorIdDao.getData();
			int actorId = (int)responseData.get(Constants.TMDB_RESPONSE_ID);
			String actorName = (String)responseData.get(Constants.TMDB_RESPONSE_NAME);

			actorList.add(new Actor(actorId, actorName));
			logger.debug("Created Actor [actorId: {}, actorName: {}]", actorId, actorName);
			
		} else if(daoReturnCode == 1){ //no movie was found
			setDialogIsAsk(Sentences.cannotFindActor(userInput), Sentences.cannotFindActorReprompt(userInput));
			setActionComplete(true);
			//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
			//logger.debug("Added actionComplete: [{}] to session", getActionComplete());		


		} else {
			responseData = actorIdDao.getData();
			String errorMessage = (responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) instanceof String) ? (String) responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) : null;
			setActionComplete(true);
			//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
			//logger.debug("Set actionComplete: [{}]", getActionComplete());		

			throw new CinemateException("TMDB: " + errorMessage);
		}		
		
		logger.info("Exited");
	}
	
	@SuppressWarnings("unchecked")
	private void setCommonMoviesList() throws CinemateException{
		logger.info("Entered: actorList: {}",actorList);
		
		Dao commonMoviesDao = new CommonMoviesDao(actorList);
		int daoReturnCode = commonMoviesDao.execute();
		HashMap<String, Object> responseData;
		
		if (daoReturnCode == 0) { //a single move match was found
			responseData = commonMoviesDao.getData();
			commonMoviesResponse = (List<String>)responseData.get(Constants.DAO_RESPONSE_COMMON_MOVIES);
			logger.debug("Retrieved [{}] common movies: {}", commonMoviesResponse.size(), commonMoviesResponse);
			
		} else if(daoReturnCode == 1){ //no movie was found
			setDialogIsAsk(Sentences.noCommonMovies(actorList), Sentences.noCommonMoviesReprompt);

		} else {
			responseData = commonMoviesDao.getData();
			String errorMessage = (responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) instanceof String) ? (String) responseData.get(Constants.TMDB_RESPONSE_ERROR_MESSAGE) : null;
			throw new CinemateException("TMDB: " + errorMessage);
		}		
		
		setActionComplete(true);
		//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
		//logger.debug("Set actionComplete: [{}]", getActionComplete());		

		logger.info("Exited");
	}
	
	private void actionSuccess(){
		logger.info("Entered");
		
		setActionComplete(true);
		//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
		//logger.debug("Added actionComplete: {} to session", getActionComplete());		

		setDialogIsAsk(Sentences.commonMovies(commonMoviesResponse, actorList), Sentences.commonMoviesReprompt,
				Constants.CARD_TITLE_COMMON_MOVIES, CardContent.commonMovies(commonMoviesResponse, actorList));
	
		logger.info("Exited");

	}

}
