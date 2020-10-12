package async;

public class User {
    private String firstName;
    private String lastName;
    private String country;
    private int rating;

    public User(String firstName, String lastName, String country, int rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.rating = rating;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
