document.addEventListener('DOMContentLoaded', () => {
    // 햄버거 버튼과 사이드바 동작 제어
    const hamburgerButton = document.getElementById('hamburgerButton');
    const sidebar = document.getElementById('sidebar');
    const navbar = document.getElementById('nav-section');

    if (hamburgerButton) {
        hamburgerButton.addEventListener('click', () => {
            console.log('Button clicked!');
            hamburgerButton.classList.toggle('active');
            sidebar.classList.toggle('active');
            navbar.classList.toggle('active');
        });
    } else {
        console.log('Button not found!');
    }
});
