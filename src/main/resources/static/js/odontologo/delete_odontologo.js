export function eliminarOdontologo(odontologo, toastSuccess, toastError) {
    if (!confirm("¿Seguro que desea eliminar este odontologo?")) return;

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
        .catch(() =>  toastError.show());
}