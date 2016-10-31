import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

import utility.Constants;

public class GetCommonMoviesTest {

	@Test
	public void test() throws SpeechletException  {
	/*
		Map<String, Slot> slots = new HashMap<String, Slot>();
	
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String intent = "";
		String actor1 = "";
		String actor2 = "";

		
		com.amazon.speech.speechlet.Session.Builder bs = Session.builder();
		bs.withIsNew(true);
		bs.withSessionId("0987654321");
		Session session = bs.build();
		session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, true);

		
		CinemateSpeechlet cs = new CinemateSpeechlet();
		
		System.out.print("Enter a intent: ");
		intent = reader.nextLine(); 
		System.out.print("Enter first actor: ");
		actor1 = reader.nextLine();
		System.out.print("Enter second actor: ");
		actor2 = reader.nextLine();
		
		while(!intent.equals("exit")){

			com.amazon.speech.slu.Slot.Builder bSlot1 = Slot.builder();
			bSlot1.withName("actor1");
			bSlot1.withValue(actor1);
			Slot slot1 = bSlot1.build();
			
			slots.put("actor1",  slot1);
			
			com.amazon.speech.slu.Slot.Builder bSlot2 = Slot.builder();
			bSlot2.withName("actor2");
			bSlot2.withValue(actor2);
			Slot slot2 = bSlot2.build();
			
			slots.put("actor2",  slot2);
			
			com.amazon.speech.slu.Intent.Builder b = Intent.builder();
			b.withName(intent);
			b.withSlots(slots);
			Intent i = b.build();
			
			com.amazon.speech.speechlet.IntentRequest.Builder bi = IntentRequest.builder();
			bi.withIntent(i);
			bi.withRequestId("1234");
			
			IntentRequest ir = bi.build();
			
			actor1 = null;
			actor2 = null;
			SpeechletResponse sr = cs.onIntent(ir, session);
			
			if (sr.getShouldEndSession()) break;
			
			System.out.print("Enter a intent: ");
			intent = reader.nextLine(); 
			System.out.print("Enter first actor: ");
			actor1 = reader.nextLine();
			System.out.print("Enter second actor: ");
			actor2 = reader.nextLine();
			
		}
		
	
		
	*/
	}
}

