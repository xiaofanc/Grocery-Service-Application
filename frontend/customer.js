$(document).ready(
    function() {
        // GET DisplayItem
        $("#customerGetItemForm").submit(function(event) {
            event.preventDefault();
            var storeName = $("form#customerGetItemForm > div > input.store-name").val();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "customermain/" + storeName + "/storeitems/all",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#customerGetItem.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#customerGetItem.result-div").html("<strong>Store does not exist.</strong>");
                }
            });
        });
        // GET DisplayOrder
        $("#displayOrderForm").submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "customermain/find/orders",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#displayOrder.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#displayOrder.result-div").html("<strong>Error</strong>");
                }
            });
        });
        // GET DisplayOrderItem
        $("#displayOrderItemForm").submit(function(event) {
            event.preventDefault();
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: "customermain/find/orderitems",
                xhrFields: {
                    withCredentials: true
                },
                success: function(result) {
                    console.log(result);
                    $("pre#displayOrderItem.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#displayOrderItem.result-div").html("<strong>Error</strong>");
                }
            });
        });
        // POST StartOrder
        $("#startOrderForm").submit(function(event) {
            event.preventDefault();
            var storename = $("form#startOrderForm > div > input.store-name").val();
            var data = {
                orderName: $("form#startOrderForm > div > input.order-ID").val(),
                droneId: $("form#startOrderForm > div > input.drone-ID").val(),
                customerAccount: $("form#startOrderForm > div > input.customer-account").val()
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "customermain/" + storename + "/order",
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#startOrder.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#startOrder.result-div").html("<strong>Cannot create this order!</strong>");
                }
            });
        });
        // POST RequestItem
        $("#requestItemForm").submit(function(event) {
            event.preventDefault();
            var storename = $("form#requestItemForm > div > input.store-name").val();
            var data = {
                orderName: $("form#requestItemForm > div > input.order-ID").val(),
                itemName: $("form#requestItemForm > div > input.item-name").val(),
                quantity: $("form#requestItemForm > div > input.quantity").val(),
                unitPrice: $("form#requestItemForm > div > input.price").val()
            };
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "customermain/" + storename + "/order/item",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#requestItem.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#requestItem.result-div").html("<strong>Cannot request this item!</strong>");
                }
            });
        });
        // PUT PurchaseOrder
        $("#purchaseOrderForm").submit(function(event) {
            event.preventDefault();
            var storename = $("form#purchaseOrderForm > div > input.store-name").val();
            var data = {
                orderName: $("form#purchaseOrderForm > div > input.order-ID").val()
            };
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "customermain/" + storename + "/order/purchase",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#purchaseOrder.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#purchaseOrder.result-div").html("<strong>Cannot purchase this order!</strong>");
                }
            });
        });
        // DELETE CancelOrder
        $("#cancelOrderForm").submit(function(event) {
            event.preventDefault();
            var storename = $("form#cancelOrderForm > div > input.store-name").val();
            var data = {
                orderName: $("form#cancelOrderForm > div > input.order-ID").val()
            };
            $.ajax({
                type: "DELETE",
                contentType: "application/json",
                url: "customermain/" + storename + "/order/cancel",
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#cancelOrder.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#cancelOrder.result-div").html("<strong>Error</strong>");
                }
            });
        });
        // PUT UpdatePhoneNumber
        $("#updatePhoneForm").submit(function(event) {
            event.preventDefault();
            var data = {
                phone: $("form#updatePhoneForm > div > input.phone-number").val()
            };
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "customermain/update",
                data: JSON.stringify(data),
                dataType: 'json',
                success: function(result) {
                    console.log(result);
                    $("pre#updatePhone.result-div").html(JSON.stringify(result, undefined, 2));
                },
                error: function(e) {
                    console.log("ERROR: ", e);
                    $("pre#updatePhone.result-div").html("<strong>This phone number cannot be updated.</strong>");
                }
            });
        });

    })