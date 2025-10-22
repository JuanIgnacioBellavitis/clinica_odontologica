function eliminarPaciente(id) {
    if (!confirm("¿Seguro que desea eliminar este paciente?")) return;

    fetch(`/paciente/eliminar/${id}`, { method: 'DELETE' })
        .then(res => {
            if (res.ok) {
                document.getElementById(`tr_${id}`).remove();
                mostrarToast("Éxito", "Paciente eliminado correctamente.", "success");
            } else {
                mostrarToast("Error", "No se pudo eliminar el paciente.", "error");
            }
        })
        .catch(() => mostrarToast("Error", "Error al conectar con el servidor.", "error"));
}