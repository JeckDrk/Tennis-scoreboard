package TennisScoreboard.config;

import TennisScoreboard.dao.*;
import TennisScoreboard.model.PlayerEntity;
import TennisScoreboard.sevice.*;
import TennisScoreboard.util.SessionFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        PersistenceStorage<PlayerEntity> playerStorage = new PlayerPersistence();
        MatchScorePersistence matchStorage = new MatchScorePersistence();
        PrepareMatchScore prepareMatchScore = new PrepareMatchScore(playerStorage);
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(matchStorage, playerStorage);
        MatchLifecycleManager matchLifecycleManager = new MatchLifecycleManager(ongoingMatchesService, finishedMatchesPersistenceService);
        SearchForNameService searchForNameService = new SearchForNameService(matchStorage);

        context.setAttribute("prepareMatchScore", prepareMatchScore);
        context.setAttribute("matchLifecycleManagerService", matchLifecycleManager);
        context.setAttribute("searchForNameService", searchForNameService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactory.getSessionFactory().close();
        ServletContextListener.super.contextDestroyed(sce);
    }
}
