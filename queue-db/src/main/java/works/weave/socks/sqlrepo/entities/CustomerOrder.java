package works.weave.socks.sqlrepo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import org.springframework.data.annotation.Id;
import javax.persistence.Id;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerOrder  {

    @Id
    private String id;

    private String customerId;

    @Transient
    private Customer customer;

    @Transient
    private Address address;

    @Transient
    private Card card;

    @Transient
    private Collection<Item> items;

    @Transient
    private Shipment shipment;

    private Date date = Calendar.getInstance().getTime();

    @Transient
    private float total;

    public CustomerOrder() {
    }

    public CustomerOrder(String id, String customerId, Customer customer, Address address, Card card,
                         Collection<Item> items, Shipment shipment, Date date, float total) {
        this.id = id;
        this.customerId = customerId;
        this.customer = customer;
        this.address = address;
        this.card = card;
        this.items = items;
        this.shipment = shipment;
        this.date = date;
        this.total = total;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "orderId='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customer=" + customer +
                ", address=" + address +
                ", card=" + card +
                ", items=" + items +
                ", date=" + date +
                '}';
    }

// Crappy getter setters for Jackson

    public String getOrderId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Collection<Item> getItems() {
        return items;
    }

    /*public void setItems(Collection<Item> items) {
        this.items = items;
    }*/

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
