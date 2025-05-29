package dao.hibernate;

import configuration.HibernateUtil;
import dao.Dao;
import exception.DaoException;
import model.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CardHibernateDaoImpl implements Dao<Card> {
    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS card(
        id SERIAL PRIMARY KEY,
        card_number VARCHAR(50),
        expiration_date DATE,
        holder_name VARCHAR(50),
        card_status_id BIGINT,
        payment_system_id BIGINT,
        account_id BIGINT,
        received_from_issuing_bank TIMESTAMP,
        sent_to_issuing_bank TIMESTAMP,
        FOREIGN KEY (card_status_id) REFERENCES card_status(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (payment_system_id) REFERENCES payment_system(id) ON DELETE RESTRICT ON UPDATE CASCADE,
        FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE RESTRICT ON UPDATE CASCADE
    );
    """;

    private static final String DROP_TABLE_SQL = """
            DROP TABLE IF EXISTS card;
            """;

    private static final String CLEAR_TABLE_SQL = """
            TRUNCATE card;
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
    public int add(Card entity) {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            session.flush();
            transaction.commit();

            return entity.getCardId().intValue();
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
            Card entity = (Card) session.get(Card.class, id);
            if (entity == null) {
                throw new DaoException("Card with id " + id + " not found");
            }
            session.delete(entity);
            session.flush();
            transaction.commit();

            return entity.getCardId().intValue();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public List<Card> getAll() {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<Card> cards = session.createQuery("from Card").list();
            transaction.commit();
            return cards;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Card> getById(Long id) {
        Transaction transaction = null;
        try(SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Card entity = (Card) session.get(Card.class, id);
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
    public void update(Card entity) {
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
