package htlwahl.commands;

import htlwahl.Wahl;

public class DetailVoteCommandHandler implements CommandHandler {
    @Override
    public void handle(Wahl wahl, String command) {
        wahl.processVoteDetailed(command);
    }
}
