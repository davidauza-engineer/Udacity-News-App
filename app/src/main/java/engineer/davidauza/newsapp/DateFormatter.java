package engineer.davidauza.newsapp;

/**
 * This class is responsible for formatting a date received in the format YYYY-MM-DDTHH:MM:SSZ to
 * the format Month DD, YYYY
 */
public class DateFormatter {

    /**
     * This method converts a date in the format YYYY-MM-DDTHH:MM:SSZ to the format Month DD, YYYY
     *
     * @param pDate is the date in the format YYYY-MM-DDTHH:MM:SSZ
     * @return the date in the format Month DD, YYYY
     */
    public static String format(String pDate) {
        char[] charArray = pDate.toCharArray();
        String year = "";
        String month = "";
        String day = "";
        for (int i = 0; i < 10; i++) {
            if (i < 4) {
                year += charArray[i];
            } else if (i > 4 && i < 7) {
                month += charArray[i];
            } else if (i > 7) {
                day += charArray[i];
            }
        }
        switch (month) {
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "July";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            default:
                month = "December";
                break;
        }
        return month + " " + day + ", " + year;
    }
}
