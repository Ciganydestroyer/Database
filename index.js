document.addEventListener("DOMContentLoaded",() => {
    document.getElementById("myForm").addEventListener("submit", function(event) {
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

        console.log(Data)
        console.log("Form submitted!");
    });
});