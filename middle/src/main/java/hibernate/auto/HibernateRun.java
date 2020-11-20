package hibernate.auto;

import hibernate.Role;
import hibernate.User;
import hibernate.auto.lazyInitializationexception.Category;
import hibernate.auto.lazyInitializationexception.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HibernateRun {

    public static void main(String[] args) {
        List<AutoBrand> autoBrands = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

//            AutoBrand autoBrand = AutoBrand.of("Volkswagen");
//            AutoModel model1 = AutoModel.of("Passat");
//            AutoModel model2 = AutoModel.of("Touareg");
//            AutoModel model3 = AutoModel.of("Polo");
//            AutoModel model4 = AutoModel.of("Tiguan");
//            AutoModel model5 = AutoModel.of("Golf");
//
//            session.save(autoBrand);
//
//            model1.setAutoBrand(autoBrand);
//            model2.setAutoBrand(autoBrand);
//            model3.setAutoBrand(autoBrand);
//            model4.setAutoBrand(autoBrand);
//            model5.setAutoBrand(autoBrand);
//
//            session.save(model1);
//            session.save(model2);
//            session.save(model3);
//            session.save(model4);
//            session.save(model5);


            autoBrands = session.createQuery("select distinct a from AutoBrand a join fetch a.models")
                    .list();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (AutoBrand brand : autoBrands) {
            for (AutoModel model : brand.getModels()) {
                System.out.println(model);
            }
        }
    }
}

