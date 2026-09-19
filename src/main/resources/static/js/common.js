async function loadHeader() {
    const response = await fetch('components/header.html');
    const html = await response.text();
    document.getElementById('header').innerHTML = html;
}

async function loadFooter() {
    const response = await fetch('components/footer.html');
    const html = await response.text();
    document.getElementById('footer').innerHTML = html;
}

document.addEventListener('DOMContentLoaded', async () => {
    await loadHeader();
    await loadFooter();
    checkLoginStatus();
    updateCartCount();
});

async function checkLoginStatus() {
    try {
        const myBtn = document.getElementById('myBtn');
        const footerLoginLink = document.getElementById('footer-login-link');

        const response = await fetch('/api/customers/my', {credentials: 'include'});
        const data = await response.json();

        if (response.ok) {
            if (data.role === 'ROLE_ADMIN') {
                document.getElementById('my-status').innerText = '관리자페이지'
                document.getElementById('my-dropdown').innerHTML = `
                    <li><a class="dropdown-item" href="admin.html">관리자페이지</a></li>
                    <li><a class="dropdown-item" href="#" onclick="logout()">로그아웃</a></li>
                `;

                footerLoginLink.href = 'admin.html';
                footerLoginLink.textContent = '관리자페이지';
            } else {
                document.getElementById('my-status').innerText = data.name + '님'
                document.getElementById('my-dropdown').innerHTML = `
                    <li><a class="dropdown-item" href="mypage.html">마이페이지</a></li>
                    <li><a class="dropdown-item" href="order.html">주문내역</a></li>
                    <li><a class="dropdown-item" href="#" onclick="logout()">로그아웃</a></li>
                `;

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

function search() {
    const keyword = document.getElementById('search-input').value;
    if (!keyword) return;
    window.location.href = `shop.html?keyword=${keyword}`;
}

document.addEventListener('keydown', function (e) {
    if (e.key === 'Enter') search();
});

function updateCartCount() {
    const cart = JSON.parse(sessionStorage.getItem('cart')) || [];
    const count = cart.length;

    document.getElementById('cart-count').innerText = count > 0 ? count : '';
}

async function fetchWithAuth(url, options = []) {
    const response = await fetch(url, {credentials: 'include', ...options});

    if (response.status === 401) {
        alert('로그인이 필요합니다.');
        location.href = `login.html?redirect=${encodeURIComponent(location.href)}`;
        return null;
    }

    return response;
}

async function logout() {
    const response = await fetch('/api/logout', {
        method: 'POST',
        credentials: 'include'
    });

    if (response.ok) {
        location.href = 'index.html';
    }
}