package hello.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Orders {
    public String getId() {
    return id;
}

    public void setId(String id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
    @Id
    private String id;
    int price;
    int quantity;
    String item;

    public Orders() {
    }

    public Orders(int price, int quantity, String item) {
        this.price = price;
        this.quantity = quantity;
        this.item = item;
    }

    @Override
    public String toString() {
        return "MyResultObject{" +
                "price=" + price +
                ", quantity=" + quantity +
                ", item='" + item + '\'' +
                '}';
    }

}
