package email;

import java.util.concurrent.ExecutorService;

public class EmailNotification {
    private final ExecutorService pool;
    private String subject;
    private String body;
    private String email;

    public EmailNotification(ExecutorService pool) {
        this.pool = pool;
    }

    public void emailTo(User user) {
        this.subject = "Notification ".concat(user.getUsername())
                .concat("to email ").concat(user.getEmail());
        this.body = "Add a new event to ".concat(user.getUsername());
        this.email = user.getEmail();
        pool.submit(() -> send(subject, body, email));
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
