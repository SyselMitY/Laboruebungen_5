package htlwahl.commands;

import htlwahl.Wahl;

import java.util.logging.Level;

public class RevertCommandHandler implements CommandHandler {

    @Override
    public void handle(Wahl wahl, String command) {
        wahl.revertLastVote();
    }
}
