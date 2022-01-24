package htlwahl;

import java.text.DecimalFormat;
import java.util.Objects;

class Kandidat {

    private final String name;
    private int punkte;
    private int platz1;

    public Kandidat(String name) {
        this.name = name;
    }


    public void addPoints(int p) {
        this.punkte += p;
        if (p == 2)
            this.platz1++;
    }

    public String toString() {
        DecimalFormat dc = new DecimalFormat("###0");
        return dc.format(punkte) + " / " + dc.format(platz1) + "   " + this.name;
    }

    public String getName() {
        return name;
    }

    public int getPunkte() {
        return punkte;
    }

    public int getPlatz1() {
        return platz1;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    public void setPlatz1(int platz1) {
        this.platz1 = platz1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kandidat kandidat = (Kandidat) o;
        return punkte == kandidat.punkte && platz1 == kandidat.platz1 && Objects.equals(name, kandidat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, punkte, platz1);
    }
}
