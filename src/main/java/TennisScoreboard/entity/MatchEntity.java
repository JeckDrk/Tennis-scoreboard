package TennisScoreboard.entity;


import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Matches")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "Player1", referencedColumnName = "id")
    private PlayerEntity player1;

    @ManyToOne
    @JoinColumn(name = "Player2", referencedColumnName = "id")
    private PlayerEntity player2;

    @ManyToOne
    @JoinColumn(name = "Winner", referencedColumnName = "id")
    private PlayerEntity winner;

    public MatchEntity(PlayerEntity player1, PlayerEntity player2, PlayerEntity winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }
}
