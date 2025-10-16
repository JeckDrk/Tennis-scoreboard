package TennisScoreboard.model;

import TennisScoreboard.entity.MatchEntity;
import TennisScoreboard.exception.UniqueException;
import TennisScoreboard.util.utilPersistence;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class MatchScorePersistence implements MatchesStorage {

    protected static final SessionFactory SESSION_FACTORY = utilPersistence.getSessionFactory();

    @Override
    public List<MatchEntity> getPaginated(int page, int pageSize) {
        try (Session session = SESSION_FACTORY.openSession()) {
            int firstEntityOnPage = 1 + (page - 1) * pageSize;
            int lastEntityOnPage = page * pageSize;
            session.beginTransaction();
            return session.createQuery("from MatchEntity m where m.id >= :first and m.id <= :last order by m.id ASC", MatchEntity.class)
                    .setParameter("first", firstEntityOnPage)
                    .setParameter("last", lastEntityOnPage)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getEntityCount() {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            return session.createQuery("select count(m) from MatchEntity m", long.class).uniqueResult();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MatchEntity> getPaginatedForSearch(int page, int pageSize, String search) {
        try (Session session = SESSION_FACTORY.openSession()) {
            int firstEntityOnPage = (page - 1) * pageSize;
            session.beginTransaction();
            return session.createQuery("from MatchEntity m where " +
                            "(lower(m.player1.name) like :search or lower(m.player2.name) like :search) " +
                            "order by m.id ASC", MatchEntity.class)
                    .setParameter("search", "%" + search.toLowerCase() + "%")
                    .setFirstResult(firstEntityOnPage)
                    .setMaxResults(pageSize)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getEntityCountForSearch(String search) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            return session.createQuery("select count(m) from MatchEntity m where " +
                    "(lower(m.player1.name) like :search or lower(m.player2.name) like :search)", long.class)
                    .setParameter("search", "%" + search.toLowerCase() + "%")
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void put(MatchEntity value) {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();

            session.persist(value);

            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            if ("PUBLIC.CONSTRAINT_INDEX_D".equals(e.getConstraintName())) {
                throw new UniqueException("Match already exists!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
