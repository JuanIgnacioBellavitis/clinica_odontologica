function modificarPaciente(id) {
    const nuevoNombre = prompt("Nuevo nombre:");
    const nuevoApellido = prompt("Nuevo apellido:");
    const nuevoNumeroContacto = prompt("Nuevo numero de contacto:");
    const nuevoFechaIngreso = prompt("Nueva fecha de ingreso:");
    const nuevoDomicilio = prompt("Nuevo domicilio:");
    const nuevoEmail = prompt("Nuevo email:");

    if (!nuevoNombre || !nuevoApellido || !nuevoEmail) {
        mostrarToast("Atención", "Debe completar todos los campos.", "error");
        return;
    }

    const paciente = {
        nombre: nuevoNombre,
        apellido: nuevoApellido,
        numeroContacto: nuevoNumeroContacto,
        fechaIngreso: nuevoFechaIngreso,
        domicilio: nuevoDomicilio,
        email: nuevoEmail
    };

    fetch(`/paciente/modificar/${id}`, {
        method: 'PUT',
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(paciente)
    })
        .then(res => {
            if (res.ok) {
                mostrarToast("Éxito", "Paciente modificado correctamente.", "success");
                setTimeout(() => location.reload(), 5000);
            } else {
                mostrarToast("Error", "No se pudo modificar el paciente.", "error");
            }
        })
        .catch(() => mostrarToast("Error", "Error de conexión.", "error"));
}