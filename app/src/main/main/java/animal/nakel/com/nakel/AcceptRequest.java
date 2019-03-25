package animal.nakel.com.nakel;

/**
 * Created by MINA on 10/04/2017.
 */

public class AcceptRequest {

    public AcceptRequest(){

    }
   private String Client_uid,Post_id,Price,Taxe_uid,Taxi_Phone;

    public AcceptRequest(String client_uid, String post_id, String price, String taxe_uid, String taxi_Phone) {
    //    this.Client_uid = client_uid;
        this.Post_id = post_id;
        this.Price = price;
        this.Taxe_uid = taxe_uid;
        this.Taxi_Phone = taxi_Phone;
    }





    public String getClient_uid() {
        return Client_uid;
    }

    public void setClient_uid(String client_uid) {
        Client_uid = client_uid;
    }
    public String getPost_id() {
        return Post_id;
    }

    public void setPost_id(String post_id) {
        Post_id = post_id;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTaxe_uid() {
        return Taxe_uid;
    }

    public void setTaxe_uid(String taxe_uid) {
        Taxe_uid = taxe_uid;
    }

    public String getTaxi_Phone() {
        return Taxi_Phone;
    }

    public void setTaxi_Phone(String taxi_Phone) {
        Taxi_Phone = taxi_Phone;
    }


}