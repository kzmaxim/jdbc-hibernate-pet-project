package dao.hibernate;

import configuration.HibernateUtil;
import dao.Dao;
import exception.DaoException;
import model.PaymentSystem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;


public class PaymentSystemHibernateDaoImpl implements Dao<PaymentSystem> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS payment_system(id SERIAL PRIMARY KEY, payment_system_name VARCHAR(50));
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS payment_system CASCADE;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE payment_system;
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
    public int add(PaymentSystem entity) {
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
            PaymentSystem entity = (PaymentSystem) session.get(PaymentSystem.class, id);
            if (entity == null) {
                throw new DaoException("PaymentSystem with id " + id + " not found");
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
    public List<PaymentSystem> getAll() {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<PaymentSystem> PaymentSystems = session.createQuery("from PaymentSystem").list();
            transaction.commit();
            return PaymentSystems;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<PaymentSystem> getById(Long id) {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            PaymentSystem entity = (PaymentSystem) session.get(PaymentSystem.class, id);
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
    public void update(PaymentSystem entity) {
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
