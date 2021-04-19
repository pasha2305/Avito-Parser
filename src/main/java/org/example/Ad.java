package org.example;

import java.util.Objects;

public class Ad {
    private String name;
    private String cost;
    private String date;
    private String href;

    public Ad(String name, String cost, String date, String href) {
        this.name = name;
        this.cost = cost;
        this.date = date;
        this.href = "https://www.avito.ru/" + href;
    }

    @Override
    public String toString() {
        return "name:" + name +
                "\ncost:" + cost +
                "\ndate:" + date +
                "\nhref:" + href + "\n";
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }

    public String getHref() {
        return href;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return Objects.equals(name, ad.name) &&
                Objects.equals(cost, ad.cost) &&
                Objects.equals(date, ad.date) &&
                Objects.equals(href, ad.href);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, date, href);
    }
}
