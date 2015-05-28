package group8.com.application.Model;



public class Medal {

    public String id;
    public String title;
    public int img_url;
    public boolean isAchieved;

    /**
     * Medal object, used to create
     * multiple medals
     *
     * @param medal_title
     * @param medal_img_url
     * @param achieved
     */

    public Medal(String medal_title, int medal_img_url, boolean achieved) {
        title = medal_title;
        img_url = medal_img_url;
        isAchieved = achieved;
    }

    /**
     * getter for the title of the medal object
     *
     * @return title
     */
    public String getTitle(){
        return title;
    }

}
