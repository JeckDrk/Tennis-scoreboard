import TennisScoreboard.dto.MatchScoreDTO;
import TennisScoreboard.sevice.MatchScoreCalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculationServiceTest {

    private final int PLAYER1 = 1;
    private final int PLAYER2 = 2;

    private final int ZERO_POINT = 0;
    private final int FIRST_POINT = 15;
    private final int SECOND_POINT = 30;
    private final int THIRD_POINT = 40;

    private MatchScoreDTO matchScore;
    private MatchScoreCalculationService matchScoreCalculationService;

    @BeforeEach
    void setUp() {
        matchScore = new MatchScoreDTO(0, 0, 0, 0, 0, 0, "player1", "player2");
        matchScoreCalculationService = new MatchScoreCalculationService(matchScore);
    }

    @Nested
    class RegularGamesTest {

        @Test
        public void firstPointForPlayer1Test() {
            matchScoreCalculationService.newPointForPlayer(PLAYER1);
            assertEquals(FIRST_POINT, matchScore.getPoint1());

        }

        @Test
        public void firstPointForPlayer2Test() {
            matchScoreCalculationService.newPointForPlayer(PLAYER2);
            assertEquals(FIRST_POINT, matchScore.getPoint2());
        }

        @Test
        public void secondPointForPlayer1Test() {
            matchScore.setPoint1(FIRST_POINT);
            matchScoreCalculationService.newPointForPlayer(PLAYER1);
            assertEquals(SECOND_POINT, matchScore.getPoint1());
        }

        @Test
        public void secondPointForPlayer2Test() {
            matchScore.setPoint2(FIRST_POINT);
            matchScoreCalculationService.newPointForPlayer(PLAYER2);
            assertEquals(SECOND_POINT, matchScore.getPoint2());
        }

        @Test
        public void thirdPointForPlayer1Test() {
            matchScore.setPoint1(SECOND_POINT);
            matchScoreCalculationService.newPointForPlayer(PLAYER1);
            assertEquals(THIRD_POINT, matchScore.getPoint1());

        }

        @Test
        public void thirdPointForPlayer2Test() {
            matchScore.setPoint2(SECOND_POINT);
            matchScoreCalculationService.newPointForPlayer(PLAYER2);
            assertEquals(THIRD_POINT, matchScore.getPoint2());
        }

        @Test
        public void newGameTest() {
            matchScore.setPoint1(THIRD_POINT);
            matchScoreCalculationService.newPointForPlayer(PLAYER1);
            assertEquals(ZERO_POINT, matchScore.getPoint1());

            matchScore.setPoint2(THIRD_POINT);
            matchScoreCalculationService.newPointForPlayer(PLAYER2);
            assertEquals(ZERO_POINT, matchScore.getPoint2());

            assertEquals(1, matchScore.getGame1());
        }

        @Test
        public void winGameFrom40_30Test() {
            matchScore.setPoint1(THIRD_POINT);
            matchScore.setPoint2(SECOND_POINT);
            matchScoreCalculationService.newPointForPlayer(PLAYER1);
            assertEquals(1, matchScore.getGame1());
        }

        @Test
        public void newSetTest() {
            matchScore.setPoint1(THIRD_POINT);
            matchScore.setGame1(5);
            matchScoreCalculationService.newPointForPlayer(PLAYER1);
            assertEquals(1, matchScore.getSet1());


            matchScore.setPoint2(THIRD_POINT);
            matchScore.setGame2(5);
            matchScoreCalculationService.newPointForPlayer(PLAYER2);
            assertEquals(1, matchScore.getSet2());
        }

        @Test
        public void player1WinTest() {
            matchScore.setSet1(1);
            matchScore.setGame1(5);
            matchScore.setPoint1(THIRD_POINT);
            matchScore.setSet2(1);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);

            assertTrue(matchScore.isFinished());
            assertEquals(matchScore.getPlayerName1(), matchScore.getWinner());
        }

        @Test
        public void player2WinTest() {
            matchScore.setSet1(1);
            matchScore.setSet2(1);
            matchScore.setGame2(5);
            matchScore.setPoint2(THIRD_POINT);

            matchScoreCalculationService.newPointForPlayer(PLAYER2);

            assertTrue(matchScore.isFinished());
            assertEquals(matchScore.getPlayerName2(), matchScore.getWinner());
        }

        @Test
        public void absolutePlayer1WinTest() {
            matchScore.setSet1(1);
            matchScore.setGame1(5);
            matchScore.setPoint1(THIRD_POINT);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);

            assertTrue(matchScore.isFinished());
            assertEquals(matchScore.getPlayerName1(), matchScore.getWinner());
        }

        @Test
        public void absolutePlayer2WinTest() {
            matchScore.setSet2(1);
            matchScore.setGame2(5);
            matchScore.setPoint2(THIRD_POINT);

            matchScoreCalculationService.newPointForPlayer(PLAYER2);

            assertTrue(matchScore.isFinished());
            assertEquals(matchScore.getPlayerName2(), matchScore.getWinner());
        }
    }

    @Nested
    class TieBreakTests {
        @Test
        public void tieBreakTest() {
            matchScore.setGame1(6);
            matchScore.setGame2(6);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);

            assertEquals(1, matchScore.getPoint1());
        }

        @Test
        public void notTieBreakTest() {
            matchScore.setGame1(6);
            matchScore.setGame2(5);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);

            assertEquals(FIRST_POINT, matchScore.getPoint1());
        }

        @Test
        public void tieBreakDeuceTest() {
            int testScore = 30;
            matchScore.setGame1(6);
            matchScore.setGame2(6);

            matchScore.setPoint1(testScore);
            matchScore.setPoint2(testScore);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);

            assertEquals(testScore + 1, matchScore.getPoint1());
        }

        @Test
        public void tieBreakWin8_6Test() {
            matchScore.setGame1(7);
            matchScore.setGame2(6);

            matchScore.setPoint1(5);
            matchScore.setPoint2(5);

            matchScoreCalculationService.newPointForPlayer(PLAYER1); // 6-5

            matchScoreCalculationService.newPointForPlayer(PLAYER1); // 7-5

            assertEquals(1, matchScore.getSet1());
            assertEquals(0, matchScore.getSet2());
        }
    }

    @Nested
    class AdvantageMatchTests {

        @Test
        public void advantageInMatchTest() {
            matchScore.setPoint1(THIRD_POINT);
            matchScore.setPoint2(THIRD_POINT);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);

            assertTrue(matchScore.isAd1());
            assertFalse(matchScore.isAd2());
        }

        @Test
        public void dropAdvantagePointTest() {
            matchScore.setPoint1(THIRD_POINT);
            matchScore.setPoint2(THIRD_POINT);
            matchScore.setAd1(true);

            matchScoreCalculationService.newPointForPlayer(PLAYER2);

            assertFalse(matchScore.isAd1());
        }

        @Test
        public void advantageAfterDeuceTest() {
            matchScore.setPoint1(THIRD_POINT);
            matchScore.setPoint2(THIRD_POINT);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);
            assertTrue(matchScore.isAd1());

            matchScoreCalculationService.newPointForPlayer(PLAYER2);
            assertFalse(matchScore.isAd1());
            assertFalse(matchScore.isAd2());
        }

        @Test
        public void winSetGame6_5AfterAdvantageTest() {
            matchScore.setGame1(6);
            matchScore.setGame2(5);

            matchScore.setPoint1(THIRD_POINT);
            matchScore.setPoint2(THIRD_POINT);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);
            matchScoreCalculationService.newPointForPlayer(PLAYER1);

            assertEquals(1, matchScore.getSet1());
        }

        @Test
        public void gameAfterAdvantageTest() {
            matchScore.setPoint1(THIRD_POINT);
            matchScore.setPoint2(THIRD_POINT);
            matchScore.setAd1(true);

            matchScoreCalculationService.newPointForPlayer(PLAYER1);

            assertEquals(1, matchScore.getGame1());
            assertEquals(ZERO_POINT, matchScore.getPoint1());
            assertEquals(ZERO_POINT, matchScore.getPoint2());
        }
    }
}
