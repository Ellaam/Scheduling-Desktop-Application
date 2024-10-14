package model;

/**
 * The FirstLevelDivision class represents a division within a country.
 * It contains attributes and methods for managing division information.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class FirstLevelDivision {

    private int divisionId;
    private String division;
    private int countryId;

    /**
     * Constructs a new FirstLevelDivision object with the specified attributes.
     *
     * @param divisionId The unique identifier of the division.
     * @param division   The name of the division.
     * @param countryId  The unique identifier of the country to which this division belongs.
     */
    public FirstLevelDivision(int divisionId, String division, int countryId) {

        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Gets the unique identifier of the country to which this division belongs.
     *
     * @return The country's unique identifier.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the unique identifier of the country to which this division belongs.
     *
     * @param countryId The country's unique identifier to set.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the unique identifier of the division.
     *
     * @return The division's unique identifier.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the unique identifier of the division.
     *
     * @param divisionId The division's unique identifier to set.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Gets the name of the division.
     *
     * @return The division's name.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the name of the division.
     *
     * @param division The division's name to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Gets a string representation of the division, which is the division's name.
     *
     * @return The division's name as a string.
     */
    @Override
    public String toString(){
        return this.getDivision();
    }
}
