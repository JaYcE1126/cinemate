
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.User;
import com.amazon.speech.speechlet.verifier.CardSpeechletResponseVerifier;
import com.amazon.speech.json.SpeechletResponseEnvelope;

import utility.Constants;


public class CinemateTest {

	@Test
	public void test() throws SpeechletException, IOException  {
/*
		Map<String, Slot> slots = new HashMap<String, Slot>();
	
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String intent = "";
		String movieTitle = "";
		String firstActor = "";
		String secondActor = "";
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

		while(!intent.equals("exit")){
			System.out.print("Enter a intent: ");
			intent = reader.nextLine(); 
			if (Constants.INTENT_GET_COMMON_MOVIES.equals(intent)){
				System.out.print("Enter a Actor 1: ");
				firstActor = reader.nextLine();
				System.out.print("Enter a Actor 2: ");
				secondActor = reader.nextLine();

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
				
			} else {
				System.out.print("Enter a movie: ");
				movieTitle = reader.nextLine();
				
				com.amazon.speech.slu.Slot.Builder bSlot1 = Slot.builder();
				bSlot1.withName("movieTitle");
				bSlot1.withValue(movieTitle);
				Slot slot1 = bSlot1.build();
				
				slots.put("movieTitle",  slot1);
			}

			
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
			
			//movieTitle = null;
			SpeechletResponse sr = cs.onIntent(ir, session);
			
			//System.out.println(session.getAttributes());
			
			
			if (sr.getShouldEndSession()) {
				System.out.println("---SESSION ENDED---");
				break;
			}
			
			//System.out.print("Enter a intent: ");
			//intent = reader.nextLine(); 
			//System.out.print("Enter a movie: ");
			//movieTitle = reader.nextLine();
			
		}
		reader.close();
*/
	}
	
	
}

