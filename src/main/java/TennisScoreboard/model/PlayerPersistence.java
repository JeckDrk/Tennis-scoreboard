package TennisScoreboard.model;


import TennisScoreboard.entity.PlayerEntity;
import TennisScoreboard.exception.UniqueException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import TennisScoreboard.util.utilPersistence;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class PlayerPersistence implements PlayerStorage {

    protected static final SessionFactory SESSION_FACTORY = utilPersistence.getSessionFactory();

    private final int ENTITY_ON_PAGE = 4;

    @Override
    public PlayerEntity get(String name) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            PlayerEntity player = session.createQuery("from PlayerEntity where name = :name", PlayerEntity.class)
                    .setParameter("name", name)
                    .uniqueResult();
            session.getTransaction().commit();
            return player;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void put(PlayerEntity player) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();

            session.persist(player);

            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            if ("PUBLIC.CONSTRAINT_INDEX_D".equals(e.getConstraintName())) {
                throw new UniqueException("Player already exists");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
