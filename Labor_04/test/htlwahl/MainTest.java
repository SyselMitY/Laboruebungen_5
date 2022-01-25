package htlwahl;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MainTest {

    @Test
    public void testCheckForExit() {
        assertFalse(Main.checkForExit("quit"));
        assertTrue(Main.checkForExit("q"));
        assertFalse(Main.checkForExit(null));
    }

    @Test
    public void testCheckArgs() {
        //Always 1 arg expected
        assertTrue(Main.checkArgs(new String[]{"input.txt"}));
        assertFalse(Main.checkArgs(new String[]{}));
        assertFalse(Main.checkArgs(new String[]{"test", "zweiter"}));
    }
}