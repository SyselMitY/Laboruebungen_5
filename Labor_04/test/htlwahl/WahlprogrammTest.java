package htlwahl;

import htlwahl.commands.CommandHandler;
import htlwahl.commands.DetailVoteCommandHandler;
import htlwahl.commands.EditCommandHandler;
import htlwahl.commands.RevertCommandHandler;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;
import java.util.logging.Logger;

import static org.testng.Assert.*;

public class WahlprogrammTest {

    private Wahl wahl;
    private Wahlprogramm wahlprogramm;
    private Kandidat[] kandidaten;
    private Map<String, CommandHandler> commandHandlers;

    @BeforeMethod
    void setupStuff() {
        Logger logger = Logger.getLogger("htlwahl");
        kandidaten = new Kandidat[]{
                new Kandidat("Peter Sysel"),
                new Kandidat("Josef Gradinger"),
                new Kandidat("Klaus Kästner"),
                new Kandidat("Karl Kästner")
        };
        commandHandlers = Map.of(
                "*",new EditCommandHandler(),
                "#",new RevertCommandHandler(),
                "+",new DetailVoteCommandHandler());
        wahlprogramm = new Wahlprogramm(kandidaten, commandHandlers,logger);
    }

    @Test
    public void testWahlNotNull() {
        assertNotNull(wahlprogramm.getWahl());
        assertThrows(NullPointerException.class,
                () -> new Wahlprogramm(null, null, null));
    }

    @Test
    public void testVoteCounted() {
        wahlprogramm.handleInput("pj");
        Wahl wahl = wahlprogramm.getWahl();
        assertEquals(wahl.getKandidaten()[1].getPunkte(), 2);
        assertEquals(wahl.getKandidaten()[2].getPunkte(), 1);
        assertEquals(wahl.getKandidaten()[1].getPlatz1(), 1);
    }

    @Test
    public void invalidVoteNotCounted() {
        wahlprogramm.handleInput("pp");
        Wahl wahl = wahlprogramm.getWahl();
        assertEquals(wahl.getKandidaten()[0].getPunkte(), 0);
        assertEquals(wahl.getKandidaten()[1].getPunkte(), 0);
    }

    @Test
    public void quittingNoException() {
        wahlprogramm.handleInput("quit");
    }

    @Test
    public void invalidCommandNoEffect() {
        wahlprogramm.handleInput("invalid");
        Wahl wahl = wahlprogramm.getWahl();
        assertEquals(wahl.getKandidaten()[0].getPunkte(), 0);
        assertEquals(wahl.getKandidaten()[1].getPunkte(), 0);
    }

    @Test
    public void detailVoteHandlerCalledAndWorking() {
        wahlprogramm.handleInput("+4+3");
        Wahl wahl = wahlprogramm.getWahl();
        assertEquals(wahl.getKandidaten()[4].getPunkte(), 2);
        assertEquals(wahl.getKandidaten()[3].getPunkte(), 1);
        assertEquals(wahl.getKandidaten()[4].getPlatz1(), 1);
    }

    @Test
    public void revertHandlerCalledAndWorking() {
        wahlprogramm.handleInput("pj");
        wahlprogramm.handleInput("#");
        Wahl wahl = wahlprogramm.getWahl();
        assertEquals(wahl.getKandidaten()[1].getPunkte(), 0);
        assertEquals(wahl.getKandidaten()[2].getPunkte(), 0);
    }

    @Test
    public void editHandlerCalledAndWorking() {
        wahlprogramm.handleInput("pj");
        wahlprogramm.handleInput("*2+1");
        Wahl wahl = wahlprogramm.getWahl();
        assertEquals(wahl.getKandidaten()[1].getPunkte(), 1);
        assertEquals(wahl.getKandidaten()[2].getPunkte(), 2);
        assertEquals(wahl.getKandidaten()[1].getPlatz1(), 0);
        assertEquals(wahl.getKandidaten()[2].getPlatz1(), 1);
    }
}