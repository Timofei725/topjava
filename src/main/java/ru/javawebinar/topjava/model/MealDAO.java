package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class MealDAO {
    private List<Meal> arrayList = new CopyOnWriteArrayList();

    {
        //special for HW01
        arrayList.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        arrayList. add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 10));
        arrayList. add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        arrayList. add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        arrayList.  add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        arrayList. add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        arrayList. add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));


        int i=0;
        for(Meal meal:arrayList){
            meal.setId(i);
            i++;
        }
    }
    public MealDAO() {


    }

    public void add(Meal meal) {
        boolean ifNull=false;
        try {
            if(meal.getId()==null) meal.setId(arrayList.stream().map(x->x.getId()).max(Integer::compare).orElse(0)+1);
        }
        catch (Exception e){
            ifNull=true;
            meal.setId(arrayList.stream().map(x->x.getId()).max(Integer::compare).orElse(0)+1);
        }

if(!ifNull){
    delete(meal.getId());
    arrayList.add(meal);
}


    }

    public void delete(Integer mealId) {
        for(Meal meal:arrayList)if(meal.getId()==mealId) arrayList.remove(meal);

    }

    public void update(Meal meal) {
        if (arrayList.stream().anyMatch(x -> x.getId().equals(meal.getId()))) {
            delete(meal.getId());
            add(meal);
        }
    }

    public  List<MealTo> getAll() {
      return arrayList.stream()
                .map(meal -> createTo(meal, false))
                .collect(Collectors.toList());

    }

    public Meal getById(Integer mealId) {
        return arrayList.stream().filter(x -> x.getId().equals(mealId)).findFirst().orElse(null);
    }
    private static MealTo createTo(Meal meal, boolean excess) {
       MealTo mealTo= new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
       mealTo.setId(meal.getId());
       return mealTo;
    }





}

