const form = document.getElementById('city-name-form');
const searchIndicatorSpan = document.getElementById("searching-indicator");
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
});

$(function () {
    fetch("/citynames").then(response => {
        return response.json();
    }).then(cities => {
        $("#city-name").autocomplete({
            source: cities
        });
    });
});
