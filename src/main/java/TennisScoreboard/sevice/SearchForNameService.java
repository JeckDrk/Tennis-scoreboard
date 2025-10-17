package TennisScoreboard.sevice;

import TennisScoreboard.dao.PaginatedSearchStorage;
import TennisScoreboard.model.MatchEntity;
import TennisScoreboard.dto.SearchDTO;

import java.util.List;



public class SearchForNameService {

    private final PaginatedSearchStorage<MatchEntity> matchStorage;

    public SearchForNameService(PaginatedSearchStorage<MatchEntity> matchStorage) {
        this.matchStorage = matchStorage;
    }

    public List<MatchEntity> getMatchesFromPageOfSearch(SearchDTO searchDTO) {
        return matchStorage.getPaginatedForSearch(searchDTO.getPage(), searchDTO.getSize(), searchDTO.getSearch());
    }


    public long getPagesCountOfSearch(SearchDTO searchDTO) {
        long entityCount = matchStorage.getEntityCountForSearch(searchDTO.getSearch());
        long pageCount = entityCount / searchDTO.getSize();

        return entityCount % searchDTO.getSize() == 0 ? pageCount : pageCount + 1;
    }
}
