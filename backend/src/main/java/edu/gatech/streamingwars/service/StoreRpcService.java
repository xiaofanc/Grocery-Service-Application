package edu.gatech.streamingwars.service;

import edu.gatech.streamingwars.model.*;
import edu.gatech.streamingwars.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StoreRpcService {
    private final StoreRepo storeRepo;
    private final PilotRepo pilotRepo;
    private final DroneRepo droneRepo;
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;

    @Autowired
    public StoreRpcService(StoreRepo storeRepo, PilotRepo pilotRepo, DroneRepo droneRepo, CustomerRepo customerRepo, OrderRepo orderRepo) {
        this.storeRepo = storeRepo;
        this.pilotRepo = pilotRepo;
        this.droneRepo = droneRepo;
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
    }

    /**
     * display stores
     * @return list of stores
     */
    public List<Store> displayStores() {
        return storeRepo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public Store findStore(String name) {
        return storeRepo.findStoreByName(name).orElseThrow(
                () -> new RuntimeException("Store " + name + " not found")
        );
    }

    /**
     * add store
     * @param store
     * @return store
     */
    public Store addStore(Store store) {
        return storeRepo.save(store);
    }

    /**
     * check if item in store
     * @param store
     * @param item
     * @return
     */
    private boolean isItemInStore(Store store, StoreItem item) {
        return store.getItems().stream()
                .anyMatch(i -> i.getName().equals(item.getName()));
    }

    /**
     * assign store to sell item
     * @param storeName
     * @param item
     * @return storeItem
     */
    public StoreItem sellItem(String storeName, StoreItem item) {
        Store store = findStore(storeName);
        if (isItemInStore(store, item)) {
            Logger log = LoggerFactory.getLogger(getClass());
            log.info("item already exists in the store");
            throw new RuntimeException("item already exists in the store");
        } else {
            store.addItem(item);
            storeRepo.save(store);
            return item;
        }
    }

    /**
     * display store items
     * @param storeName
     * @return list of storeItems
     */
    public List<StoreItem> displayItems(String storeName){
        Store store = findStore(storeName);
        return store.getItems();
    }

    /**
     * check if drone is in store
     * @param store
     * @param drone
     * @return boolean
     */
    private boolean isDroneInStore(Store store, Drone drone) {
        return store.getDrones().stream()
                .anyMatch(i -> i.getDroneId().equals(drone.getDroneId()));
    }

    /**
     * add drone for store
     * @param storeName
     * @param drone
     * @return Drone
     */
    public Drone addDrone(String storeName, Drone drone) {
        Store store = findStore(storeName);
        if (isDroneInStore(store, drone)) {
            Logger log = LoggerFactory.getLogger(getClass());
            log.info("drone already exists in the store");
            throw new RuntimeException("drone already exists in the store");
        } else {
            drone.setRemainingCapacity(drone.getCapacity());
            store.addDrone(drone);
            storeRepo.save(store);
            return drone;
        }
    }

    /**
     * display drones in the store
     * @param storeName
     * @return list of drones
     */
    public List<Drone> displayDrones(String storeName){
        Store store = findStore(storeName);
        return store.getDrones();
    }

    /**
     * find pilot by account
     * @param account
     * @return pilot
     */
    public Pilot findPilot(String account) {
        return pilotRepo.findPilotByAccount(account).orElseThrow(
                () -> new RuntimeException("Pilot " + account + " not found")
        );
    }

    /**
     * find drone by id
     * @param store
     * @param droneId
     * @return drone
     */
    public Drone findDrone(Store store, String droneId) {
        Drone drone = store.getDrones().stream()
                .filter(d -> d.getDroneId().equals(droneId))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Drone " + droneId + " not found"));
        return drone;
    }

    /**
     * assign drone a pilot
     * @param storeName
     * @param droneId
     * @param pilotAccount
     * @return drone
     */
    public Drone flyDrone(String storeName, String droneId, String pilotAccount){
        Store store = findStore(storeName);
        Drone drone = findDrone(store, droneId);
        Pilot pilot = findPilot(pilotAccount);
        if (drone.getPilotName() != null || drone.getPilotAccount() != null) {
            Logger log = LoggerFactory.getLogger(getClass());
            log.info("Drone is already assigned");
            throw new RuntimeException("Drone is already assigned");
        }
        if (pilot.getDroneName() != null) {
            String oldDroneId = pilot.getDroneName();
            Drone oldDrone = findDrone(store, oldDroneId);
            oldDrone.setPilot(null, null);
        }
        drone.setPilot(pilot.getName(), pilot.getAccount());
        pilot.setDroneName(droneId);
        droneRepo.save(drone);
        pilotRepo.save(pilot);
        return drone;
    }

    /**
     * display orders of the store
     * @param storeName
     * @return list of orders
     */
    public List<Order> displayOrders(String storeName){
        Store store = findStore(storeName);
        return store.getOrders();
    }

    /**
     * find customer by account
     * @param account
     * @return customer
     */
    public Customer findCustomer(String account) {
        return customerRepo.findCustomerByAccount(account).orElseThrow(
                () -> new RuntimeException("Customer " + account + " not found")
        );
    }

    /**
     * update customer's credit
     * @param customerAccount
     * @param credit
     * @return customer
     */
    public Customer updateCredit(String customerAccount, int credit) {
        Customer customer = findCustomer(customerAccount);
        customer.setCredit(credit);
        customerRepo.save(customer);
        return customer;
    }

}

