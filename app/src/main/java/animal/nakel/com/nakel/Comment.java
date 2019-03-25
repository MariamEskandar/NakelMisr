package animal.nakel.com.nakel;

/**
 * Created by MINA on 03/04/2017.
 */

public class Comment {


   public Comment(){

   }
    private  String  Price;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    private String Post_id;
    private String Taxe_uid;
    private String Name;
    private String Phone_Number;

    public Comment(String name, String phone_Number) {
        Name = name;
        Phone_Number = phone_Number;
    }
    //  private String Taxe_uid;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public Comment(String username) {

        Username = username;
    }

    private String Username;

    public Comment(String price, String post_id, String taxe_uid) {
        this.Price = price;
        this.Post_id = post_id;
        this.Taxe_uid = taxe_uid;
    }

    public String getPrice() {
        return Price;
    }

   public void setPrice(String price) {
       Price = price;
    }

    public void setPost_id(String post_id) {
        Post_id = post_id;
    }

    public String getPost_id() {
        return Post_id;
    }

    public void setTaxi_uid(String taxe_uid) {
        Taxe_uid = taxe_uid;
    }

    public String getTaxe_uid() {
        return Taxe_uid;
    }




}
