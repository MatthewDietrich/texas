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
