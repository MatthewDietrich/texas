<html>

<head>
    <meta http-equiv="Content-Type"
          content="text/html; charset=ISO-8859-1">
    <title>Welcome to ${cityName} - Texas City Snapshot</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>

<body>
<div id="main">
    <h1>${cityName}</h1>
    <h2>Weather</h2>
    <table>
        <tr>
            <td><strong>Temperature</strong></td>
            <td>${weather.temperature}&deg;F</td>
        </tr>
        <tr>
            <td><strong>Feels Like</strong></td>
            <td>${weather.apparentTemperature}&deg;F</td>
        </tr>
        <tr>
            <td><strong>Humidity</strong></td>
            <td>${weather.humidity}%</td>
        </tr>
        <tr>
            <td><strong>Description</strong></td>
            <td>${weather.description}</td>
        </tr>
        <tr>
            <td><strong>Precipitation Chance</strong></td>
            <td>${weather.precipitation}%</td>
        </tr>
        <tr>
            <td><strong>Cloud Cover</strong></td>
            <td>${weather.cloudCover}%</td>
        </tr>
    </table>
    <h2>Snapshots</h2>
    <table id="snapshots">
        <tr>
            <td>${snapshots[0].icdId}</td>
            <td><img src="data:image/png;base64,${snapshots[0].snippet}"/></td>
        </tr>
        <tr>
            <td>${snapshots[1].icdId}</td>
            <td><img src="data:image/png;base64,${snapshots[1].snippet}"/></td>
        </tr>
        <tr>
            <td>${snapshots[2].icdId}</td>
            <td><img src="data:image/png;base64,${snapshots[2].snippet}"/></td>
        </tr>
        <tr>
            <td>${snapshots[3].icdId}</td>
            <td><img src="data:image/png;base64,${snapshots[3].snippet}"/></td>
        </tr>
        <tr>
            <td>${snapshots[4].icdId}</td>
            <td><img src="data:image/png;base64,${snapshots[4].snippet}"/></td>
        </tr>
        <tr>
            <td>${snapshots[5].icdId}</td>
            <td><img src="data:image/png;base64,${snapshots[5].snippet}"/></td>
        </tr>
        <tr>
            <td>${snapshots[6].icdId}</td>
            <td><img src="data:image/png;base64,${snapshots[6].snippet}"/></td>
        </tr>
        <tr>
            <td>${snapshots[7].icdId}</td>
            <td><img src="data:image/png;base64,${snapshots[7].snippet}"/></td>
        </tr>
    </table>
</div>
</body>

</html>