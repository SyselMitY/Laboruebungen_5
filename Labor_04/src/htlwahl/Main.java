package htlwahl;

import htlwahl.commands.CommandHandler;
import htlwahl.commands.DetailVoteCommandHandler;
import htlwahl.commands.EditCommandHandler;
import htlwahl.commands.RevertCommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.*;

public class Main {
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
        if (checkArgs(args)) {

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
    }

    private static void mainLoop(BufferedReader consoleReader) throws IOException {
        String readLine = null;
        Map<String, CommandHandler> commandHandlers = Map.of(
                "*", new EditCommandHandler(),
                "#", new RevertCommandHandler(),
                "+", new DetailVoteCommandHandler());
        Wahlprogramm wahlProgramm = new Wahlprogramm(kandidatenDaten, commandHandlers, logger);

        logger.log(Level.INFO, decimalFormat.format(wahlProgramm.getWahl().getVoteCount()) + " >");
        do {
            readLine = consoleReader.readLine();
            wahlProgramm.handleInput(readLine);
            Arrays.stream(logger.getHandlers()).forEach(Handler::flush);
            logger.log(Level.INFO, "-----------------------------------------------------------\n");
            logger.log(Level.INFO, decimalFormat.format(wahlProgramm.getWahl().getVoteCount()) + " >");
        } while (checkForExit(readLine));
    }

    static boolean checkForExit(String readLine) {
        return readLine != null && !readLine.equals("quit");
    }

    static boolean checkArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Keine Logdatei");
            System.out.println("Usage: java htlwahl.Main pathToLog");
            return false;
        }
        return true;
    }
}
