fetch('fragments/navbar.html')
    .then(res => res.text())
    .then(html => {
        document.getElementById('navbar-container').innerHTML = html;
        inicializarNavbar();
    })
    .catch(err => console.error('Error cargando navbar:', err));

function inicializarNavbar() {
    fetch('/auth/me', { credentials: 'include' })
        .then(response => {
            if (!response.ok) throw new Error('No autenticado');
            return response.json();
        })
        .then(data => {
            console.log("Usuario autenticado:", data);
            sessionStorage.setItem("rol", data.rol);

            // Mostrar nombre completo
            const span = document.getElementById('usuarioActual');
            if (span) span.textContent = `${data.nombre} ${data.apellido}`;

            // Ocultar elementos solo para admin
            if (data.rol !== 'ROLE_ADMIN') {
                document.querySelectorAll('.solo-admin').forEach(el => el.style.display = 'none');
            }

            // Cerrar sesión
            const btnCerrarSesion = document.getElementById('btnCerrarSesion');
            if (btnCerrarSesion) {
                btnCerrarSesion.addEventListener('click', () => {
                    fetch('/auth/logout', { method: 'POST', credentials: 'include' })
                        .finally(() => window.location.href = '/login.html');
                });
            }
        })
        .catch(() => {
            window.location.href = '/login.html';
        });
}
