// script.js

function openSidebar() {
    const sidebar = document.getElementById("sidebar");
    const mainContent = document.getElementById("mainContent");
    if (sidebar && mainContent) {
        sidebar.style.width = "250px";
        mainContent.style.marginLeft = "250px";
    }
}

function closeSidebar() {
    const sidebar = document.getElementById("sidebar");
    const mainContent = document.getElementById("mainContent");
    if (sidebar && mainContent) {
        sidebar.style.width = "0";
        mainContent.style.marginLeft = "0";
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const toggleButton = document.getElementById("sidebarToggle");
    if (toggleButton) {
        toggleButton.addEventListener("click", openSidebar);
    }

    // ✅ Open sidebar by default
    openSidebar();
});
