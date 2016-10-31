
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

import action.*;
import dialog.Dialog;
import utility.Constants;
import utility.Sentences;
import utility.Utilities;
import wrapper.MovieIdWrapper;


/**
 * This sample shows how to create a simple speechlet for handling speechlet requests.
 */
public class CinemateSpeechlet implements Speechlet {
	private static final Logger logger = LoggerFactory.getLogger(CinemateSpeechlet.class);
	Action action;
	Dialog dialog;
	
	public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
		logger.info("--START SESSION--: requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, true);
		dialog = new Dialog();

	}

	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		logger.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),session.getSessionId());
		
		dialog.setInitSentence(Sentences.welcome);
		dialog.setRepromptSentence(Sentences.welcomeReprompt);
		dialog.setIsTell(false);
		
		logger.info("Exited: [dialog: {}]",dialog.toString());
		return dialog.getSpeechletResponse();
	}

	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {

//TODO getMovieReleaseDate
		Intent intent = request.getIntent();
		String intentName = intent.getName();
		logger.info("intentName: {}", intentName);
		HashMap<String, String> userInput = Utilities.retriveSlotValues(intent.getSlots());
		logger.info("userInput: {}", userInput);
		
		if (Constants.INTENT_REPEAT.equals(intentName)) {
			dialog = (Dialog) session.getAttribute(Constants.SESSION_KEY_DIALOG);
			logger.info("Exited: [dialog: {}]",dialog.toString());
			return dialog.getSpeechletResponse();
		}

		Boolean actionComplete = (Boolean) session.getAttribute(Constants.SESSION_KEY_ACTION_COMPLETE);
		logger.debug("actionComplete: {}",actionComplete);
		
		try{
			if (!actionComplete) {
				action = (Action)session.getAttribute(Constants.SESSION_KEY_ACTION);
				action.reattempt(intentName);
				dialog = action.getDialog();
			} else {
				
				switch (intentName){
					case Constants.INTENT_GET_MOVIE_INFO:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_INFO);
						action = new GetMovieInfoAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE), session);
						action.performAction();
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_DIRECTOR:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_DIRECTOR);
						action = new GetMovieDirectorAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE), session);
						action.performAction();
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_PRODUCER:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_PRODUCER);
						action = new GetMovieProducerAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE), session);
						action.performAction();
						dialog = action.getDialog();	
						break;
						
					case Constants.INTENT_GET_MOVIE_WRITER:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_WRITER);
						action = new GetMovieWriterAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE), session);
						action.performAction();
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_COMPOSER:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_COMPOSER);
						action = new GetMovieComposerAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE), session);
						action.performAction();
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_STREAMING_SOURCES:
						logger.debug("entered case: {}", Constants.INTENT_GET_STREAMING_SOURCES);
						action = new GetMovieSourceAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE), session);
						action.performAction();
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_CAST:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_CAST);
						action = new GetMovieCastAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE), session);
						action.performAction();
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_COMMON_MOVIES:
						logger.debug("entered case: {}", Constants.INTENT_GET_COMMON_MOVIES);
						List<String> userInputActors = new ArrayList<String>();
						userInputActors.add(userInput.get(Constants.SLOT_NAME_ACTOR_1));
						userInputActors.add(userInput.get(Constants.SLOT_NAME_ACTOR_2));
						action = new GetCommonMoviesAction(userInputActors, session);
						action.performAction();
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_CANCEL:
						logger.debug("entered case: {}", Constants.INTENT_CANCEL);
						dialog.setInitSentence(Sentences.goodbye);
						dialog.setIsTell(true);
						break;
						
					case Constants.INTENT_CUSTOM_STOP:
						logger.debug("entered case: {}", Constants.INTENT_CANCEL);
						dialog.setInitSentence(Sentences.goodbye);
						dialog.setIsTell(true);
						break;
						
					case Constants.INTENT_STOP:
						logger.debug("entered case: {}", Constants.INTENT_CANCEL);
						dialog.setInitSentence(Sentences.goodbye);
						dialog.setIsTell(true);
						break;	
						
					case Constants.INTENT_KEEP_GOING://This case needs to always be before the Default
						logger.debug("entered case: {}", Constants.INTENT_KEEP_GOING);
						Action previousAction = (Action)session.getAttribute(Constants.SESSION_KEY_ACTION);
						if (previousAction != null){
							String previousActionName = previousAction.getClass().getSimpleName();
							if (Constants.ACTION_GET_MOVIE_CAST.equals(previousActionName)){
								previousAction.reattempt(intentName);
								break;
							} 
						}
						
					default:
						logger.debug("entered case: Default");
						dialog.setInitSentence(Sentences.invalidIntent);
						dialog.setRepromptSentence(Sentences.invalidIntentReprompt);
						dialog.setCardContent("", "");
						dialog.setIsTell(false);
						break;
				}
			}
		}catch(Exception e){
			logger.error("Exception e = {}",e.getMessage());
			logger.error("StackTrace = ",e);
			dialog = new Dialog();
			dialog.setInitSentence(Sentences.unexpectedError);
			dialog.setIsTell(true);
		}
		
		session.setAttribute(Constants.SESSION_KEY_DIALOG, dialog);
		logger.info("Exited: [dialog: {}]",dialog.toString());
		return dialog.getSpeechletResponse();
		
	}

	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
		logger.info("--END SESSION--: onSessionEnded requestId={}, sessionId={}", request.getRequestId(),session.getSessionId());
		// any cleanup logic goes here   
	}

	
	
}
