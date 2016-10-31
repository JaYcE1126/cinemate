package exception;

@SuppressWarnings("serial")

public class TmdbApiException extends Exception {


    public TmdbApiException() {
    }

    public TmdbApiException(final String responseBody) {
        super(responseBody);
    }

}

