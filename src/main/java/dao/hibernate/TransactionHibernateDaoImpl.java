package dao.hibernate;

import configuration.HibernateUtil;
import dao.Dao;
import exception.DaoException;
import model.Transaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class TransactionHibernateDaoImpl implements Dao<Transaction> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS transaction(
        id SERIAL PRIMARY KEY,
        transaction_date DATE,
        sum DECIMAL,
        transaction_name VARCHAR(255),
        account_id BIGINT,
        transaction_type_id BIGINT,
        card_id BIGINT,
        terminal_id BIGINT,
        response_code_id BIGINT,
        authorization_code VARCHAR(6),
        received_from_issuing_bank TIMESTAMP,
        sent_to_issuing_bank TIMESTAMP,
        FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (transaction_type_id) REFERENCES transaction_type(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (card_id) REFERENCES card(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (terminal_id) REFERENCES terminal(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (response_code_id) REFERENCES response_code(id) ON DELETE RESTRICT ON UPDATE CASCADE
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS transaction;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE transaction;
            """;


    @Override
    public void createTable() {
        org.hibernate.Transaction transaction = null;
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
        org.hibernate.Transaction transaction = null;
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
        org.hibernate.Transaction transaction = null;
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
    public int add(Transaction entity) {
        org.hibernate.Transaction transaction = null;
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
        org.hibernate.Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Transaction entity = (Transaction) session.get(Transaction.class, id);
            if (entity == null) {
                throw new DaoException("Transaction with id " + id + " not found");
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
    public List<Transaction> getAll() {
        org.hibernate.Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<Transaction> Transactions = session.createQuery("from model.Transaction").list();
            transaction.commit();
            return Transactions;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Transaction> getById(Long id) {
        org.hibernate.Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Transaction entity = (Transaction) session.get(Transaction.class, id);
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
    public void update(Transaction entity) {
        org.hibernate.Transaction transaction = null;
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

