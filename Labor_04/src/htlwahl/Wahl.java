package htlwahl;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class Wahl {
    public static final String NON_VOTE_NAME = "Nicht Abgestimmt";

    private final Kandidat[] kandidaten;
    private Kandidat lastVoteFirst;
    private Kandidat lastVoteSecond;
    private int voteCount;

    public Kandidat[] getKandidaten() {
        return kandidaten;
    }

    public Kandidat getLastVoteFirst() {
        return lastVoteFirst;
    }

    public Kandidat getLastVoteSecond() {
        return lastVoteSecond;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public Wahl(Kandidat[] kandidaten) {
        this.kandidaten = new Kandidat[kandidaten.length + 1];
        System.arraycopy(kandidaten, 0, this.kandidaten, 1, kandidaten.length);
        this.kandidaten[0] = new Kandidat(NON_VOTE_NAME);
        this.voteCount = 0;
    }

    public void giveVotes(Kandidat kandidat, boolean isFirst, boolean revert) {
        int currentVotes = kandidat.getPunkte();
        int currentPlatz1 = kandidat.getPlatz1();
        int revertFactor = revert ? -1 : 1;
        if (isFirst) {
            kandidat.setPlatz1(currentPlatz1 + revertFactor);
            currentVotes += revertFactor;
        }
        kandidat.setPunkte(currentVotes + revertFactor);
    }

    public void processVote(String vote) throws AmbiguousVoteException, IllegalArgumentException {
        if (!isValidVotingString(vote))
            throw new IllegalArgumentException("Ungültiger Wahlstring\n");
        Kandidat[] firstChoice = getKandidatenFromVote(vote.substring(0, 1));
        Kandidat[] secondChoice = getKandidatenFromVote(vote.substring(1, 2));
        checkKandidatArray(firstChoice, secondChoice);
        registerVote(firstChoice[0], secondChoice[0]);
        this.voteCount++;
    }

    private void registerVote(Kandidat kandidat, Kandidat kandidat1) {
        checkDuplicate(kandidat, kandidat1);
        giveVotes(kandidat, true, false);
        giveVotes(kandidat1, false, false);
        lastVoteFirst = kandidat;
        lastVoteSecond = kandidat1;
    }

    private void checkDuplicate(Kandidat kandidat, Kandidat kandidat1) {
        if(kandidat.equals(kandidaten[0]))
            return;
        if (kandidat.equals(kandidat1))
            throw new IllegalArgumentException("Doppelte Wahl\n");
    }

    public void revertLastVote(){
        if(Objects.nonNull(lastVoteFirst) && Objects.nonNull(lastVoteSecond)){
            giveVotes(lastVoteFirst, true, true);
            giveVotes(lastVoteSecond, false, true);
            lastVoteFirst = null;
            lastVoteSecond = null;
            voteCount--;
        } else throw new IllegalArgumentException("Bereits einmal gelöscht!\n");
    }

    private void checkKandidatArray(Kandidat[] firstChoice, Kandidat[] secondChoice) throws AmbiguousVoteException {
        if (!validCandidatePresent(firstChoice, secondChoice))
            throw new IllegalArgumentException("Keine gültigen Kandidaten ermittelt\n");
        if (ambiguousVotePresent(firstChoice, secondChoice))
            throw new AmbiguousVoteException("Mehrere Kandidaten mit gleichem Buchstaben\n");
    }

    private boolean validCandidatePresent(Kandidat[] firstChoice, Kandidat[] secondChoice) {
        return firstChoice.length >= 1 &&
                secondChoice.length >= 1;
    }

    private boolean ambiguousVotePresent(Kandidat[] firstChoice, Kandidat[] secondChoice) {
        return firstChoice.length > 1 || secondChoice.length > 1;
    }


    static boolean isValidVotingString(String votingString) {
        return votingString.length() == 2;
    }

    private Kandidat[] getKandidatenFromVote(String name) {
        if (name.equals("-"))
            return getKandidatenFromName(NON_VOTE_NAME);
        return getKandidatenFromName(name);
    }

    private Kandidat[] getKandidatenFromName(String name) {
        return Arrays.stream(kandidaten)
                .filter(k -> k.getName().toLowerCase().startsWith(name.toLowerCase()))
                .toArray(Kandidat[]::new);
    }

    public void processVoteDetailed(String readLine) {
        Kandidat[] choices = getKandidatenOfDetailVote(readLine);
        if (choices.length != 2) throw new IllegalArgumentException("Ungültiger Wahlstring");
        registerVote(choices[0], choices[1]);
    }

    private Kandidat[] getKandidatenOfDetailVote(String readLine) {
        return Stream.of(readLine.split("\\+"))
                .filter(s -> !s.isBlank())
                .map(this::getKandidatFromStringIndex)
                .toArray(Kandidat[]::new);
    }

    private Kandidat getKandidatFromStringIndex(String index) {
        int indexInt = Integer.parseInt(index);
        return kandidaten[indexInt];
    }
}
