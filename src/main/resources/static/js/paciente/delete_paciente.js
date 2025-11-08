export function eliminarPaciente(paciente, toastSuccess, toastError) {
    if (!confirm("¿Eliminar este paciente?")) return;

    fetch(`/paciente/eliminar/${paciente.id}`, {
        method: "DELETE",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(paciente)
    })
        .then(res => {
            if (res.ok) {
                document.getElementById(`pac-${paciente.id}`).remove();
                toastSuccess.show();
            } else throw new Error();
        })
        .catch(() => toastError.show());
}
