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
            const span = document.getElementById('usuarioActual');
            if (span) {
                span.textContent = `Bienvenido/a ${data.nombre} ${data.apellido}`;
            }

            if (data.rol !== 'ROLE_ADMIN') {
                document.querySelectorAll('.solo-admin').forEach(el => el.style.display = 'none');
            }
        })
        .catch(() => {
            window.location.href = '/login.html';
        });
}
