async function loadHeader() {
    const response = await fetch('components/header.html');
    const html = await response.text();
    document.getElementById('header').innerHTML = html;
    checkLoginStatus(); // 헤더 로드 후 로그인 상태 체크
}

async function loadFooter() {
    const response = await fetch('components/footer.html');
    const html = await response.text();
    document.getElementById('footer').innerHTML = html;
}


document.addEventListener('DOMContentLoaded', () => {
    loadHeader();
    loadFooter();
});

async function checkLoginStatus() {
    try {
        const myBtn = document.getElementById('myBtn');
        const footerLoginLink = document.getElementById('footer-login-link');

        const response = await fetch('/api/customers/my');
        const data = await response.json();

        if (response.ok) {
            if (data.role === 'ROLE_ADMIN') {
                myBtn.onclick = () => location.href = 'admin.html';
                document.getElementById('my-status').innerText = '관리자페이지'

                footerLoginLink.href = 'admin.html';
                footerLoginLink.textContent = '관리자페이지';
            } else {
                myBtn.onclick = () => location.href = 'mypage.html';
                document.getElementById('my-status').innerText = '마이페이지'

                footerLoginLink.textContent = '마이페이지';
                footerLoginLink.href = 'mypage.html';
            }
        } else {
            myBtn.onclick = () => location.href = 'login.html';
            document.getElementById('my-status').innerText = '로그인'

            footerLoginLink.textContent = '로그인'
            footerLoginLink.href = 'login.html';
        }
    } catch (e) {
        const myBtn = document.getElementById('myBtn');
        myBtn.onclick = () => location.href = 'login.html';
        document.getElementById('my-status').innerText = '로그인'
    }
}
