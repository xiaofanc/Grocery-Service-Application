package edu.gatech.streamingwars.service;

import edu.gatech.streamingwars.model.*;
import edu.gatech.streamingwars.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerRpcService {
    private final CustomerRepo customerRepo;
    private final StoreRepo storeRepo;
    private final OrderRepo orderRepo;
    private final DroneRepo droneRepo;
    private final PilotRepo pilotRepo;

    @Autowired
    public CustomerRpcService(CustomerRepo customerRepo, StoreRepo storeRepo, OrderRepo orderRepo, DroneRepo droneRepo, PilotRepo pilotRepo) {
        this.customerRepo = customerRepo;
        this.storeRepo = storeRepo;
        this.orderRepo = orderRepo;
        this.droneRepo = droneRepo;
        this.pilotRepo = pilotRepo;
    }

    /**
     * find all customers
     * @return list of customers
     */
    public List<Customer> findAllCustomers() {
        return customerRepo.findAll(Sort.by(Sort.Direction.ASC, "account"));
    }

    public Customer findCustomer(String account) {
        return customerRepo.findCustomerByAccount(account).orElseThrow(
                () -> new RuntimeException("Customer " + account + " not found")
        );
    }

    /**
     * add customer
     * @param userAccount
     * @return customer
     */
    public Customer addCustomer(UserAccount userAccount) {
        Customer customer = new Customer(userAccount);
        return customerRepo.save(customer);
    }

    /**
     * find all orders for the customer
     * @param account
     * @return list of orders
     */
    public List<Order> findAllOrders(String account){
        List<Order> orders= orderRepo.findAll();
        return orders.stream().filter(o -> o.getCustomerAccount().equals(account))
                .collect(Collectors.toList());
    }

    /**
     * find store
     * @param name
     * @return store
     */
    public Store findStore(String name) {
        return storeRepo.findStoreByName(name).orElseThrow(
                () -> new RuntimeException("Store " + name + " not found")
        );
    }

    /**
     * find drone
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
     * check if order is in store
     * @param store
     * @param order
     * @return boolean
     */
    public boolean isOrderInStore(Store store, Order order) {
        return store.getOrders().stream()
                .anyMatch(i -> i.getOrderName().equals(order.getOrderName()));
    }

    /**
     * start order
     * @param storeName
     * @param orderName
     * @param droneId
     * @param customerAccount
     * @return order
     */
    public Order startOrder(String storeName, String orderName, String droneId, String customerAccount) {
        Store store = findStore(storeName);
        Drone drone = findDrone(store, droneId);
        Customer customer = findCustomer(customerAccount);
        Order newOrder = new Order(orderName, droneId, customerAccount, drone.getRemainingCapacity(), customer.getCredit());
        if (isOrderInStore(store, newOrder)) {
            Logger log = LoggerFactory.getLogger(getClass());
            log.info("order already exists in the store");
            throw new RuntimeException("order already exists in the store");
        } else {
            store.addOrder(newOrder);
            orderRepo.save(newOrder);
            storeRepo.save(store);
            return newOrder;
        }
    }

    /**
     * find order in the store
     * @param store
     * @param orderName
     * @return order
     */
    public Order findOrder(Store store, String orderName) {
        Order order = store.getOrders().stream()
                .filter(o -> o.getOrderName().equals(orderName))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Order " + orderName + " not found"));
        return order;
    }

    /**
     * find store item
     * @param store
     * @param itemName
     * @return storeitem
     */
    public StoreItem findStoreItem(Store store, String itemName) {
        StoreItem item = store.getItems().stream()
                .filter(i -> i.getName().equals(itemName))
                .findAny()
                .orElseThrow(() -> new RuntimeException("StoreItem " + itemName + " not found"));
        return item;
    }

    /**
     * check if item is in order
     * @param order
     * @param itemName
     * @return
     */
    public boolean isItemInOrder(Order order, String itemName) {
        return order.getItems().stream()
                .anyMatch(i -> i.getName().equals(itemName));
    }

    /**
     * request item for the order
     * @param storeName
     * @param orderName
     * @param itemName
     * @param quantity
     * @param unitPrice
     * @return Order
     */
    public Order requestItem(String storeName, String orderName, String itemName, int quantity, int unitPrice){
        Store store = findStore(storeName);
        Order order = findOrder(store, orderName);
        Drone drone = findDrone(store, order.getDroneId());
        StoreItem storeItem = findStoreItem(store, itemName);
        // check if customer has enough credit and drone has enough capacity
        if (isItemInOrder(order, itemName)) {
            //throw new RuntimeException(("item already exists in the order"));
            Logger log = LoggerFactory.getLogger(getClass());
            log.info("item already exists in the order");
            throw new RuntimeException("item already exists in the order");
        }
        if (order.getCustomerCredit() < quantity * unitPrice + order.getTotalCost()) {
            Logger log = LoggerFactory.getLogger(getClass());
            log.info("customer does not have enough credit");
            throw new RuntimeException("customer does not have enough credit");
        }
        if (order.getDroneCapacity() < quantity * storeItem.getWeight() + order.getTotalWeight()) {
            Logger log = LoggerFactory.getLogger(getClass());
            log.info("drone cannot load more items");
            throw new RuntimeException("drone cannot load more items");
        }
        // update order cost etc;
        order.setTotalCost(order.getTotalCost() + quantity * unitPrice);
        order.setTotalWeight(order.getTotalWeight() + quantity * storeItem.getWeight());
        order.setDroneCapacity(drone.getRemainingCapacity() - quantity * storeItem.getWeight());
        drone.setRemainingCapacity(drone.getRemainingCapacity() - quantity * storeItem.getWeight());
        droneRepo.save(drone);
        OrderItem orderitem = new OrderItem(storeItem.getName(), storeItem.getWeight());
        orderitem.setQuantity(quantity);
        order.getItems().add(orderitem);
        orderRepo.save(order);
        return order;
    }

    /**
     * find pilot
     * @param account
     * @return pilot
     */
    public Pilot findPilot(String account) {
        return pilotRepo.findPilotByAccount(account).orElseThrow(
                () -> new RuntimeException("Pilot " + account + " not found")
        );
    }

    /**
     * purchase order
     * @param storeName
     * @param orderName
     */
    public void purchaseOrder(String storeName, String orderName){
        Store store = findStore(storeName);
        Order order = findOrder(store, orderName);
        Customer customer = findCustomer(order.getCustomerAccount());
        Drone drone = findDrone(store, order.getDroneId());
        Pilot pilot = findPilot(drone.getPilotAccount());
        // update store revenue
        store.setRevenue(store.getRevenue() + order.getTotalCost());
        storeRepo.save(store);
        // update customer credit
        customer.setCredit(customer.getCredit() - order.getTotalCost());
        customerRepo.save(customer);
        // update pilot experience
        pilot.setExperience(pilot.getExperience() + 1);
        pilotRepo.save(pilot);
        // update drone
        drone.setFuel(drone.getFuel() - 1);
        drone.setNumOrders(drone.getNumOrders() + 1);
        // remove order from store
        store.removeOrder(order);
        storeRepo.save(store);
    }

    /**
     * cancel order
     * @param storeName
     * @param orderName
     */
    public void cancelOrder(String storeName, String orderName){
        Store store = findStore(storeName);
        Order order = findOrder(store, orderName);
        store.removeOrder(order);
        storeRepo.save(store); // orderRepo.delete does not work
    }

    /**
     * update customer's phone number
     * @param account
     * @param phone
     * @return
     */
    public Customer updateProfile(String account, String phone) {
        Customer customer = findCustomer(account);
        customer.setPhone(phone);
        customerRepo.save(customer);
        return customer;
    };

}
