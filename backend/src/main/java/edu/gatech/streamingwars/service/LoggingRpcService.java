package edu.gatech.streamingwars.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoggingRpcService {
    @Autowired
    public LoggingRpcService() { }

    /**
     * Search log by requestId
     * @param requestId
     * @return
     */
    public List<String> searchLog(String requestId) {
        List<String> log = new ArrayList<>();
        try{
            File logFile = new File("./log/logback.log");
            BufferedReader br = new BufferedReader(new FileReader(logFile));

            String line = br.readLine();
            while(line!=null){
                if(line.contains(requestId)){
                    log.add(line);
                }
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return log;
    }
}
