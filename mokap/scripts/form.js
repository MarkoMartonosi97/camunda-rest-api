function generateForm(taskId, taskName) {
    //var btn = $(`<button id="form-submit${taskId}" type="submit" class="btn btn-primary btn-submit" data-task-id="${taskId}">Submit</button>`);
    $.ajax({
        url: `http://localhost:8080/restapi/task/${taskId}`,
        headers: { 'Authorization' : localStorage.getItem('loggedInUser') },
        dataType: "html",
        success: function(data) {
            if(data.length < 1) {
                $('p').remove();
                $('.container-fluid').append('<p>No tasks right now :( </p>');
                return;
            }
            var newdata = $(data);
            newdata.attr('novalidate', 'novalidate');
            //newdata.append(btn);
            newdata.children().find('.has-error').remove();
            newdata.children().find('input[type="text"]:not([name="pitanjeClanaKomisije"])').attr('data-rule-required',true);
            newdata.children().find('input[type="checkbox"][readonly]').attr('disabled',true);
            newdata.children().find('input[datepicker-popup="dd/MM/yyyy"]').attr('data-provide', 'datepicker').attr('data-date-format', 'dd/mm/yyyy');
            $('.accordion').append($(`<div class="card">
            <div class="card-header" id="heading${taskId}">
                <span>${taskName}</span>
            </div>

            <div id="collapse${taskId}" class="collapse" aria-labelledby="heading${taskId}" data-parent="#accordionExample">
              <div class="card-body" data-task-id=${taskId}>
                    ${$(newdata).prop('outerHTML')}
              </div>
            </div>
            </div>`));
        },
        error: function(err) {
            console.log(err);
        }
    });
}

    // $('#js-button-submit').click(function(e){
    //     console.log('clicked');
    // });

    $(document).on('click', '#js-button-submit',function(e){
        console.log('click event', $(this).parent().parent().data('task-id'));
        e.preventDefault();
        e.stopPropagation();
        console.log($(this).parent().find('form'));
        var form = $(this).closest('form');
        if(form.valid()) { 
            var jsonData = customSerializer(form);
            console.log(jsonData);
            completeTask($(this).parent().parent().data('task-id'), jsonData);
        }
    });


    function customSerializer(form) {
        var retVal = {};
        var inputs = form.find('input[type="text"]:not([readonly])');
        var checkboxes = form.find('input[type="checkbox"]:not([disabled="disabled"])');
        var selects = form.find('select');
        //var radios = form.find('')

        inputs.each(function() {
            //retVal[this.name] = {'value': this.value};
            retVal[this.name] = this.value;
            //retVal[this.name] = '2016-01-25T13:33:42';
        })
        checkboxes.each(function(){
            //retVal[this.name] = {'value': this.checked};
            retVal[this.name] = this.checked;
        });

        selects.each(function(){
            //retVal[this.name] = {'value': this.value};
            retVal[this.name] = this.value;
        });
        //return {'variables':retVal};
        return retVal;

        // var retVal = new FormData();

        // inputs.each(function() {
        //     retVal.append(this.name, this.value);
        // })
        // checkboxes.each(function(){
        //     retVal.append(this.name, this.checked);
        // });
        // selects.each(function(){
        //     retVal.append(this.name, this.value);
        // });
        //return retVal;
    }

