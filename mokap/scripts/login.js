function renderLoginForm() {
    $('.container-fluid').empty();

    let form = $(`<div class="container login-container">
    <div class="row">
        <div class="col-md-12 login-form-1">
            <h3>Login</h3>
            <form>
                <div class="form-group">
                    <input id="js-username" type="text" class="form-control" placeholder="Your username" value="" />
                </div>
                <div class="form-group">
                    <input type="submit" class="btnSubmit" value="Login" />
                </div>
            </form>
        </div>
    </div>
</div>`);

    $('.container-fluid').append(form);
    $('.container-fluid').append($(`<div class="accordion" id="accordionExample">`));
}


// $(document).on('click', '.ForgetPwd', function(e){
//     e.preventDefault();
//     if($(this).text().includes('Register')) {
//         renderRegisterForm();
//     } else {
//         renderLoginForm();
//     }
// });


$(document).on('click','#js-logout', function(e){
    e.preventDefault();
    $.ajax({
        url: 'http://localhost:8080/restapi/authentication/logout',
        type: 'POST',
        success: function(data) {
            localStorage.removeItem('loggedInUser');
            location.reload();
        }
    });
});


$(document).on('click', '.btnSubmit', function(e){
    e.preventDefault();
    var frmData = new FormData();
    console.log($('#js-username').val());
    var username = $('#js-username').val();
    //var password = $('#js-password').val();
    frmData.append('userId', username);
    frmData.append('password',username);

    if($(this).val().includes('Login')) {
        $.ajax({
            url: `http://localhost:8080/restapi/authentication/login`,
            type: 'POST',
            contentType: false,
            cache : false,
            processData: false,
            data: frmData,
            success: function(data) {
                //console.log(data);
                localStorage.setItem('loggedInUser', data);
                location.reload();
                //getTask();
            },
            error: function(err) {
                console.log(err);
            }
        });
        
    } else {
        $.ajax({
            url: `http://localhost:8080/restapi/authentication/register`,
            type: 'POST',
            contentType: false,
            cache : false,
            processData: false,
            data: frmData,
            success: function(data) {
                console.log(data);
                localStorage.setItem('loggedInUser', data);
            },
            error: function(err) {
                console.log(err);
            }
        });
    }
})