import TennisScoreboard.dao.PersistenceStorage;
import TennisScoreboard.dto.MatchScoreDTO;
import TennisScoreboard.model.PlayerEntity;
import TennisScoreboard.sevice.PrepareMatchScore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PrepareServiceTest {

    class TestDAOPersistence implements PersistenceStorage<PlayerEntity> {
        @Override
        public PlayerEntity get(Object name) {
            for (PlayerEntity playerEntity : playersTestStorage) {
                if (playerEntity.getName().equals(name)) {
                    return playerEntity;
                }
            }
            return null;
        }

        @Override
        public void put(PlayerEntity player) {
            playersTestStorage.add(player);
        }
    }

    private final String NAME1 = "testName1";
    private final String NAME2 = "testName2";


    private List<PlayerEntity> playersTestStorage;

    private TestDAOPersistence testDAOPlayer;
    private PrepareMatchScore prepareMatchScore;

    @BeforeEach
    void setUp() {
        playersTestStorage = new ArrayList<>();
        testDAOPlayer = new TestDAOPersistence();
        prepareMatchScore = new PrepareMatchScore(testDAOPlayer);
    }

    @Test
    public void newNamesPrepareTest() {
        MatchScoreDTO matchScore = prepareMatchScore.prepareMatchScore(NAME1, NAME2);

        assertEquals(NAME1, matchScore.getPlayerName1());
        assertEquals(NAME2, matchScore.getPlayerName2());

        assertEquals(NAME1, testDAOPlayer.get(NAME1).getName());
        assertEquals(NAME2, testDAOPlayer.get(NAME2).getName());
    }

    @Test
    public void oneNameAlreadyExistPrepareTest() {
        testDAOPlayer.put(new PlayerEntity(NAME1));

        MatchScoreDTO matchScore = prepareMatchScore.prepareMatchScore(NAME1, NAME2);

        assertEquals(NAME1, matchScore.getPlayerName1());
        assertEquals(NAME2, matchScore.getPlayerName2());

        assertEquals(NAME1, testDAOPlayer.get(NAME1).getName());
        assertEquals(NAME2, testDAOPlayer.get(NAME2).getName());
    }

    @Test
    public void bothNamesAlreadyExistPrepareTest() {
        testDAOPlayer.put(new PlayerEntity(NAME1));
        testDAOPlayer.put(new PlayerEntity(NAME2));

        MatchScoreDTO matchScore = prepareMatchScore.prepareMatchScore(NAME1, NAME2);

        assertEquals(NAME1, matchScore.getPlayerName1());
        assertEquals(NAME2, matchScore.getPlayerName2());

        assertEquals(NAME1, testDAOPlayer.get(NAME1).getName());
        assertEquals(NAME2, testDAOPlayer.get(NAME2).getName());
    }
}
