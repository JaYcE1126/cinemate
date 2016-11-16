package action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.Session;

import dialog.Dialog;
import exception.CinemateException;
import utility.Sentences;

public abstract class Action {

	protected Dialog alexaResponse;
	private boolean actionComplete;
	private static final Logger logger = LoggerFactory.getLogger(Sentences.class);
	
	public abstract void performAction(Session session) throws CinemateException;
	public abstract void reattempt(String intentName, Session session) throws CinemateException;

	public void setActionComplete(boolean actionComplete) {
		this.actionComplete = actionComplete;
		logger.debug("Set actionComplete: [{}]", getActionComplete());		
	}
	
	public boolean getActionComplete() {
		return this.actionComplete;
	}
	
	public Dialog getDialog(){
		return this.alexaResponse;
	}
	
	public void setDialogIsTell(String initSentence){
		logger.debug("Entered: [initSentence: {}, isTell: true]", initSentence);
		alexaResponse.setIsTell(true);
		alexaResponse.setInitSentence(initSentence);
		logger.trace("Exited");
	}
	public void setDialogIsTell(String initSentence, String cardTitle, String cardContent){
		logger.debug("Entered: [initSentence: {}, cardTitle: {}, cardContent: {}, isTell: true]",
				initSentence, cardTitle, cardContent);
		alexaResponse.setIsTell(true);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setCardContent(cardTitle, cardContent);
		logger.trace("Exited");
	}
	public void setDialogIsTell(String initSentence, String cardTitle, String cardContent, String imagePath){
		logger.debug("Entered: [initSentence: {}, cardTitle: {}, cardContent: {}, imagePath: {}, isTell: true]", 
				initSentence, cardTitle, cardContent, imagePath);
		alexaResponse.setIsTell(true);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setCardContent(cardTitle, cardContent, imagePath);
		logger.trace("Exited");
	}
	
	public void setDialogIsAsk(String initSentence, String reprompt){
		logger.debug("Entered: [initSentence: {}, reprompt: {}, isTell: true]", initSentence, reprompt);
		alexaResponse.setIsTell(false);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setRepromptSentence(reprompt);
		logger.trace("Exited");
	}
	public void setDialogIsAsk(String initSentence, String reprompt, String cardTitle, String cardContent){
		logger.debug("Entered: [initSentence: {}, reprompt: {}, cardTitle: {}, cardContent: {}, isTell: true]", 
				initSentence, reprompt, cardTitle, cardContent);

		alexaResponse.setIsTell(false);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setRepromptSentence(reprompt);
		alexaResponse.setCardContent(cardTitle, cardContent);
		logger.trace("Exited");

	}
	public void setDialogIsAsk(String initSentence, String reprompt, String cardTitle, String cardContent, String imagePath){
		logger.debug("Entered: [initSentence: {}, reprompt: {}, cardTitle: {}, cardContent: {}, imagePath {}, isTell: true]", 
				initSentence, reprompt, cardTitle, cardContent, imagePath);
		alexaResponse.setIsTell(false);
		alexaResponse.setInitSentence(initSentence);
		alexaResponse.setRepromptSentence(reprompt);
		alexaResponse.setCardContent(cardTitle, cardContent, imagePath);
		logger.info("Exited");
	}

}
