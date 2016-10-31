package exception;

@SuppressWarnings("serial")

public class CinemateException extends Exception {


    public CinemateException() {
    }

    public CinemateException(final String responseBody) {
        super(responseBody);
    }

}

