package model;

/**
 * Created by hu_minghao on 3/21/17.
 */
public class Keywords {
    protected String KeywordName;

    //constructor

    public Keywords(String keywordName) {
        KeywordName = keywordName;
    }

    //getter and setter

    public String getKeywordName() {
        return KeywordName;
    }

    public void setKeywordName(String keywordName) {
        KeywordName = keywordName;
    }
}
