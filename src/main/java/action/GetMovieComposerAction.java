package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;

import exception.TmdbApiException;
import utility.CardContent;
import utility.Constants;
import utility.Sentences;
import value.Movie;

public class GetMovieComposerAction extends GetMovieAction{
	private static final Logger logger = LoggerFactory.getLogger(GetMovieComposerAction.class);

	public GetMovieComposerAction(String userInput, Session session){
		super(userInput, session);
	}
	
	public void performAction() throws TmdbApiException{
		logger.info("Entered");
				
		if (super.userInput==null || super.userInput.length()==0) {
			super.movie = (Movie) session.getAttribute(Constants.SESSION_KEY_MOVIE);
			if (super.movie!=null){
				actionSuccess();
			}else {
				setDialogIsAsk(Sentences.speakMovie, Sentences.speakMovieReprompt);
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

		setDialogIsAsk(Sentences.movieComposer(super.movie), Sentences.movieComposerReprompt, 
				movie.getTitle(), CardContent.movieComposer(super.movie), movie.getPosterLocation());		
		logger.info("Exited");

	}
	
}
