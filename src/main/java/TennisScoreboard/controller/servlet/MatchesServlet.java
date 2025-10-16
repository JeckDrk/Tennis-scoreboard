package TennisScoreboard.controller.servlet;

import TennisScoreboard.entity.MatchEntity;
import TennisScoreboard.entity.SearchDTO;
import TennisScoreboard.sevice.PagesMatchesService;
import TennisScoreboard.sevice.SearchForNameService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/matches")
public class MatchesServlet extends HttpServlet {

    private static final int PAGE_SIZE = 4;

    PagesMatchesService pagesMatchesService;
    SearchForNameService searchForNameService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        pagesMatchesService = (PagesMatchesService) config.getServletContext().getAttribute("pagesMatchesService");
        searchForNameService = (SearchForNameService) config.getServletContext().getAttribute("searchForNameService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("filter_by_player_name");
        processSearchPage(request, response, name);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("filter_by_player_name");
        if (name != null && !name.isEmpty()) {
            processSearchPage(request, response, name);
        } else {
            processDefaultPage(request, response);
        }
    }

    private void processSearchPage(HttpServletRequest request, HttpServletResponse response, String name)
            throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page") == null ? "1" : request.getParameter("page"));

        SearchDTO searchDTO = new SearchDTO(page, PAGE_SIZE, name);

        List<MatchEntity> matches = searchForNameService.getMatchesFromPageOfSearch(searchDTO);
        long totalPages = searchForNameService.getPagesCountOfSearch(searchDTO);

        forwardToPage(request, response, matches, totalPages, page, name);
    }

    private void processDefaultPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = Integer.parseInt(request.getParameter("page") == null ? "1" : request.getParameter("page"));

        SearchDTO searchDTO = new SearchDTO(page, PAGE_SIZE);

        List<MatchEntity> matches = pagesMatchesService.getMatchesFromPage(searchDTO);
        long totalPages = pagesMatchesService.getPagesCount(searchDTO);

        forwardToPage(request, response, matches, totalPages, page, "");
    }
    
    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, List<MatchEntity> matches,
                               long totalPages, int page, String name) throws ServletException, IOException {
        request.setAttribute("matches", matches);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("page", page);
        if (!name.isEmpty()) {
            request.setAttribute("filter_by_player_name", name);
        }

        request.getRequestDispatcher("/WEB-INF/view/matches.jsp").forward(request, response);
    }
    
}
