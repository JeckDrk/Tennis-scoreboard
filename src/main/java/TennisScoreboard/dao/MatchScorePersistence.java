package TennisScoreboard.dao;

import TennisScoreboard.exception.ApplicationException;
import TennisScoreboard.model.MatchEntity;
import TennisScoreboard.exception.UniqueException;
import TennisScoreboard.util.SessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class MatchScorePersistence implements PersistenceStorage<MatchEntity>, PaginatedSearchStorage {

    protected static final org.hibernate.SessionFactory SESSION_FACTORY = SessionFactory.getSessionFactory();

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
        } catch (HibernateException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public long getEntityCount() {
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            return session.createQuery("select count(m) from MatchEntity m", long.class).uniqueResult();
        } catch (HibernateException e) {
            throw new ApplicationException(e.getMessage());
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
        } catch (HibernateException e) {
            throw new ApplicationException(e.getMessage());
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
            throw new ApplicationException(e.getMessage());
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
        } catch (HibernateException e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    @Override
    public MatchEntity get(Object object) {
        int id = Integer.parseInt((String) object);
        try (Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();
            return session.getReference(MatchEntity.class, id);
        } catch (HibernateException e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
