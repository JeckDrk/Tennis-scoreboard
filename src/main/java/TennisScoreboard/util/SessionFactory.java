package TennisScoreboard.util;

import TennisScoreboard.model.MatchEntity;
import TennisScoreboard.model.PlayerEntity;
import org.hibernate.cfg.Configuration;

public class SessionFactory {

    private static final org.hibernate.SessionFactory SESSION_FACTORY;

    public static org.hibernate.SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    static {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MatchEntity.class);
        configuration.addAnnotatedClass(PlayerEntity.class);

        SESSION_FACTORY = configuration.buildSessionFactory();
    }
}
