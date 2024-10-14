package model;

/**
 * The Country class represents a country entity.
 * It contains attributes and methods for managing country information.
 * @author Elham Pazhakh
 * JAVADOCS FOLDER LOCATION: in this project under the src folder.
 */
public class Country {

    private int countryId;
    private String country;

    /**
     * Constructs a new Country object with the specified attributes.
     *
     * @param countryId The unique identifier of the country.
     * @param country   The name of the country.
     */
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Gets the unique identifier of the country.
     *
     * @return The country's unique identifier.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Sets the unique identifier of the country.
     *
     * @param countryId The country's unique identifier to set.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Gets the name of the country.
     *
     * @return The country's name.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     *
     * @param country The country's name to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets a string representation of the country, which is the country's name.
     *
     * @return The country's name as a string.
     */
    @Override
    public String toString(){
        return this.getCountry();
    }
}
