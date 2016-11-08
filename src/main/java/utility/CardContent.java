package utility;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import value.*;


public class CardContent {

	private static final Logger logger = LoggerFactory.getLogger(CardContent.class);

	public static final String welcome = "Welcome to Cinemate!  You can ask Cinemate to give you any of the follow information:\n" + 
			"A movie's plot.\n" + 
			"A movie's release date.\n" + 
			"A movie's director.\n" +
			"A movie's producer.\n" +
			"A movie's writer.\n" +
			"A movie's music composer.\n" +
			"A movie's cast members.\n" +
			"Common movies between two actors \n" +
			"Where you can stream a movie.";
			//"A list of movies now playing in theaters.\n";
	
	public static final String help = "You can ask Cinemate to give you any of the follow information:\n" + 
			"A movies streaming sources.\n" +
			"A movies plot.\n" + 
			"A movies release date.\n" + 
			"A movies runtime.\n" +
			"A movies director.\n" +
			"A movies producer.\n" +
			"A movies writer.\n" +
			"A movies music composer.\n" +
			"A movies cast members.\n" +
			"Common movies between two actors \n";
	
	public static String movieInfo(Movie movie){
		String cardContent = "";
		String directors = Utilities.convertListToString(movie.getDirectors());
		cardContent = movie.getTitle() + " was released on " + movie.getReleaseDate() + " and was directed by " + directors + ". " + "It's plot is " + movie.getPlot();

		return cardContent;
	}
	
	public static String moviePlot(Movie movie){
		String cardContent = "";
		
		cardContent = "The plot to, " + movie.getTitle() + " is: " + movie.getPlot();

		return cardContent;
	}
	
	public static String movieReleaseDate(Movie movie){
		String cardContent = "";
		
		cardContent = movie.getTitle() + " was released on " + movie.getReleaseDate() + ". ";

		return cardContent;
	}
	
	public static String movieCrewList(Movie movie){
		String cardContent = "";
		String directors = Utilities.convertListToString(movie.getDirectors());
		String writers = Utilities.convertListToString(movie.getWriters());
		String producers = Utilities.convertListToString(movie.getProducers());
		String composers = Utilities.convertListToString(movie.getOriginalMusicComposers());
		
		cardContent += movie.getTitle() + " was directed by " + directors + ", written by " + writers + ", and produced by " + producers + "." + "The original music was composed by " + composers + ".";

		return cardContent;
	}
	
	public static String movieCast(Movie movie, int index){
		
		List<String> castList = movie.getCast();
		List<String> castListRoles = movie.getCastRoles();

		StringBuilder cardContent = (index==0)? 		
				new StringBuilder("The cast of " + movie.getTitle() + " includes: ") :
				new StringBuilder("Also included are: ");
		
		for (int i = index; i < (index+5) && i < castList.size(); i++){
			logger.debug("i: [{}]", i);
			cardContent.append(
					(i != (index+4) && (castList.size()-i)!=1 && (castList.size()-index)!=1) ? 
							castList.get(i) + " as " + castListRoles.get(i) + ", \n":
							"and " + castList.get(i) + " as " + castListRoles.get(i) + ".");
		}
	
		return cardContent.toString();
	}

	
	public static String movieDirector(Movie movie){
		String cardContent = "";
		String directors = Utilities.convertListToString(movie.getDirectors());
		cardContent = movie.getTitle() + " was directed by:  " + directors + ".";

		return cardContent;
	}
	
	public static String movieProducer(Movie movie){
		String cardContent = "";
		String producers = Utilities.convertListToString(movie.getProducers());
		cardContent = movie.getTitle() + " was produced by:  " + producers + ".";

		return cardContent;
	}
	
	public static String movieWriter(Movie movie){
		String cardContent = "";
		String writers = Utilities.convertListToString(movie.getWriters());
		cardContent = movie.getTitle() + " was written by:  " + writers + ".";

		return cardContent;
	}
	
	public static String movieComposer(Movie movie){
		String cardContent = "";
		String composers = Utilities.convertListToString(movie.getOriginalMusicComposers());
		cardContent = "The original music for " + movie.getTitle() + " was composed by:  " + composers + ".";

		return cardContent;
	}
	
	public static String streamingSources(Movie movie){
		String cardContent = "";
		String streamingSources = Utilities.convertListToString(movie.getWebSources());
		
		cardContent = movie.getTitle() + "is available for streaming on " + streamingSources;

		return cardContent;
	}
	
	public static String commonMovies(List<String> commonMovies, List<Actor> actors){
		StringBuilder cardContent = new StringBuilder();
		
		cardContent.append("The common movies between ");
		cardContent.append(actors.get(0).getName());
		cardContent.append(" and ");
		cardContent.append(actors.get(1).getName());
		cardContent.append(" are: ");
		cardContent.append(Utilities.convertListToString(commonMovies).replace(",", "\n"));

		return cardContent.toString();
	}
	
	public static String movieRuntime(Movie movie){
		int hours = Integer.valueOf(movie.getRuntime())/60;
		int mins = Integer.valueOf(movie.getRuntime())%60;
		
		StringBuilder cardContent = new StringBuilder();

		cardContent.append(movie.getTitle()); 
		cardContent.append(" has a runtime of ");
		cardContent.append(hours);
		if (hours>1){
			cardContent.append(" hours and ");
		}else if (hours==1){
			cardContent.append(" hour and ");
		}
		cardContent.append(mins);
		cardContent.append(" minutes.");

		return cardContent.toString();
	}

}
