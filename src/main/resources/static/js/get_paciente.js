document.addEventListener("DOMContentLoaded", () => {
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    // Botón crear
    document.getElementById("btnCrear").addEventListener("click", () => {
        window.location.href = "datosPacientes.html";
    });

    // Cargar lista
    fetch("/paciente")
        .then(res => res.json())
        .then(data => renderPacientes(data))
        .catch(() => toastError.show());

    function renderPacientes(pacientes) {
        const tbody = document.getElementById("pacienteTableBody");
        tbody.innerHTML = "";
        pacientes.forEach(p => {
            tbody.innerHTML += `
            <tr id="pac-${p.id}">
          <td>${p.id}</td>
          <td>${p.nombre}</td>
          <td>${p.apellido}</td>
          <td>${p.numeroContacto}</td>
          <td>${p.fechaIngreso}</td>
          <td>${p.domicilio}</td>
          <td>${p.email}</td>
          <td>
            <button class="btn btn-primary btn-sm me-1" onclick="editarPaciente(${p.id})">✏️</button>
            <button class="btn btn-danger btn-sm" onclick="eliminarPaciente(${p.id})">🗑️</button>
          </td>
        </tr>`;
        });
    }

    // Eliminar
    window.eliminarPaciente = (id) => {
        if (!confirm("¿Eliminar este paciente?")) return;
        fetch(`/paciente/eliminar/${id}`, { method: "DELETE" })
            .then(res => {
                if (res.ok) {
                    document.getElementById(`pac-${id}`).remove();
                    toastSuccess.show();
                    setTimeout(() => (window.location.href = "pacienteLista.html"), 500);
                } else throw new Error();
            })
            .catch(() => toastError.show());
    };

    // Modificar
    window.editarPaciente = (id) => {
        window.location.href = `datosPacientes.html?id=${id}`;
    };
});
