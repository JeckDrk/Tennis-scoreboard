package TennisScoreboard.dao;


import TennisScoreboard.exception.StorageException;
import TennisScoreboard.model.PlayerEntity;
import TennisScoreboard.exception.UniqueException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import TennisScoreboard.util.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

public class PlayerPersistence implements PersistenceStorage<PlayerEntity> {

    protected static final org.hibernate.SessionFactory SESSION_FACTORY = SessionFactory.getSessionFactory();

    @Override
    public PlayerEntity get(Object name) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            PlayerEntity player = session.createQuery("from PlayerEntity where name = :name", PlayerEntity.class)
                    .setParameter("name", name)
                    .uniqueResult();
            session.getTransaction().commit();
            return player;
        } catch (HibernateException e) {
            throw new StorageException(e.getMessage());
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
        } catch (HibernateException e) {
            throw new StorageException(e.getMessage());
        }
    }
}
