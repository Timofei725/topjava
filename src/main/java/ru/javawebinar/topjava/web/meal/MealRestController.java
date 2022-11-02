package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    private MealService service;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id,authUserId());

    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
                service.create(meal,authUserId());
        meal.setUserId(authUserId());
        return       meal;
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id,authUserId());
    }

    public void update(Meal meal) {
        meal.setUserId(authUserId());
        log.info("update {} with userId={}", meal, authUserId());
        assureIdConsistent(meal, meal.getId());
        service.update(meal, authUserId());
    }



}