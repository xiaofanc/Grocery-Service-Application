package edu.gatech.streamingwars.controller;

import edu.gatech.streamingwars.dto.UpdatePilotProfileRequest;
import edu.gatech.streamingwars.model.Order;
import edu.gatech.streamingwars.model.Pilot;
import edu.gatech.streamingwars.logging.Response;
import edu.gatech.streamingwars.service.PilotRpcService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pilotmain")
public class PilotController {

    PilotRpcService pilotRpcService;

    public PilotController(PilotRpcService pilotRpcService) {
        this.pilotRpcService = pilotRpcService;
    }

    @GetMapping("/all")
    @ResponseBody
    public Response displayPilots() {
        List<Pilot> pilots = pilotRpcService.findAllPilots();
        return new Response(ResponseEntity.ok(pilots));
    }

    @PostMapping("/add")
    @ResponseBody
    public Response addPilot(@RequestBody Pilot pilot){
        Pilot newPilot = pilotRpcService.addPilot(pilot);

        return new Response(ResponseEntity.ok(newPilot));
    }

    @GetMapping("/find/{name}/orders")
    @ResponseBody
    public Response displayOrders(@PathVariable("name") String name) {
        List<Order> orders = pilotRpcService.findAllOrders(name);
        return new Response(ResponseEntity.ok(orders));
    }

    @PutMapping("/{name}/update")
    @ResponseBody
    public Response updateProfile(
            @PathVariable("name") String name,
            @RequestBody UpdatePilotProfileRequest request) {
        Pilot pilot = pilotRpcService.updateProfile(name, request.getPhone(), request.getTax(), request.getLicense(), request.getExperience());
        return new Response(ResponseEntity.ok(pilot));
    }

    private String getAccountByCookie(String cookie){
        return cookie.split("&")[1];
    }
}
