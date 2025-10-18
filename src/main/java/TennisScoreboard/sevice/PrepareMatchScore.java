package TennisScoreboard.sevice;

import TennisScoreboard.dto.MatchScoreDTO;
import TennisScoreboard.model.PlayerEntity;
import TennisScoreboard.exception.UniqueException;
import TennisScoreboard.dao.PersistenceStorage;

public class PrepareMatchScore {

    private final PersistenceStorage<PlayerEntity> persistenceStorage;

    public PrepareMatchScore(PersistenceStorage<PlayerEntity> playerStorage) {
        this.persistenceStorage = playerStorage;
    }

    public MatchScoreDTO prepareMatchScore(String name1, String name2) {
        try {
            persistenceStorage.put(new PlayerEntity(name1));
        } catch (UniqueException ignored) {
            // Сохранилось - хорошо, нет - да и всё равно
        }
        try {
            persistenceStorage.put(new PlayerEntity(name2));
        } catch (UniqueException ignored) {
            // Сохранилось - хорошо, нет - да и всё равно
        }
        return new MatchScoreDTO(name1, name2);
    }

}
