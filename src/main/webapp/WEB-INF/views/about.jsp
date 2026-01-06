<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>About - Texas City Snapshot</title>
    <link
        href="https://fonts.googleapis.com/css2?family=Roboto+Condensed:ital,wght@0,100..900;1,100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
        rel="stylesheet">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0&icon_names=arrow_back,colors,commute,home,partly_cloudy_day,photo_camera,water" />
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
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
                <div class="row">
                    <div class="col-sm">
                        <div class="row">
                            <div class="col pt-2">
                                <h1>About</h1>
                            </div>
                        </div>
                        <p><strong>Texas City Snapshot</strong> is a tool to find weather data and road camera images from
                            cities around Texas.</p>
                        <h2>Weather</h2>
                        <p>Weather forecasts and historical weather data are provided by <a
                                href="https://open-meteo.com/">Open-Meteo</a>.</p>
                        <p>Weather alerts are provided by the <a href="https://www.weather.gov/alerts">National Weather
                                Service</a>.</p>
                        <p>Weather data is updated every 5 minutes.</p>
                        <h2>Road Camera Snapshots</h2>
                        <p>Images are sourced from road cameras owned by the <a
                                href="https://www.txdot.gov/discover/live-traffic-cameras.html">Texas
                                Department of
                                Transportation</a>.</p>
                        <p>Road camera images are updated every 5 minutes.</p>
                        <h2>Population</h2>
                        <p>Population data is taken from the 2020 United States Census.</p>
                        <h2>Water Reservoirs</h2>
                        <p>Reservoir fullness data is provided by <a href="https://www.waterdatafortexas.org/reservoirs/statewide">Water Data for Texas</a>.</p>
                        <p>Reservoir fullness is updated every 24 hours.</p>
                    </div>
                </div>
                <footer class="pt-4">
                    <p><a href="/">Back to search</a></p>
                    <p><small>Copyright 2025 <a href="https://lizard.fun">Squam</a> &bull; Made with &hearts; in
                            Carrollton</small></p>
                </footer>
            </main>
        </div>
    </div>

    <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
    <script src="theme.js"></script>
</body>

</html>