let turnoAEliminar = null;

export function eliminarTurno(turno, toastSuccess, toastError) {
    turnoAEliminar = turno;

    const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    modal.show();

    const confirmBtn = document.getElementById("btnConfirmDelete");

    confirmBtn.replaceWith(confirmBtn.cloneNode(true));
    const nuevoBoton = document.getElementById("btnConfirmDelete");

    nuevoBoton.addEventListener("click", () => {
        modal.hide();

        fetch(`/turno/eliminar/${turno.id}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(turnoAEliminar)
        })
            .then(res => {
                if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
                document.getElementById(`turno-${turno.id}`)?.remove();
                toastSuccess.show();
            })
            .catch(() =>  toastError.show());
    });
}