function processRequest(e) {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(xhr)
        }
    }


    var tab =
    {
        "tab": [
            {
                "firstname": "John",
                "lastname": "Doe",
                "email": "john@exemple.com"
            },
            {
                "firstname": "Mary",
                "lastname": "Moe",
                "email": "mary@exemple.com"
            },
            {
                "firstname": "July",
                "lastname": "Dooley",
                "email": "july@exemple.com"
            },
            {
                "firstname": "Etienne",
                "lastname": "Gandilhon",
                "email": "etienne@exemple.com"
            }
        ]
    }
    /*var xhr = new XMLHttpRequest();
    xhr.open( "GET", "http://localhost:8080/json");
    xhr.send();

    xhr.onreadystatechange = processRequest;*/

    for (var i = 0; i < tab.tab.length; i++) {
        var array = tab.tab[i];
        var tr = document.createElement("tr");
        var tdFirstname = document.createElement("td");
        var tdLastname = document.createElement("td");
        var tdEmail = document.createElement("td");

        tdFirstname.appendChild(document.createTextNode(array.firstname));
        tdLastname.appendChild(document.createTextNode(array.lastname));
        tdEmail.appendChild(document.createTextNode(array.email));

        tr.appendChild(tdFirstname);
        tr.appendChild(tdLastname);
        tr.appendChild(tdEmail);
        var tableau = document.getElementById("tableau");
        tableau.appendChild(tr);
    }