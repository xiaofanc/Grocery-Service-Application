package edu.gatech.streamingwars.controller;

import edu.gatech.streamingwars.dto.OrderAddItemRequest;
import edu.gatech.streamingwars.dto.PurchaseOrderRequest;
import edu.gatech.streamingwars.dto.StartOrderRequest;
import edu.gatech.streamingwars.dto.UpdateCustomerProfileRequest;
import edu.gatech.streamingwars.model.Customer;
import edu.gatech.streamingwars.logging.Response;
import edu.gatech.streamingwars.model.Order;
import edu.gatech.streamingwars.model.StoreItem;
import edu.gatech.streamingwars.service.CustomerRpcService;
import edu.gatech.streamingwars.service.StoreRpcService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customermain")
public class CustomerController {

    private final CustomerRpcService customerRpcService;
    private final StoreRpcService storeRpcService;

    public CustomerController(CustomerRpcService customerRpcService, StoreRpcService storeRpcService) {
        this.customerRpcService = customerRpcService;
        this.storeRpcService = storeRpcService;
    }

    /**
     * Get all customers API
     * @return
     */
    @GetMapping("/all")
    @ResponseBody
    public Response getAllCustomers() {
        List<Customer> customers = customerRpcService.findAllCustomers();

        return new Response(ResponseEntity.ok(customers));
    }

    /**
     * Find customer info by customer's account
     * @param account
     * @return
     */
    @GetMapping("/find/{account}")
    @ResponseBody
    public Response getCustomer(@PathVariable("account") String account) {
        Customer customer = customerRpcService.findCustomer(account);
        return new Response(ResponseEntity.ok(customer));
    }

    /**
     * Update customer's profile
     * @param cookie
     * @param request
     * @return
     */
    @PutMapping("/update")
    @ResponseBody
    public Response updateProfile(
            @CookieValue(name = "auth_by_cookie") String cookie,
            @RequestBody UpdateCustomerProfileRequest request) {
        String account = getAccountByCookie(cookie);
        Customer customer = customerRpcService.updateProfile(account, request.getPhone());
        return new Response(ResponseEntity.ok(customer));
    }

    /**
     * Get all store items
     * @param name
     * @return
     */
    @GetMapping("/{name}/storeitems/all")
    @ResponseBody
    public Response displayStoreItems(@PathVariable("name") String name) {
        List<StoreItem> items = storeRpcService.displayItems(name);
        return new Response(ResponseEntity.ok(items));
    }

    /**
     * Find customer's own orders
     * @param cookie
     * @return
     */
    @GetMapping("/find/orders")
    @ResponseBody
    public Response displayOrders(@CookieValue(name = "auth_by_cookie") String cookie) {
        String account = getAccountByCookie(cookie);
        List<Order> orders = customerRpcService.findAllOrders(account);
        return new Response(ResponseEntity.ok(orders));
    }

    /**
     * Find customer's own ordered items
     * @param cookie
     * @return
     */
    @GetMapping("/find/orderitems")
    @ResponseBody
    public Response displayOrderItems(@CookieValue(name = "auth_by_cookie") String cookie) {
        String account = getAccountByCookie(cookie);
        List<Order> orders = customerRpcService.findAllOrders(account);
        return new Response(ResponseEntity.ok(orders));
    }

    /**
     * Start a customers's order
     * @param name
     * @param cookie
     * @param request
     * @return
     */
    @PostMapping("/{name}/order")
    @ResponseBody
    public Response startOrder(
            @PathVariable("name") String name,
            @CookieValue(name = "auth_by_cookie") String cookie,
            @RequestBody StartOrderRequest request) {
        String account = getAccountByCookie(cookie);
        Order newOrder = customerRpcService.startOrder(name, request.getOrderName(), request.getDroneId(), account);
        return new Response(ResponseEntity.ok(newOrder));
    }

    /**
     * Request an item by customer
     * @param name
     * @param request
     * @return
     */
    @PostMapping("/{name}/order/item")
    @ResponseBody
    public Response requestItem(
            @PathVariable("name") String name,
            @RequestBody OrderAddItemRequest request) {
        Order newOrder = customerRpcService.requestItem(name, request.getOrderName(), request.getItemName(), request.getQuantity(), request.getUnitPrice());
        return new Response(ResponseEntity.ok(newOrder));
    }

    /**
     * Purhase a order by customer
     * @param name
     * @param request
     * @return
     */
    @PutMapping("/{name}/order/purchase")
    @ResponseBody
    public Response purchaseOrder(
            @PathVariable("name") String name,
            @RequestBody PurchaseOrderRequest request) {
        customerRpcService.purchaseOrder(name, request.getOrderName());
        return new Response(ResponseEntity.ok().build());
    }

    /**
     * Cancel a order by customer
     * @param name
     * @param request
     * @return
     */
    @DeleteMapping("/{name}/order/cancel")
    @ResponseBody
    public Response cancelOrder(
            @PathVariable("name") String name,
            @RequestBody PurchaseOrderRequest request) {
        customerRpcService.cancelOrder(name, request.getOrderName());
        return new Response(ResponseEntity.ok().build());
    }

    /**
     * Get account by cookie
     * @param cookie
     * @return
     */
    private String getAccountByCookie(String cookie){
        return cookie.split("&")[1];
    }
}
