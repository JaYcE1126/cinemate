package utility;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import value.Actor;
import value.Movie;

public class Sentences {
	private static final Logger logger = LoggerFactory.getLogger(Sentences.class);
	
	public static final String speakMovie = 			
			"<speak><s>I'm sorry!</s><s>I don't know which movie you're asking about.</s><s>Please state your request again and clearly speak your movie of interest.</s></speak>";
	
	public static final String speakMovieReprompt = 			
			"<speak><s>Please state your request again, and clearly speak your movie of interest.</s></speak>";
	
	public static String cannotFindMovie(String movieTitle){
		StringBuilder sentence = new StringBuilder("<speak>");
		
		sentence.append("<s>I cannot find any information on ");
		sentence.append(movieTitle);
		sentence.append("</s>");
		sentence.append("<s>Please try again later or select a different movie for your request.</s>");
		sentence.append("</speak>");

		return sentence.toString();
	}
	
	public static final String help = 
			"<speak>" + 
			"<s>You can ask Cinemate to provide you with the following information for all kinds of movie information such as:</s>"+
			"<s>A movie's plot, release date, director, producer, writer, or composer.</s>" +
			"<s>You can also ask for a list of a movie's cast members or common movies between two actors.</s>" +
			"<s>I've sent a list of information I can provide to the Alexa app on your phone.</s>" +
			"<s>Now, go ahead, tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static final String helpReprompt = 
			"<speak>" + 
			"<s>You can ask Cinemate to provide you with the following information for all kinds of movie information such as:</s>"+
			"<s>A movie's plot, release date, director, producer, writer, or composer.</s>" +
			"<s>You can also ask for a list of a movie's cast members or common movies between two actors.</s>" +
			"<s>I've sent a list of information I can provide to the Alexa app on your phone.</s>" +
			"<s>Now, go ahead, tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String confirmMovieInit(int moviesFound, String movieTitle, String releaseDate){
		logger.debug("Entered: [moviesFound: {}, movieTitle: {}, releaseDate: {}, director: {}]",
				moviesFound, movieTitle, releaseDate);
		StringBuilder sentence = new StringBuilder("<speak>");
		
		sentence.append("<s>I've found ");
		sentence.append(moviesFound);
		sentence.append(" movies that best match your request.</s>");
		sentence.append("<s>The first is  ");
		sentence.append(movieTitle);
		sentence.append(", released on ");
		sentence.append(releaseDate);
		sentence.append(".</s><s>Is this the movie you are looking for? </s><s>Say Yes or No.</s>");
		sentence.append("</speak>");

