package hibernate.auto;

import hibernate.Role;
import hibernate.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            AutoModel model1 = AutoModel.of("Passat");
            AutoModel model2 = AutoModel.of("Touareg");
            AutoModel model3 = AutoModel.of("Polo");
            AutoModel model4 = AutoModel.of("Tiguan");
            AutoModel model5 = AutoModel.of("Golf");

            session.save(model1);
            session.save(model2);
            session.save(model3);
            session.save(model4);
            session.save(model5);

            AutoBrand autoBrand = AutoBrand.of("Volkswagen");
            autoBrand.addAutoModel(model1);
            autoBrand.addAutoModel(model2);
            autoBrand.addAutoModel(model3);
            autoBrand.addAutoModel(model4);
            autoBrand.addAutoModel(model5);

            session.save(autoBrand);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

