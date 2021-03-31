package Igushkin.Lesson3;

import java.util.Objects;

public class Address implements Comparable{

    private String city;
    private String street;
    private int house;
    private int apartment;

    public Address(String city, String street, int house, int apartment) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public int getApartment() {
        return apartment;
    }

    public void setApartment(int apartment) {
        this.apartment = apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return house == address.house && apartment == address.apartment && city.equals(address.city) && street.equals(address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, house, apartment);
    }

    @Override
    public int compareTo(Object o) {
        Address that = (Address) o;
        StringBuilder builderThis = new StringBuilder ();
        StringBuilder builderThat = new StringBuilder ();
        builderThis.append(this.city).append(this.street).append(this.house).append(this.apartment);
        builderThat.append(that.city).append(that.street).append(that.house).append(that.apartment);
        return builderThis.toString().compareTo(builderThat.toString());
    }

    @Override
    public String toString() {
        return "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house=" + house +
                ", apartment=" + apartment;
    }
}