		logger.debug("Exited: [sentence: {}]",sentence.toString());
		return sentence.toString();
	}
	
	public static String confirmMovieReprompt(String movieTitle, String releaseDate){
		logger.debug("Entered: [movieTitle: {}, releaseDate: {}]", movieTitle, releaseDate);

		StringBuilder sentence = new StringBuilder("<speak>");
		
		sentence.append("<s>Please say Yes or No.</s>");
		sentence.append("<s>Is ");
		sentence.append(movieTitle);
		sentence.append(" released on ");
		sentence.append(releaseDate);
		sentence.append(" the movie you are looking for? </s>");
		sentence.append("</speak>");
		
		logger.debug("Exited: [sentence: {}]",sentence.toString());
		return sentence.toString();
	}
	
	public static String confirmMovieNextMovie(boolean isLastResult, String movieTitle, String releaseDate){
		StringBuilder sentence = new StringBuilder("<speak>");
		
		if (isLastResult){
			sentence.append("<s>The last result I have is ");
		}else {
			sentence.append("<s>The next is ");
		}
		sentence.append(movieTitle);
		sentence.append(", released on ");
		sentence.append(releaseDate);
		sentence.append("</s><s>Is this the movie you are looking for? </s><s>Say Yes or No.</s>");
		sentence.append("</speak>");

		return sentence.toString();
	}
	
	
	public static String confirmMovieNoneSelected = 
			"<speak><s>There are no more movies that best match your request.</s>" + 
			"<s>Please try your request again and clearly state the movie of interest.</s></speak>";
			
	public static String confirmMovieNoneSelectedReprompt = 
			"<s>Please try your request again and clearly state the movie of interest.</s></speak>";

	
	public static String confirmMovieInvalidIntent =
		"<speak><s>Your response is invalid.</s>";
	
	public static String invalidIntent =
			"<speak><s>Your request is invalid.</s><s> I've sent a list of information I can provide to your Alexa App.</s><s> Now, go ahead and ask me what you would like to know.</s></speak>";

	public static String invalidIntentReprompt =
			"<speak><s>Please go ahead and ask me what you would like to know about any movie.</s></speak>";
	
	public static String movieInfo(Movie movie){
		int hours = Integer.valueOf(movie.getRuntime())/60;
		int mins = Integer.valueOf(movie.getRuntime())%60;
		
		StringBuilder sentence = new StringBuilder("<speak><s>");
		sentence.append(movie.getTitle()); 
		if (Utilities.isPastDate(movie.getReleaseDate())){
			sentence.append(" was released on ");
		} else {
			sentence.append(" will be released on ");
		}
		sentence.append(movie.getReleaseDate());
		sentence.append(" and has a runtime of ");
		sentence.append(hours);
		if (hours>1){
			sentence.append(" hours and ");
		}else if (hours==1){
			sentence.append(" hour and ");
		}
		sentence.append(mins);
		sentence.append(" minutes.</s><s>It's plot is: ");
		sentence.append(movie.getPlot().replaceAll("\\.", "</s><s>"));
		sentence.append("What else would you like to know about ");
		sentence.append(movie.getTitle());
		sentence.append("</s></speak>");

		return sentence.toString();
	}
	
	public static final String movieInfoReprompt =
			"<speak>" + 
			"<s>I can tell you more about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String movieReleaseDate(Movie movie){
		StringBuilder sentence = new StringBuilder("<speak><s>");
		if (movie.getReleaseDate().length() > 0){
			sentence.append(movie.getTitle()); 
			if (Utilities.isPastDate(movie.getReleaseDate())){
				sentence.append(" was released on ");
			} else {
				sentence.append(" will be released on ");
			}
			sentence.append(movie.getReleaseDate());
			sentence.append(".</s><s>What else would you like to know about ");
			sentence.append(movie.getTitle());
			sentence.append(".</s></speak>");
		} else {
			sentence.append("I cannot find the release date for ");
			sentence.append(movie.getTitle()); 
			sentence.append("</s><s>What else would you like to know about this movie?</s></speak>"); 
		}

		return sentence.toString();
	}
	
	public static final String movieReleaseDateReprompt =
			"<speak>" + 
			"<s>I can tell you other things about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String movieRuntime(Movie movie){
		int hours = Integer.valueOf(movie.getRuntime())/60;
		int mins = Integer.valueOf(movie.getRuntime())%60;
		
		StringBuilder sentence = new StringBuilder("<speak><s>");
		if (movie.getReleaseDate().length() > 0){
			sentence.append(movie.getTitle()); 
			sentence.append(" has a runtime of ");
			sentence.append(hours);
			if (hours>1){
				sentence.append(" hours and ");
			}else if (hours==1){
				sentence.append(" hour and ");
			}
			sentence.append(mins);
			sentence.append(" minutes.</s><s>What else would you like to know about ");
			sentence.append(movie.getTitle());
			sentence.append(".</s></speak>");
		} else {
			sentence.append("I cannot find the release date for ");
			sentence.append(movie.getTitle()); 
			sentence.append("</s><s>What else would you like to know about this movie?</s></speak>"); 
		}

		return sentence.toString();
	}
	
	public static final String movieRuntimeReprompt =
			"<speak>" + 
			"<s>I can tell you other things about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String movieDirector(Movie movie){
		StringBuilder sentence = new StringBuilder("<speak><s>");
		if (movie.getDirectors().size() > 0){
			sentence.append(movie.getTitle()); 
			sentence.append(" was directed by ");
			sentence.append(movie.getDirectors());
			sentence.append(".</s><s>What else would you like to know about ");
			sentence.append(movie.getTitle());
			sentence.append(".</s></speak>");
		} else {
			sentence.append("I cannot find director information on ");
			sentence.append(movie.getTitle()); 
			sentence.append("</s><s>What else would you like to know about this movie?</s></speak>"); 
		}

		return sentence.toString();
	}
	
	public static final String movieDirectorReprompt =
			"<speak>" + 
			"<s>I can tell you more about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String movieProducer(Movie movie){
		StringBuilder sentence = new StringBuilder("<speak><s>");
		if (movie.getProducers().size() > 0){
			sentence.append(movie.getTitle()); 
			sentence.append(" was produced by ");
			sentence.append(movie.getProducers());
			sentence.append(".</s><s>What else would you like to know about ");
			sentence.append(movie.getTitle());
			sentence.append(".</s></speak>");
		} else {
			sentence.append("I cannot find producer information on ");
			sentence.append(movie.getTitle()); 
			sentence.append("</s><s>What else would you like to know about this movie?</s></speak>"); 
		}

		return sentence.toString();
	}
	
	public static final String movieProducerReprompt =
			"<speak>" + 
			"<s>I can tell you more about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String movieWriter(Movie movie){
		StringBuilder sentence = new StringBuilder("<speak><s>");
		if (movie.getWriters().size() > 0){
			sentence.append(movie.getTitle()); 
			sentence.append(" was written by ");
			sentence.append(movie.getWriters());
			sentence.append(".</s><s>What else would you like to know about ");
			sentence.append(movie.getTitle());
			sentence.append(".</s></speak>");
		} else {
			sentence.append("I cannot find information who wrote ");
			sentence.append(movie.getTitle()); 
			sentence.append("</s><s>What else would you like to know about this movie?</s></speak>"); 
		}

		return sentence.toString();
	}
	
	public static final String movieWriterReprompt =
			"<speak>" + 
			"<s>I can tell you more about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String movieComposer(Movie movie){
		StringBuilder sentence = new StringBuilder("<speak><s>");
		if (movie.getOriginalMusicComposers().size() > 0){
			sentence.append("The music for "); 
			sentence.append(movie.getTitle()); 
			sentence.append(" was composed by ");
			sentence.append(movie.getOriginalMusicComposers());
			sentence.append(".</s><s>What else would you like to know about ");
			sentence.append(movie.getTitle());
			sentence.append(".</s></speak>");
		} else {
			sentence.append("I cannot find information on who composed the music for ");
			sentence.append(movie.getTitle()); 
			sentence.append("</s><s>What else would you like to know about this movie?</s></speak>"); 
		}

		return sentence.toString();
	}
	
	public static final String movieComposerReprompt =
			"<speak>" + 
			"<s>I can tell you more about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String movieSources(Movie movie){
		StringBuilder sentence = new StringBuilder("<speak><s>");
		
		sentence.append(movie.getTitle());
		sentence.append(" is available for streaming on, ");
		sentence.append(Utilities.convertListToString(movie.getWebSources()));
		sentence.append(".</s><s>What else would you like to know about ");
		sentence.append(movie.getTitle());
		sentence.append(".</s></speak>");

		return sentence.toString();
	}
	
	public static final String movieSourcesReprompt =
			"<speak>" + 
			"<s>I can tell you more about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String noMovieSources(String movieTitle){
		StringBuilder sentence = new StringBuilder("<speak>");
		
		sentence.append("<s>I cannot find any streaming sources for ");
		sentence.append(movieTitle);
		sentence.append("</s>");
		sentence.append("<s>What other information would you like me to tell you about this movie?</s>");
		sentence.append("</speak>");

		return sentence.toString();
	}
	public static final String noMovieSourcesReprompt =
			"<speak>" + 
			"<s>I can give you other information about this movie, like it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what else you would like to know?</s>" + 
			"</speak>";
	
	public static final String speakActor =
			"<speak>" + 
			"<s>I'm Sorry! I didn't understand the names of the actors in your request.</s>" +
			"<s>Please repeat your request and clearly speak the first and last name of the actor's of interest.</s>" +
			"</speak>";
	
	public static final String speakActorReprompt =
			"<speak>" + 
			"<s>I didn't understand the names of the actors in your request.</s>" +
			"<s>Please repeat your request and clearly speak the first and last name of the actor's of interest.</s>" +
			"</speak>";
	
	public static String cannotFindActor(String actorName){
		StringBuilder sentence = new StringBuilder("<speak>");
		
		sentence.append("<s>I'm sorry! I cannot find any information on ");
		sentence.append(actorName);
		sentence.append("</s>");
		sentence.append("<s>Please try again or select a different actor for your request.</s>");
		sentence.append("</speak>");

		return sentence.toString();
	}
	
	public static String cannotFindActorReprompt(String actorName){
		StringBuilder sentence = new StringBuilder("<speak>");
		
		sentence.append("<s>I cannot find any information on ");
		sentence.append(actorName);
		sentence.append("</s>");
		sentence.append("<s>Please try again or select a different actor for your request.</s>");
		sentence.append("</speak>");

		return sentence.toString();
	}
	
	public static String moviePlot(Movie movie){
		StringBuilder sentence = new StringBuilder("<speak><s>");
		if (movie.getPlot().length() > 0){
			sentence.append("The plot to ");
			sentence.append(movie.getTitle()); 
			sentence.append(" is: ");
			sentence.append(movie.getPlot().replaceAll("\\.", "</s><s>"));
			sentence.append("What else would you like to know about this movie? ");
			sentence.append(".</s></speak>");
		} else {
			sentence.append("I cannot find the plot information on ");
			sentence.append(movie.getTitle()); 
			sentence.append("</s><s>What else would you like to know about this movie?</s></speak>"); 
		}

		return sentence.toString();
	}
	
	public static final String moviePlotReprompt =
			"<speak>" + 
			"<s>I can tell you other things about this movie like who it's cast are, or provide information on its crew.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you want to know?</s>" + 
			"</speak>";
	
	public static String movieCast(Movie movie, int index){
		
		List<String> castList = movie.getCast();
		List<String> castListRoles = movie.getCastRoles();

		StringBuilder sentence = (index==0)? 		
				new StringBuilder("<speak><s>The cast of " + movie.getTitle() + " includes: ") :
				new StringBuilder("<speak><s>Also included are: ");
		
		for (int i = index; i < (index+5) && i < castList.size(); i++){
			logger.debug("i: [{}]", i);
			sentence.append(
					(i != (index+4) && (castList.size()-i)!=1 && (castList.size()-index)!=1) ? 
							castList.get(i) + ", as " + castListRoles.get(i) + ", ":
							"and " + castList.get(i) + ", as " + castListRoles.get(i) + ".");
				
			
		}
		
		sentence.append("</s><s>If you want me to continue you can say 'Keep Going'.</s>"); 
		sentence.append("<s>Or you can ask me for other information about this movie or any other movie.</s> </speak>");
	
		return sentence.toString();

	}
	
	public static final String movieCastReprompt =
			"<speak>" + 
			"<s>I can tell you more about this movie like it's plot, release date, or provide information on its crew.</s>" +
			"<s>You can also ask me for information about another movie if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static String noCommonMovies(List<Actor> actors){
		StringBuilder sentence = new StringBuilder("<speak>");
		
		sentence.append("<s>There are no common movies between actors ");
		sentence.append(actors.get(0).getName());
		sentence.append(" and ");
		sentence.append(actors.get(1).getName());
		sentence.append(".</s>");
		sentence.append("<s>You can try again by using the names of two different actors in your request.</s>");
		sentence.append("<s>Now, go ahead and tell me what you would like to know.</s>");
		sentence.append("</speak>");

		return sentence.toString();
	}
	
	public static final String noCommonMoviesReprompt =
			"<speak>" + 
			"<s>You can try again by using the names of two different actors in your request.</s>" +
			"<s>Now, go ahead and tell me what you would like to know.</s>" +
			"</speak>";
	
	public static String commonMovies(List<String> commonMovies, List<Actor> actors){
		StringBuilder sentence = new StringBuilder("<speak>");
		
		sentence.append("<s>I've found the following ");
		sentence.append(commonMovies.size());
		sentence.append(" in common between actors ");
		sentence.append(actors.get(0).getName());
		sentence.append(" and ");
		sentence.append(actors.get(1).getName());
		sentence.append(": ");
		sentence.append(Utilities.convertListToString(commonMovies));
		sentence.append("</s><s>What else would you like me to tell you?</s>");
		sentence.append("</speak>");

		return sentence.toString();
	}
	
	public static final String commonMoviesReprompt =
			"<speak>" + 
			"<s>You can ask me to give you information about any movie, such as it's producers, writers, original music composer, or cast.</s>" +
			"<s>You can also ask me for common movies between two actors again if you'd like.</s>" +
			"<s>Please tell me what more you would like to know?</s>" + 
			"</speak>";
	
	public static final String unexpectedError =
			"<speak>" + 
			"<s>I'm sorry!  I've run into an unexpected error and cannot complete your request.</s>" +
			"<s>If this continues to occur, please try again later.</s>" + 
			"</speak>";
	
	public static final String tryAgain =
			"<speak>" + 
			"<s>Sure!  Please say restate your request or you can ask me for something else.</s>" +
			"</speak>";
	
	public static final String tryAgainReprompt =
			"<speak>" + 
			"<s>You can restate your request or ask me for something else such as inofmraiton a movie's direct, composer, or cast.</s>" +
			"<s>You can also ask me where you can stream your movie of interest.</s>"+
			"<s>Now, go ahead and tell me what you would like to know.</s>" +
			"</speak>";
	
	public static final String stopAction =
			"<speak>" + 
			"<s>Your request has been cancelled.</s>" +
			"<s>PLease tell what else you would like to know.</s>" +
			"</speak>";
	
	public static final String stopActionReprompt =
			"<speak>" + 
			"<s>You can restate your request or ask me for something else such as inofmraiton a movie's direct, composer, or cast.</s>" +
			"<s>You can also ask me where you can stream your movie of interest.</s>"+
			"<s>Now, go ahead and tell me what you would like to know.</s>" +
			"</speak>";
	
	public static final String goodbye =
			"<speak>" + 
			"<s>Thank you for using Cinemate, your number 1 movie guide.</s>" +
			"<s>Goodbye!</s>"+
			"</speak>";
	
	public static final String welcome = 
			"<speak>" +
			"<s>Welcome to Cinemate.</s>" + 
			"<s>I can now tell you where your reqested movie is available for streaming.</s>" +
			"<s>I can also give you a vast amount of movie information including plots, release dates, cast and crew members and common movies betweeen two actors.</s>" +
			"<s>Please tell me what you want to know?</s>" +
			"</speak>";
		
	public static final String welcomeReprompt =
			"<speak>" +
			"<s>You can say things like, tell me the plot to Gladiator, or, give me the cast of Titatic.</s>" +
			"<s>What would you like to know?</s>" +
			"</speak>";
}
