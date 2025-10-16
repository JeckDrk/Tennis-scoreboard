package TennisScoreboard.util;

import TennisScoreboard.entity.MatchEntity;
import TennisScoreboard.entity.PlayerEntity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class utilPersistence {

    private static final SessionFactory SESSION_FACTORY;

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    static {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MatchEntity.class);
        configuration.addAnnotatedClass(PlayerEntity.class);

        SESSION_FACTORY = configuration.buildSessionFactory();
    }
}
