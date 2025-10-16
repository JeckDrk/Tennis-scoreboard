package TennisScoreboard.model;

import TennisScoreboard.entity.PlayerEntity;

public interface PlayerStorage {
    public PlayerEntity get(String name);

    public void put(PlayerEntity player);

}
