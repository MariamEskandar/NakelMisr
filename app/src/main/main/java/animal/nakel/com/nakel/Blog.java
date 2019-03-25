package animal.nakel.com.nakel;

/**
 * Created by MINA on 17/03/2017.
 */
public class Blog {



    private  String  Title, Description,image,MoneyOffere,FromWhere,ToLocation,Weight,PostTime,uid,dayToMove,Username;

    public Blog(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Blog(String username, String title, String description, String image, String moneyOffere, String fromWhere, String toLocation, String weight, String postTime, String dayToMove) {
        Title = title;
        Description = description;
        this.image = image;
        MoneyOffere = moneyOffere;

        FromWhere = fromWhere;
        ToLocation = toLocation;

        Weight = weight;
        PostTime = postTime;

        this.dayToMove = dayToMove;
        this.Username=username;
    }

    public Blog (){

    }

    public String getDayToMove() {
        return dayToMove;
    }



    public void setDayToMove(String dayToMove) {
        this.dayToMove = dayToMove;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public String getMoneyOffere() {
        return MoneyOffere;
    }

    public void setMoneyOffere(String moneyOffere) {
        MoneyOffere = moneyOffere;
    }

    public String getFromWhere() {
        return FromWhere;
    }

    public void setFromWhere(String fromWhere) {
        FromWhere = fromWhere;
    }

    public String getToLocation() {
        return ToLocation;
    }

    public void setToLocation(String toLocation) {
        ToLocation = toLocation;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getPostTime() {
        return PostTime;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }


    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return image;
    }




    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
///////////////////////////////////////////////////////////////////////////////////**********************/////////////////////////////////


/////////////////////////////////////////////////////////////////************************************/////////////////////////////////



}
