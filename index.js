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
            //TODO: Change the style accordingly

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