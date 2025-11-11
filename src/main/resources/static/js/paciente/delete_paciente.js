let pacienteAEliminar = null;

export function eliminarPaciente(paciente, toastSuccess, toastError) {
    pacienteAEliminar = paciente;

    const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    modal.show();

    const confirmBtn = document.getElementById("btnConfirmDelete");

    confirmBtn.replaceWith(confirmBtn.cloneNode(true));
    const nuevoBoton = document.getElementById("btnConfirmDelete");

    nuevoBoton.addEventListener("click", () => {
        modal.hide();

        fetch(`/paciente/eliminar/${pacienteAEliminar.id}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(pacienteAEliminar)
        })
            .then(res => {
                if (res.ok) {
                    document.getElementById(`pac-${pacienteAEliminar.id}`).remove();
                    toastSuccess.show();
                } else throw new Error();
            })
            .catch(() => toastError.show());
    });
}
