package dao.hibernate;

import configuration.HibernateUtil;
import dao.Dao;
import exception.DaoException;
import model.CardStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CardStatusHibernateDaoImpl implements Dao<CardStatus> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS card_status(id SERIAL PRIMARY KEY, card_status_name VARCHAR(255));
    """;

        private static final String DROP_TABLE_SQL = """
    DROP TABLE IF EXISTS card_status CASCADE;
            """;

        private static final String CLEAR_TABLE_SQL = """
    TRUNCATE card_status;
            """;


    @Override
    public void createTable() {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void dropTable() {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(DROP_TABLE_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void clearTable() {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CLEAR_TABLE_SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public int add(CardStatus entity) {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            session.flush();
            transaction.commit();

            return entity.getId().intValue();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public int deleteById(Long id) {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CardStatus entity = (CardStatus) session.get(CardStatus.class, id);
            if (entity == null) {
                throw new DaoException("Account with id " + id + " not found");
            }
            session.delete(entity);
            session.flush();
            transaction.commit();

            return entity.getId().intValue();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public List<CardStatus> getAll() {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<CardStatus> cardStatuses = session.createQuery("from CardStatus").list();
            transaction.commit();
            return cardStatuses;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<CardStatus> getById(Long id) {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CardStatus entity = (CardStatus) session.get(CardStatus.class, id);
            transaction.commit();
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public void update(CardStatus entity) {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }
}
