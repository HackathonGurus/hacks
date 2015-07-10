/**
 * ERoom App JavaScript
 */
 $("button[type=submit]").click(function() {
	 alert("Ajax Script has now been called");
   $.ajax({
     url: "http://localhost:8080/Login",
     type: "POST",
     contentType: "application/json;charset=utf-8",
     data: JSON.stringify({
       UserName: $("input[placeholder=Username]").val(),
       Password: $("input[placeholder=Password]").val()
     }),
     success: function(response) {
       alert("success");
     },
     error: function(e) {
       alert("error");
     }
   });
 });