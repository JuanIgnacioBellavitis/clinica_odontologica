document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formTurno");
    if (!form) return;

    const pacienteSelect = document.getElementById("pacienteSelect");
    const odontologoSelect = document.getElementById("odontologoSelect");
    const fechaInput = document.getElementById("fecha");

    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    // Detectar si hay ID en la URL (para edición)
    const params = new URLSearchParams(window.location.search);
    const turnoId = params.get("id");

    function formatearFechaParaInput(fechaStr) {
        // Solo usar si la fecha no está en formato ISO (dd/mm/yyyy -> yyyy-mm-dd)
        const [dd, mm, yyyy] = fechaStr.split('/');
        return `${yyyy}-${mm.padStart(2, '0')}-${dd.padStart(2, '0')}`;
    }

    // Cargar pacientes y odontólogos en paralelo
	Promise.all([
	    fetch("/paciente").then(res => res.json()),
	    fetch("/odontologo").then(res => res.json())
	])
	.then(([pacientes, odontologos]) => {
	    pacienteSelect.innerHTML = pacientes
	        .map(p => `<option value="${p.id}">${p.nombre} ${p.apellido}</option>`)
	        .join("");
	    odontologoSelect.innerHTML = odontologos
	        .map(o => `<option value="${o.id}">${o.nombre} ${o.apellido}</option>`)
	        .join("");

	    if (turnoId && !isNaN(turnoId)) {
	        // Aquí, usa setTimeout 0 para asegurar que el DOM termina de procesar el innerHTML
	        setTimeout(() => cargarTurno(turnoId), 0);
	    }
	})
	.catch(() => toastError.show());

	function cargarTurno(id) {
	    console.log("Cargando turno con ID:", id);
	    fetch(`/turno/${id}`)
	        .then(res => {
	            if (!res.ok) throw new Error(`Error al cargar turno: ${res.status}`);
	            return res.json();
	        })
	        .then(turno => {
	            console.log("Turno recibido:", turno);

	            // Convertir a string para asegurar coincidencia con value
	            pacienteSelect.value = String(turno.paciente.id);
	            odontologoSelect.value = String(turno.odontologo.id);

	            // Verificación para debugging:
	           
				const pacienteIdStr = String(turno.paciente.id);
				if (pacienteSelect.querySelector(`option[value="${pacienteIdStr}"]`)) {
				    pacienteSelect.value = pacienteIdStr;
				} else {
				    console.warn(`Paciente con id ${pacienteIdStr} NO encontrado en opciones`);
				}

				const odontologoIdStr = String(turno.odontologo.id);
				if (odontologoSelect.querySelector(`option[value="${odontologoIdStr}"]`)) {
				    odontologoSelect.value = odontologoIdStr;
				} else {
				    console.warn(`Odontólogo con id ${odontologoIdStr} NO encontrado en opciones`);
				}
	            // Fecha en formato correcto
	            if (turno.fecha.includes('T')) {
	                fechaInput.value = turno.fecha.split('T')[0];
	            } else if (turno.fecha.includes('/')) {
	                fechaInput.value = formatearFechaParaInput(turno.fecha);
	            } else {
	                fechaInput.value = turno.fecha;
	            }
	            console.log("Fecha asignada al input:", fechaInput.value);
	        })
	        .catch(err => {
	            console.error("Error en cargarTurno:", err);
	            toastError.show();
	        });
	}

    // Guardar o actualizar turno
    form.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Validación de Bootstrap
        if (!form.checkValidity()) {
            e.stopPropagation();
            form.classList.add("was-validated");
            return;
        }

        const turno = {
            paciente: { id: parseInt(pacienteSelect.value) },
            odontologo: { id: parseInt(odontologoSelect.value) },
            fecha: fechaInput.value
        };

        try {
            let url = "/turno/crear";
            let method = "POST";

            if (turnoId) {
                url = `/turno/modificar/${turnoId}`;
                method = "PUT";
            }

            const res = await fetch(url, {
                method: method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(turno)
            });

            if (res.ok) {
                toastSuccess.show();
                setTimeout(() => window.location.href = "turnoLista.html", 1500);
            } else {
                toastError.show();
            }
        } catch (err) {
            console.error("Error al guardar turno:", err);
            toastError.show();
        }
    });
});
