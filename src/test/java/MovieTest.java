
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


public class MovieTest {

	//@Test
	public void test() throws SpeechletException  {
		//fail("Not yet implemented");
		//testMovie();
		//testMovie2();
		
		
		/*Map<String, Slot> slots = new HashMap<String, Slot>();
	
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String intent = "";
		String movieTitle = "";
		CinemateSpeechlet cs = new CinemateSpeechlet();

		
		com.amazon.speech.speechlet.Session.Builder bs = Session.builder();
		bs.withIsNew(true);
		bs.withSessionId("0987654321");
		Session session = bs.build();
		
		com.amazon.speech.speechlet.SessionStartedRequest.Builder bssr = SessionStartedRequest.builder();
		bssr.withRequestId("requestId");
		SessionStartedRequest ssr = bssr.build();
		cs.onSessionStarted(ssr, session);
		
		//session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, true);

		System.out.print("Enter a intent: ");
		intent = reader.nextLine(); 
		System.out.print("Enter a movie: ");
		movieTitle = reader.nextLine();
		
		while(!intent.equals("exit")){

			com.amazon.speech.slu.Slot.Builder bSlot1 = Slot.builder();
			bSlot1.withName("movieTitle");
			bSlot1.withValue(movieTitle);
			Slot slot1 = bSlot1.build();
			
			slots.put("movieTitle",  slot1);
			
			com.amazon.speech.slu.Intent.Builder b = Intent.builder();
			b.withName(intent);
			b.withSlots(slots);
			Intent i = b.build();
			
			com.amazon.speech.speechlet.IntentRequest.Builder bi = IntentRequest.builder();
			bi.withIntent(i);
			bi.withRequestId("1234");
			
			IntentRequest ir = bi.build();
			
			movieTitle = null;
			SpeechletResponse sr = cs.onIntent(ir, session);
			
			if (sr.getShouldEndSession()) {
				System.out.println("SESSION ENDED");
				break;
			}
			
			System.out.print("Enter a intent: ");
			intent = reader.nextLine(); 
			System.out.print("Enter a movie: ");
			movieTitle = reader.nextLine();
			
		}

	}

		

		com.amazon.speech.slu.Slot.Builder bSlot1 = Slot.builder();
		bSlot1.withName("movie");
		bSlot1.withValue("teenage mutant ninja turtles");
		Slot slot1 = bSlot1.build();
		
		com.amazon.speech.slu.Slot.Builder bSlot2 = Slot.builder();
		bSlot2.withName("actor1");
		bSlot2.withValue("Walter Matthau");
		Slot slot2 = bSlot2.build();
		
		com.amazon.speech.slu.Slot.Builder bSlot3 = Slot.builder();
		bSlot3.withName("actor2");
		bSlot3.withValue("Jack Lemmon");
		Slot slot3 = bSlot3.build();
		
		slots.put("movie",  slot1);
		slots.put("firstActor",  slot2);
		slots.put("secondActor",  slot3);

		com.amazon.speech.slu.Intent.Builder b = Intent.builder();
		b.withName("intent");
		b.withSlots(slots);
		Intent i = b.build();
		
		com.amazon.speech.speechlet.IntentRequest.Builder bi = IntentRequest.builder();
		bi.withIntent(i);
		bi.withRequestId("1234");
		
		IntentRequest ir = bi.build();
		System.out.println("-------" + ir);
		
		com.amazon.speech.speechlet.Session.Builder bs = Session.builder();
		bs.withIsNew(true);
		bs.withSessionId("0987654321");
		Session session = bs.build();
		
		
		try {
			//Action mia = new GetMovieInfoAction("Batman",session);
			//mia.performAction();
			session.setAttribute(Constants.SESSION_KEY_ACTION_COMPLETE, true);

			SpeechletResponse sr = cs.onIntent(ir, session);
			
			b = Intent.builder();
			b.withName("No");
			b.withSlots(slots);
			i = b.build();
			
			bi = IntentRequest.builder();
			bi.withIntent(i);
			bi.withRequestId("1234");
			
			ir = bi.build();
			
			sr = cs.onIntent(ir, session);
			
			b = Intent.builder();
			b.withName("No");
			b.withSlots(slots);
			i = b.build();
			
			bi = IntentRequest.builder();
			bi.withIntent(i);
			bi.withRequestId("1234");
			
			ir = bi.build();
			
			sr = cs.onIntent(ir, session);
			
			b = Intent.builder();
			b.withName("Yes");
			b.withSlots(slots);
			i = b.build();
			
			bi = IntentRequest.builder();
			bi.withIntent(i);
			bi.withRequestId("1234");
			
			ir = bi.build();
			
			sr = cs.onIntent(ir, session);
			
			b = Intent.builder();
			b.withName("GetMovieInfo");
			b.withSlots(slots);
			i = b.build();
			
			bi = IntentRequest.builder();
			bi.withIntent(i);
			bi.withRequestId("1234");
			
			ir = bi.build();
			
			sr = cs.onIntent(ir, session);

		} catch (SpeechletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private void testMovie() throws TmdbApiException{
		Movie movie = new Movie("Avengers");
		
		String title = movie.getTitle();
		System.out.println(title);
		assertTrue("Not True", title.equals("The Avengers"));
	}
	private void testMovie2() throws TmdbApiException{
		Movie movie = new Movie("The Matrix");
		
		String title = movie.getTitle();
		System.out.println(title);
		assertTrue("Not True", title.equals("The Matrix"));
	}
	*/
	}
	
}