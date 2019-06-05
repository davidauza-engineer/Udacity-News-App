package engineer.davidauza.newsapp;

/**
 * //TODO check this description
 * {@link News} represents a News obtained from the Guardian API.
 * It contains a title, the name of the section it belongs to, the author of the news, if available,
 * the date of the news, if available, and a link to the news on the web.
 */
public class News {

    /**
     * The title of the news.
     */
    private String mTitle;

    /**
     * The section the news belongs to.
     */
    private String mSection;

    /**
     * The author of the news.
     */
    private String mAuthor;

    /**
     * The date of the news.
     */
    private String mDate;

    /**
     * The url where the new is located.
     */
    private String mLink; //TODO check if a getter is needed

    public News(String pTitle,
                String pSection,
                String pAuthor,
                String pDate,
                String pLink) {
        mTitle = pTitle;
        mSection = pSection;
        // TODO check if present
        mAuthor = pAuthor;
        // TODO check if present
        mDate = pDate;
        mLink = pLink;
    }

    /**
     * This method returns the title of the news.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * This method returns the section the news belongs to.
     */
    public String getSection() {
        return mSection;
    }

    /**
     * This method returns the author of the news.
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * This method returns the date of the news.
     */
    public String getDate() {
        return mDate;
    }
}
