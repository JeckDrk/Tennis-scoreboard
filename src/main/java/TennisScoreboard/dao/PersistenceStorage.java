package TennisScoreboard.dao;

import TennisScoreboard.model.PlayerEntity;

public interface PersistenceStorage<T> {
    public T get(Object object);

    public void put(T player);

}
