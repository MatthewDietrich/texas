const form = document.getElementById('city-name-form');
const searchIndicatorSpan = document.getElementById("searching-indicator");
const randomButton = document.getElementById("random-button");
const cityInput = document.getElementById("city-name");
const searchingModal = document.getElementById("searching-modal");
const themeName = localStorage.getItem("theme") || "default";
let animationInterval;

function startSearchingAnimation() {
    searchingModal.showModal();
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

window.addEventListener('pageshow', function (event) {
    if (event.persisted) {
        clearInterval(animationInterval);
        searchIndicatorSpan.textContent = "";
        searchingModal.close();
    }
});

function getRandomElement(array) {
    const randomIndex = Math.floor(Math.random() * array.length);
    return array[randomIndex];
}

document.addEventListener("DOMContentLoaded", function () {
    fetch("/citynames").then(response => {
        return response.json();
    }).then(cities => {
        randomButton.addEventListener("click", (event) => {
            event.preventDefault();
            cityInput.value = getRandomElement(cities);
            form.submit();
            startSearchingAnimation();
        });
        
        const datalist = document.createElement('datalist');
        datalist.id = 'city-names-list';
        cities.forEach(city => {
            const option = document.createElement('option');
            option.value = city;
            datalist.appendChild(option);
        });
        document.body.appendChild(datalist);
        cityInput.setAttribute('list', 'city-names-list');
    });
    
    fetch(`/map?theme=${themeName}`).then(response => {
        return response.text();
    }).then(text => {
        document.getElementById("texas-map").setAttribute("src", `data:image/png;base64,${text}`);
    });
    
    document.querySelectorAll(".scoreboard-item").forEach(item => {
        item.addEventListener("click", startSearchingAnimation);
    });
    
    document.getElementById("texas-map").addEventListener("click", function (event) {
        const bounds = this.getBoundingClientRect();
        const left = bounds.left;
        const top = bounds.top;
        const x = event.pageX - left;
        const y = event.pageY - top;
        const cw = this.clientWidth;
        const ch = this.clientHeight;
        const iw = this.naturalWidth;
        const ih = this.naturalHeight;
        const px = x / cw * iw;
        const py = y / ch * ih;

        const minLon = -106.65;
        const maxLon = -93.51;
        const minLat = 25.84;
        const maxLat = 36.5;
        const borderWidth = 20;
        const ew = iw - 2 * borderWidth;
        const eh = ih - 2 * borderWidth;
        const lon = minLon + (px / ew) * (maxLon - minLon);
        const lat = maxLat - ((py - borderWidth) / eh) * (maxLat - minLat);
        
        document.querySelector('input[name="lat"]').value = lat;
        document.querySelector('input[name="lon"]').value = lon;
        document.getElementById("texas-img-form").submit();
        startSearchingAnimation();
    });
});