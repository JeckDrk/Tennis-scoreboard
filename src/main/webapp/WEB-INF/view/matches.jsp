<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
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
        <h1>Matches</h1>
        <div class="input-container">
            <form method="post" action="${pageContext.request.contextPath}/matches"
                  style="width: 100%; margin-right: 20px">
                <input name="filter_by_player_name" class="input-filter" placeholder="Filter by name" type="text"/>
            </form>
            <div>
                <a href="${pageContext.request.contextPath}/matches">
                    <button class="btn-filter" type="button">Reset Filter</button>
                </a>
            </div>
        </div>
        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:forEach var="match" items="${requestScope.matches}">
                <tr>
                    <td>${match.getPlayer1().getName()}</td>
                    <td>${match.getPlayer2().getName()}</td>
                    <td><span class="winner-name-td">${match.getWinner().getName()}</span></td>
                </tr>
            </c:forEach>
        </table>
        <div class="pagination">
            <c:if test="${page > 1}">
                <a class="prev"
                   href="${pageContext.request.contextPath}/matches?page=${page - 1}&filter_by_player_name=${filter_by_player_name}">
                    < </a>
            </c:if>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:choose>
                    <c:when test="${i == page}">
                        <a class="num-page current"
                           href="${pageContext.request.contextPath}/matches?page=${i}&filter_by_player_name=${filter_by_player_name}">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a class="num-page"
                           href="${pageContext.request.contextPath}/matches?page=${i}&filter_by_player_name=${filter_by_player_name}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${page < totalPages}">
                <a class="next"
                   href="${pageContext.request.contextPath}/matches?page=${page + 1}&filter_by_player_name=${filter_by_player_name}">
                    > </a>
            </c:if>
        </div>
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
