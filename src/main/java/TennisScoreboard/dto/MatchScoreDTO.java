package TennisScoreboard.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class MatchScoreDTO {

    private int point1 = 0;
    private int game1 = 0;
    private int set1 = 0;

    private int point2 = 0;
    private int game2 = 0;
    private int set2 = 0;

    private boolean ad1 = false;
    private boolean ad2 = false;

    private boolean finished = false;

    private String playerName1;
    private String playerName2;
    private String winner;

    public MatchScoreDTO(String playerName1, String playerName2) {
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
    }

    public MatchScoreDTO(int point1, int game1, int set1, int point2, int game2, int set2, String playerName1, String playerName2) {
        this.point1 = point1;
        this.game1 = game1;
        this.set1 = set1;
        this.point2 = point2;
        this.game2 = game2;
        this.set2 = set2;
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
    }
}
