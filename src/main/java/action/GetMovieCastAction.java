package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

import exception.CinemateException;
import utility.CardContent;
import utility.Constants;
import utility.Sentences;
import value.Movie;

public class GetMovieCastAction extends GetMovieAction{
	private static final Logger logger = LoggerFactory.getLogger(GetMovieCastAction.class);
	private int index = 0;

	public GetMovieCastAction(String userInput){
		super(userInput);
	}
	
	public void performAction(Session session) throws CinemateException{
		logger.info("Entered");
		this.session = session;

		if (super.userInput==null || super.userInput.length()==0) {			
			ObjectMapper mapper = new ObjectMapper();
			super.movie = mapper.convertValue(session.getAttribute(Constants.SESSION_KEY_MOVIE), Movie.class);
			logger.debug("Retrieved movie from session as [{}]", movie);
			if (super.movie!=null){
				actionSuccess();
			}else {
				setActionComplete(true);
				setDialogIsAsk(Sentences.speakMovie, Sentences.speakMovieReprompt);
			}
		} else {
			setMovieId();
			if (super.movieId == -1) {
				logger.info("Exited");
				return;
			}
			setMovieInfo();
			if (super.movie!=null)
				actionSuccess();
				//session.setAttribute(Constants.SESSION_KEY_ACTION, this);
		}
		logger.info("Exited");
	}
	
	public void reattempt(String intentName, Session session) throws CinemateException{
		logger.info("Entered: [intentName: {}]", intentName);

		if (Constants.INTENT_KEEP_GOING.equals(intentName)){
			index = index + 5;
			logger.debug("index: [{}]", index);
			logger.debug("movie.getCast().size: [{}]", movie.getCast().size());
			if (index >= movie.getCast().size()) index = 0;
			actionSuccess();

		} else {
			super.reattempt(intentName, session);
			if (super.movieId != -1) setMovieInfo();
			if (super.movie!=null) actionSuccess();			
		}

		logger.info("Exited");
	}
	
	private void actionSuccess(){
		logger.info("Entered");
		
		setActionComplete(true);
		//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, getActionComplete());
		//logger.debug("Added actionComplete: [{}] to session", getActionComplete());		
		
		//session.setAttribute(Constants.SESSION_KEY_ACTION, this);
		//logger.debug("Added action: {} to session", GetMovieCastAction.class);

		setDialogIsAsk(Sentences.movieCast(super.movie, index), Sentences.movieCastReprompt, 
				movie.getTitle(), CardContent.movieCast(super.movie, index), movie.getPosterLocation());
		
		logger.info("Exited");

	}
	
}
