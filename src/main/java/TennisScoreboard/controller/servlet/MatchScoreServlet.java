package TennisScoreboard.controller.servlet;

import TennisScoreboard.entity.MatchScoreDTO;
import TennisScoreboard.exception.InputException;
import TennisScoreboard.exception.UrlException;
import TennisScoreboard.sevice.FinishedMatchesPersistenceService;
import TennisScoreboard.sevice.MatchScoreCalculationService;
import TennisScoreboard.sevice.OngoingMatchesService;
import TennisScoreboard.util.Mapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/match-score")
public class MatchScoreServlet extends HttpServlet {

    OngoingMatchesService ongoingMatchesService;
    FinishedMatchesPersistenceService finishedMatchesPersistenceService;

    @Override
    public void init(ServletConfig config) {
        ongoingMatchesService = (OngoingMatchesService) config.getServletContext().getAttribute("ongoingMatchesService");
        finishedMatchesPersistenceService = (FinishedMatchesPersistenceService) config.getServletContext().getAttribute("finishedMatchesPersistenceService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID uuid = extractUuidFromRequest(request);
        MatchScoreDTO matchScoreDTO = getMatchScoreByUUID(uuid);
        int player = Integer.parseInt(request.getParameter("player"));

        if (matchScoreDTO.isFinished()) {
            finishedMatchesPersistenceService.persist(matchScoreDTO);
            response.sendRedirect(request.getContextPath() + "/matches");
        } else {
            new MatchScoreCalculationService(matchScoreDTO, player).calculate();
            forwardToScorePage(request, response, matchScoreDTO);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID uuid = extractUuidFromRequest(request);
        MatchScoreDTO matchScoreDTO = getMatchScoreByUUID(uuid);
        forwardToScorePage(request, response, matchScoreDTO);
    }

    private MatchScoreDTO getMatchScoreByUUID(UUID uuid) {
        MatchScoreDTO matchScoreDTO = ongoingMatchesService.getMatch(uuid);

        if (matchScoreDTO == null) {
            throw new UrlException();
        }
        return matchScoreDTO;
    }

    private UUID extractUuidFromRequest(HttpServletRequest request) {
        String uuidBuffer = request.getParameter("uuid");
        try {
            return Mapper.mapUUID(uuidBuffer);
        } catch (InputException e) {
            throw new UrlException();
        }
    }

    private void forwardToScorePage(HttpServletRequest request, HttpServletResponse response,
                                    MatchScoreDTO matchScoreDTO) throws ServletException, IOException {
        UUID uuid = extractUuidFromRequest(request);
        request.setAttribute("uuid", uuid);
        request.setAttribute("matchScoreDTO", matchScoreDTO);
        request.getRequestDispatcher("/WEB-INF/view/match-score.jsp").forward(request, response);
    }
}
