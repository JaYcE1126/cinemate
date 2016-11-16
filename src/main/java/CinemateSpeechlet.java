
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
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
import utility.CardContent;
import utility.Constants;
import utility.Sentences;
import utility.Utilities;

import co.voicelabs.sdk.alexa.VoiceInsights;

//http://stackoverflow.com/questions/4062022/how-to-convert-words-to-a-number

public class CinemateSpeechlet implements Speechlet {
	private static final Logger logger = LoggerFactory.getLogger(CinemateSpeechlet.class);

	private VoiceInsights voiceInsights = null;
	private String viAppToken =  "3f19179c-24ad-38bd-8b5e-70ca25e422a1";
	
	private Action action;
	private Dialog dialog;
	private boolean actionComplete;
	
	public void onSessionStarted(final SessionStartedRequest request, final Session session) throws SpeechletException {
		logger.info("--START SESSION--: requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
		//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, true);
		dialog = new Dialog();
		actionComplete = true;
				
    /***** VoiceInsights Initialize *****/
		voiceInsights = new VoiceInsights(viAppToken, session);

	}

	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session) throws SpeechletException {
		logger.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),session.getSessionId());
		
		dialog.setInitSentence(Sentences.welcome);
		dialog.setRepromptSentence(Sentences.welcomeReprompt);
		dialog.setCardContent(Constants.CARD_TITLE_WELCOME, CardContent.welcome);
		dialog.setIsTell(false);
		
		logger.info("Exited: [dialog: {}]",dialog.toString());
		return dialog.getSpeechletResponse();
	}

	public SpeechletResponse onIntent(final IntentRequest request, final Session session) throws SpeechletException {
		final long startTime = System.currentTimeMillis();
		Intent intent = request.getIntent();
		String intentName = intent.getName();
		logger.info("intentName: {}", intentName);
		HashMap<String, String> userInput = Utilities.retriveSlotValues(intent.getSlots());
		logger.info("userInput: {}", userInput);


		if (Constants.INTENT_REPEAT.equals(intentName)) {
			//dialog = (Dialog) session.getAttribute(Constants.SESSION_KEY_DIALOG);
			logger.info("Exited: [dialog: {}]",dialog.toString());
			return dialog.getSpeechletResponse();
		}

		//Boolean actionComplete = (Boolean) session.getAttribute(Constants.SESSION_KEY_ACTION_COMPLETE);
		actionComplete = (action != null) ? action.getActionComplete() : true;
		logger.debug("actionComplete: {}",actionComplete);
		
		try{
			if (!actionComplete) {
				//action = (Action)session.getAttribute(Constants.SESSION_KEY_ACTION);
				action.reattempt(intentName, session);
				dialog = action.getDialog();
			} else {
				
				switch (intentName){
					case Constants.INTENT_GET_MOVIE_INFO:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_INFO);
						action = new GetMovieInfoAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_PLOT:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_PLOT);
						action = new GetMoviePlotAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_DIRECTOR:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_DIRECTOR);
						action = new GetMovieDirectorAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_PRODUCER:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_PRODUCER);
						action = new GetMovieProducerAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();	
						break;
						
					case Constants.INTENT_GET_MOVIE_WRITER:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_WRITER);
						action = new GetMovieWriterAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_COMPOSER:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_COMPOSER);
						action = new GetMovieComposerAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_RUNTIME:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_RUNTIME);
						action = new GetMovieRuntimeAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_RELEASE_DATE:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_RELEASE_DATE);
						action = new GetMovieReleaseDateAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_STREAMING_SOURCES:
						logger.debug("entered case: {}", Constants.INTENT_GET_STREAMING_SOURCES);
						action = new GetMovieSourceAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_MOVIE_CAST:
						logger.debug("entered case: {}", Constants.INTENT_GET_MOVIE_CAST);
						action = new GetMovieCastAction(userInput.get(Constants.SLOT_NAME_MOVIE_TITLE));
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_GET_COMMON_MOVIES:
						logger.debug("entered case: {}", Constants.INTENT_GET_COMMON_MOVIES);
						List<String> userInputActors = new ArrayList<String>();
						userInputActors.add(userInput.get(Constants.SLOT_NAME_ACTOR_1));
						userInputActors.add(userInput.get(Constants.SLOT_NAME_ACTOR_2));
						action = new GetCommonMoviesAction(userInputActors);
						action.performAction(session);
						dialog = action.getDialog();
						break;
						
					case Constants.INTENT_HELP:
						logger.debug("entered case: {}", Constants.INTENT_HELP);
						dialog.setInitSentence(Sentences.help);
						dialog.setRepromptSentence(Sentences.helpReprompt);
						dialog.setCardContent(Constants.CARD_TITLE_HELP, CardContent.help);
						dialog.setIsTell(false);
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
						if (action != null){
							String previousActionName = action.getClass().getSimpleName();
							if (Constants.ACTION_GET_MOVIE_CAST.equals(previousActionName)){
								action.reattempt(intentName, session);
								break;
							} 
						}
						
					default:
						logger.debug("entered case: Default");
						dialog.setInitSentence(Sentences.invalidIntent);
						dialog.setRepromptSentence(Sentences.invalidIntentReprompt);
						//dialog.setCardContent("", "");
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
		
		//session.setAttribute(Constants.SESSION_KEY_DIALOG, dialog);
		
    /**** VoiceInsights tracking ****/
    voiceInsights.track(intent.getName(), intent.getSlots(), dialog.getSpeechletResponse().getOutputSpeech());
		
    logger.info("Set dialog: [{}]",dialog.toString());
		logger.info("Exited: [Session: {}]",session.getAttributes());
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime)/1000 + " seconds" );
		return dialog.getSpeechletResponse();
		
	}

	public void onSessionEnded(final SessionEndedRequest request, final Session session) throws SpeechletException {
		logger.info("--END SESSION--: onSessionEnded requestId={}, sessionId={}", request.getRequestId(),session.getSessionId());
		
		// any cleanup logic goes here   
	}
	
	public Dialog getDialog(){
		return dialog;
	}

	
	
}
