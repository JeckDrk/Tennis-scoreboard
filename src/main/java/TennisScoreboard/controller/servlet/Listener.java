package TennisScoreboard.controller.servlet;

import TennisScoreboard.model.MatchScorePersistence;
import TennisScoreboard.model.PlayerPersistence;
import TennisScoreboard.model.PlayerStorage;
import TennisScoreboard.model.MatchesStorage;
import TennisScoreboard.sevice.*;
import TennisScoreboard.util.utilPersistence;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener("/*")
public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        PlayerStorage playerStorage = new PlayerPersistence();
        MatchesStorage matchStorage = new MatchScorePersistence();
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
        utilPersistence.getSessionFactory().close();
        ServletContextListener.super.contextDestroyed(sce);
    }
}
