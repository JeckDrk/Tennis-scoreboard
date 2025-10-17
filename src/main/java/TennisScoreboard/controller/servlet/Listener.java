package TennisScoreboard.controller.servlet;

import TennisScoreboard.dao.*;
import TennisScoreboard.model.PlayerEntity;
import TennisScoreboard.sevice.*;
import TennisScoreboard.util.SessionFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener("/*")
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        PersistenceStorage<PlayerEntity> playerStorage = new PlayerPersistence();
        MatchScorePersistence matchStorage = new MatchScorePersistence();
        PrepareMatchScore prepareMatchScore = new PrepareMatchScore(playerStorage);
        OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();
        FinishedMatchesPersistenceService finishedMatchesPersistenceService = new FinishedMatchesPersistenceService(matchStorage, playerStorage);
        PagesMatchesService pagesMatchesService = new PagesMatchesService(matchStorage);
        SearchForNameService searchForNameService = new SearchForNameService(matchStorage);

        context.setAttribute("playerStorage", playerStorage);
        context.setAttribute("matchStorage", matchStorage);
        context.setAttribute("prepareMatchScore", prepareMatchScore);
        context.setAttribute("ongoingMatchesService", ongoingMatchesService);
        context.setAttribute("finishedMatchesPersistenceService", finishedMatchesPersistenceService);
        context.setAttribute("pagesMatchesService", pagesMatchesService);
        context.setAttribute("searchForNameService", searchForNameService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        SessionFactory.getSessionFactory().close();
        ServletContextListener.super.contextDestroyed(sce);
    }
}
