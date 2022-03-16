package edu.gatech.streamingwars.service;

import edu.gatech.streamingwars.model.Order;
import edu.gatech.streamingwars.model.Pilot;
import edu.gatech.streamingwars.repo.OrderRepo;
import edu.gatech.streamingwars.repo.PilotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PilotRpcService {
    private final PilotRepo pilotRepo;
    private final OrderRepo orderRepo;

    @Autowired
    public PilotRpcService(PilotRepo pilotRepo, OrderRepo orderRepo) {
        this.pilotRepo = pilotRepo;
        this.orderRepo = orderRepo;
    }

    /**
     * find all pilots
     * @return List of pilot
     */
    public List<Pilot> findAllPilots() {
        return pilotRepo.findAll(Sort.by(Sort.Direction.ASC, "account"));
    }

    public Pilot findPilot(String account) {
        return pilotRepo.findPilotByAccount(account).orElseThrow(
                () -> new RuntimeException("Pilot " + account + " not found")
        );
    }

    /**
     * add pilot
     * @param pilot
     * @return pilot
     */
    public Pilot addPilot(Pilot pilot) {
        return pilotRepo.save(pilot);
    }

    /**
     * find all orders for pilot
     * @param account
     * @return list of order
     */
    public List<Order> findAllOrders(String account){
        Pilot pilot = findPilot(account);
        String droneId = pilot.getDroneName();
        List<Order> orders= orderRepo.findAll();
        return orders.stream().filter(o -> o.getDroneId().equals(droneId))
                .collect(Collectors.toList());
    }

    /**
     * update profile
     * @param account
     * @param phone
     * @param tax
     * @param license
     * @param experience
     * @return
     */
    public Pilot updateProfile(String account, String phone, String tax, String license, int experience) {
        Pilot pilot = findPilot(account);
        pilot.setPhone(phone);
        pilot.setTax(tax);
        pilot.setLicense(license);
        pilot.setExperience(experience);
        pilotRepo.save(pilot);
        return pilot;
    }
}
