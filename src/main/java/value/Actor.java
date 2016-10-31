package value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Actor {
	  private static final Logger logger = LoggerFactory.getLogger(Actor.class);	

	  private String actorName;
	  private int actorId;
	  //private String posterLocation;
	  
	  public Actor(int actorId, String actorName) {
			logger.info("Entered");
			this.actorId = actorId;
			this.actorName = actorName;
			logger.info("Exited");
	  }
	  
	  public String getName(){
		  return this.actorName;
	  }
	  
	  public int getId(){
		  return this.actorId;
	  }
	  
	  
}
