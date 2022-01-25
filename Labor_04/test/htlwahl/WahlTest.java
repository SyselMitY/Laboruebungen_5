package htlwahl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Logger;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNull;

public class WahlTest {
    private Wahl wahl;
    private Kandidat[] kandidaten;

    @BeforeMethod
    void setupStuff() {
        Logger logger = Logger.getLogger("htlwahl");
        kandidaten = new Kandidat[]{
                new Kandidat("Peter Sysel"),
                new Kandidat("Josef Gradinger"),
                new Kandidat("Klaus Kästner"),
                new Kandidat("Karl Kästner")
        };
        wahl = new Wahl(kandidaten);
    }

    @Test
    void getKandidaten() {
        var kandidaten = wahl.getKandidaten();
        assertEquals(5, kandidaten.length);
    }

    @Test
    void lastVoteStorageCorrect() {
        assertNull(wahl.getLastVoteFirst());
        assertNull(wahl.getLastVoteSecond());

        wahl.registerVote(kandidaten[1], kandidaten[2]);

        assertEquals(kandidaten[1], wahl.getLastVoteFirst());
        assertEquals(kandidaten[2], wahl.getLastVoteSecond());

        wahl.revertLastVote();
        assertNull(wahl.getLastVoteFirst());
        assertNull(wahl.getLastVoteSecond());
    }

    @Test
    void revertBufferEmpty() {
        assertThrows(IllegalStateException.class, () -> wahl.revertLastVote());
        wahl.registerVote(kandidaten[1], kandidaten[2]);
        wahl.registerVote(kandidaten[2], kandidaten[3]);
        wahl.revertLastVote();
        assertThrows(IllegalStateException.class, () -> wahl.revertLastVote());
    }

    @Test
    void voteCountCorrect() {
        assertEquals(0, wahl.getVoteCount());
        wahl.registerVote(kandidaten[1], kandidaten[2]);
        assertEquals(1, wahl.getVoteCount());
        wahl.revertLastVote();
        assertEquals(0, wahl.getVoteCount());
    }

    @Test
    void noDuplicateVoteAllowed() {
        assertThrows(IllegalArgumentException.class, () -> wahl.registerVote(kandidaten[2], kandidaten[2]));
        wahl.registerVote(wahl.getNonVoteKandidat(), wahl.getNonVoteKandidat());
    }

    @Test
    void processVoteCorrectVote() throws AmbiguousVoteException {
        wahl.processVote("pj");
        assertEquals(1, wahl.getVoteCount());
        assertEquals(2,wahl.getKandidaten()[1].getPunkte());
        assertEquals(1,wahl.getKandidaten()[1].getPlatz1());
        assertEquals(1,wahl.getKandidaten()[2].getPunkte());
        assertEquals(0,wahl.getKandidaten()[2].getPlatz1());
    }

    @Test
    void processVoteNoVote() throws AmbiguousVoteException {
        wahl.processVote("j-");
        assertEquals(1, wahl.getVoteCount());
        assertEquals(2,wahl.getKandidaten()[2].getPunkte());
        assertEquals(1,wahl.getKandidaten()[2].getPlatz1());
        assertEquals(1,wahl.getKandidaten()[0].getPunkte());
    }

    @Test
    void processVoteAmbiguousException() throws AmbiguousVoteException {
        assertThrows(AmbiguousVoteException.class, () -> wahl.processVote("kk"));
        wahl.processVote("--");
    }

    @Test
    void processVoteNonexistantCandidate() {
        assertThrows(IllegalArgumentException.class, () -> wahl.processVote("cj"));
    }

    @Test
    void processVoteInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> wahl.processVote("Josef Gradinger"));
    }

    @Test
    void processVoteDetailedValid(){
        wahl.processVoteDetailed("+1+2");
        assertEquals(2,wahl.getKandidaten()[1].getPunkte());
        assertEquals(1,wahl.getKandidaten()[1].getPlatz1());
        assertEquals(1,wahl.getKandidaten()[2].getPunkte());
        assertEquals(0,wahl.getKandidaten()[2].getPlatz1());

        wahl.processVoteDetailed("4+3");
        assertEquals(2,wahl.getKandidaten()[4].getPunkte());
        assertEquals(1,wahl.getKandidaten()[4].getPlatz1());
        assertEquals(1,wahl.getKandidaten()[3].getPunkte());
        assertEquals(0,wahl.getKandidaten()[3].getPlatz1());
    }

    @Test
    void processVoteDetailedNonexistantCandidate() {
        assertThrows(IllegalArgumentException.class, () -> wahl.processVoteDetailed("+1+6"));
    }
}