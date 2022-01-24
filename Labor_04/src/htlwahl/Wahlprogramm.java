package htlwahl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.logging.*;

public class Wahlprogramm {
    private static final Kandidat[] kandidatenDaten = new Kandidat[5];
    private static final Logger logger = Logger.getLogger("htlwahl");
    private static final DecimalFormat decimalFormat = new DecimalFormat("000");


    static {
        kandidatenDaten[0] = new Kandidat("Dominik Hofmann");
        kandidatenDaten[1] = new Kandidat("Kilian Prager");
        kandidatenDaten[2] = new Kandidat("Niklas Hochstöger");
        kandidatenDaten[3] = new Kandidat("Paul Pfiel");
        kandidatenDaten[4] = new Kandidat("Raid Alarkhanov");
    }

    public static void main(String[] args) {
        checkArgs(args);

        FileHandler fileHandler = null;
        ConsoleHandler consoleHandler = null;
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            fileHandler = new FileHandler(args[0]);
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(new CustomConsoleFormatter());

            consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(new CustomConsoleFormatter());

            logger.addHandler(fileHandler);
            logger.addHandler(consoleHandler);
            logger.setUseParentHandlers(false);

            mainLoop(consoleReader);
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, ioe.getMessage());
        } finally {
            if (fileHandler != null) fileHandler.close();
        }

    }

    private static void mainLoop(BufferedReader consoleReader) throws IOException {
        String readLine = null;
        Wahl wahl = new Wahl(kandidatenDaten);

        logger.log(Level.INFO, decimalFormat.format(wahl.getVoteCount()) + " >");
        do {
            readLine = consoleReader.readLine();

            switch (getCommandType(readLine)) {
                case SIMPLE_VOTING_STRING -> voteSimpleString(readLine, wahl);
                case DETAILED_VOTING -> voteDetailed(readLine, wahl);
                case EDIT_VOTE -> editVote(readLine, wahl);
                case REVERT_VOTE -> revertVote(wahl);
                case EXIT -> logger.log(Level.INFO, "Exiting...");
                case UNKNOWN -> logger.log(Level.WARNING, "     Falsche Eingabe!");
            }

            logger.log(Level.INFO, "-----------------------------------------------------------\n");
            logger.log(Level.INFO, decimalFormat.format(wahl.getVoteCount()) + " >");
        } while (checkForExit(readLine));
    }

    private static InputType getCommandType(String readLine){
        if(readLine.equals("quit")) return InputType.EXIT;
        if(readLine.startsWith("#")) return InputType.REVERT_VOTE;
        if(readLine.startsWith("*")) return InputType.EDIT_VOTE;
        if(readLine.startsWith("+")) return InputType.DETAILED_VOTING;
        if(readLine.length() == 2) return InputType.SIMPLE_VOTING_STRING;
        return InputType.UNKNOWN;
    }

    private static void revertVote(Wahl wahl) {
        try {
            wahl.revertLastVote();
            printZwischenergebnis(wahl);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private static void editVote(String readLine, Wahl wahl) {
        try {
            wahl.revertLastVote();
            logger.log(Level.INFO, "Letzte Stimme wurde zurückgesetzt.\n");
            wahl.processVoteDetailed(readLine.substring(1));
            printZwischenergebnis(wahl);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private static void voteSimpleString(String readLine, Wahl wahl) {
        try {
            wahl.processVote(readLine);
            printZwischenergebnis(wahl);
        } catch (AmbiguousVoteException e) {
            logger.log(Level.INFO, "Mehrere Kandidaten mit gleichem Buchstaben");
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private static void voteDetailed(String readLine, Wahl wahl) {
        try {
            wahl.processVoteDetailed(readLine);
            printZwischenergebnis(wahl);
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private static boolean checkForExit(String readLine) {
        return readLine != null && !readLine.equals("quit");
    }

    private static void printZwischenergebnis(Wahl wahl) {
        Arrays.stream(wahl.getKandidaten())
                .forEach(kandidat -> System.out.println("    " + kandidat));
    }

    private static void checkArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Keine Logdatei");
            System.out.println("Usage: java htlwahl.Wahl pathToLog");
            System.exit(1);
        }
    }

}
