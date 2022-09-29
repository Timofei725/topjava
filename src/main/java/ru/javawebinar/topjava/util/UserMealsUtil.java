package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

      List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1400);
        mealsTo.forEach(System.out::println);

      System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 1400));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> userMealWithExcesses=new ArrayList<>();
        int currentCalories=0;
        for(UserMeal userMeal:meals) {
            if (TimeUtil.isBetweenHalfOpen(TimeUtil.getLocalTime(userMeal.getDateTime()), startTime, endTime)) {
                currentCalories += userMeal.getCalories();
                if (caloriesPerDay < currentCalories) break;
            }
        }

       if(caloriesPerDay<currentCalories){
           for(UserMeal userMeal:meals) {
                   userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(),
                           userMeal.getDescription(), userMeal.getCalories(), true));
           }
       }
       else {
           for(UserMeal userMeal:meals) {
                   userMealWithExcesses.add(new UserMealWithExcess(userMeal.getDateTime(),
                           userMeal.getDescription(), userMeal.getCalories(), false));
           }


       }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        List<UserMealWithExcess> userMealWithExcesses=new ArrayList<>();
        AtomicInteger currentCalories= new AtomicInteger();
      meals.stream()
              .filter(x->TimeUtil.isBetweenHalfOpen(TimeUtil.getLocalTime(x.getDateTime()),startTime,endTime)).forEach(x-> currentCalories.addAndGet(x.getCalories()));
        if(caloriesPerDay<currentCalories.get()){
       meals.stream().forEach(x->    userMealWithExcesses.add(new UserMealWithExcess(x.getDateTime(),
               x.getDescription(), x.getCalories(), true)));

            }
        else {
            if(caloriesPerDay<currentCalories.get()){
                meals.stream().forEach(x->    userMealWithExcesses.add(new UserMealWithExcess(x.getDateTime(),
                        x.getDescription(), x.getCalories(), false)));
            }
        }
        return userMealWithExcesses;
    }

}
