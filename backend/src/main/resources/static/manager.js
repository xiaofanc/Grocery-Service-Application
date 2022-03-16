$(document).ready(
    function() {
        // POST MakeStore
        $("#makeStoreForm").submit(function(event) {
            event.preventDefault();
            var data = {
                name: $("form#makeStoreForm > div > input.store-name").val(),
                revenue: $("form#makeStoreForm > div > input.revenue").val()
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "managermain/add",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#makeStore.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#makeStore.result-div").html("<strong>Store already exists!</strong>");
                }
            });
        });
        // GET DisplayStore
        $("#displayStoreForm").submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "managermain/all/",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#displayStore.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#displayStore.result-div").html("<strong>Cannot find this store!</strong>");
                }
            });
        });
        // POST SellItem
        $("#sellItemForm").submit(function(event) {
            event.preventDefault();
            var storeName = $("form#sellItemForm > div > input.store-name").val();
            var data = {
                name: $("form#sellItemForm > div > input.item-name").val(),
                weight: $("form#sellItemForm > div > input.weight").val()
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "managermain/" + storeName+ "/item/sell",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#sellItem.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#sellItem.result-div").html("<strong>Store does not exist or item already exists in the stores.</strong>");
                }
            });
        });
        // GET DisplayItem
        $("#displayItemForm").submit(function(event) {
            event.preventDefault();
            var storeName = $("form#displayItemForm > div > input.store-name").val();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "managermain/" + storeName+ "/item/all",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#displayItem.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#displayItem.result-div").html("<strong>Error</strong>");
                }
            });
        });
        // POST MakePilot
        $("#makePilotForm").submit(function(event) {
            event.preventDefault();
            var data = {
                account: $("form#makePilotForm > div > input.pilot-account").val(),
                firstName: $("form#makePilotForm > div > input.pilot-firstname").val(),
                lastName: $("form#makePilotForm > div > input.pilot-lastname").val(),
                phone: $("form#makePilotForm > div > input.pilot-phonenumber").val(),
                tax: $("form#makePilotForm > div > input.pilot-taxID").val(),
                license: $("form#makePilotForm > div > input.pilot-license").val(),
                experience: $("form#makePilotForm > div > input.pilot-experience").val()
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "managermain/pilot/add",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#makePilot.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#makePilot.result-div").html("<strong>Pilot account already exists or license id is not unique.</strong>");
                }
            });
        });
        // GET DisplayPilot
        $("#displayPilotForm").submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "managermain/pilot/all/",
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
        // POST MakeDrone
        $("#makeDroneForm").submit(function(event) {
            event.preventDefault();
            var storeName = $("form#makeDroneForm > div > input.store-name").val();
            var data = {
                droneId: $("form#makeDroneForm > div > input.drone-id").val(),
                capacity: $("form#makeDroneForm > div > input.drone-capacity").val(),
                fuel: $("form#makeDroneForm > div > input.no-delivery").val()
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "managermain/" + storeName+ "/drone/add",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#makeDrone.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#makeDrone.result-div").html("<strong>Store does not exist or drone already exists in the store.</strong>");
                }
            });
        });
        // GET DisplayDrone
        $("#displayDroneForm").submit(function(event) {
            event.preventDefault();
            var storeName = $("form#displayDroneForm > div > input.store-name").val();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "managermain/" + storeName+ "/drone/all",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#displayDrone.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#displayDrone.result-div").html("<strong>Error</strong>");
                }
            });
        });
        // PUT FlyDrone
        $("#flyDroneForm").submit(function(event) {
            event.preventDefault();
            var storeName = $("form#flyDroneForm > div > input.store-name").val();
            var data = {
                droneId: $("form#flyDroneForm > div > input.drone-id").val(),
                pilotAccount: $("form#flyDroneForm > div > input.pilot-account").val()
            };
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "managermain/" + storeName+ "/flydrone",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#flyDrone.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#flyDrone.result-div").html("<strong>Store or drone or pilot does not exist.</strong>");
                }
            });
        });
        // GET DisplayOrder
        $("#displayOrderForm").submit(function(event) {
            event.preventDefault();
            var storeName = $("form#displayOrderForm > div > input.store-name").val();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "managermain/" + storeName+ "/order/all",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#displayOrder.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#displayOrder.result-div").html("<strong>Store does not exist.</strong>");
                }
            });
        });
        // PUT UpdateCredit
        $("#updateCreditForm").submit(function(event) {
            event.preventDefault();
            var data = {
                customerAccount: $("form#updateCreditForm > div > input.user-account").val(),
                credit: $("form#updateCreditForm > div > input.credit").val()
            };
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "managermain/updatecredit",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#updateCredit.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#updateCredit.result-div").html("<strong>Customer does not exist.</strong>");
                }
            });
        });
        // GET ViewLogs
        $("#viewLogsForm").submit(function(event) {
            event.preventDefault();
            var requestId = $("form#viewLogsForm > div > input.log-id").val();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "log/search",
                xhrFields: {
                    withCredentials: true
                },
                data: {
                    "requestId": requestId
                },
                success: function(result) {
                    console.log(result);
                    $("pre#viewLogs.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#viewLogs.result-div").html("<strong>Error</strong>");
                }
            });
        });
        // GET Inspect Status
        $("#viewInspectionForm").submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: "GET",
                url: "actuator/health",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#viewInspection.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#viewInspection.result-div").html("<strong>Error</strong>");
                }
            });
        });
    })