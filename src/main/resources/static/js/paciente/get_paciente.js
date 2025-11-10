import { eliminarPaciente } from './delete_paciente.js';

document.addEventListener("DOMContentLoaded", () => {
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    // Botón crear
    document.getElementById("btnCrear").addEventListener("click", () => {
        window.location.href = "datosPacientes.html";
    });

    let pacientesGlobal = [];

    // Cargar lista de pacientes
    fetch("/paciente")
        .then(res => res.json())
        .then(data => {
            pacientesGlobal = data;
            renderPacientes(data);
        })
        .catch(() => toastError.show());

    function renderPacientes(pacientes) {
        const tbody = document.getElementById("pacienteTableBody");
        tbody.innerHTML = "";

        pacientes.forEach(p => {
            const tr = document.createElement("tr");
            tr.id = `pac-${p.id}`;
            tr.innerHTML = `
                <td>${p.id}</td>
                <td>${p.nombre}</td>
                <td>${p.apellido}</td>
                <td>${p.numeroContacto}</td>
                <td>${p.fechaIngreso}</td>
                <td>${p.domicilio ? `${p.domicilio.calle} ${p.domicilio.numero}, ${p.domicilio.localidad}` : "-"}</td>
                <td>${p.email}</td>
                <td>
                    <button class="btn btn-primary btn-sm me-1 solo-admin">✏️</button>
                    <button class="btn btn-danger btn-sm solo-admin">🗑️</button>
                </td>
            `;
            tbody.appendChild(tr);

            // Editar
            tr.querySelector(".btn-primary").addEventListener("click", () => {
                editarPaciente(p.id);
            });

            // Eliminar
            tr.querySelector(".btn-danger").addEventListener("click", () => {
                eliminarPaciente(p, toastSuccess, toastError);
            });

            const rol = sessionStorage.getItem("rol");
            if (rol !== "ROLE_ADMIN") {
                document.querySelectorAll('.solo-admin').forEach(el => el.style.display = 'none');
            }
        });
    }

    // Función para editar paciente
    window.editarPaciente = (id) => {
        window.location.href = `datosPacientes.html?id=${id}`;
    };
});
