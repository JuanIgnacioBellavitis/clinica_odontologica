document.addEventListener("DOMContentLoaded", () => {
    const turnoTableBody = document.getElementById("turnoTableBody");
    const btnCrearTurno = document.getElementById("btnCrearTurno");
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));

    // Redirigir a formulario para crear turno
    btnCrearTurno.addEventListener("click", () => {
        window.location.href = "datosTurno.html";
    });

    // Cargar turnos
    fetch("/turno/todos")
        .then(res => res.json())
        .then(turnos => {
            turnoTableBody.innerHTML = "";
            turnos.forEach(turno => {
                const row = document.createElement("tr");
                row.id = `turno-${turno.id}`;
                row.innerHTML = `
                    <td>${turno.id}</td>
                    <td>${turno.paciente?.nombre || ""} ${turno.paciente?.apellido || ""}</td>
                    <td>${turno.odontologo?.nombre || ""} ${turno.odontologo?.apellido || ""}</td>
                    <td>${turno.fecha}</td>
    
                    <td>
                        <button class="btn btn-primary btn-sm me-1">✏️</button>
                        <button class="btn btn-danger btn-sm">🗑️</button>
                    </td>
                `;
                turnoTableBody.appendChild(row);

                // 👉 Editar turno
                row.querySelector(".btn-primary").addEventListener("click", () => {
                    window.location.href = `datosTurno.html?id=${turno.id}`;
                });

                // 👉 Eliminar turno
                row.querySelector(".btn-danger").addEventListener("click", () => {
                    if (confirm(`¿Seguro que deseas eliminar el turno #${turno.id}?`)) {
                        fetch(`/turno/eliminar/${turno.id}`, { method: "DELETE" })
                            .then(res => {
                                if (!res.ok) throw new Error();
                                row.remove();
                                toastSuccess.show();
                            })
                            .catch(() => toastError.show());
                    }
                });
            });
        })
        .catch(() => toastError.show());
});
