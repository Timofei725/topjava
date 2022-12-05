package ru.javawebinar.topjava.web.meal;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_MEALS_URL = MealRestController.REST_MEALS_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEALS_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MealTestData.meal1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_MEALS_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class,
                () -> mealService.get(MEAL1_ID, USER_ID));

    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEALS_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(
                        List.of(meal1, meal2, meal3, meal4, meal5
                                        , meal6, meal7).stream()
                                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                                .collect(Collectors.toList())));
    }

    @Test
    void update() throws Exception {
        Meal updated = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_MEALS_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_MEALS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal created = MEAL_MATCHER.readFromJson(action);
        int newId = created.id();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(newId, USER_ID), newMeal);
    }
    @Test
    void getBetweenWithNull() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEALS_URL
              + "getBetween?startDate" + "&startTime" + "&endDate" + "&endTime" ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
           .andExpect(MEAL_MATCHER.contentJson(meals));

    }
    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_MEALS_URL
                   + "getBetween?startDate=" + LocalDate.of(2020, Month.JANUARY, 30) + "&startTime=" + LocalTime.of(10, 0)
                  + "&endDate=" + LocalDate.of(2020, Month.JANUARY, 30) + "&endTime="+
                  LocalTime.of(21, 0) ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                 .andExpect(MEAL_MATCHER.contentJson(meal3,meal2,meal1));
    }
}
/*
Original getBetween
    @Test
    void getBetween() throws Exception {
        LocalDateTime since = meal1.getDateTime();
        LocalDateTime to = meal3.getDateTime().plusSeconds(3000l);
        perform(MockMvcRequestBuilders.get(REST_MEALS_URL
                + "getBetween?since=" + since + "&to=" + to))

                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal3, meal2, meal1));
    }
 */