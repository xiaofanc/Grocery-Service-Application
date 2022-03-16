$(document).ready(
    function() {
        // GET DisplayPilot
        $("#displayPilotForm").submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "pilotmain/all",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#displayPilot.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#displayPilot.result-div").html("<strong>Error</strong>");
                }
            });
        });
        // GET DisplayOrder
        $("#displayOrderForm").submit(function(event) {
            event.preventDefault();
            var account = $("form#displayOrderForm > div > input.pilot-account").val();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "pilotmain/find/" + account + "/orders",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#displayOrder.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#displayOrder.result-div").html("<strong>Did not find orders for the pilot.</strong>");
                }
            });
        });
        // PUT UpdateInfo
        $("#updateInfoForm").submit(function(event) {
           event.preventDefault();
           var account = $("form#updateInfoForm > div > input.pilot-account").val();
           var data = {
               phone: $("form#updateInfoForm > div > input.phone-number").val(),
               tax: $("form#updateInfoForm > div > input.tax-id").val(),
               license: $("form#updateInfoForm > div > input.license-id").val(),
               experience: $("form#updateInfoForm > div > input.experience").val()
           };
           $.ajax({
               type: "PUT",
               contentType: "application/json",
               url: "pilotmain/" + account + "/update",
               data: JSON.stringify(data),
               dataType: 'json',
               success: function(result) {
                   console.log(result);
                   $("pre#updateInfo.result-div").html(JSON.stringify(result, undefined, 2));
               },
               error: function(e) {
                   console.log("ERROR: ", e);
                   $("pre#updateInfo.result-div").html("<strong>The information cannot be updated!</strong>");
               }
           });
        });

   })