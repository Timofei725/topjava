package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@RestController
@RequestMapping(value = MealRestController.REST_MEALS_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_MEALS_URL = "/rest/meals";

    @Override
    @GetMapping("/{id}")//
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}") //
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping()//
    public List<MealTo> getAll() {
        return super.getAll();
    }

    //Ne tochno
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)//
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_MEALS_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)//
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @GetMapping("/getBetween") //
    public List<MealTo> getBetween(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable LocalDate startDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)  @Nullable LocalTime startTime,
                                   @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @Nullable LocalDate endDate,
                                   @RequestParam  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)  @Nullable LocalTime endTime) {
        if(startDate == null) startDate= DateTimeUtil.atStartOfDayOrMin(null).toLocalDate();
        if(endDate == null) endDate= DateTimeUtil.atStartOfNextDayOrMax(null).toLocalDate();
        if(startTime==null) startTime=LocalTime.of(0,0);
        if(endTime==null) endTime=LocalTime.of(23,59);

        return   super.getBetween(startDate, startTime, endDate, endTime);
    }
}
/*
Original getBetween
    @GetMapping("/getBetween")
    public List<MealTo> getBetween(@RequestParam   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       LocalDateTime since,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime to) {
      LocalDate startDate=LocalDate.of(since.getYear(),since.getMonth(),since.getDayOfMonth());
        LocalTime startTime=LocalTime.of(since.getHour(),since.getMinute());
        LocalDate endDate=LocalDate.of(to.getYear(),to.getMonth(),to.getDayOfMonth());
        LocalTime endTime=LocalTime.of(to.getHour(),to.getMinute());
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
 */
