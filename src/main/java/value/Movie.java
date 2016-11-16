package value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exception.CinemateException;
import utility.Constants;

public class Movie {

  //private static final Logger logger = LoggerFactory.getLogger(Movie.class);	
	
  private String id;
	private String title;
	private String plot;
	private String posterLocation;
	private String releaseDate;
	private String runtime;

	private List<String> originalMusicComposers = new ArrayList<String>();
	private List<String> directors = new ArrayList<String>();
	private List<String> writers = new ArrayList<String>();
	private List<String> producers = new ArrayList<String>();
	private List<String> cast = new ArrayList<String>();
	private List<String> castRoles = new ArrayList<String>();
	private List<String> webSources = new ArrayList<String>();
	
    //Introducing the default constructor
    public Movie() {
    }

	
	public Movie(String movieId, String movieTitle) throws CinemateException{
		if (movieTitle.length() ==0 || movieTitle == null) throw new CinemateException(Constants.CINEMATE_EXCEPTION_NO_TITLE);
		this.id = movieId;
		this.title = movieTitle;
	}
		
	public Movie (HashMap<String, Object> movieInfo) throws CinemateException {
		setMovieInfo(movieInfo);
	}
	
	@SuppressWarnings("unchecked")
	public void setMovieInfo(HashMap<String, Object> movieInfo) throws CinemateException{
		String movieTitle = (movieInfo.get(Constants.TMDB_RESPONSE_TITLE) instanceof String) ? (String) movieInfo.get(Constants.TMDB_RESPONSE_TITLE) : null;
		int movieId = (Integer) movieInfo.get(Constants.TMDB_RESPONSE_ID);

		if (movieTitle.length() ==0 || movieTitle == null) throw new CinemateException(Constants.CINEMATE_EXCEPTION_NO_TITLE);
		this.id = "" + movieId;
		this.title = movieTitle;
			
		plot = (movieInfo.get(Constants.TMDB_RESPONSE_PLOT) instanceof String) ? (String) movieInfo.get(Constants.TMDB_RESPONSE_PLOT) : null;
		posterLocation = (movieInfo.get(Constants.TMDB_RESPONSE_POSTER_PATH) instanceof String) ? (String) movieInfo.get(Constants.TMDB_RESPONSE_POSTER_PATH) : null;
		posterLocation = Constants.URL_TMDB_POSTER_PATH + posterLocation;
		releaseDate = (movieInfo.get(Constants.TMDB_RESPONSE_RELEASE_DATE) instanceof String) ? (String) movieInfo.get(Constants.TMDB_RESPONSE_RELEASE_DATE) : null;
		runtime = (movieInfo.get(Constants.TMDB_RESPONSE_RUNTIME) instanceof String) ? (String) movieInfo.get(Constants.TMDB_RESPONSE_RUNTIME) : null;
		originalMusicComposers = (movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_COMPOSER) instanceof List) ? (List<String>) movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_COMPOSER) : null;
		directors = (movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_DIRECTOR) instanceof List) ? (List<String>) movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_DIRECTOR) : null;
		writers = (movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_WRITER) instanceof List) ? (List<String>) movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_WRITER) : null;
		producers = (movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_PRODUCER) instanceof List) ? (List<String>) movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_PRODUCER) : null;
		cast = (movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_CAST) instanceof List) ? (List<String>) movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_CAST) : null;
		castRoles = (movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_CAST_ROLES) instanceof List) ? (List<String>) movieInfo.get(Constants.TMDB_RESPONSE_CREDITS_CAST_ROLES) : null;
	}
	
	public void setWebSources(List<String> webSources){
		this.webSources = webSources;
	}
	
	public List<String> getWebSources(){
		return this.webSources;
	}
		
	public String getId(){
		return this.id;
	}
	
	public String getPlot(){
		return this.plot;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getPosterLocation(){
		if ("".equals(posterLocation) || posterLocation == null) return "";
		return this.posterLocation;
	}
	
	public String getRuntime(){
		return this.runtime;
	}
	
	public String getReleaseDate(){
		return this.releaseDate;
	}
	
	public List<String> getDirectors(){
		return this.directors;
	}
	
	public List<String> getProducers(){
		return this.producers;
	}
	
	public List<String> getOriginalMusicComposers(){
		return this.originalMusicComposers;
	}
	
	public List<String> getWriters(){
		return this.writers;
	}
	
	public List<String> getCast(){
		return this.cast;
	}
	
	public List<String> getCastRoles(){
		return this.castRoles;
	}
	
}
