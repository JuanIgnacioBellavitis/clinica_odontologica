let odontologoAEliminar = null;

export function eliminarOdontologo(odontologo, toastSuccess, toastError) {
    odontologoAEliminar = odontologo;

    const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    modal.show();

    const confirmBtn = document.getElementById("btnConfirmDelete");

    confirmBtn.replaceWith(confirmBtn.cloneNode(true));
    const nuevoBoton = document.getElementById("btnConfirmDelete");

    nuevoBoton.addEventListener("click", () => {
        modal.hide();

        fetch(`/odontologo/eliminar/${odontologo.id}`, {
            method: 'DELETE',
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(odontologo)
        })
            .then(res => {
                if (res.ok) {
                    document.getElementById(`odo-${odontologo.id}`).remove();
                    toastSuccess.show();
                } else throw new Error();
            })
            .catch(() =>  toastError.show())
    })
}