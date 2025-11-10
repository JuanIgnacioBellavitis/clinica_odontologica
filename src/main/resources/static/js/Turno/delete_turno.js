function eliminarTurno(turno, toastSuccess, toastError) {
    if (!confirm(`¿Seguro que deseas eliminar el turno #${turno.id}?`)) return;

    fetch(`/turno/eliminar/${turno.id}`, { 
        method: "DELETE"
    })
    .then(res => {
        if (!res.ok) throw new Error(`Error HTTP ${res.status}`);
        document.getElementById(`turno-${turno.id}`)?.remove();
        toastSuccess.show();
    })
    .catch(err => {
        console.error("Error al eliminar el turno:", err);
        toastError.show();
    });
}