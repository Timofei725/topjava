package ru.javawebinar.topjava;

import org.junit.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final Meal meal = new Meal(100004,LocalDateTime.of(
            2022, 10, 19, 10, 23, 54),"obed",1488);

    public static final  Meal meal1 = new Meal(100005 ,LocalDateTime.of(
            2022, 10, 19, 11, 00, 00),"zavtrack",228);

    public static final  Meal meal2 =  new Meal(100006,LocalDateTime.of(
            2020, 10, 19, 11, 00, 00),"Uzin",1444);
    public static final  Meal newMEal = new Meal(null,LocalDateTime.of(
            2020, 9, 19, 12, 00, 00),"UzinNew",1234);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "newTest", 1555);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(newMEal);
        LocalDate localDate=LocalDate.of(2022,11,03);
        LocalTime localTime=LocalTime.of(15,25);
        //updated.setDate_time(LocalDateTime.of(2022, 03, 10, 14, 22, 22));
        updated.setDate_time(LocalDateTime.of(localDate,localTime));
        updated.setDescription("UpdateTest228");
        updated.setCalories(1280);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);

    }
     //сравниваем список
     public static void assertMatchArray(ArrayList<Meal> actual, ArrayList<Meal> expected) {
         assertThat(actual).hasSize(expected.size()).hasSameElementsAs(actual);
     }

}
