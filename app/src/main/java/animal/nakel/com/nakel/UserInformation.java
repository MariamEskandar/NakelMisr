package animal.nakel.com.nakel;

/**
 * Created by Mbeder on 22/3/2017.
 */
/*this class used in ProfileActivity to save data in firebase*/
public class UserInformation {
    public String Type;
    public String Name;
    public String National_Number ;
    public String Phone_Number ;
    public String Car_Number;
    public String License_Number;
    public String SecondTime;
    public String photo;
    public String photo_id;
    public String Email;
    public String User_id;
    public String Block;

/*
    public String getPhoto_id_Client() {
        return photo_id_Client;
    }

    public void setPhoto_id_Client(String photo_id_Client) {
        this.photo_id_Client = photo_id_Client;
    }

    public String photo_id_Client;

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }
*/
    public UserInformation(String secondTime) {
       this.SecondTime = secondTime;
    }

    public UserInformation(String type, String name, String national_Number, String phone_Number, String photo_id,String photo_id_Client){



    }



    public UserInformation(String Type,String Name, String National_Number,String Phone_Number,String Car_Number,String License_Number,String photo,String photo_id,String Email,String User_id,String Block) {
        this.Type = Type;
        this.Name = Name;
        this.National_Number = National_Number;
        this.Phone_Number = Phone_Number;
        this.Car_Number = Car_Number;
        this.License_Number = License_Number;
        this.photo = photo;
        this.photo_id=photo_id;
        this.photo_id=photo_id;
        this.Email=Email;
        this.User_id=User_id;
        this.Block=Block;
    }

    public UserInformation(String type, String name, String national_Number, String phone_Number,String photo_id,String Email,String User_id) {
        Type = type;
        Name = name;
        National_Number = national_Number;
        Phone_Number = phone_Number;
        this.photo_id=photo_id;
        this.Email=Email;
        this.User_id=User_id;
        this.Block=Block;
    }
}
