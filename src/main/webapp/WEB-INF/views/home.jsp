<html>
<head>
    <meta http-equiv="Content-Type"
          content="text/html; charset=ISO-8859-1">
    <title>Texas City Snapshot</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>
<div id="main">
    <h1>Enter the name of a city or town in Texas:</h1>
    <div id="city-typeahead">
        <form method="POST">
            <input id="city-name" name="cityName" type="text" placeholder="City name" autocomplete="off"/>
            <input type="submit" value="Submit" id="submit-button"/>
        </form>
    </div>
</div>

<script src="/webjars/jquery/3.6.0/jquery.js"></script>
<script src="/webjars/jquery-ui/1.12.1/jquery-ui.js"></script>
<script src="home.js"></script>
</body>
</html>