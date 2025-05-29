package dao.hibernate;

import configuration.HibernateUtil;
import dao.Dao;
import exception.DaoException;
import model.TransactionType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionTypeHibernateDaoImpl implements Dao<TransactionType> {
    private static final String CREATE_TABLE_SQL = """
            CREATE TABLE IF NOT EXISTS transaction_type(
              id SERIAL PRIMARY KEY,
              transaction_type_name VARCHAR(255),
              operator VARCHAR(1)
          );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS transaction_type;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE transaction_type;
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
    public int add(TransactionType entity) {
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
            TransactionType entity = (TransactionType) session.get(TransactionType.class, id);
            if (entity == null) {
                throw new DaoException("TransactionType with id " + id + " not found");
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
    public List<TransactionType> getAll() {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<TransactionType> TransactionTypes = session.createQuery("from model.TransactionType").list();
            transaction.commit();
            return TransactionTypes;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<TransactionType> getById(Long id) {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            TransactionType entity = (TransactionType) session.get(TransactionType.class, id);
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
    public void update(TransactionType entity) {
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

