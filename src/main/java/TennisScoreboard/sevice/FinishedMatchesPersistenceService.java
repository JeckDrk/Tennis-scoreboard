package TennisScoreboard.sevice;

import TennisScoreboard.model.MatchEntity;
import TennisScoreboard.dto.MatchScoreDTO;
import TennisScoreboard.model.PlayerEntity;
import TennisScoreboard.dao.PersistenceStorage;

public class FinishedMatchesPersistenceService {

    private final PersistenceStorage<MatchEntity> matchStorage;
    private final PersistenceStorage<PlayerEntity> playerStorage;

    public FinishedMatchesPersistenceService(PersistenceStorage<MatchEntity> matchStorage, PersistenceStorage<PlayerEntity> playerStorage) {
        this.matchStorage = matchStorage;
        this.playerStorage = playerStorage;
    }

    public void saveMatch(MatchScoreDTO matchScoreDTO) {
        PlayerEntity player1 = playerStorage.get(matchScoreDTO.getPlayerName1());
        PlayerEntity player2 = playerStorage.get(matchScoreDTO.getPlayerName2());

        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setPlayer1(player1);
        matchEntity.setPlayer2(player2);
        matchEntity.setWinner(matchScoreDTO.getPlayerName1().equals(matchScoreDTO.getWinner()) ? player1 : player2);

        matchStorage.put(matchEntity);
    }
}
