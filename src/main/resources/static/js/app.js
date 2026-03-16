/* ================================================
   Inventory Management System — App Scripts
   ================================================ */

document.addEventListener('DOMContentLoaded', function () {

    // ---- Sidebar Toggle (Mobile) ----
    const sidebar = document.querySelector('.sidebar');
    const toggle = document.getElementById('sidebarToggle');

    if (toggle && sidebar) {
        toggle.addEventListener('click', function () {
            sidebar.classList.toggle('open');
        });
        // Close sidebar when clicking outside on mobile
        document.addEventListener('click', function (e) {
            if (window.innerWidth <= 768 && sidebar.classList.contains('open')
                && !sidebar.contains(e.target) && !toggle.contains(e.target)) {
                sidebar.classList.remove('open');
            }
        });
    }

    // ---- Active Nav Link ----
    const currentPath = window.location.pathname;
    document.querySelectorAll('.nav-link').forEach(function (link) {
        const href = link.getAttribute('href');
        if (href && currentPath.startsWith(href) && href !== '/') {
            link.classList.add('active');
        } else if (href === '/dashboard' && (currentPath === '/' || currentPath === '/dashboard')) {
            link.classList.add('active');
        }
    });

    // ---- Alert Auto‑Dismiss ----
    document.querySelectorAll('.alert').forEach(function (alert) {
        // Close button
        const closeBtn = alert.querySelector('.close-alert');
        if (closeBtn) {
            closeBtn.addEventListener('click', function () {
                alert.style.opacity = '0';
                alert.style.transform = 'translateY(-8px)';
                setTimeout(function () { alert.remove(); }, 200);
            });
        }
        // Auto dismiss after 5 seconds
        setTimeout(function () {
            if (alert.parentNode) {
                alert.style.opacity = '0';
                alert.style.transform = 'translateY(-8px)';
                setTimeout(function () { if (alert.parentNode) alert.remove(); }, 200);
            }
        }, 5000);
    });

    // ---- Delete Confirmation ----
    document.querySelectorAll('.btn-delete').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            if (!confirm('Are you sure you want to delete this item? This action cannot be undone.')) {
                e.preventDefault();
            }
        });
    });
});
