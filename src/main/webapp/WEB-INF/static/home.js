const form = document.getElementById('city-name-form');
const searchIndicatorSpan = document.getElementById("searching-indicator");
const randomButton = document.getElementById("random-button")
const cityInput = document.getElementById("city-name")
let animationInterval;

function startSearchingAnimation() {
    clearInterval(animationInterval);
    let dotCount = 0;
    searchIndicatorSpan.textContent = 'Searching';

    animationInterval = setInterval(() => {
        dotCount = (dotCount + 1) % 4;
        searchIndicatorSpan.textContent = 'Searching' + '.'.repeat(dotCount);
    }, 500);
}

form.addEventListener("submit", (event) => {
    startSearchingAnimation();
    $("#city-name").autocomplete("close")
});

window.addEventListener('pageshow', function (event) {
    if (event.persisted) {
        clearInterval(animationInterval);
        searchIndicatorSpan.textContent = "";
    }
});

function getRandomElement(array) {
    const randomIndex = Math.floor(Math.random() * array.length);
    return array[randomIndex];
}

$(function () {
    fetch("/citynames").then(response => {
        return response.json();
    }).then(cities => {
        randomButton.addEventListener("click", (event) => {
            event.preventDefault();
            cityInput.value = getRandomElement(cities);
            form.submit();
            startSearchingAnimation();
        });
        $("#city-name").autocomplete({
            source: cities
        });
    });

});

$(document).ready(function () {
    $("#texas-img").on("click", function (event) {
        bounds = this.getBoundingClientRect();
        var left = bounds.left;
        var top = bounds.top;
        var x = event.pageX - left;
        var y = event.pageY - top;
        var cw = this.clientWidth;
        var ch = this.clientHeight;
        var iw = this.naturalWidth;
        var ih = this.naturalHeight;
        var px = x / cw * iw;
        var py = y / ch * ih;

        var minLon = -106.65;
        var maxLon = -93.51;
        var minLat = 25.84;
        var maxLat = 36.5;
        var borderWidth = 20;
        var ew = iw - 2 * borderWidth;
        var eh = ih - 2 * borderWidth;
        var lon = minLon + (px / ew) * (maxLon - minLon);
        var lat = maxLat - ((py - borderWidth) / eh) * (maxLat - minLat);
        $('input[name="lat"]').val(lat);
        $('input[name="lon"]').val(lon);
        $("#texas-img-form").submit();
        startSearchingAnimation();
    });
});