document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formOdontologo");
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get("id");
    const titulo = document.getElementById("tituloForm");
    const toastSuccess = new bootstrap.Toast(document.getElementById("toastSuccess"));
    const toastError = new bootstrap.Toast(document.getElementById("toastError"));

    if (id) {
        titulo.textContent = "Modificar Odontologo";
        fetch(`/odontologo/${id}`)
            .then(res => res.json())
            .then(p => {
                // Campos simples
                document.getElementById("nombre").value = p.nombre;
                document.getElementById("apellido").value = p.apellido;
                document.getElementById("matricula").value = p.matricula;

            })
            .catch(() => toastError.show());
    } else {
        titulo.textContent = "Crear Nuevo Odontologo";
    }

    form.addEventListener("submit", e => {
        e.preventDefault();
        if (!form.checkValidity()) {
            e.stopPropagation();
            form.classList.add("was-validated");
            return;
        }

        const odontologo = {
            nombre: nombre.value.trim(),
            apellido: apellido.value.trim(),
            matricula: parseInt(matricula.value),
        };

        const method = id ? "PUT" : "POST";
        const endpoint = id ? `/odontologo/modificar/${id}` : "/odontologo/crear";

        fetch(endpoint, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(odontologo)
        })
            .then(res => {
                if (!res.ok) throw new Error();
                toastSuccess.show();
                setTimeout(() => (window.location.href = "odontologoLista.html"), 1000);
            })
            .catch(() => toastError.show());
    });
});
