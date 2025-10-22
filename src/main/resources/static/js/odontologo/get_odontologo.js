document.addEventListener("DOMContentLoaded", () => {
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    // Botón crear
    document.getElementById("btnCrear").addEventListener("click", () => {
        window.location.href = "datosOdontologos.html";
    });

    // Cargar lista
    fetch("/odontologo")
        .then(res => res.json())
        .then(data => renderOdontologos(data))
        .catch(() => toastError.show());

    function renderOdontologos(odontologos) {
        const tbody = document.getElementById("odontologoTableBody");
        tbody.innerHTML = "";
        odontologos.forEach(p => {
            tbody.innerHTML += `
            <tr id="pac-${p.id}">
          <td>${p.id}</td>
          <td>${p.nombre}</td>
          <td>${p.apellido}</td>
          <td>${p.matricula}</td>
          <td>
            <button class="btn btn-primary btn-sm me-1" onclick="editarOdontologo(${p.id})">✏️</button>
            <button class="btn btn-danger btn-sm" onclick="eliminarOdontologo(${p.id})">🗑️</button>
          </td>
        </tr>`;
        });
    }

    // Eliminar
    window.eliminarOdontologo = (id) => {
        if (!confirm("¿Eliminar este odontologo?")) return;
        fetch(`/odontologo/eliminar/${id}`, { method: "DELETE" })
            .then(res => {
                if (res.ok) {
                    document.getElementById(`pac-${id}`).remove();
                    toastSuccess.show();
                    setTimeout(() => (window.location.href = "odontologoLista.html"), 1500);
                } else throw new Error();
            })
            .catch(() => toastError.show());
    };

    // Modificar
    window.editarOdontologo = (id) => {
        window.location.href = `datosOdontologos.html?id=${id}`;
    };
});
