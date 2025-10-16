package TennisScoreboard.sevice;

import TennisScoreboard.entity.MatchEntity;
import TennisScoreboard.entity.MatchScoreDTO;
import TennisScoreboard.entity.PlayerEntity;
import TennisScoreboard.model.MatchesStorage;
import TennisScoreboard.model.PlayerStorage;

public class FinishedMatchesPersistenceService {

    private final MatchesStorage matchStorage;
    private final PlayerStorage playerStorage;

    public FinishedMatchesPersistenceService(MatchesStorage matchStorage, PlayerStorage playerStorage) {
        this.matchStorage = matchStorage;
        this.playerStorage = playerStorage;
    }

    public void persist(MatchScoreDTO matchScoreDTO) {
        PlayerEntity player1 = playerStorage.get(matchScoreDTO.getPlayerName1());
        PlayerEntity player2 = playerStorage.get(matchScoreDTO.getPlayerName2());

        MatchEntity matchEntity = new MatchEntity();
        matchEntity.setPlayer1(player1);
        matchEntity.setPlayer2(player2);
        matchEntity.setWinner(matchScoreDTO.getPlayerName1().equals(matchScoreDTO.getWinner()) ? player1 : player2);

        matchStorage.put(matchEntity);
    }
}
