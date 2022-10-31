package ru.javawebinar.topjava.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MealTo {
    private Integer id;
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean excess;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean excess) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.excess = excess;
    }

    public MealTo() {
        this.dateTime = LocalDateTime.of(222,2,2,2,2);
        this.description = null;
        this.calories = 1;
        this.excess = true;

    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExcess() {
        return excess;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", excess=" + excess +
                '}';
    }
static class ExercisesWithRepeatsHelper {

    Map<String, String> exercisesWithRepetitions = new LinkedHashMap <>();

    public ExercisesWithRepeatsHelper(String exercisesWithRepetitionsString) {
        String[] exercisesWithRepetitionsArray = exercisesWithRepetitionsString.split(" ");
        for (String string : exercisesWithRepetitionsArray) {
            String thisExerciseId = string.substring(0, 1);
            String thisRepetitions = string.substring(string.indexOf(":") + 1);
            exercisesWithRepetitions.put(thisExerciseId, thisRepetitions);

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExercisesWithRepeatsHelper that = (ExercisesWithRepeatsHelper) o;
        return Objects.equals(exercisesWithRepetitions, that.exercisesWithRepetitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exercisesWithRepetitions);
    }
}
}
