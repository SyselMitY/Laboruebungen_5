package htlwahl;

import htlwahl.commands.CommandHandler;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.*;

import static htlwahl.InputType.*;

public class Wahlprogramm {

    private final Wahl wahl;
    private final Logger logger;
    private final Map<String, CommandHandler> commandHandlers;

    public Wahlprogramm(Kandidat[] kandidaten, Map<String, CommandHandler> commandHandlers, Logger logger) {
        this.commandHandlers = Objects.requireNonNull(commandHandlers);
        this.wahl = new Wahl(kandidaten);
        this.logger = logger;
    }

    public Wahl getWahl() {
        return wahl;
    }

    public void handleInput(String readLine) {
        try {
            switch (getCommandType(readLine)) {
                case SIMPLE_VOTING_STRING -> voteSimpleString(readLine, wahl);
                case EXTERNAL -> getCommandHandler(readLine).orElseThrow().handle(wahl,readLine);
                case EXIT -> logger.log(Level.INFO, "Exiting...");
                case UNKNOWN -> logger.log(Level.WARNING, "Falsche Eingabe!\n");
            }
        } catch (IllegalArgumentException | IllegalStateException | AmbiguousVoteException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        printZwischenergebnis(wahl);
    }

    private InputType getCommandType(String readLine) {
        InputType commandType = UNKNOWN;
        if (readLine.equals("quit"))
            commandType = EXIT;
        else if (getCommandHandler(readLine).isPresent())
            commandType = EXTERNAL;
        else if (validSimpleVotingString(readLine))
            commandType = SIMPLE_VOTING_STRING;
        return commandType;
    }

    private boolean validSimpleVotingString(String readLine) {
        return readLine.length() == 2;
    }

    private Optional<CommandHandler> getCommandHandler(String readLine) {
        return commandHandlers.entrySet()
                .stream()
                .filter(entry -> readLine.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();

    }

    private void voteSimpleString(String readLine, Wahl wahl) throws AmbiguousVoteException {
            wahl.processVote(readLine);
    }

    private void printZwischenergebnis(Wahl wahl) {
        Arrays.stream(wahl.getKandidaten())
                .forEach(kandidat -> System.out.println("    " + kandidat));
    }
}
