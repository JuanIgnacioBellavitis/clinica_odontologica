document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formPaciente");
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");
    const titulo = document.getElementById("tituloForm");
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    if (id) {
        titulo.textContent = "Modificar Paciente";
        fetch(`/paciente/${id}`)
            .then(res => res.json())
            .then(p => {
                // Campos simples
                document.getElementById("nombre").value = p.nombre;
                document.getElementById("apellido").value = p.apellido;
                document.getElementById("numeroContacto").value = p.numeroContacto;
                document.getElementById("fechaIngreso").value = p.fechaIngreso;
                document.getElementById("email").value = p.email;

                // Campos de domicilio
                if (p.domicilio) {
                    console.log("domicilio", p.domicilio)
                    document.getElementById("domicilio_id").value = p.domicilio.id || "";
                    document.getElementById("calle").value = p.domicilio.calle || "";
                    document.getElementById("numero").value = p.domicilio.numero || "";
                    document.getElementById("localidad").value = p.domicilio.localidad || "";
                    document.getElementById("provincia").value = p.domicilio.provincia || "";
                }
            })
            .catch(() => toastError.show());
    } else {
        titulo.textContent = "Crear Nuevo Paciente";
    }

    form.addEventListener("submit", e => {
        e.preventDefault();
        if (!form.checkValidity()) {
            e.stopPropagation();
            form.classList.add("was-validated");
            return;
        }

        const paciente = {
            nombre: nombre.value.trim(),
            apellido: apellido.value.trim(),
            numeroContacto: parseInt(numeroContacto.value),
            fechaIngreso: fechaIngreso.value,
            email: email.value.trim(),
            domicilio: {
                calle: calle.value.trim(),
                numero: parseInt(numero.value),
                localidad: localidad.value.trim(),
                provincia: provincia.value.trim()
            }
        };

        if (id) {
            const domicilioId = document.getElementById("domicilio_id").value;
            if (domicilioId) {
                paciente.domicilio.id = parseInt(domicilioId);
            }
        }

        const method = id ? "PUT" : "POST";
        const endpoint = id ? `/paciente/modificar/${id}` : "/paciente/crear";

        fetch(endpoint, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(paciente)
        })
            .then(res => {
                if (!res.ok) throw new Error();
                toastSuccess.show();
                setTimeout(() => (window.location.href = "pacienteLista.html"), 1000);
            })
            .catch(() => toastError.show());
    });
});
