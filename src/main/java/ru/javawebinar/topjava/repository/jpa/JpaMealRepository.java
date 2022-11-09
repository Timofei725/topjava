package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {


    @PersistenceContext
    private EntityManager em;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            User owner=new User();
            owner.setId(userId);
            meal.setUser(owner);
            em.persist(meal);
            return meal;
        } else if(em.createNamedQuery(Meal.UPDATE,Meal.class).setParameter("description",meal.getDescription())
                       .setParameter( "calories",meal.getCalories())
                .setParameter( "dateTime",meal.getDateTime())
                .setParameter("id",meal.getId())
                .setParameter("user_id",userId)
                .executeUpdate()==0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE,Meal.class).setParameter("id",id).setParameter("user_id",userId).executeUpdate() !=0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals= em.createNamedQuery(Meal.FIND,Meal.class).setParameter("id",id).setParameter("user_id",userId).getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
       return em.createNamedQuery(Meal.FIND,Meal.class).setParameter("user_id",userId).getResultList();

    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id=:userId  AND m.dateTime >=:startDateTime " +
                "AND m.dateTime <:endDateTime ORDER BY m.dateTime DESC",Meal.class).setParameter("userId",userId)
                .setParameter("startDateTime",startDateTime)
                .setParameter("endDateTime",endDateTime).getResultList();
    }
}