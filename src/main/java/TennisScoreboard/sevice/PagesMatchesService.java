package TennisScoreboard.sevice;

import TennisScoreboard.entity.MatchEntity;
import TennisScoreboard.entity.SearchDTO;
import TennisScoreboard.model.MatchesStorage;

import java.util.List;

public class PagesMatchesService {

    private final MatchesStorage matchStorage;

    public PagesMatchesService(MatchesStorage matchStorage) {
        this.matchStorage = matchStorage;
    }

    public List<MatchEntity> getMatchesFromPage(SearchDTO searchDTO) {
        return matchStorage.getPaginated(searchDTO.getPage(), searchDTO.getSize());
    }

    public long getPagesCount(SearchDTO searchDTO) {
        long entityCount = matchStorage.getEntityCount();
        long pageCount = entityCount / searchDTO.getSize();

        return entityCount % searchDTO.getSize() == 0 ? pageCount : pageCount + 1;
    }
}
