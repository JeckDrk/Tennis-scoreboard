package TennisScoreboard.sevice;

import TennisScoreboard.entity.MatchScoreDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OngoingMatchesService {
    private final Map<UUID, MatchScoreDTO> MATCH_SCORES = new HashMap<>();

    public MatchScoreDTO getMatch(UUID id) {
        return MATCH_SCORES.get(id);
    }

    public UUID addMatch(MatchScoreDTO matchScoreDTO) {

        UUID id = UUID.randomUUID();

        MATCH_SCORES.put(id, matchScoreDTO);

        return id;
    }
}
