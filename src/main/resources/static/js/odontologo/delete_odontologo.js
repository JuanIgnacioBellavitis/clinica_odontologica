export function eliminarOdontologo(odontolgo, toastSuccess, toastError) {
    if (!confirm("¿Seguro que desea eliminar este odontologo?")) return;

    fetch(`/odontologo/eliminar/${odontolgo.id}`, {
        method: 'DELETE',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(odontolgo)
    })
        .then(res => {
            if (res.ok) {
                document.getElementById(`pac-${id}`).remove();
                toastSuccess("Éxito", "Odontologo eliminado correctamente.", "success");
            } else {
                toastError("Error", "No se pudo eliminar el odontologo.", "error");
            }
        })
        .catch(() => toastError("Error", "Error al conectar con el servidor.", "error"));
}