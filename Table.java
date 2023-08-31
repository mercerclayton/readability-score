package readability;

/**
 * The Table enum represents different age groups and their corresponding scores.
 *
 * @version 1.10 14 Aug 2023
 * @author Clayton Mercer
 */
public enum Table {

    GROUP_ONE("6", 1.0),
    GROUP_TWO("7", 2.0),
    GROUP_THREE("8", 3.0),
    GROUP_FOUR("9", 4.0),
    GROUP_FIVE("10", 5.0),
    GROUP_SIX("11", 6.0),
    GROUP_SEVEN("12", 7.0),
    GROUP_EIGHT("13", 8.0),
    GROUP_NINE("14", 9.0),
    GROUP_TEN("15", 10.0),
    GROUP_ELEVEN("16", 11.0),
    GROUP_TWELVE("17", 12.0),
    GROUP_THIRTEEN("18", 13.0),
    GROUP_FOURTEEN("22", 14.0),
    GROUP_FIFTEEN("24", 15.0);

    private final String ageGroup;
    private final double score;

    /**
     * Constructor to initialize an age-score pair for different scores.
     *
     * @param ageGroup The age group corresponding to the score
     * @param score The readability score of the text
     */
    Table(String ageGroup, double score) {
        this.ageGroup = ageGroup;
        this.score = score;
    }

    /**
     * Gets the age group for the specified score.
     *
     * @return age group
     */
    public String getAgeGroup() {
        return ageGroup;
    }

    /**
     * Gets the score for the specified age group.
     *
     * @return readability score
     */
    public double getScore() {
        return score;
    }
}
