$(function () {
    $("#tabs").tabs();
});

$("#themes-list li a").on("click", (event) => {
    switch (event.target.innerText) {
        case "Default Green":
            document.documentElement.setAttribute("data-theme", "default");
            break;
        case "Burnt Orange":
            document.documentElement.setAttribute("data-theme", "burntorange");
            break;
1   }
});