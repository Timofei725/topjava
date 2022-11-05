package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Meal extends AbstractBaseEntity {
    private  LocalDateTime date_time;

    private  String description;

    private  int calories;



    public Meal() {

    }

    public Meal(LocalDateTime date_time, String description, int calories) {
        this(null, date_time, description, calories);
    }

    public Meal(Integer id, LocalDateTime date_time, String description, int calories) {
        super(id);
        this.date_time = date_time;
        this.description = description;
        this.calories = calories;
    }
    public Meal(Meal m) {
        this(m.id, m.date_time, m.description, m.calories);
    }


    public LocalDateTime getDate_time() {
        return date_time;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return date_time.toLocalDate();
    }

    public LocalTime getTime() {
        return date_time.toLocalTime();
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", date_time=" + date_time +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
