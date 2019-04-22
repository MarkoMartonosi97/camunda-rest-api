function renderRegisterForm() {
    $('.container-fluid').empty();

    let form = $(`<div class="container login-container">
    <div class="row">
        <div class="col-md-12 login-form-1">
            <h3>Register</h3>
            <form>
                <div class="form-group">
                    <input id="js-username" type="text" class="form-control" placeholder="Username" value="" />
                </div>
                <div class="form-group">
                    <input id="js-password" type="password" class="form-control" placeholder="Your Password *" value="" />
                </div>
                <div class="form-group">
                        <select id="groupselect" class="form-control">
                            <option>Profesor</option>
                            <option>Referent SS</option>
                            <option>Student</option>
                        </select>
                </div> 
                <div class="form-group">
                    <input type="submit" class="btnSubmit" value="Register" />
                </div>
                <div class="form-group">
                    <a href="#" class="ForgetPwd">Login?</a>
                </div>
            </form>
        </div>
    </div>
</div>`);

    $('.container-fluid').append(form);
    $('.container-fluid').append($(`<div class="accordion" id="accordionExample">
    </div>`));
}