$(function () {
    fetch("/citynames").then(response => {
        return response.json();
    }).then(cities => {
        $("#city-name").autocomplete({
            source: cities
        });
    });
});
