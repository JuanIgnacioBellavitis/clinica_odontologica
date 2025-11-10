import { eliminarOdontologo } from "./delete_odontologo.js";

document.addEventListener("DOMContentLoaded", () => {
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    // Botón crear
    document.getElementById("btnCrear").addEventListener("click", () => {
        window.location.href = "datosOdontologos.html";
    });

    let odontologoGlobal = [];

    // Cargar lista
    fetch("/odontologo")
        .then(res => res.json())
        .then(data => {
            odontologoGlobal = data;
            renderOdontologos(data);
        })
        .catch(() => toastError.show());

    function renderOdontologos(odontologos) {
        const tbody = document.getElementById("odontologoTableBody");
        tbody.innerHTML = "";

        odontologos.forEach(o => {
            tbody.innerHTML += `
              <tr id="pac-${o.id}">
              <td>${o.id}</td>
              <td>${o.nombre}</td>
              <td>${o.apellido}</td>
              <td>${o.matricula}</td>
                <td>
                    <button class="btn btn-primary btn-sm me-1 solo-admin">✏️</button>
                    <button class="btn btn-danger btn-sm solo-admin">🗑️</button>
                </td>
            </tr>`;

            // Eliminar
            tbody.querySelector(".btn-danger").addEventListener("click", () => {
                eliminarOdontologo(o, toastSuccess, toastError);
            });

            const rol = sessionStorage.getItem("rol");
            if (rol !== "ROLE_ADMIN") {
                document.querySelectorAll('.solo-admin').forEach(el => el.style.display = 'none');
            }
        });
    }

    // Modificar
    window.editarOdontologo = (id) => {
        window.location.href = `datosOdontologos.html?id=${id}`;
    };
});
