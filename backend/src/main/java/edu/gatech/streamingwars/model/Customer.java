package edu.gatech.streamingwars.model;

import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 50)
    private String account;

    @Column(nullable = false)
    private String firstName;
    private String lastName;

    private String phone;
    private int rating = 5;
    private int credit = 0;

    public Customer(String account, String firstName, String lastName, String phone, String rating, String credit){
        this.setAccount(account);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhone(phone);
        this.setRating(Integer.parseInt(rating));
        this.setCredit(Integer.parseInt(credit));
    }

    public Customer(UserAccount userAccount){
        this.setFirstName(userAccount.getFirstName());
        this.setLastName(userAccount.getLastName());
        this.setAccount(userAccount.getUserName());
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public String getName() {
        return this.firstName + "_" + this.lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getCredit() {
        return credit;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

}

