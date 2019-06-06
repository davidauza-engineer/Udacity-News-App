package engineer.davidauza.newsapp;

/**
 * {@link News} represents a News obtained from the Guardian API.
 * It contains a title, the name of the section it belongs to, the author of the news, the date of
 * the news, and a link to the news on the web.
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
     * The URL where the new is located.
     */
    private String mLink;

    /**
     * Create a new {@link News} object.
     *
     * @param pTitle   is the title of the news.
     * @param pSection is the section the news belongs to.
     * @param pAuthor  is the author of the news.
     * @param pDate    is the date the news has been published.
     * @param pLink    is the URL to find the news on the web.
     */
    public News(String pTitle,
                String pSection,
                String pAuthor,
                String pDate,
                String pLink) {
        mTitle = pTitle;
        mSection = pSection;
        mAuthor = pAuthor;
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

    /**
     * This method returns the link of the news.
     */
    public String getLink() {
        return mLink;
    }
}
