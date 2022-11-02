package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);


    {
      save(new Meal(null,LocalDateTime.now(),"Zavtrack",1000),1);
        save(new Meal(null,LocalDateTime.now(),"Obed",1499),1);
        save(new Meal(null,LocalDateTime.now(),"Uzin",1600),1);


    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()){
            int newId=counter.incrementAndGet();
            meal.setId(newId);
            meal.setUserId(userId);
            repository.put(newId, meal);
            log.info("save {}", meal);

            return meal;
        }
        else if(repository.get(meal.getId()).getUserId()!=userId){
            log.info("save {} meal.getId()).getUserId()!=userId", meal);

            return null;
        }
        else if(repository.get(meal.getId()).getUserId()==userId){
            repository.put(meal.getId(), meal);
            return meal;
        }
        log.info("update {}", meal);
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id,int userId) {
        log.info("delete {}", id);
        if(repository.containsKey(id)){
            if(repository.get(id).getUserId()!=userId)return false;// если айди еды не совпадает с айди пользователя вовзращаю фолс
            return repository.remove(id) != null;
        }
        else return false;
    }

    @Override
    public Meal get(int id,int userId) {
        log.info("get {} where userId {}", id,userId);
        if(repository.containsKey(id)){
        if(repository.get(id).getUserId()!=userId)return null;// если айди еды не совпадает с айди пользователя вовзращаю нулл
        return repository.getOrDefault(id,null);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll() {

        Collection <Meal> meals=new ArrayList<>();
        log.info("getAll");
          meals.addAll(repository.values().stream().collect(Collectors.toList()));

          return meals;
    }

    @Override
    public List<Meal> getListForUserId(Integer userId) {
        log.info("getListForUserId {}",userId);
        List<Meal> listMeal=new ArrayList<>();
        listMeal.addAll( getAll().stream().sorted(Comparator.comparing(Meal::getDate))
                .filter(x->x.getUserId()==userId).collect(Collectors.toList()));

        return listMeal;
    }
/*
    public static void main(String[] args) {
        InMemoryMealRepository inMemoryMealRepository=new InMemoryMealRepository();
        inMemoryMealRepository.save(new Meal(0, LocalDateTime.now(),"sss",1488),4);

        System.out.println(inMemoryMealRepository.get(4,1));
    }

 */
}

