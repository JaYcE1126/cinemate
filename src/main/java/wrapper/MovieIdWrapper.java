package wrapper;

public class MovieIdWrapper {
	
	private int movieId;
	private String movieTitle;
	private String movieReleaseDate;
	
	public MovieIdWrapper(Integer movieId){
		this.movieId = movieId;
	}
	
	public MovieIdWrapper(Integer movieId, String movieTitle, String movieReleaseDate){
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.movieReleaseDate = movieReleaseDate;
	}
	
	public int getMovieId(){
		return this.movieId;
	}
	
	public String getMovieTitle(){
		return this.movieTitle;
	}
	
	public String getMovieReleaseDate(){
		return this.movieReleaseDate;
	}
	
	public String toString(){
		return "[movieId: " + this.movieId + ", movieTitle: " + this.movieTitle + ", movieReleaseDate: " + this.movieReleaseDate + "]";
	}

}
