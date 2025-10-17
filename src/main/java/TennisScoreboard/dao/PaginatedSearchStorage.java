package TennisScoreboard.dao;

import TennisScoreboard.model.MatchEntity;

import java.util.List;

public interface PaginatedSearchStorage<T> {

    public abstract List<T> getPaginated(int page, int size);

    public abstract long getEntityCount();

    public abstract List<T> getPaginatedForSearch(int page, int size, String search);

    public abstract long getEntityCountForSearch(String search);
}
