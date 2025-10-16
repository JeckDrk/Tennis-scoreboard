package TennisScoreboard.model;

import TennisScoreboard.entity.MatchEntity;

import java.util.List;

public interface MatchesStorage {
    public abstract void put(MatchEntity value);

    public abstract List<MatchEntity> getPaginated(int page, int size);

    public abstract long getEntityCount();

    public abstract List<MatchEntity> getPaginatedForSearch(int page, int size, String search);

    public abstract long getEntityCountForSearch(String search);
}
