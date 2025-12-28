const queryParams = new URLSearchParams(window.location.search);
let theme = localStorage.getItem("theme") || "default";
document.addEventListener("DOMContentLoaded", function() {
    document.documentElement.setAttribute("data-theme", theme);
});
$("#themes-list li a").on("click", (event) => {
    let name = queryParams.get("name");
    let lat = queryParams.get("lat");
    let lon = queryParams.get("lon");

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
1   }
    let url;
    if (lat !== null && lon !== null) {
        url = `/map?theme=${theme}&lat=${lat}&lon=${lon}`
    } else if (name !== null) {
        url = `/map?theme=${theme}&name=${name}`
    } else {
        url = `/map?theme=${theme}`
    }
    fetch(url)
        .then((response) => {return response.text()})
        .then((text) => {
            document.getElementById("texas-map").setAttribute("src", `data:image/png;base64,${text}`)
    });
    localStorage.setItem("theme", theme);
    document.documentElement.setAttribute("data-theme", theme);
});