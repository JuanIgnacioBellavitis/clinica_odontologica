document.addEventListener("DOMContentLoaded", () => {
    const pacienteSelect = document.getElementById("pacienteSelect");
    const odontologoSelect = document.getElementById("odontologoSelect");
    const formTurno = document.getElementById("formTurno");
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    // --- Cargar pacientes ---
    fetch("/paciente")
        .then(res => res.json())
        .then(pacientes => {
            pacientes.forEach(p => {
                const option = document.createElement("option");
                option.value = p.id;
                option.textContent = `${p.nombre} ${p.apellido}`;
                pacienteSelect.appendChild(option);
            });
        })
        .catch(err => {
            console.error("Error al obtener pacientes:", err);
            toastError.show();
        });

    // --- Cargar odontólogos ---
    fetch("/odontologo")
        .then(res => res.json())
        .then(odontologos => {
            odontologos.forEach(o => {
                const option = document.createElement("option");
                option.value = o.id;
                option.textContent = `Dr/a. ${o.nombre} ${o.apellido}`;
                odontologoSelect.appendChild(option);
            });
        })
        .catch(err => {
            console.error("Error al obtener odontólogos:", err);
            toastError.show();
        });

    // --- Guardar turno ---
    formTurno.addEventListener("submit", async (e) => {
        e.preventDefault();

        // Validación de Bootstrap
        if (!formTurno.checkValidity()) {
            e.stopPropagation();
            formTurno.classList.add("was-validated");
            return;
        }

        const turno = {
            paciente: { id: parseInt(pacienteSelect.value) },
            odontologo: { id: parseInt(odontologoSelect.value) },
            fecha: document.getElementById("fecha").value
        };

        try {
            const res = await fetch("/turno/crear", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(turno)
            });

            if (res.ok) {
                toastSuccess.show();
                // Esperar 1.5 segundos y redirigir al histórico
                setTimeout(() => {
                    window.location.href = "turnoLista.html";
                }, 1500);
            } else {
                toastError.show();
            }
        } catch (err) {
            console.error("Error al crear turno:", err);
            toastError.show();
        }
    });
});
