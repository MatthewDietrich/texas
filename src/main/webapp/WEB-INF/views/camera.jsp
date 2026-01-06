<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>${cameraId} - Texas City Snapshot</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Roboto+Condensed:ital,wght@0,100..900;1,100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0&icon_names=colors" />
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="weather-icons.min.css" />
        <link rel="stylesheet" type="text/css" href="weather-icons-wind.min.css" />
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>

    <body>
        <main id="main" class="container">
            <header>
                <div class="col">
                    <div class="row align-items-center">
                        <h1 id="camera-id">Viewing camera ${cameraId}</h1>
                        <small id="coordinates">${lat}&deg;N, ${lon}&deg;W</small>
                        <small id="county-name">Near <a href="/city?name=${cityName}">${cityName}</a>, ${countyName} County,
                            Texas</small>
                    </div>
                </div>
                <div class="col pt-2">
                        <details>
                            <summary><span class="material-symbols-outlined">colors</span></summary>
                            <ul id="themes-list">
                                <c:forEach items="${themes}" var="themeName">
                                    <li><a>${themeName}</a></li>
                                </c:forEach>
                            </ul>
                        </details>
                </div>
            </header>
            <br />
            <div id="tabs">
                <div id="tabs-1">
                    <div class="container" id="snapshots-container">
                        <div class="row align-items-center">
                            <div class="img-container-lg align-items-center py-4"><img
                                    src="data:image/png;base64,${snapshot}" />
                            </div>
                            <p class="text-center">Snapshot taken at ${snapshotTime}</p>
                            <div class="row"><small>Camera data from <a
                                        href="https://www.txdot.gov/discover/live-traffic-cameras.html">Texas
                                        Department of
                                        Transportation</a></small></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <footer class="pt-4">
                    <p>Viewed ${timesViewed} times</p>
                    <p><a href="/">Back to search</a></p>
                    <p><small>Copyright 2025 <a href="https://lizard.fun">Squam</a> &bull; Made with &hearts; in
                            Carrollton</small></p>
                </footer>
            </div>
        </main>

        <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
        <script src="theme.js"></script>
    </body>

    </html>