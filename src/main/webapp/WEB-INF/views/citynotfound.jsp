<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Not Found - Texas City Snapshot</title>
    <link
        href="https://fonts.googleapis.com/css2?family=Roboto+Condensed:ital,wght@0,100..900;1,100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0&icon_names=arrow_back,colors,commute,home,partly_cloudy_day,photo_camera,water" />
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="/style.css" />
</head>

<body>
    <div class="container-fluid">
        <div class="d-flex flex-column">
            <nav class="p-2 mx-auto align-items-center justify-content-center">
                <h4>Texas City Snapshot</h4>
                <ul class="d-flex flex-row align-items-center justify-content-center p-0">
                    <li><a href="javascript:history.back()"><span class="material-symbols-outlined p-2">arrow_back</span></a></li>
                    <li><a href="/"><span class="material-symbols-outlined p-2">home</span></a></li>
                    <li><details>
                        <summary><span class="material-symbols-outlined p-2">colors</span></summary>
                        <ul id="themes-list">
                            <c:forEach items="${themes}" var="themeName">
                                <li><a>${themeName}</a></li>
                            </c:forEach>
                        </ul>
                    </details></li>
                </ul>
            </nav>
            <main class="container">
                <h1>Not Found</h1>
                <c:choose>
                    <c:when test="${empty cityName}">
                        <h2>No city name supplied</h2>
                    </c:when>
                    <c:otherwise>
                        <h2>"${cityName}" is not a city or town in Texas.</h2>
                        <p><strong>Note:</strong> This site only has geographical data stored for incorporated cities and towns in Texas. Most unincorporated communities are not currently represented.</p>
                    </c:otherwise>
                </c:choose>
                <div class="row">
                    <footer class="pt-4">
                        <p><a href="/">Back to search</a></p>
                        <p><small>Copyright 2025 <a href="https://lizard.fun">Squam</a> &bull; Made with &hearts; in
                                Carrollton</small></p>
                    </footer>
                </div>
            </main>
        </div>
    </div>

    <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
    <script src="/theme.js"></script>
</body>

</html>