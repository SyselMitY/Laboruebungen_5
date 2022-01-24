package htlwahl;

public class AmbiguousVoteException extends Exception {
    public AmbiguousVoteException() {
    }

    public AmbiguousVoteException(String message) {
        super(message);
    }
}
