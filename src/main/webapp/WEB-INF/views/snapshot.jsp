<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Welcome to ${city.name} - Texas City Snapshot</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="weather-icons.min.css" />
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>
    <div id="main" class="container">
        <header>
            <div class="row">
                <div class="col">
                    <h1 id="city-name">${city.name}</h1>
                    <small id="county-name">${city.countyName} County, Texas</small><br />
                    <small id="coordinates">${city.latitude}&deg;N, ${city.longitude}&deg;W</small>
                </div>
                <div class="col">
                    <img src="data:image/png;base64,${cityMap}" id="city-map" />
                </div>
            </div>
        </header>
        <br />
        <div class="row">
            <h2>Weather</h2>
        </div>
        <div class="container" id="weather-container">
            <div class="row">
                <div class="row">
                    <div class="col-sm" id="weather">
                        <h3>Current</h3>
                        <div class="row">
                            <div class="col-4 text-center" id="weather-icon">
                                <i class="wi ${weather.current.iconClass}"></i>
                            </div>
                            <div class="col-8">
                                <table>
                                    <tbody>
                                        <tr>
                                            <th scope="row">Temperature</th>
                                            <td>${weather.current.temperature}&deg;F</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Feels Like</th>
                                            <td>${weather.current.apparentTemperature}&deg;F</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Humidity</th>
                                            <td>${weather.current.humidity}%</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Description</th>
                                            <td>${weather.current.description}</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Precipitation Chance</th>
                                            <td>${weather.current.precipitation}%</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Cloud Cover</th>
                                            <td>${weather.current.cloudCover}%</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm" id="forecast">
                        <h3>3-Day Forecast</h3>
                        <table>
                            <thead>
                                <tr>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                    <th scope="col">High</th>
                                    <th scope="col">Low</th>
                                    <th scope="col">Precip. Chance</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">${weather.forecasts[1].shortWeekday}</th>
                                    <td><i class="wi ${weather.forecasts[1].iconClass}"></i>
                                        ${weather.forecasts[1].description}</td>
                                    <td>${weather.forecasts[1].highTemperature}&deg;F</td>
                                    <td>${weather.forecasts[1].lowTemperature}&deg;F</td>
                                    <td>${weather.forecasts[1].precipitationChance}%</td>
                                </tr>
                                <tr>
                                    <th scope="row">${weather.forecasts[2].shortWeekday}</th>
                                    <td><i class="wi ${weather.forecasts[2].iconClass}"></i>
                                        ${weather.forecasts[2].description}</td>
                                    <td>${weather.forecasts[2].highTemperature}&deg;F</td>
                                    <td>${weather.forecasts[2].lowTemperature}&deg;F</td>
                                    <td>${weather.forecasts[2].precipitationChance}%</td>
                                </tr>
                                <tr>
                                    <th scope="row">${weather.forecasts[3].shortWeekday}</th>
                                    <td><i class="wi ${weather.forecasts[3].iconClass}"></i>
                                        ${weather.forecasts[3].description}</td>
                                    <td>${weather.forecasts[3].highTemperature}&deg;F</td>
                                    <td>${weather.forecasts[3].lowTemperature}&deg;F</td>
                                    <td>${weather.forecasts[3].precipitationChance}%</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="row"><small>Weather data from <a href="https://open-meteo.com/">Open-Meteo</a></small></div>
    </div>
    <br />
    <div class="container" id="snapshots-container">
        <div class="row">
            <h2>Snapshots</h2>
        </div>
        <div class="container">
            <div class="row" id="snapshots">
                <div class="row">
                    <div class="col-sm">
                        <div class="container">
                            <div class="row">
                                <div class="img-container"><img src="data:image/png;base64,${snapshots[0].snippet}" />
                                </div>
                            </div>
                            <div class="row"><span class="icdId">${snapshots[0].icdId}</span></div>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="container">
                            <div class="row">
                                <div class="img-container"><img src="data:image/png;base64,${snapshots[1].snippet}" />
                                </div>
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
        </div>
        <div class="row"><small>Camera data from <a
                    href="https://www.txdot.gov/discover/live-traffic-cameras.html">Texas
                    Department of
                    Transportation</a></small></div>
    </div>

    <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
</body>

</html>