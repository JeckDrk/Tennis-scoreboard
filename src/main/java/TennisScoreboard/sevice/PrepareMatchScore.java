package TennisScoreboard.sevice;

import TennisScoreboard.entity.MatchScoreDTO;
import TennisScoreboard.entity.PlayerEntity;
import TennisScoreboard.exception.UniqueException;
import TennisScoreboard.model.PlayerStorage;

public class PrepareMatchScore {

    private final PlayerStorage playerStorage;

    public PrepareMatchScore(PlayerStorage matchesStorage) {
        this.playerStorage = matchesStorage;
    }

    public MatchScoreDTO prepareMatchScore(String name1, String name2) {
        try {
            playerStorage.put(new PlayerEntity(name1));
        } catch (UniqueException ignored) {}
        try {
            playerStorage.put(new PlayerEntity(name2));
        } catch (UniqueException ignored) {}

        return new MatchScoreDTO(name1, name2);
    }

}
