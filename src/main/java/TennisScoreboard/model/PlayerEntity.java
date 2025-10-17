package TennisScoreboard.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Players")
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name = "Name", unique = true)
    private String name;

    @OneToMany(mappedBy = "player1")
    private List<MatchEntity> matches1;

    @OneToMany(mappedBy = "player2")
    private List<MatchEntity> matches2;

    @OneToMany(mappedBy = "winner")
    private List<MatchEntity> winnerMatches;

    public PlayerEntity(String name) {
        this.name = name;
    }
}
