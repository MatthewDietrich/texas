<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Welcome to ${city.name} - Texas City Snapshot</title>
    <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css" href="weather-icons.min.css" />
    <link rel="stylesheet" type="text/css" href="weather-icons-wind.min.css" />
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>
    <div id="main" class="container">
        <header>
            <div class="row align-items-center">
                <div class="col flex-column justify-content-center">
                    <h1 id="city-name" class="display-1 fw-normal">${city.name}</h1>
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
                    <div class="col-md-12 col-lg-6" id="weather">
                        <h3>Current</h3>
                        <div class="row align-items-center">
                            <div class="col-md-12 col-lg-6 text-lg-center" id="weather-icon">
                                <div class="row">
                                    <div class="col-2 col-md-2 col-lg-12 pb-2"><i
                                            class="wi ${weather.current.iconClass} display-2"></i></div>
                                    <div class="col pt-2 pt-lg-0"><strong
                                            class="h5 fw-normal text-md-left">${weather.current.description}</strong>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 col-lg-6">
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
                                            <th scope="row">Precipitation Chance</th>
                                            <td>${weather.current.precipitation}%</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Cloud Cover</th>
                                            <td>${weather.current.cloudCover}%</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">Wind</th>
                                            <td><i class="wi wi-wind from-${weather.current.windDirection}-deg"></i>
                                                ${weather.current.windSpeed} mph</td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-12 col-lg-6" id="forecast">
                        <h3>5-Day Forecast</h3>
                        <table class="table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                    <th scope="col">High</th>
                                    <th scope="col">Low</th>
                                    <th scope="col">Precip. Chance</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">${weather.forecasts[0].shortWeekday}</th>
                                    <td><i class="wi ${weather.forecasts[0].iconClass}"></i></td>
                                    <td>${weather.forecasts[0].description}</td>
                                    <td>${weather.forecasts[0].highTemperature}&deg;F</td>
                                    <td>${weather.forecasts[0].lowTemperature}&deg;F</td>
                                    <td>${weather.forecasts[0].precipitationChance}%</td>
                                </tr>
                                <tr>
                                    <th scope="row">${weather.forecasts[1].shortWeekday}</th>
                                    <td><i class="wi ${weather.forecasts[1].iconClass}"></i></td>
                                    <td>${weather.forecasts[1].description}</td>
                                    <td>${weather.forecasts[1].highTemperature}&deg;F</td>
                                    <td>${weather.forecasts[1].lowTemperature}&deg;F</td>
                                    <td>${weather.forecasts[1].precipitationChance}%</td>
                                </tr>
                                <tr>
                                    <th scope="row">${weather.forecasts[2].shortWeekday}</th>
                                    <td><i class="wi ${weather.forecasts[2].iconClass}"></i></td>
                                    <td>${weather.forecasts[2].description}</td>
                                    <td>${weather.forecasts[2].highTemperature}&deg;F</td>
                                    <td>${weather.forecasts[2].lowTemperature}&deg;F</td>
                                    <td>${weather.forecasts[2].precipitationChance}%</td>
                                </tr>
                                <tr>
                                    <th scope="row">${weather.forecasts[3].shortWeekday}</th>
                                    <td><i class="wi ${weather.forecasts[3].iconClass}"></i></td>
                                    <td>${weather.forecasts[3].description}</td>
                                    <td>${weather.forecasts[3].highTemperature}&deg;F</td>
                                    <td>${weather.forecasts[3].lowTemperature}&deg;F</td>
                                    <td>${weather.forecasts[3].precipitationChance}%</td>
                                </tr>
                                <tr>
                                    <th scope="row">${weather.forecasts[4].shortWeekday}</th>
                                    <td><i class="wi ${weather.forecasts[4].iconClass}"></i></td>
                                    <td>${weather.forecasts[4].description}</td>
                                    <td>${weather.forecasts[4].highTemperature}&deg;F</td>
                                    <td>${weather.forecasts[4].lowTemperature}&deg;F</td>
                                    <td>${weather.forecasts[4].precipitationChance}%</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="row pb-4">
                    <div class="col-12" id="almanac">
                        <h3>Almanac for ${dateString}</h3>
                        <table class="table-bordered">
                            <thead>
                                <th scope="col"></th>
                                <th scope="col">1 yr ago</th>
                                <th scope="col">2 yrs ago</th>
                                <th scope="col">3 yrs ago</th>
                            </thead>
                            <tbody>
                                <tr>
                                    <th scope="row">Condition</th>
                                    <td>${weatherHistory[0].description}</td>
                                    <td>${weatherHistory[1].description}</td>
                                    <td>${weatherHistory[2].description}</td>
                                </tr>
                                <tr>
                                    <th scope="row">High</th>
                                    <td>${weatherHistory[0].maxTemperature}&deg;F</td>
                                    <td>${weatherHistory[1].maxTemperature}&deg;F</td>
                                    <td>${weatherHistory[2].maxTemperature}&deg;F</td>
                                </tr>
                                <tr>
                                    <th scope="row">Low</th>
                                    <td>${weatherHistory[0].maxTemperature}&deg;F</td>
                                    <td>${weatherHistory[1].maxTemperature}&deg;F</td>
                                    <td>${weatherHistory[2].maxTemperature}&deg;F</td>
                                </tr>
                                <tr>
                                    <th scope="row">Total Precip.</th>
                                    <td>${weatherHistory[0].precipitationSum} in</td>
                                    <td>${weatherHistory[1].precipitationSum} in</td>
                                    <td>${weatherHistory[2].precipitationSum} in</td>
                                </tr>
                                <tr>
                                    <th scope="row">Avg. Humidity</th>
                                    <td>${weatherHistory[0].humidity}%</td>
                                    <td>${weatherHistory[1].humidity}%</td>
                                    <td>${weatherHistory[2].humidity}%</td>
                                </tr>
                                <tr>
                                    <th scope="row">Avg. Cloud Cover</th>
                                    <td>${weatherHistory[0].cloudCover}%</td>
                                    <td>${weatherHistory[1].cloudCover}%</td>
                                    <td>${weatherHistory[2].cloudCover}%</td>
                                </tr>
                                <tr>
                                    <th scope="row">Avg. Wind</th>
                                    <td><i class="wi wi-wind from-${weatherHistory[0].windDirection}-deg"></i>
                                        ${weatherHistory[0].windSpeed}
                                        mph</td>
                                    <td><i class="wi wi-wind from-${weatherHistory[1].windDirection}-deg"></i>
                                        ${weatherHistory[1].windSpeed}
                                        mph</td>
                                    <td><i class="wi wi-wind from-${weatherHistory[2].windDirection}-deg"></i>
                                        ${weatherHistory[2].windSpeed}
                                        mph</td>
                                </tr>
                                <tr>
                                    <th scope="row">Sunrise</th>
                                    <td>${weatherHistory[0].sunrise}</td>
                                    <td>${weatherHistory[1].sunrise}</td>
                                    <td>${weatherHistory[2].sunrise}</td>
                                </tr>
                                <tr>
                                    <th scope="row">Sunset</th>
                                    <td>${weatherHistory[0].sunset}</td>
                                    <td>${weatherHistory[1].sunset}</td>
                                    <td>${weatherHistory[2].sunset}</td>
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
                            <div class="row"><span class="icdId">${snapshots[0].icdId}</span>
                                <small>${snapshots[0].direction}</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="container">
                            <div class="row">
                                <div class="img-container"><img src="data:image/png;base64,${snapshots[1].snippet}" />
                                </div>
                            </div>
                            <div class="row"><span class="icdId">${snapshots[1].icdId}</span>
                                <small>${snapshots[1].direction}</small>
                            </div>
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
                            <div class="row"><strong class="icdId">${snapshots[2].icdId}</strong>
                                <small>${snapshots[2].direction}</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="container">
                            <div class="row">
                                <div class="img-container"><img src="data:image/png;base64,${snapshots[3].snippet}" />
                                </div>
                            </div>
                            <div class="row"><strong class="icdId">${snapshots[3].icdId}</strong>
                                <small>${snapshots[3].direction}</small>
                            </div>
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
                            <div class="row"><strong class="icdId">${snapshots[4].icdId}</strong>
                                <small>${snapshots[4].direction}</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="container">
                            <div class="row">
                                <div class="img-container"><img src="data:image/png;base64,${snapshots[5].snippet}" />
                                </div>
                            </div>
                            <div class="row"><strong class="icdId">${snapshots[5].icdId}</strong>
                                <small>${snapshots[5].direction}</small>
                            </div>
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
                            <div class="row"><strong class="icdId">${snapshots[6].icdId}</strong>
                                <small>${snapshots[6].direction}</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="container">
                            <div class="row">
                                <div class="img-container"><img src="data:image/png;base64,${snapshots[7].snippet}" />
                                </div>
                            </div>
                            <div class="row"><strong class="icdId">${snapshots[7].icdId}</strong>
                                <small>${snapshots[7].direction}</small>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row"><small>Camera data from <a
                    href="https://www.txdot.gov/discover/live-traffic-cameras.html">Texas
                    Department of
                    Transportation</a></small></div>
        <br />
        <div class="row">
            <h2>Nearby Airports</h2>
        </div>
        <div class="container">
            <div class="row">
                <div class="container" id="airports">
                    <ul>
                        <li>${airports[0].name} (${airports[0].code}) - ${airports[0].cityName}</li>
                        <li>${airports[1].name} (${airports[1].code}) - ${airports[1].cityName}</li>
                        <li>${airports[2].name} (${airports[2].code}) - ${airports[2].cityName}</li>
                        <li>${airports[3].name} (${airports[3].code}) - ${airports[3].cityName}</li>
                        <li>${airports[4].name} (${airports[4].code}) - ${airports[4].cityName}</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="row">
            <footer class="pt-4">
                <p><a href="/">Back to search</a></p>
                <p><small>Copyright 2025 <a href="https://lizard.fun">Squam</a> &bull; Made with &hearts; in
                        Carrollton</small></p>
            </footer>
        </div>
    </div>

    <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
</body>

</html>