package htlwahl.commands;

import htlwahl.Wahl;

public interface CommandHandler {
    void handle(Wahl wahl, String command);
}
