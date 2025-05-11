document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("myForm");
    const fieldIds = ["vezeteknev", "keresztnev", "email", "szuletes", "telefon", "egyeb", "varos", "utca", "haz_szam"];

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const data = Object.fromEntries(
            fieldIds.map(id => [id, document.getElementById(id).value])
        );

        try {
            const submitRes = await fetch("http://127.0.0.1:8080/submit", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });
            console.log("Submitted!", submitRes);

            const postsRes = await fetch("../posts.json");
            const posts = await postsRes.json();
            console.log(posts);

            posts.forEach((val, i) => {
                if (val === "Wrong") {
                    const invalid = document.getElementById(`Invalid${i}`);
                    if (invalid) {
                        invalid.innerHTML = "Hibás vagy Üres a rublika!";
                        invalid.style.color = "red";
                    }

                    const fieldMap = [0, 1, 2, 3, 4, null, 6, 7, 8];
                    const fieldIndex = fieldMap[i];
                    if (fieldIndex !== null) {
                        const input = document.getElementById(fieldIds[fieldIndex]);
                        if (input) input.style.border = "1px solid #ff0000";
                    }
                }
            });

            const deleteRes = await fetch("http://127.0.0.1:8080/submit", {
                method: "POST",
                headers: { "Content-Type": "text/plain" },
                body: "DELETE"
            });
            console.log("Sent delete request", deleteRes);

        } catch (err) {
            console.error("Error:", err);
        }


        const inputs = document.querySelectorAll('input:not([type="submit"])');

        inputs.forEach((input, index) => {
            const invalidMsg = document.getElementById(`Invalid${index}`);

            input.addEventListener('click', () => {
                input.style.border = '1px solid #ccc';
                if (invalidMsg) invalidMsg.textContent = '';
            });

            input.addEventListener('blur', () => {
                const isOptional = input.id === 'egyeb';
                if (!isOptional && input.value.trim() === '') {
                    input.style.border = '1px solid #ff0000';
                    if (invalidMsg) invalidMsg.textContent = 'Hibás vagy Üres a rublika!';
                }
            });
        });
    });
});