var server = 'http://localhost:8080/Security/api/loginapi/';
function login() {

    var username = document.getElementById('username').value.toString();
    var password = document.getElementById('password').value.toString();

    if (!escape(username) && !escape(password)) {
        alert("Login forbidden, due to nasty stuff (special chars)");
        window.location.href = 'http://localhost:8080/Security/index.html';
    }
    var input = {
        username: username,
        password: password
    };
    $.ajax({
        url: server + 'login',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(input),
        success: function (data) {
            var link = data;
            window.location.href = 'http://localhost:8080/Security/' + link;
        },
        error: function (xhr, ajaxOptions, thrownError) {
            console.log(xhr.status);
            console.log(thrownError);
        }

    });

}

function signup() {

    var username = document.getElementById('username').value.toString();
    var password = document.getElementById('password').value.toString();

    if (!escape(username) && !escape(password)) {
        alert("Login forbidden, due to nasty stuff (special chars)");
        window.location.href = 'http://localhost:8080/Security/index.html';
    }
    var input = {
        username: username,
        password: password
    };

    function CheckPassword(password)
    {
        var passw = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,50}$/;
        if (inputtxt.value.match(passw))
        {
            $.ajax({
                url: server + 'signup',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify(input),
                success: function (data) {
                    var link = data;
                    window.location.href = 'http://localhost:8080/Security/' + link;
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(xhr.status);
                    console.log(thrownError);
                }

            });
        }
        else
        {
            alert('insufficient strength of password');
        }
    }


}

