package TennisScoreboard.sevice;

import TennisScoreboard.dto.MatchScoreDTO;

import static java.lang.Math.abs;

public class MatchScoreCalculationService {

    public MatchScoreCalculationService(MatchScoreDTO MATCH_SCORE) {
        this.MATCH_SCORE = MATCH_SCORE;
    }

    private static final int ZERO_POINT = 0;
    private static final int FIRST_POINT = 15;
    private static final int SECOND_POINT = 30;
    private static final int THIRD_POINT = 40;

    private static final int MAX_POINT_SCORE = 7;
    private static final int MAX_GAME = 6;
    private static final int MAX_SET = 2;

    private static final int DIFFERENCE = 2;

    private final MatchScoreDTO MATCH_SCORE;

    private int player;

    public void newPointForPlayer(int player) {
        if (MATCH_SCORE.isFinished()) {
            return;
        } else if (isFinished()) {
            finishMatch();
        }

        this.player = player;

        if (isTaiBreak()) {
            addPointTaiBreak();

            if (isFinishedTaiBreak()) {
                dropPoint();
            }
        } else if (isAdvantage()) {
            doAdvantage();
        } else if (isDeuce()) {
            doDeuce();
        } else {
            addPoint();
        }

        if (isAddGame()) {
            dropPoint();
            addGame();

            if (isAddSet()) {
                dropGame();
                addSet();

                if (isFinished()) {
                    finishMatch();
                }
            }
        }
    }

    private boolean isTaiBreak() {
        return abs(MATCH_SCORE.getGame1() - MATCH_SCORE.getGame2()) < DIFFERENCE &&
                MATCH_SCORE.getGame1() >= MAX_GAME && MATCH_SCORE.getGame2() >= MAX_GAME;
    }

    private void addPointTaiBreak() {
        if (player == 1) {
            MATCH_SCORE.setPoint1(MATCH_SCORE.getPoint1() + 1);
        } else {
            MATCH_SCORE.setPoint2(MATCH_SCORE.getPoint2() + 1);
        }
    }

    private boolean isFinishedTaiBreak() {
        return abs(MATCH_SCORE.getPoint1() - MATCH_SCORE.getPoint2()) >= DIFFERENCE &&
                (MATCH_SCORE.getPoint1() >= MAX_POINT_SCORE || MATCH_SCORE.getPoint2() >= MAX_POINT_SCORE);
    }

    private boolean isDeuce() {
        return (!MATCH_SCORE.isAd1() && !MATCH_SCORE.isAd2() &&
                (MATCH_SCORE.getPoint1() == THIRD_POINT && MATCH_SCORE.getPoint2() == THIRD_POINT));
    }

    private void doDeuce() {
        if (player == 1) {
            MATCH_SCORE.setAd1(true);
        } else {
            MATCH_SCORE.setAd2(true);
        }
    }

    private boolean isAdvantage() {
        return MATCH_SCORE.isAd1() || MATCH_SCORE.isAd2();
    }

    private void doAdvantage() {
        if ((MATCH_SCORE.isAd1() && player == 1) ||
                (MATCH_SCORE.isAd2() && player == 2)) {
            MATCH_SCORE.setPoint1(0);
            MATCH_SCORE.setPoint2(0);
        } else {
            MATCH_SCORE.setPoint1(40);
            MATCH_SCORE.setPoint2(40);
        }
        MATCH_SCORE.setAd1(false);
        MATCH_SCORE.setAd2(false);
    }

    private void addPoint() {
        if (player == 1) {
            MATCH_SCORE.setPoint1(setNewPoint(MATCH_SCORE.getPoint1()));
        } else {
            MATCH_SCORE.setPoint2(setNewPoint(MATCH_SCORE.getPoint2()));
        }
    }

    private int setNewPoint(int point) {
        return switch (point) {
            case ZERO_POINT -> FIRST_POINT;
            case FIRST_POINT -> SECOND_POINT;
            case SECOND_POINT -> THIRD_POINT;
            default -> ZERO_POINT;
        };
    }

    private void dropPoint() {
        MATCH_SCORE.setPoint1(ZERO_POINT);
        MATCH_SCORE.setPoint2(ZERO_POINT);
    }

    private boolean isAddGame() {
        if (player == 1) {
            return MATCH_SCORE.getPoint1() == ZERO_POINT;
        } else {
            return MATCH_SCORE.getPoint2() == ZERO_POINT;
        }
    }

    private void addGame() {
        if (player == 1) {
            MATCH_SCORE.setGame1(MATCH_SCORE.getGame1() + 1);
        } else {
            MATCH_SCORE.setGame2(MATCH_SCORE.getGame2() + 1);
        }
    }

    private void dropGame() {
        MATCH_SCORE.setGame1(0);
        MATCH_SCORE.setGame2(0);
    }

    private boolean isAddSet() {
        return ((MATCH_SCORE.getGame1() >= MAX_GAME || MATCH_SCORE.getGame2() >= MAX_GAME) &&
                abs(MATCH_SCORE.getGame1() - MATCH_SCORE.getGame2()) >= DIFFERENCE);
    }

    private void addSet() {
        if (player == 1) {
            MATCH_SCORE.setSet1(MATCH_SCORE.getSet1() + 1);
        } else {
            MATCH_SCORE.setSet2(MATCH_SCORE.getSet2() + 1);
        }
    }

    private boolean isFinished() {
        return (MATCH_SCORE.getSet1() >= MAX_SET) || (MATCH_SCORE.getSet2() >= MAX_SET);
    }

    private void finishMatch() {
        if (MATCH_SCORE.getSet1() == 2) {
            MATCH_SCORE.setWinner(MATCH_SCORE.getPlayerName1());
        } else {
            MATCH_SCORE.setWinner(MATCH_SCORE.getPlayerName2());
        }
        MATCH_SCORE.setFinished(true);
    }
}
