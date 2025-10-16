package TennisScoreboard.sevice;

import TennisScoreboard.entity.MatchScoreDTO;

import static java.lang.Math.abs;

public class MatchScoreCalculationService {

    public MatchScoreCalculationService(MatchScoreDTO matchScoreDTO, int player) {
        this.matchScoreDTO = matchScoreDTO;
        this.player = player;
    }

    private static final int ZERO_POINT = 0;
    private static final int FIRST_POINT = 15;
    private static final int SECOND_POINT = 30;
    private static final int THIRD_POINT = 40;

    private static final int MAX_POINT_SCORE = 7;
    private static final int MAX_GAME = 6;
    private static final int MAX_SET = 2;

    private static final int DIFFERENCE = 2;

    private final MatchScoreDTO matchScoreDTO;
    private final int player;

    public void calculate() {
        if (isTaiBreak()) {
            addPointTaiBreak();

            if(isFinishedTaiBreak()){
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

                if (isFinished()){
                    if (player == 1){
                        matchScoreDTO.setWinner(matchScoreDTO.getPlayerName1());
                    } else {
                        matchScoreDTO.setWinner(matchScoreDTO.getPlayerName2());
                    }
                    matchScoreDTO.setFinished(true);
                }
            }
        }
    }

    private boolean isTaiBreak() {
        return abs(matchScoreDTO.getGame1() - matchScoreDTO.getGame2()) < DIFFERENCE &&
                matchScoreDTO.getGame1() >= MAX_GAME && matchScoreDTO.getGame2() >= MAX_GAME;
    }

    private void addPointTaiBreak() {
        if (player == 1) {
            matchScoreDTO.setPoint1(matchScoreDTO.getPoint1() + 1);
        } else {
            matchScoreDTO.setPoint2(matchScoreDTO.getPoint2() + 1);
        }
    }

    private boolean isFinishedTaiBreak() {
        return abs(matchScoreDTO.getPoint1() - matchScoreDTO.getPoint2()) >= DIFFERENCE &&
                (matchScoreDTO.getPoint1() >= MAX_POINT_SCORE || matchScoreDTO.getPoint2() >= MAX_POINT_SCORE);
    }

    private boolean isDeuce() {
        return (!matchScoreDTO.isAd1() && !matchScoreDTO.isAd2() &&
                (matchScoreDTO.getPoint1() == THIRD_POINT && matchScoreDTO.getPoint2() == THIRD_POINT));
    }

    private void doDeuce() {
        if (player == 1) {
            matchScoreDTO.setAd1(true);
        } else {
            matchScoreDTO.setAd2(true);
        }
    }

    private boolean isAdvantage() {
        return matchScoreDTO.isAd1() || matchScoreDTO.isAd2();
    }

    private void doAdvantage() {
        if ((matchScoreDTO.isAd1() && player == 1) ||
                (matchScoreDTO.isAd2() && player == 2)) {
            matchScoreDTO.setPoint1(0);
            matchScoreDTO.setPoint2(0);
        } else {
            matchScoreDTO.setPoint1(40);
            matchScoreDTO.setPoint2(40);
        }
        matchScoreDTO.setAd1(false);
        matchScoreDTO.setAd2(false);
    }

    private void addPoint() {
        if (player == 1) {
            matchScoreDTO.setPoint1(newPoint(matchScoreDTO.getPoint1()));
        } else {
            matchScoreDTO.setPoint2(newPoint(matchScoreDTO.getPoint2()));
        }
    }

    private int newPoint(int point) {
        return switch (point) {
            case ZERO_POINT -> FIRST_POINT;
            case FIRST_POINT -> SECOND_POINT;
            case SECOND_POINT -> THIRD_POINT;
            default -> ZERO_POINT;
        };
    }

    private void dropPoint() {
        matchScoreDTO.setPoint1(ZERO_POINT);
        matchScoreDTO.setPoint2(ZERO_POINT);
    }

    private boolean isAddGame() {
        if (player == 1) {
            return matchScoreDTO.getPoint1() == ZERO_POINT;
        } else {
            return matchScoreDTO.getPoint2() == ZERO_POINT;
        }
    }

    private void addGame() {
        if (player == 1) {
            matchScoreDTO.setGame1(matchScoreDTO.getGame1() + 1);
        } else {
            matchScoreDTO.setGame2(matchScoreDTO.getGame2() + 1);
        }
    }

    private void dropGame() {
        matchScoreDTO.setGame1(0);
        matchScoreDTO.setGame2(0);
    }

    private boolean isAddSet() {
        return ((matchScoreDTO.getGame1() >= MAX_GAME || matchScoreDTO.getGame2() >= MAX_GAME) &&
                abs(matchScoreDTO.getGame1() - matchScoreDTO.getGame2()) >= DIFFERENCE);
    }

    private void addSet() {
        if (player == 1) {
            matchScoreDTO.setSet1(matchScoreDTO.getSet1() + 1);
        } else {
            matchScoreDTO.setSet2(matchScoreDTO.getSet2() + 1);
        }
    }

    private boolean isFinished() {
        return (matchScoreDTO.getSet1() >= MAX_SET) || (matchScoreDTO.getSet2() >= MAX_SET);
    }
}
