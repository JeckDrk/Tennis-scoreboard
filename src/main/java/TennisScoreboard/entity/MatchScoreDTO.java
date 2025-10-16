package TennisScoreboard.entity;

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
}
