package group8.com.application.Model;

/**
 * Created by Kristiyan on 5/12/2015.
 */
public class Medal {

    public String id;
    public String title;
    public int img_url;


    public Medal(String medal_title, int medal_img_url) {
        title = medal_title;
        img_url = medal_img_url;
    }

}
