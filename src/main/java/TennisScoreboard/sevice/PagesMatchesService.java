package TennisScoreboard.sevice;

import TennisScoreboard.dao.PaginatedSearchStorage;
import TennisScoreboard.model.MatchEntity;
import TennisScoreboard.dto.SearchDTO;

import java.util.List;

public class PagesMatchesService {

    private final PaginatedSearchStorage<MatchEntity> matchStorage;

    public PagesMatchesService(PaginatedSearchStorage<MatchEntity> matchStorage) {
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
