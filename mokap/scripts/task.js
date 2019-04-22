

    function getTask() {
        $.ajax({
            //url: 'http://localhost:8080/engine-rest/task?processDefinitionKey=prijavaDiplomskog',
            url: 'http://localhost:8080/restapi/tasks',
            headers: { 'Authorization' : localStorage.getItem('loggedInUser')},
            dataType: "json",
            success: function(data) { 
                if(data == null || data.length < 1) {
                    
                    $('.container-fluid').append('<p>No tasks right now :( </p>');
                    return;
                }
                
                console.log(data);
                for(var i=0;i<data.length;i++){
                    generateForm(data[i].taskId, data[i].name);
                }
            },
            error: function(err) {
                console.log(err);
            }
        });
    }


    function completeTask(taskId, data) {
        console.log(taskId, data);
        $.ajax({
            url: `http://localhost:8080/restapi/task/${taskId}`,
            headers: { 'Authorization' : localStorage.getItem('loggedInUser')},
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(data) {
                location.reload();
            },
            error: function(err) {
                console.log(err);
            }
        });
    }
