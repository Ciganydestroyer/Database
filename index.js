document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("myForm").addEventListener("submit", async function(event) {
        event.preventDefault();

        const getValue = id => document.getElementById(id).value;

        const Data = {
            vezeteknev: getValue("vezeteknev"),
            keresztnev: getValue("keresztnev"),
            email: getValue("email"),
            szuletes: getValue("szuletes"),
            telefon: getValue("telefon"),
            egyeb: getValue("egyeb"),
            varos: getValue("varos"),
            utca: getValue("utca"),
            haz_szam: getValue("haz_szam")
        };

        try {
            const submitResponse = await fetch("http://127.0.0.1:8080/submit", {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify(Data)
            });
            console.log("Submitted!", submitResponse);

            const postsResponse = await fetch("../posts.json");
            const postsJson = await postsResponse.json();
            console.log(postsJson);

            for (let i = 0; i < postsJson.length; i++) {
                if (postsJson[i] === "Wrong") {
                    document.getElementById(`Invalid${i}`).innerHTML = "Hibás vagy Üres a rublika!"
                    document.getElementById(`Invalid${i}`).style.color = "red"
                    let Form;
                    switch (i) {
                        case 0:
                            Form = document.getElementById("vezeteknev").style.border = "1px solid #ff0000"
                            break;
                        case 1:
                            Form = document.getElementById("keresztnev").style.border = "1px solid #ff0000"
                            break;
                        case 2:
                            Form = document.getElementById("email").style.border = "1px solid #ff0000"
                            break;
                        case 3:
                            Form = document.getElementById("szuletes").style.border = "1px solid #ff0000"
                            break;
                        case 4:
                            Form = document.getElementById("telefon").style.border = "1px solid #ff0000"
                            break;
                        case 6:
                            Form = document.getElementById("varos").style.border = "1px solid #ff0000"
                            break;
                        case 7:
                            Form = document.getElementById("utca").style.border = "1px solid #ff0000"
                            break;
                        case 8:
                            Form = document.getElementById("haz_szam").style.border = "1px solid #ff0000"
                            break;
                    }
                }
            }

            const DeleteResponse = await fetch("http://127.0.0.1:8080/submit", {
                method: "POST",
                headers: {"Content-Type": "text/plain"},
                body: "DELETE"
            });
            console.log("Sent delete request", DeleteResponse);

        } catch (error) {
            console.error("Error:", error);
        }
    });
});