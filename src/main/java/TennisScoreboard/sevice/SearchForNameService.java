package TennisScoreboard.sevice;

import TennisScoreboard.entity.MatchEntity;
import TennisScoreboard.entity.SearchDTO;
import TennisScoreboard.model.MatchesStorage;

import java.util.List;



public class SearchForNameService {

    private final MatchesStorage matchStorage;

    public SearchForNameService(MatchesStorage matchStorage) {
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
