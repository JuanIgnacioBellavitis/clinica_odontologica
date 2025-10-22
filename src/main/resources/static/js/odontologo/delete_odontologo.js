function eliminarOdontologo(id) {
    if (!confirm("¿Seguro que desea eliminar este odontologo?")) return;

    fetch(`/odontologo/eliminar/${id}`, { method: 'DELETE' })
        .then(res => {
            if (res.ok) {
                document.getElementById(`tr_${id}`).remove();
                mostrarToast("Éxito", "Odontologo eliminado correctamente.", "success");
            } else {
                mostrarToast("Error", "No se pudo eliminar el odontologo.", "error");
            }
        })
        .catch(() => mostrarToast("Error", "Error al conectar con el servidor.", "error"));
}