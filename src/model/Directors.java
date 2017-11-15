package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class Directors {
    protected String Name;
    protected int FaceBookLikes;

    //constructor

    public Directors(String name) {
        Name = name;
    }

    public Directors(String name, int faceBookLikes) {
        Name = name;
        FaceBookLikes = faceBookLikes;
    }

    //getter and setter

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getFaceBookLikes() {
        return FaceBookLikes;
    }

    public void setFaceBookLikes(int faceBookLikes) {
        FaceBookLikes = faceBookLikes;
    }
}
