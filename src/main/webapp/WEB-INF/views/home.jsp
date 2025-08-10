<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Texas City Snapshot</title>
        <link rel="stylesheet" type="text/css" href="/webjars/bootstrap/5.3.5/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="style.css" />
    </head>

    <body>
        <div id="search-main" class="container">
            <div class="col">
                <div class="row">
                    <div class="col-12">
                        <h1>Enter the name of a city or town in Texas:</h1>
                        <div id="city-typeahead">
                            <form id="city-name-form" method="GET" action="/city">
                                <input id="city-name" name="name" type="text" placeholder="City name" autocomplete="off"
                                    required />
                                <input type="submit" value="Search" id="submit-button" /> or
                                <input type="submit" value="Random City" id="random-button" />
                            </form>
                        </div>
                    </div>
                    <div class="col">
                        <h1>
                            or throw a dart at the map:
                        </h1>
                        <form id="texas-img-form" action="/coordinates" method="GET">
                            <input name="lat" id="lat" hidden />
                            <input name="lon" id="lon" hidden />
                            <img id="texas-img" src="data:image/png;base64,${texasMap}" />
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm">
                        <span id="searching-indicator"></span>
                    </div>
                </div>
            </div>
            <div class="col">
                <div class="row">
                    <div class="col">
                        <h2>Most Searched</h2>
                        <ol>
                            <c:forEach items="${mostSearched}" var="msCity">
                                <li><a href="city?name=${msCity.properties.name}">${msCity.properties.name}</a></li>
                            </c:forEach>
                        </ol>
                    </div>
                    <div class="col">
                        <h2>Recently Searched</h2>
                        <ol>
                            <c:forEach items="${recentlySearched}" var="rCity">
                                <li><a href="city?name=${rCity.properties.name}">${rCity.properties.name}</a></li>
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
        </div>

        <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
        <script src="/webjars/jquery/3.6.0/jquery.js"></script>
        <script src="/webjars/jquery-ui/1.12.1/jquery-ui.js"></script>
        <script src="home.js"></script>
    </body>

    </html>