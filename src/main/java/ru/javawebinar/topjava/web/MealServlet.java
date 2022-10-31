package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealDAO;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.sql.Time;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.regex.Pattern;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet  {
    private static final Logger LOG = getLogger(UserServlet.class);
    private MealDAO dao =new MealDAO();
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String MEALS = "/meals.jsp";

    public MealServlet() {
        super();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            int mealId= Integer.parseInt(request.getParameter("mealId"));
            dao.delete(mealId);
            forward = MEALS;
            request.setAttribute("meals", dao.getAll());
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealId= Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeals")){
            forward = MEALS;
            request.setAttribute("meals", dao.getAll());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Meal meal=null;
        try {
            Date date= new Date(String.valueOf(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(request.getParameter("dateTime"))));
            LocalDate localDate=LocalDate.of(date.getYear(),date.getMonth(),date.getDay());
            LocalTime localTime=LocalTime.of(date.getHours(),date.getMinutes());
            LocalDateTime dateTime=LocalDateTime.of(localDate,localTime);

             meal=new Meal(dateTime,request.getParameter("description"),Integer.parseInt(request.getParameter("calories")));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String mealID = request.getParameter("mealID");
        if(mealID == null || mealID.isEmpty())
        {
            dao.add(meal);
        }
        else
        {
            meal.setId(Integer.parseInt(mealID));
            dao.update(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(MEALS);
        request.setAttribute("meals", dao.getAll());
        view.forward(request, response);
    }


}