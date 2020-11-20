package hibernate.auto.manyToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

//            Book book1 = Book.of("Kniga 1");
//            Book book2 = Book.of("Kniga 2");
//            session.save(book1);
//            session.save(book2);
//
//            Author author1 = Author.of("Pushkin");
//            author1.addBook(book1);
//            Author author2 = Author.of("Evgen");
//            author2.addBook(book1);
//            author2.addBook(book2);
//
//            session.persist(author1);
//            session.persist(author2);
            Author author = session.get(Author.class, 4);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
