package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.ejb.DuplicateKeyException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;
    @Test
    public void get() {
        Meal meal = service.get(100006,100003);
        assertThat(meal).isEqualTo(MealTestData.meal2);
    }
    @Test
    public void getOtherFood() {
        assertThrows(NotFoundException.class, ()->service.get(100006,100001));
    }
    @Test
    public void create() {
        // Arrange
        Meal newMEal=MealTestData.newMEal;
        // Act
        service.create(newMEal,USER_ID);
        // Assert
        MealTestData. assertMatch(service.get(100007,USER_ID),MealTestData.newMEal);
    }
    @Test
    public void createDataAccessException() {
        // Arrange
        Meal newMEalWithOldTime=new Meal(MealTestData.meal.getDate_time(),"obedWITHOLDTIme",1488);
        // Act
        // Assert
        assertThrows(DataAccessException.class, ()->service.create(newMEalWithOldTime,USER_ID));

    }
    @Test
    public void update() {
        // Arrange
        Meal updated=MealTestData.getUpdated();
        updated.setId(100006);
        // Act
        service.update(updated,USER_ID+3);
        //Assert
        MealTestData. assertMatch(service.get(100006,USER_ID+3),updated);
    }
    @Test
    public void delete() {
        assertThrows(NotFoundException.class, () -> service.delete(10,10));
    }

    @Test
    public void getBetweenInclusive() {
    }


    @Test
    public void getAll() {
        // Arrange
        ArrayList<Meal> mealsFromTestDate=new ArrayList<>();
        ArrayList<Meal> realMeals;

        // Act
        mealsFromTestDate.add(MealTestData.meal);
        mealsFromTestDate.add(MealTestData.meal1);
        mealsFromTestDate.add(MealTestData.meal2);
        realMeals= (ArrayList<Meal>) service.getAll(USER_ID+1);
        realMeals.add(service.getAll(USER1_ID+1).get(0));
        realMeals.size();
        // Assert
        MealTestData.assertMatchArray(mealsFromTestDate,realMeals);
    }





}
// Arrange
// Act
//Assert