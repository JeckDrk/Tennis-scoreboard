package TennisScoreboard.util;

import TennisScoreboard.exception.InputException;

import java.util.UUID;

public class Mapper {

    public static UUID mapUUID(String uuid) throws InputException {
        try {
            return java.util.UUID.fromString(uuid);
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new InputException("Not correct UUID!");
        }
    }

}
