document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formPaciente");
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");
    const titulo = document.getElementById("tituloForm");
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    console.log("ID: ", id);
    if (id) {
        titulo.textContent = "Modificar Paciente";
        fetch(`/paciente/${id}`)
            .then(res => res.json())
            .then(p => {
                Object.keys(p).forEach(k => {
                    if (document.getElementById(k)) document.getElementById(k).value = p[k];
                });
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
            domicilio: domicilio.value.trim(),
            email: email.value.trim()
        };

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
