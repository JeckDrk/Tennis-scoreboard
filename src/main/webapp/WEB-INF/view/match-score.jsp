<%@ page import="TennisScoreboard.sevice.OngoingMatchesService" %>
<%@ page import="TennisScoreboard.dto.MatchScoreDTO" %>
<%@ page import="TennisScoreboard.model.PlayerEntity" %>
<%@ page import="java.util.UUID" %>
<html>
<head>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Match Score</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Mono:wght@300&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    <script src="js/app.js"></script>
</head>
<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="${pageContext.request.contextPath}">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/matches">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Current match</h1>
        <div class="current-match-image"></div>
        <section class="score">
            <table class="table">
                <thead class="result">
                <tr>
                    <th class="table-text">Player</th>
                    <th class="table-text">Sets</th>
                    <th class="table-text">Games</th>
                    <th class="table-text">Points</th>
                </tr>
                </thead>
                <tbody>
                <tr class="player1">
                    <td class="table-text">
                        ${matchScoreDTO.getPlayerName1()}
                    </td>
                    <td class="table-text">
                        ${matchScoreDTO.getSet1()}
                    </td>
                    <td class="table-text">
                        ${matchScoreDTO.getGame1()}
                    </td>
                    <td class="table-text">
                        <c:choose>
                            <c:when test="${matchScoreDTO.isAd1()}">
                                AD
                            </c:when>
                            <c:otherwise>
                                ${matchScoreDTO.getPoint1()}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="table-text">
                        <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${uuid}">
                            <input type="hidden" name="player" value="1">
                            <input class="score-btn" type="submit" value="Score">
                        </form>
                    </td>
                </tr>
                <tr class="player2">
                    <td class="table-text">
                        ${matchScoreDTO.getPlayerName2()}
                    </td>
                    <td class="table-text">
                        ${matchScoreDTO.getSet2()}
                    </td>
                    <td class="table-text">
                        ${matchScoreDTO.getGame2()}
                    </td>
                    <td class="table-text">
                        <c:choose>
                            <c:when test="${matchScoreDTO.isAd2()}">
                                AD
                            </c:when>
                            <c:otherwise>
                                ${matchScoreDTO.getPoint2()}
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="table-text">
                        <form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${uuid}">
                            <input type="hidden" name="player" value="2">
                            <input class="score-btn" type="submit" value="Score">
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project created @jeckdrk from <a
                href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>
