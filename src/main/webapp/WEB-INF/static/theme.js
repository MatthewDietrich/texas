const path = window.location.pathname;
const segments = path.split('/').filter(segment => segment !== "");
const cityName = segments[1];

let theme = localStorage.getItem("theme") || "default";
document.addEventListener("DOMContentLoaded", function() {
    document.documentElement.setAttribute("data-theme", theme);
});

function setMap(themeName) {
    const url = `/map?theme=${theme}&name=${cityName}`;
    fetch(url)
        .then((response) => {return response.text()})
        .then((text) => {
            document.getElementById("texas-map").setAttribute("src", `data:image/png;base64,${text}`);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    setMap(theme);
});

document.querySelectorAll("#themes-list li a").forEach(link => {
    link.addEventListener("click", (event) => {
        switch (event.target.innerText) {
            case "Default Green":
                theme = "default";
                break;
            case "Burnt Orange":
                theme = "burntorange";
                break;
            case "Maroon":
                theme = "maroon";
                break;
            case "Purple":
                theme = "purple";
                break;
        }
        setMap(theme);
        localStorage.setItem("theme", theme);
        document.documentElement.setAttribute("data-theme", theme);
    });
});