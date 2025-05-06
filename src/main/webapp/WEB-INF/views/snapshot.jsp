<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Welcome to ${cityName} - Texas City Snapshot</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="weather-icons.min.css" />
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>
    <div id="main" class="container">
        <div class="row">
            <small id="welcome-message">Welcome to</small>
            <h1 id="city-name">${cityName}</h1>
        </div>
        <br />
        <div class="row">
            <h2>Weather</h2>
        </div>
        <div class="row">
            <div class="col-sm" id="weather">
                <div class="row">
                    <div class="col-sm"><strong>Temperature</strong></div>
                    <div class="col-sm">${weather.temperature}&deg;F</div>
                </div>
                <div class="row">
                    <div class="col-sm"><strong>Feels Like</strong></div>
                    <div class="col-sm">${weather.apparentTemperature}&deg;F</div>
                </div>
                <div class="row">
                    <div class="col-sm"><strong>Humidity</strong></div>
                    <div class="col-sm">${weather.humidity}%</div>
                </div>
                <div class="row">
                    <div class="col-sm"><strong>Description</strong></div>
                    <div class="col-sm">${weather.description}</div>
                </div>
                <div class="row">
                    <div class="col-sm"><strong>Precipitation Chance</strong></div>
                    <div class="col-sm">${weather.precipitation}%</div>
                </div>
                <div class="row">
                    <div class="col-sm"><strong>Cloud Cover</strong></div>
                    <div class="col-sm">${weather.cloudCover}%</div>
                </div>
            </div>
            <div class="col-sm" id="weather-icon">
                <i class="wi ${weather.iconClass}"></i>
            </div>
        </div>
        <div class="row"><small>Weather data from <a href="https://open-meteo.com/">Open-Meteo</a></small></div>
        <br />
        <div class="row">
            <h2>Snapshots</h2>
        </div>
        <div class="container" id="snapshots">
            <div class="row">
                <div class="col-sm">
                    <div class="container">
                        <div class="row">
                            <div class="img-container"><img src="data:image/png;base64,${snapshots[0].snippet}" /></div>
                        </div>
                        <div class="row"><span class="icdId">${snapshots[0].icdId}</span></div>
                    </div>
                </div>
                <div class="col-sm">
                    <div class="container">
                        <div class="row">
                            <div class="img-container"><img src="data:image/png;base64,${snapshots[1].snippet}" /></div>
                        </div>
                        <div class="row"><span class="icdId">${snapshots[1].icdId}</span></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <div class="container">
                        <div class="row">
                            <div class="img-container"><img src="data:image/png;base64,${snapshots[2].snippet}" />
                            </div>
                        </div>
                        <div class="row"><span class="icdId">${snapshots[2].icdId}</span></div>
                    </div>
                </div>
                <div class="col-sm">
                    <div class="container">
                        <div class="row">
                            <div class="img-container"><img src="data:image/png;base64,${snapshots[3].snippet}" />
                            </div>
                        </div>
                        <div class="row"><span class="icdId">${snapshots[3].icdId}</span></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <div class="container">
                        <div class="row">
                            <div class="img-container"><img src="data:image/png;base64,${snapshots[4].snippet}" />
                            </div>
                        </div>
                        <div class="row"><span class="icdId">${snapshots[4].icdId}</span></div>
                    </div>
                </div>
                <div class="col-sm">
                    <div class="container">
                        <div class="row">
                            <div class="img-container"><img src="data:image/png;base64,${snapshots[5].snippet}" />
                            </div>
                        </div>
                        <div class="row"><span class="icdId">${snapshots[5].icdId}</span></div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm">
                    <div class="container">
                        <div class="row">
                            <div class="img-container"><img src="data:image/png;base64,${snapshots[6].snippet}" />
                            </div>
                        </div>
                    </div>
                    <div class="row"><span class="icdId">${snapshots[6].icdId}</span></div>
                </div>
                <div class="col-sm">
                    <div class="container">
                        <div class="row">
                            <div class="img-container"><img src="data:image/png;base64,${snapshots[7].snippet}" />
                            </div>
                        </div>
                        <div class="row"><span class="icdId">${snapshots[7].icdId}</span></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row"><small>Camera data from <a
                    href="https://www.txdot.gov/discover/live-traffic-cameras.html">Texas Department of
                    Transportation</a></small></div>
    </div>

    <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
</body>

</html>