package dialog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.Image;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SsmlOutputSpeech;
import com.amazon.speech.ui.StandardCard;

public class Dialog {
	
	private static final Logger logger = LoggerFactory.getLogger(Dialog.class);

	private SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
	private SsmlOutputSpeech repromptOS = new SsmlOutputSpeech();
	private Reprompt reprompt = new Reprompt();

	private StandardCard card = new StandardCard();
	private boolean isTell;
	
	public void setInitSentence(String outputSpeech){		
		this.outputSpeech.setSsml(outputSpeech);
	}
	
	public SsmlOutputSpeech getInitSentence(){
		return this.outputSpeech;
	}
	
	public void setRepromptSentence(String reprompt){
		this.repromptOS.setSsml(reprompt);
		this.reprompt.setOutputSpeech(this.repromptOS);
	}
	
	public Reprompt getRepromptSentence(){

		return reprompt;
	}
	
	public void setCardContent(String cardTitle, String cardContent){
		this.card.setTitle(cardTitle);
		this.card.setText(cardContent);
	}
	
	public void setCardContent(String cardTitle, String cardContent, String imagePath){
		Image cardImage = new Image();
		cardImage.setLargeImageUrl(imagePath);
		
		this.card.setTitle(cardTitle);
		this.card.setText(cardContent);
		this.card.setImage(cardImage);
	}
	public Card getCard(){
		return this.card;
	}
	
	public void setIsTell(boolean isTell){
		this.isTell = isTell;
	}
	
	public boolean isTell(){
		return this.isTell;
	}

	public SpeechletResponse getSpeechletResponse(){
		if (isTell){
			return SpeechletResponse.newTellResponse(outputSpeech, card);
		}
		return SpeechletResponse.newAskResponse(outputSpeech, reprompt, card);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer("[");
		
		sb.append("outputSpeech: ");
		sb.append(outputSpeech.getSsml());
		sb.append(", reprompt: ");
		sb.append(repromptOS.getSsml());
		sb.append(", cardTitle: ");
		sb.append(card.getTitle());
		sb.append(", cardText: ");
		sb.append(card.getText());
		sb.append("]");
		
		return sb.toString();
	}
}
