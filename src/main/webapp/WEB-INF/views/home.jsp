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
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,1,0&icon_names=casino,colors,leaderboard,search" />
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>

    <body>
        <div class="container-fluid">
            <div class="d-flex flex-column">
                <nav class="p-2 mx-auto align-items-center justify-content-center">
                    <h1>Texas City Snapshot</h1>
                    <ul class="d-flex flex-row align-items-center justify-content-center p-0">
                        <li><span class="material-symbols-outlined"><a href="/mostsearched">leaderboard</a></span></li>
                        <li><details>
                            <summary><span class="material-symbols-outlined p-2">colors</span></summary>
                            <ul id="themes-list">
                                <c:forEach items="${themes}" var="themeName">
                                    <li><a>${themeName}</a></li>
                                </c:forEach>
                            </ul>
                        </details>
                    </ul>
                </nav>
                <main id="search-main" class="container flex-grow-1">
                    <dialog id="searching-modal" aria-modal="true" aria-labelledby="searching-indicator">
                        <span id="searching-indicator" class="h3 text-center"></span>
                    </dialog>
                    <div class="col">
                        <div class="row">
                            <div class="col">
                                <h2>Enter the name of a city or town in Texas:</h2>
                                <div id="city-typeahead">
                                    <form id="city-name-form" class="d-flex align-items-center p-0" method="GET" action="/city">
                                        <label for="city-name" class="visually-hidden">City Name</label>
                                        <input id="city-name" name="name" type="text" placeholder="City name" autocomplete="off" class="w-75"
                                            required />
                                        <button id="submit-button" class="p-2"><span class="material-symbols-outlined py-2">search</span></button>
                                        <button id="random-button" class="p-2"><span class="material-symbols-outlined py-2">casino</span></button>
                                    </form>
                                </div>
                                <div class="col">
                                    <div class="row">
                                        <div class="col">
                                            <h4>Most Searched</h4>
                                            <ol>
                                                <c:forEach items="${mostSearched}" var="msCity">
                                                    <li><a class="scoreboard-item"
                                                            href="/city/${msCity.properties.name}">${msCity.properties.name}</a></li>
                                                </c:forEach>
                                            </ol>
                                        </div>
                                        <div class="col">
                                            <h4>Recently Searched</h4>
                                            <ol>
                                                <c:forEach items="${recentlySearched}" var="rCity">
                                                    <li><a class="scoreboard-item"
                                                            href="/city/${rCity.properties.name}">${rCity.properties.name}</a></li>
                                                </c:forEach>
                                            </ol>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col">
                                <h2>
                                    or throw a dart at the map:
                                </h2>
                                <form id="texas-img-form" action="/coordinates" method="GET">
                                    <input name="lat" id="lat" hidden />
                                    <input name="lon" id="lon" hidden />
                                    <img id="texas-map" src="texas.png" />
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col pb-3">
                            <br/>
                            <a href="/about">About this app</a>
                            <br/>
                        </div>
                    </div>
                </main>
            </div>
        </div>

        <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
        <script src="theme.js"></script>
        <script src="home.js"></script>
    </body>

    </html>