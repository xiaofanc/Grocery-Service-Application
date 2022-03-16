package edu.gatech.streamingwars.controller;

import edu.gatech.streamingwars.logging.Response;
import edu.gatech.streamingwars.model.*;
import edu.gatech.streamingwars.service.PilotRpcService;
import edu.gatech.streamingwars.service.StoreRpcService;
import edu.gatech.streamingwars.dto.FlyDroneRequest;
import edu.gatech.streamingwars.dto.UpdateCreditRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managermain")
public class StoreController {

    private final StoreRpcService storeRpcService;
    private final PilotRpcService pilotRpcService;

    public StoreController(StoreRpcService storeRpcService, PilotRpcService pilotRpcService) {
        this.storeRpcService = storeRpcService;
        this.pilotRpcService = pilotRpcService;
    }

    @GetMapping("/all")
    @ResponseBody
    public Response displayStores() {
        List<Store> stores = storeRpcService.displayStores();

        return new Response(ResponseEntity.ok(stores));
    }

    @GetMapping("/find/{name}")
    @ResponseBody
    public Response getStore(@PathVariable("name") String name) {
        Store store = storeRpcService.findStore(name);

        return new Response(ResponseEntity.ok(store));
    }

    @PostMapping("/add")
    @ResponseBody
    public Response addStore(@RequestBody Store store){
        Store newStore = storeRpcService.addStore(store);

        return new Response(ResponseEntity.ok(newStore));
    }

    @PostMapping("/{name}/item/sell")
    @ResponseBody
    public Response sellItem(
            @PathVariable("name") String name,
            @RequestBody StoreItem item) {
        StoreItem newItem = storeRpcService.sellItem(name, item);

        return new Response(ResponseEntity.ok(newItem));
    }

    @GetMapping("/{name}/item/all")
    @ResponseBody
    public Response displayItems(@PathVariable("name") String name) {
        List<StoreItem> items = storeRpcService.displayItems(name);

        return new Response(ResponseEntity.ok(items));
    }

    @GetMapping("/pilot/all")
    @ResponseBody
    public Response displayPilots() {
        List<Pilot> pilots = pilotRpcService.findAllPilots();
        return new Response(ResponseEntity.ok(pilots));
    }

    @PostMapping("/pilot/add")
    @ResponseBody
    public Response addPilot(@RequestBody Pilot pilot){
        Pilot newPilot = pilotRpcService.addPilot(pilot);
        return new Response(ResponseEntity.ok(newPilot));
    }

    @PostMapping("/{name}/drone/add")
    @ResponseBody
    public Response addDrone(
            @PathVariable("name") String name,
            @RequestBody Drone drone) {
        Drone newDrone = storeRpcService.addDrone(name, drone);

        return new Response(ResponseEntity.ok(newDrone));
    }

    @GetMapping("/{name}/drone/all")
    @ResponseBody
    public Response displayDrones(@PathVariable("name") String name) {
        List<Drone> drones = storeRpcService.displayDrones(name);

        return new Response(ResponseEntity.ok(drones));
    }

    @PutMapping("/{name}/flydrone")
    @ResponseBody
    public Response flyDrone(
            @PathVariable("name") String name,
            @RequestBody FlyDroneRequest request
    ) {
        Drone drone = storeRpcService.flyDrone(name, request.getDroneId(), request.getPilotAccount());

        return new Response(ResponseEntity.ok(drone));
    }

    @GetMapping("/{name}/order/all")
    @ResponseBody
    public Response displayOrders(@PathVariable("name") String name) {
        List<Order> orders = storeRpcService.displayOrders(name);

        return new Response(ResponseEntity.ok(orders));
    }

    @PutMapping("/updatecredit")
    @ResponseBody
    public Response updateCredit(@RequestBody UpdateCreditRequest request) {
        Customer customer = storeRpcService.updateCredit(request.getCustomerAccount(), request.getCredit());
        return new Response(ResponseEntity.ok(customer));
    }

    private String getAccountByCookie(String cookie){
        return cookie.split("&")[1];
    }
}
