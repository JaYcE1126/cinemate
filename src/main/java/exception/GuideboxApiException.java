package exception;

@SuppressWarnings("serial")

public class GuideboxApiException extends Exception {


    public GuideboxApiException() {
    }

    public GuideboxApiException(final String responseBody) {
        super(responseBody);
    }

}

