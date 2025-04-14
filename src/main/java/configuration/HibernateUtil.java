package configuration;

import lombok.experimental.UtilityClass;
import model.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.configure("hibernate.cfg.xml")
                .addAnnotatedClass(CardStatus.class)
                .addAnnotatedClass(PaymentSystem.class)
                .addAnnotatedClass(Currency.class)
                .addAnnotatedClass(IssuingBank.class)
                .addAnnotatedClass(AcquiringBank.class)
                .addAnnotatedClass(SalesPoint.class)
                .addAnnotatedClass(MerchantCategoryCode.class)
                .addAnnotatedClass(Terminal.class)
                .addAnnotatedClass(ResponseCode.class)
                .addAnnotatedClass(TransactionType.class)
                .addAnnotatedClass(Account.class)
                .addAnnotatedClass(Card.class)
                .addAnnotatedClass(Transaction.class);
        return configuration.buildSessionFactory();
    }
}