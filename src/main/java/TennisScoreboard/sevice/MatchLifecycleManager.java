package TennisScoreboard.sevice;

import TennisScoreboard.dto.MatchScoreDTO;

import java.util.UUID;

public class MatchLifecycleManager {
    private final OngoingMatchesService ONGOING_MATCH_SERVICE;
    private final FinishedMatchesPersistenceService FINISHED_MATCHES_PERSISTENCE;


    public MatchLifecycleManager(OngoingMatchesService ongoingMatchesService, FinishedMatchesPersistenceService finishedMatchesPersistenceService) {
        this.ONGOING_MATCH_SERVICE = ongoingMatchesService;
        this.FINISHED_MATCHES_PERSISTENCE = finishedMatchesPersistenceService;
    }

    public UUID createNewMatch(MatchScoreDTO matchScoreDTO) {
        return ONGOING_MATCH_SERVICE.addMatch(matchScoreDTO);
    }

    public MatchScoreDTO getMatch(UUID id) {
        return ONGOING_MATCH_SERVICE.getMatch(id);
    }

    public MatchScoreDTO updateMatchScore(UUID uuid, int winner) {
        MatchScoreDTO match = getMatch(uuid);

        new MatchScoreCalculationService(match).newPointForPlayer(winner);

        if (match.isFinished()) {
            FINISHED_MATCHES_PERSISTENCE.saveMatch(match);
            ONGOING_MATCH_SERVICE.removeMatch(uuid);
        }

        return match;
    }

    public boolean isMatchFinished(UUID uuid) {
        MatchScoreDTO match = getMatch(uuid);
        return match == null || match.isFinished();
    }
}
