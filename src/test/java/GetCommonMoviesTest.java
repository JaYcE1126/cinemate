import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.User;
import com.amazon.speech.ui.SsmlOutputSpeech;

import utility.Constants;
import utility.Utilities;

public class GetCommonMoviesTest {

	//@Test
	public void test() throws SpeechletException  {

		Map<String, Slot> slots = new HashMap<String, Slot>();

		String intent = "GetCommonMovies";
		String firstActor = "Walter Matthau";
		String secondActor = "Jack Lemmon";
		CinemateSpeechlet cs = new CinemateSpeechlet();

		//Application application = new Application("amzn1.echo-sdk-ams.app.46c90ce9-4635-4dc9-94a5-b002f36c7253");
		com.amazon.speech.speechlet.User.Builder bu = User.builder();
		bu.withUserId("EclipseTest");
		User user = bu.build();
		
		DateFormat dateF = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dateO = new Date();
		String sessionId = dateF.format(dateO).toString();
		System.out.print(sessionId);
		
		com.amazon.speech.speechlet.Session.Builder bs = Session.builder();
		//bs.withApplication(application);
		bs.withUser(user);
		bs.withSessionId(sessionId);
		bs.withIsNew(true);
		Session session = bs.build();
		
		com.amazon.speech.speechlet.SessionStartedRequest.Builder bssr = SessionStartedRequest.builder();
		bssr.withRequestId("requestId");
		SessionStartedRequest ssr = bssr.build();
		cs.onSessionStarted(ssr, session);
		
		System.out.println("---SESSION STARTED---");
		
		com.amazon.speech.slu.Slot.Builder bSlot1 = Slot.builder();
		bSlot1.withName("firstActor");
		bSlot1.withValue(firstActor);
		Slot slot1 = bSlot1.build();

		com.amazon.speech.slu.Slot.Builder bSlot2 = Slot.builder();
		bSlot2.withName("secondActor");
		bSlot2.withValue(secondActor);
		Slot slot2 = bSlot2.build();
		
		slots.put("firstActor",  slot1);
		slots.put("secondActor",  slot2);
			
		
		com.amazon.speech.slu.Intent.Builder b = Intent.builder();
		b.withName(intent);
		b.withSlots(slots);
		Intent i = b.build();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateobj = new Date();
		System.out.println(df.format(dateobj));
		
		com.amazon.speech.speechlet.IntentRequest.Builder bi = IntentRequest.builder();
		bi.withIntent(i);
		bi.withTimestamp(dateobj);
		bi.withRequestId("EdwRequestId.a733ad08-0f05-4336-a39d-52e062fba0e7");
		
		IntentRequest ir = bi.build();
		
		SpeechletResponse sr = cs.onIntent(ir, session);
		
		String outputSpeech = "<speak><s>I've found the following 12 movies in common between actors Walter Matthau and Jack Lemmon: "
				+ "JFK, Grumpy Old Men, Grumpier Old Men, The Odd Couple, The Front Page, Buddy Buddy, The Fortune Cookie, Out to Sea, "
				+ "The Odd Couple II, The Grass Harp, The Gentleman Tramp, and Portrait of a '60% Perfect Man': Billy Wilder</s><s>What "
				+ "else would you like me to tell you?</s></speak>"; 
			
		System.out.println(outputSpeech);
		System.out.println(cs.getDialog().getInitSentence().getSsml());
		System.out.println(cs.getDialog().getInitSentence().getSsml().equals(outputSpeech));
		
		assertTrue("GetCommonMovies Init Sentence Mismatch",cs.getDialog().getInitSentence().getSsml().equals(outputSpeech));
		
	}
}

