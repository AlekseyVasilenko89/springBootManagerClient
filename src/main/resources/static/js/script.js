document.addEventListener('DOMContentLoaded', function () {
    tableFunction();

    //Update user
    $("#editUser").click(function () {
        updateUser();
        setTimeout(tableFunction, 500);
    });

    // Add user
    $("#addUser").click(function () {
        addUser();
        clickTable();
        $('#addForm')[0].reset();
        setTimeout(tableFunction, 500);
    });

    // Delete user
    $("#deleteUser").click(function () {
        deleteUser();
        setTimeout(tableFunction, 500);
    });

    function clickTable() {
        $("#nav-home-tab").click();
    }

});

function addUser() {
    //Add new user
    // var user = {};
    var userRoles = [];
    var username = $("#name").val();
    var age = $("#age").val();
    var password = $("#password").val();

    //check the user role, fill the array of userRoles
    if ($('#inlineCheckbox1').is(':checked')) {

        userRoles.push({"id": 1, "authority": "ADMIN"})
    }
    if ($('#inlineCheckbox2').is(':checked')) {
        userRoles.push({"id": 2, "authority": "USER"})
    }

    user = {
        username: username,
        age: age,
        password: password,
        userRoles: userRoles
    };
    //
    $.ajax
    ({
        type: "POST",
        data: JSON.stringify(user),
        url: "/admin/rest/",
        dataType: 'json',
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (status) {
            setTimeout(tableFunction, 500);

        },
        error: function (e) {
        }
    });
}

function updateUser() {
    let user = {};
    let userRoles = [];
    let userId = $("#id").val();
    let username = $("#nameEdit").val();
    let age = $("#ageEdit").val();
    let password = $("#passwordEdit").val();

    //check the user role, fill the array of userRoles
    if ($('#inlineCheckbox3').is(':checked')) {
        userRoles.push({"id": 1, "authority": "ADMIN"})
    }
    if ($('#inlineCheckbox4').is(':checked')) {
        userRoles.push({"id": 2, "authority": "USER"})
    }
    user = {
        id: userId,
        username: username,
        age: age,
        password: password,
        userRoles: userRoles
    };
    //
    $.ajax
    ({
        type: "PUT",
        data: JSON.stringify(user),
        url: "/admin/rest/",
        dataType: 'json',//delete
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (status) {
            setTimeout(tableFunction, 500);
        },
        error: function () {

        }
    });
}

function deleteUser() {
    let userId = $("#id").val();
    //
    $.ajax
    ({
        type: "PUT",
        url: "/admin/rest/" + userId,
        async: false,
        contentType: "application/json; charset=utf-8",
        success: function (status) {
            setTimeout(tableFunction, 500);
        },
        error: function () {

        }
    });
}

function tableFunction() {
    // для генерации select в allUsers.html в модальном окне
    $.ajax({
        url: '/admin/rest/role/',
        type: 'GET',
        contentType: "application/json",
        async: false,
        dataType: 'json',
        success: function (roles) {
            $form = $(".formForJs");

            var select = "<select name=\"roles\" class=\"form-control rolesForJs\" multiple>";

            for (var i = 0; i < roles.length; i++) {
                select +=
                    "<option value='" + roles[i].id + "'>" +
                    roles[i].authority +
                    "</option>";
            }

            $("#selectFoRoles").html(select);
        }
    });

    // скрипт для генерации таблиц в allUsers.html
    $.ajax({
        url: '/admin/rest',
        type: 'GET',
        contentType: "application/json",
        dataType: 'json',
        success: function (allUsers) {

            var tbl = "<table class=\"table table-striped\">";

            tbl +=
                "<tr>" +
                "<th>id</th>" +
                "<th>name</th>" +
                "<th>age</th>" +
                "<th>password</th>" +
                "<th>role</th>" +
                "<th>action</th>" +
                "<th>action</th>" +
                "</tr>";

            // переменная, куда будет собираться имя роли пользователя
            var userRole = "";

            for (var i = 0; i < allUsers.length; i++) {
                var userId = allUsers[i].id;
                tbl += "<tr id='user-" + userId + "'>";

                tbl += "<td class='id'>" + userId + "</td>";
                tbl += "<td class=\"name nameFoChange\">" + allUsers[i].username + "</td>";
                tbl += "<td class=\"email emailFoChange\">" + allUsers[i].age + "</td>";
                tbl += "<td class=\"password passwordFoChange\">" + allUsers[i].password + "</td>";

                for (var j = 0; j < allUsers[i].authorities.length; j++) {
                    userRole += allUsers[i].authorities[j].authority;
                    userRole += " ";
                }

                tbl += "<td class=\"roles rolesFoChange\">" + userRole + "</td>";
                userRole = "";
                //кнопка вызова модльного окна
                tbl += `
                    <td>
                        <button
                            type="button"
                            class="btn btn-primary"
                            data-toggle="modal"
                            onclick="modalShow(${userId})">
                            Update
                        </button>
                    </td>
                `;

                // кнопка удаления пользователя
                tbl += `
                    <td>
                        <sub
                            class="btn btn-primary"
                            onclick="modalDelete(${userId})">
                            Delete
                        </sub>
                    </td>
                `;
                tbl += "</tr>";
            }

            tbl += "</table>";
            $("#forJsScript").html(tbl);
        }
    });
}


// скрипт для заполнения данных в модальном окне в allUsers.html
function modalShow(id) {
    $.ajax(
        {
            method: 'get',
            url: '/admin/rest/' + id,
            dataType: 'json',
            async: false,
            success: function (user) {
                $(`.rolesForJs option`).prop('selected', false);

                $form = $(".formForJs");

                $form.find(".idForJs").val(user.id);
                $(".idForJs").attr("value", user.id);
                $form.find(".nameForJs").val(user.username);
                $(".nameForJs").attr("value", user.username);
                $form.find(".ageForJs").val(user.age);
                $(".ageForJs").attr("value", user.age);
                $form.find(".passwordForJs").attr("placeholder", "*********");

            }
        }
    );

    $('.modal').modal('show');
}

function modalDelete(id) {
    $.ajax(
        {
            method: 'delete',
            url: '/admin/rest/' + id,
            async: false,
            success: function (user) {
                setTimeout(tableFunction, 500);
            }
        }
    );
}

