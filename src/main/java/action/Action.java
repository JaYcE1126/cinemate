package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;

import dialog.Dialog;
import exception.TmdbApiException;
import utility.Sentences;

public abstract class Action {

	protected Session session;
	protected Dialog alexaResponse;
	private boolean actionComplete;
	private static final Logger logger = LoggerFactory.getLogger(Sentences.class);
	
	public abstract void performAction() throws TmdbApiException;
	public abstract void reattempt(String intentName) throws TmdbApiException;

	public void setActionComplete(boolean actionComplete) {
		this.actionComplete = actionComplete;
	}
	
	public boolean getActionComplete() {
		return this.actionComplete;
	}
	
	public Dialog getDialog(){
		return this.alexaResponse;
	}
	
	public void setDialogIsTell(String initSentence){
		logger.info("Entered: [initSentence: {}, isTell: true]", initSentence);
		alexaResponse.setIsTell(true);
		alexaResponse.setInitSentence(initSentence);
		logger.info("Exited");
	}
	public void setDialogIsTell(String initSentence, String cardTitle, String cardContent){
		logger.info("Entered: [initSentence: {}, cardTitle: {}, cardContent: {}, isTell: true]",
				initSentence, cardTitle, cardContent);
		alexaResponse.setIsTell(true);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setCardContent(cardTitle, cardContent);
		logger.info("Exited");
	}
	public void setDialogIsTell(String initSentence, String cardTitle, String cardContent, String imagePath){
		logger.info("Entered: [initSentence: {}, cardTitle: {}, cardContent: {}, imagePath: {}, isTell: true]", 
				initSentence, cardTitle, cardContent, imagePath);
		alexaResponse.setIsTell(true);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setCardContent(cardTitle, cardContent, imagePath);
		logger.info("Exited");
	}
	
	public void setDialogIsAsk(String initSentence, String reprompt){
		logger.info("Entered: [initSentence: {}, reprompt: {}, isTell: true]", initSentence, reprompt);
		alexaResponse.setIsTell(false);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setRepromptSentence(reprompt);
		logger.info("Exited");
	}
	public void setDialogIsAsk(String initSentence, String reprompt, String cardTitle, String cardContent){
		logger.info("Entered: [initSentence: {}, reprompt: {}, cardTitle: {}, cardContent: {}, isTell: true]", 
				initSentence, reprompt, cardTitle, cardContent);

		alexaResponse.setIsTell(false);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setRepromptSentence(reprompt);
		alexaResponse.setCardContent(cardTitle, cardContent);
		logger.info("Exited");

	}
	public void setDialogIsAsk(String initSentence, String reprompt, String cardTitle, String cardContent, String imagePath){
		logger.info("Entered: [initSentence: {}, reprompt: {}, cardTitle: {}, cardContent: {}, imagePath, isTell: true]", 
				initSentence, reprompt, cardTitle, cardContent, imagePath);
		alexaResponse.setIsTell(false);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setRepromptSentence(reprompt);
		alexaResponse.setCardContent(cardTitle, cardContent, imagePath);
		logger.info("Exited");
	}

}
