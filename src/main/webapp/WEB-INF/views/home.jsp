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
        <div class="row">
            <div class="col-sm">
                <h1>Enter the name of a city or town in Texas:</h1>
                <div id="city-typeahead">
                    <form id="city-name-form" method="GET" action="/city">
                        <input id="city-name" name="name" type="text" placeholder="City name" autocomplete="off"
                            required />
                        <input type="submit" value="Search" id="submit-button" /> or
                        <input type="submit" value="Random City" id="random-button" />
                    </form>
                    <a href="/about">About this app</a>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm">
                <span id="searching-indicator"></span>
            </div>
        </div>
    </div>

    <script src="/webjars/bootstrap/5.3.5/js/bootstrap.min.js"></script>
    <script src="/webjars/jquery/3.6.0/jquery.js"></script>
    <script src="/webjars/jquery-ui/1.12.1/jquery-ui.js"></script>
    <script src="home.js"></script>
</body>

</html>