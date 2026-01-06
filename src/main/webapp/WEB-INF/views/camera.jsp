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
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0&icon_names=arrow_back,colors,commute,home,partly_cloudy_day,photo_camera,water" />
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="weather-icons.min.css" />
        <link rel="stylesheet" type="text/css" href="weather-icons-wind.min.css" />
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>

    <body>
        <div class="container-fluid">
            <div class="d-flex flex-column">
                <nav class="p-2 mx-auto align-items-center justify-content-center">
                    <h4>Texas City Snapshot</h4>
                    <ul class="d-flex flex-row align-items-center justify-content-center">
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
                <main id="main" class="container flex-grow-1">
                    <header>
                        <div class="col">
                            <div class="row align-items-center">
                                <h1 id="camera-id">Viewing camera ${cameraId}</h1>
                                <small id="coordinates">${lat}&deg;N, ${lon}&deg;W</small>
                                <small id="county-name">Near <a href="/city?name=${cityName}">${cityName}</a>, ${countyName} County,
                                    Texas</small>
                            </div>
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
            </div>
        </div>

        <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
        <script src="theme.js"></script>
    </body>

    </html>