<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Texas City Snapshot</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link
            href="https://fonts.googleapis.com/css2?family=Roboto+Condensed:ital,wght@0,100..900;1,100..900&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap"
            rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>

    <body>
        <main id="main" class="container">
            <div class="row">
                    <div class="col">
                        <h1>Top 100 Most Searched Cities</h1>
                    </div>
                    <div class="col">
                        <details>
                            <summary>Select theme</summary>
                            <ul id="themes-list">
                                <c:forEach items="${themes}" var="themeName">
                                    <li><a>${themeName}</a></li>
                                </c:forEach>
                            </ul>
                        </details>
                    </div>
                </div>
            <div class="row">
                <table id="most-searched">
                    <thead>
                        <th scope="col">Rank</th>
                        <th scope="col">City name</th>
                        <th scope="col">Times searched</th>
                    </thead>
                    <tbody>
                        <c:forEach items="${cities}" var="city" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td><a href="/city?name=${city.properties.name}">${city.properties.name}</a></td>
                                <td>${city.timesSearched}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="row">
                <footer class="pt-4">
                    <p><a href="/">Back to search</a></p>
                    <p><small>Copyright 2025 <a href="https://lizard.fun">Squam</a> &bull; Made with &hearts; in
                            Carrollton</small></p>
                </footer>
            </div>
        </main>

        <script src="theme.js"></script>
    </body>

    </html>