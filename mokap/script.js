$(document).ready(function() {
    $('#add-new-member').click(function(e) {
        e.preventDefault();
        var $labelEl = $($(this).parent().parent().find('label').last());
        var nextNumber = parseInt($labelEl.text().slice(0,1)) + 1;
        var nextInput = parseInt($labelEl.attr('for').slice(-2)) + 1;
        var newElement = $(`<div class="form-group">
        <label for="input${nextInput}">${nextNumber}. clan komisije: </label>
        <select id="input${nextInput}" class="form-control form-control-sm">
                                                    <option>Small select</option>
                                                </select>`);
        newElement.insertBefore($(this));
        if(nextNumber === 5) {
            $(this).css('display','none');
        }
    });

    $('form').validate();


    // $.ajax({
    //     url: 'http://localhost:8080/engine-rest/task/0ace1fe7-570e-11e9-a45e-e03f49b29d72/rendered-form',
    //     dataType: "html",
    //     success: function(data) {
    //         console.log('success');
    //         var newdata = $(data);
    //         newdata.attr('novalidate', 'novalidate');
    //         newdata.append(`<button id="form-submit" type="submit" class="btn btn-primary">Submit</button>`);
    //         newdata.children().find('.has-error').remove();
    //         newdata.children().find('input').attr('data-rule-required',true);
    //         $('.accordion').append($(`<div class="card">
    //         <div class="card-header" id="heading15">
    //           <h2 class="mb-0">
    //             <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapse15" aria-expanded="false" aria-controls="collapse15">
    //               Predlog diplomskog
    //             </button>
    //           </h2>
    //         </div>

    //         <div id="collapse15" class="collapse" aria-labelledby="heading15" data-parent="#accordionExample">
    //           <div class="card-body">
    //                 ${$(newdata).prop('outerHTML')}
    //           </div>
    //         </div>
    //         </div>`));
    //     },
    //     error: function(err) {
    //         console.log(err);
    //     }
    // });


    
    $('#js-start-new-process').click(function(e){
        e.preventDefault();
        $.ajax({
            //url: 'http://localhost:8080/engine-rest/task?processDefinitionKey=prijavaDiplomskog',
            url: 'http://localhost:8080/restapi/start',
            headers: { 'Authorization' : localStorage.getItem('loggedInUser') },
            dataType: "json",
            success: function(data) { 
                location.reload();
            },
            error: function(err) {
                console.log(err);
            }
        });
    });

    $(document).on('click', '.card-header', function(e){
        var collapseDiv = $(this).parent().find('.collapse');
        // .collapse('show');
        if(collapseDiv.hasClass('show')) {
            collapseDiv.find('#js-button-submit').remove();
            collapseDiv.collapse('hide');
        } else {
            collapseDiv.find('form').append(`<button id="js-button-submit" class="btn btn-primary">Submit</button>`);
            collapseDiv.collapse('show');
        }
    });

});