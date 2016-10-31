package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;

import exception.TmdbApiException;
import utility.Constants;
import utility.Sentences;
import value.Movie;

public class GetMovieProducerAction extends GetMovieAction{
	private static final Logger logger = LoggerFactory.getLogger(GetMovieProducerAction.class);

	public GetMovieProducerAction(String userInput, Session session){
		super(userInput, session);
	}
	
	public void performAction() throws TmdbApiException{
		logger.info("Entered");
				
		if (super.userInput==null || super.userInput.length()==0) {
			super.movie = (Movie) session.getAttribute(Constants.SESSION_KEY_MOVIE);
			if (super.movie!=null){
				actionSuccess();
			}else {
				alexaResponse.setInitSentence(Sentences.speakMovie);
				alexaResponse.setRepromptSentence(Sentences.speakMovieReprompt);
				alexaResponse.setIsTell(false);
			}
		} else {
			setMovieId();
			if (super.movieId == -1) return;
			setMovieInfo();
			if (super.movie!=null)
				actionSuccess();
		}
		logger.info("Exited");
	}
	
	public void reattempt(String intentName) throws TmdbApiException{
		logger.info("Entered: [intentName: {}]", intentName);

		super.reattempt(intentName);
		setMovieInfo();
		if (super.movie!=null)
			actionSuccess();
		
		logger.info("Exited");
	}
	
	private void actionSuccess(){
		logger.info("Entered");
		
		setActionComplete(true);
		session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
		logger.debug("Added actionComplete: {} to session", getActionComplete());	

		alexaResponse.setInitSentence(Sentences.movieProducer(super.movie));
		alexaResponse.setRepromptSentence(Sentences.movieProducerReprompt);
		alexaResponse.setCardContent("", "", ""); //TODO
		alexaResponse.setIsTell(false);
		
		logger.info("Exited");

	}
	
}
