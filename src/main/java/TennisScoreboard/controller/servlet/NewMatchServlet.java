package TennisScoreboard.controller.servlet;

import TennisScoreboard.dto.MatchScoreDTO;
import TennisScoreboard.exception.InputException;
import TennisScoreboard.sevice.MatchLifecycleManager;
import TennisScoreboard.sevice.OngoingMatchesService;
import TennisScoreboard.sevice.PrepareMatchScore;
import TennisScoreboard.util.InputValidator;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet(urlPatterns = "/new-match")
public class NewMatchServlet extends HttpServlet {

    private PrepareMatchScore prepareMatchScore;
    private MatchLifecycleManager matchLifecycleManager;

    @Override
    public void init(ServletConfig config) {
        prepareMatchScore = (PrepareMatchScore) config.getServletContext().getAttribute("prepareMatchScore");
        matchLifecycleManager = (MatchLifecycleManager) config.getServletContext().getAttribute("matchLifecycleManager");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name1 = request.getParameter("namePlayer1");
        String name2 = request.getParameter("namePlayer2");
        try {
            InputValidator.namesValidator(name1, name2);
            MatchScoreDTO matchScoreDTO = prepareMatchScore.prepareMatchScore(name1, name2);
            UUID uuid = matchLifecycleManager.createNewMatch(matchScoreDTO);
            response.sendRedirect(request.getContextPath() + "/match-score?uuid=" + uuid);
        } catch (InputException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/new-match.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/new-match.jsp").forward(request, response);
    }
}
