const PASSWORD_REGEX = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])\S{8,}$/;

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
    initAiWidget();

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

async function initAiWidget() {
    document.getElementById('ai-question').addEventListener('input', e => {
        document.getElementById('ai-count').textContent = e.target.value.length;
    });

    document.querySelector('#ai-submit').addEventListener('click', async (e) => {
        const btn = e.currentTarget;

        const question = document.getElementById('ai-question').value.trim();

        if (!question) {
            document.getElementById('ai-error').textContent = '질문을 입력해주세요.';
            document.getElementById('ai-error').classList.remove('d-none');
            return;
        }

        btn.disabled = true;
        document.getElementById('ai-loading').classList.remove('d-none');
        document.getElementById('ai-error').classList.add('d-none');
        document.getElementById('ai-result').classList.add('d-none');

        try {
            const response = await fetch(`/api/ai/recipe/recommend`, {
                method: 'POST',
                credentials: 'include',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({question})
            });

            if (response.ok) {
                const data = await response.json();

                document.getElementById('ai-answer').textContent = data.answer;
                document.getElementById('ai-result').classList.remove('d-none');

            } else {
                const error = await response.json().catch(() => ({}));
                document.getElementById('ai-error').textContent = error.message || 'ai 답변 중 에러가 발생하였습니다. 다시 시도해주세요.';
                document.getElementById('ai-error').classList.remove('d-none');
            }
        } catch (error) {
            document.getElementById('ai-error').textContent = 'ai 답변 중 에러가 발생하였습니다. 다시 시도해주세요.';
            document.getElementById('ai-error').classList.remove('d-none');
            console.error(error);
        } finally {
            document.getElementById('ai-loading').classList.add('d-none');

            btn.disabled = false;
        }
    });
}

const ALLOWED_IMAGE_EXT = new Set(['jpg', 'jpeg', 'png', 'gif', 'bmp', 'webp']);

function isAllowedImage(file) {

    const filename = file.name;
    const fileExt = filename.substring(filename.lastIndexOf('.') + 1);

    return ALLOWED_IMAGE_EXT.has(fileExt.toLowerCase());
}

const LOW_STOCK = 10;

function renderStock(stock) {
    return `
    <p class="mt-3 fs-5 fw-bold ${stock == 0 ? 'text-secondary' : stock <= LOW_STOCK ? 'text-danger' : 'text-success'}">
        ${stock == 0 ? '품절' : stock <= LOW_STOCK ? stock + '개 남음' : '재고 있음'}
    </p>`;
}