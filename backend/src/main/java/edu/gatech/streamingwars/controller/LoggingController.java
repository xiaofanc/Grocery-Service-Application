package edu.gatech.streamingwars.controller;

import edu.gatech.streamingwars.logging.Response;
import edu.gatech.streamingwars.service.LoggingRpcService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LoggingController {

    LoggingRpcService loggingRpcService;

    public LoggingController(LoggingRpcService loggingRpcService) {
        this.loggingRpcService = loggingRpcService;
    }

    @GetMapping("/search")
    @ResponseBody
    public Response searchLog(@RequestParam(name = "requestId") String requestId) {
        List<String> logs = loggingRpcService.searchLog(requestId);

        return new Response(ResponseEntity.ok(logs));
    }
}
