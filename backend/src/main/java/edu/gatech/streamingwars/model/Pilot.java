package edu.gatech.streamingwars.model;

import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Pilot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String account;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    @Column(nullable = true)
    private String phone;
    private String tax;

    @Column(nullable = false, unique = true, length = 50)
    private String license;

    private int experience;
    private String droneName;

    public Pilot(String account, String firstName, String lastName, String phone, String tax, String license, String experience){
        this.setAccount(account);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhone(phone);
        this.setTax(tax);
        this.setLicense(license);
        this.setExperience(Integer.parseInt(experience));
    }

    public Pilot(UserAccount userAccount){
        this.setFirstName(userAccount.getFirstName());
        this.setLastName(userAccount.getLastName());
        this.setAccount(userAccount.getUserName());
    }

    public String getAccount() {
        return account;
    }

    private void setAccount(String account) {
        this.account = account;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + "_" + lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTax() {
        return tax;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicense() {
        return license;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }

    public void setDroneName(String droneName) {
        this.droneName = droneName;
    }

    public String getDroneName() {
        return droneName;
    }

}
