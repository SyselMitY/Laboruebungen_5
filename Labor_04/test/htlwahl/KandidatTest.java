package htlwahl;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class KandidatTest {

    @Test
    public void testEqualsWorking() {
        Kandidat k1 = new Kandidat("Sossi Sossnberger");
        Kandidat k2 = new Kandidat("Sossi Sossnberger");
        assertEquals(k1, k2);
        assertEquals(k1.hashCode(), k2.hashCode());

        Kandidat k3 = new Kandidat("Kristian Kranalunden");
    }

}