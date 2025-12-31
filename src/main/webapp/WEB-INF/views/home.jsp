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
        <main id="search-main" class="container">
            <dialog id="searching-modal" aria-modal="true" aria-labelledby="searching-indicator">
                <span id="searching-indicator" class="h3 text-center"></span>
            </dialog>
            <div class="col">
                <div class="row">
                    <div class="col">
                        <h1>Texas City Snapshot</h1>
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
                    <div class="col-12">
                        <h2>Enter the name of a city or town in Texas:</h2>
                        <div id="city-typeahead">
                            <form id="city-name-form" method="GET" action="/city">
                                <label for="city-name" class="visually-hidden">City Name</label>
                                <input id="city-name" name="name" type="text" placeholder="City name" autocomplete="off"
                                    required />
                                <input type="submit" value="Search" id="submit-button" /> or
                                <input type="submit" value="Random City" id="random-button" />
                            </form>
                        </div>
                    </div>
                    <div class="col">
                        <h2>
                            or throw a dart at the map:
                        </h2>
                        <form id="texas-img-form" action="/coordinates" method="GET">
                            <input name="lat" id="lat" hidden />
                            <input name="lon" id="lon" hidden />
                            <img id="texas-map" src="data:image/png;base64,${texasMap}" />
                        </form>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="row">
                    <div class="col">
                        <h3>Most Searched</h3>
                        <ol>
                            <c:forEach items="${mostSearched}" var="msCity">
                                <li><a class="scoreboard-item"
                                        href="city?name=${msCity.properties.name}">${msCity.properties.name}</a></li>
                            </c:forEach>
                        </ol>
                    </div>
                    <div class="col">
                        <h3>Recently Searched</h3>
                        <ol>
                            <c:forEach items="${recentlySearched}" var="rCity">
                                <li><a class="scoreboard-item"
                                        href="city?name=${rCity.properties.name}">${rCity.properties.name}</a></li>
                            </c:forEach>
                        </ol>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <a href="/about">About this app</a>
                    </div>
                </div>
            </div>
        </main>

        <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
        <script src="theme.js"></script>
        <script src="home.js"></script>
    </body>

    </html>