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
            document.getElementById('my-status').innerText = data.name + '님'
            if (data.role === 'ROLE_ADMIN') {
                document.getElementById('my-dropdown').innerHTML = `
                    <li><a class="dropdown-item" href="admin.html">관리자페이지</a></li>
                    <li><a class="dropdown-item" href="#" onclick="logout()">로그아웃</a></li>
                `;

                footerLoginLink.href = 'admin.html';
                footerLoginLink.textContent = '관리자페이지';
            } else {
                document.getElementById('my-dropdown').innerHTML = `
                    <li><a class="dropdown-item" href="mypage.html">마이페이지</a></li>
                    <li><a class="dropdown-item" href="orders.html">주문내역</a></li>
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
    window.location.href = `shop.html?keyword=${encodeURIComponent(keyword)}`;
}

document.addEventListener('keydown', function (e) {
    if (e.key === 'Enter') search();
});

function updateCartCount() {
    const cart = JSON.parse(sessionStorage.getItem('cart')) || [];
    const count = cart.length;

    document.getElementById('cart-count').innerText = count > 0 ? count : '';
}

async function fetchWithAuth(url, options = {}) {
    const response = await fetch(url, {credentials: 'include', ...options});

    if (response.status === 401) {
        alert('로그인이 필요합니다.');
        location.href = `login.html?redirect=${encodeURIComponent(location.href)}`;
        return null;
    }

    return response;
}

async function checkLogin() {
    const response = await fetchWithAuth('/api/customers/my');

    if (!response) return false;
    if (!response.ok) {
        location.href = `login.html?redirect=${encodeURIComponent(location.href)}`;
        return false;
    }
    return await response.json();
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

function escapeHtml(str) {
    if (str == null) return '';
    str = String(str);

    return str
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

function maskingName(str) {
    if (str.length == 1) return "*";
    else if (str.length == 2) return str.substring(0, 1) + "*";
    else return str.substring(0, 1) + "*".repeat(str.length - 2) + str.substring(str.length - 1);
}

function maskingEmail(str) {
    const localPart = str.substring(0, str.indexOf("@"));
    const nonMaskLen = Math.floor(localPart.length / 2);

    return str.substring(0, nonMaskLen) + "*".repeat(localPart.length - nonMaskLen) + str.substring(str.indexOf("@"));
}

function maskingPhone(str) {
    return str.substring(0, str.indexOf('-') + 1) + "*".repeat(4) + str.substring(str.lastIndexOf('-'));
}

function initPagination(groupSelector, onLoad) {
    const group = document.querySelector(groupSelector);
    if (!group) return;

    group.addEventListener('click', (e) => {
        const btn = e.target.closest('button[data-page]');
        if (!btn) return;

        onLoad(Number(btn.dataset.page))
    });
}

function renderPagination(data) {
    let html = '';

    html += `
        <li class="page-item ${!data.hasPrevious ? 'disabled' : ''}">
                <button type="button" class="page-link" data-page="${data.currentPage - 1}">이전</button>
        </li>
        `;

    for (let page = 0; page < data.totalPage; page++) {
        html += `
            <li class="page-item ${page === data.currentPage ? 'active' : ''}">
                <button type="button" class="page-link" data-page="${page}">${page + 1}</button>
            </li>
            `;
    }

    html += `
        <li class="page-item ${!data.hasNext ? 'disabled' : ''}">
                <button type="button" class="page-link" data-page="${data.currentPage + 1}">다음</button>
        </li>
        `;

    return html;
}