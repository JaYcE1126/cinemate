package utility;

public class Constants {
	
	/*
	 * Intent Names
	 */
	public static final String INTENT_YES = "Yes";
	public static final String INTENT_NO = "No";
	public static final String INTENT_GET_MOVIE_INFO = "GetMovieInfo";
	public static final String INTENT_GET_MOVIE_PLOT = "GetMoviePlot";
	public static final String INTENT_GET_MOVIE_RELEASE_DATE = "GetMovieReleaseDate";
	public static final String INTENT_GET_MOVIE_CREW = "GetMovieCrew";
	public static final String INTENT_GET_MOVIE_CAST = "GetMovieCast";
	public static final String INTENT_GET_MOVIE_DIRECTOR = "GetMovieDirector";
	public static final String INTENT_GET_MOVIE_PRODUCER = "GetMovieProducer";
	public static final String INTENT_GET_MOVIE_WRITER = "GetMovieWriter";
	public static final String INTENT_GET_MOVIE_COMPOSER = "GetMovieComposer";
	public static final String INTENT_GET_MOVIE_RUNTIME= "GetMovieRuntime";
	public static final String INTENT_GET_UPCOMING = "GetUpcoming";
	public static final String INTENT_GET_COMMON_MOVIES = "GetCommonMovies";
	public static final String INTENT_GET_STREAMING_SOURCES = "GetStreamingSources";
	public static final String INTENT_KEEP_GOING = "KeepGoing";
	public static final String INTENT_TRY_AGAIN = "TryAgain";
	public static final String INTENT_REPEAT = "Repeat";
	public static final String INTENT_CUSTOM_STOP = "CustomStop";
	public static final String INTENT_STOP = "AMAZON.StopIntent";
	public static final String INTENT_HELP = "AMAZON.HelpIntent";
	public static final String INTENT_CANCEL = "AMAZON.CancelIntent";
	
	/*
	 * Action Names
	 */
	public static final String ACTION_GET_MOVIE_CAST = "GetMovieCastAction";
	
	/*
	 * Slot Names
	 */
	public static final String SLOT_NAME_MOVIE_TITLE = "movieTitle";
	public static final String SLOT_NAME_ACTOR_1 = "firstActor";
	public static final String SLOT_NAME_ACTOR_2 = "secondActor";
	
	/*
	 * TMDB URls
	 */
	public static final String URL_TMDB_SEARCH_MOVIE = "https://api.themoviedb.org/3/search/movie?api_key=39e754ea863578c2a6c6f1b518d62443&query=";
	public static final String URL_TMDB_SEARCH_ACTOR = "https://api.themoviedb.org/3/search/person?api_key=39e754ea863578c2a6c6f1b518d62443&query=";
	public static final String URL_TMDB_MOVIE_INFO = "https://api.themoviedb.org/3/movie/###?api_key=39e754ea863578c2a6c6f1b518d62443&append_to_response=credits";
	public static final String URL_TMDB_POSTER_PATH = "https://image.tmdb.org/t/p/";
	public static final String URL_TMDB_DISCOVER_COMMON_MOVIES = "https://api.themoviedb.org/3/discover/movie?api_key=39e754ea863578c2a6c6f1b518d62443&with_cast=";
	public static final String URL_GB_SEARCH_ID = "https://api-public.guidebox.com/v1.43/US/rKQGwLJ0FLqoHUU8oyKEwce6arOgwGmM/search/movie/id/themoviedb/";
	public static final String URL_GB_MOVIE_INFOL = "https://api-public.guidebox.com/v1.43/US/rKQGwLJ0FLqoHUU8oyKEwce6arOgwGmM/movie/";


	/*
	 * TMDB API Response Parameters
	 */
	public static final String TMDB_RESPONSE_RESULTS = "results";
	public static final String TMDB_RESPONSE_TOTAL_RESULTS = "total_results";
	public static final String TMDB_RESPONSE_ID = "id";
	public static final String TMDB_RESPONSE_NAME = "name";
	public static final String TMDB_RESPONSE_TITLE = "title";
	public static final String TMDB_RESPONSE_PLOT = "overview";
	public static final String TMDB_RESPONSE_POSTER_PATH = "poster_path";
	public static final String TMDB_RESPONSE_RELEASE_DATE = "release_date";
	public static final String TMDB_RESPONSE_VOTE_COUNT = "vote_count";
	public static final String TMDB_RESPONSE_RUNTIME = "runtime";
	public static final String TMDB_RESPONSE_CREDITS = "credits";
	public static final String TMDB_RESPONSE_CREDITS_CAST = "cast";
	public static final String TMDB_RESPONSE_CREDITS_CREW = "crew";
	public static final String TMDB_RESPONSE_CREDITS_CAST_ROLES = "castRoles";
	public static final String TMDB_RESPONSE_CREDITS_JOB = "job";
	public static final String TMDB_RESPONSE_CREDITS_NAME = "name";
	public static final String TMDB_RESPONSE_CREDITS_CHARACTER = "character";
	public static final String TMDB_RESPONSE_CREDITS_DIRECTOR = "Director";
	public static final String TMDB_RESPONSE_CREDITS_PRODUCER = "Executive Producer";
	public static final String TMDB_RESPONSE_CREDITS_SCREENPLAY = "Screenplay";
	public static final String TMDB_RESPONSE_CREDITS_WRITER = "Writer";
	public static final String TMDB_RESPONSE_CREDITS_COMPOSER = "Original Music Composer";
	public static final String TMDB_RESPONSE_CREDITS_MUSIC = "Music";
	public static final String TMDB_RESPONSE_DATE_FORMAT = "yyyy-MM-dd";
	public static final String TMDB_RESPONSE_ERROR_MESSAGE = "errors";	
	public static final String TMDB_RESPONSE_STATUS_MESSAGE = "status_message";
	
	/*
	 * Guidebox API Response Parameters
	 */
	public static final String GB_RESPONSE_MOVIE_ID = "id";
	public static final String GB_RESPONSE_ERROR = "error";
	public static final String GB_RESPONSE_WEB_SUBSCRIPTION = "subscription_web_sources";
	public static final String GB_RESPONSE_SOURCE_NAME = "display_name";
	
	/*
	 * General DAO Response Parameters
	 */
	public static final String DAO_RESPONSE_COMMON_MOVIES = "common_movies";


	/*
	 * General API Constants
	 */
	public static final int SUCCESSFUL_HTTPS_STATUS_CODE = 200;
	public static final String UNKNOWN_ACTOR = "an unknown actor";
	public static final String UNKNOWN_CHARACTER = "an unknown character";
	
	/*
	 * Session Keys
	 */
		public static final String SESSION_KEY_MOVIE = "movie";
		public static final String SESSION_KEY_ACTION_COMPLETE = "actionComplete";
		public static final String SESSION_KEY_ACTION = "action";
		public static final String SESSION_KEY_DIALOG = "dialog";

	/*
	 * Card Content Titles
	 */
		public static final String CARD_TITLE_WELCOME = "Welcome";
		public static final String CARD_TITLE_COMMON_MOVIES = "Common Movies";
		public static final String CARD_TITLE_HELP = "Help";

	
	/*
	 * Exception Messages
	 */
		public static final String CINEMATE_EXCEPTION_NO_TITLE = "Cannot create Movie instance with no title.";
	
	
}
