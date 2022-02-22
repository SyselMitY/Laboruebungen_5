package htlwahl.commands;

import htlwahl.Wahl;

public class EditCommandHandler implements CommandHandler {
    @Override
    public void handle(Wahl wahl, String command) {
        wahl.revertLastVote();
        wahl.processVoteDetailed(command.substring(1));
    }
}
