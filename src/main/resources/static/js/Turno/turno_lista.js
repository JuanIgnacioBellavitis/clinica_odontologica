document.addEventListener("DOMContentLoaded", () => {
    const turnoTableBody = document.getElementById("turnoTableBody");
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    // Función para cargar todos los turnos
    const cargarTurnos = () => {
        fetch("/turno/todos") // Endpoint que devuelve todos los turnos
            .then(res => res.json())
            .then(turnos => {
                turnoTableBody.innerHTML = ''; // Limpiar tabla
                turnos.forEach(turno => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                        <td>${turno.id}</td>
                        <td>${turno.paciente.nombre} ${turno.paciente.apellido}</td>
                        <td>${turno.odontologo.nombre} ${turno.odontologo.apellido}</td>
                        <td>${turno.fecha}</td>
                    `;
                    turnoTableBody.appendChild(row);
                });
            })
            .catch(err => {
                console.error("Error al cargar turnos:", err);
                toastError.show();
            });
    };

    // Cargar los turnos al iniciar la página
    cargarTurnos();
});