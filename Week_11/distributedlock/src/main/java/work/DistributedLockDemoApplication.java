package work;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import work.biz.LockManager;

@SpringBootApplication
public class DistributedLockDemoApplication implements CommandLineRunner {

    @Autowired
    private LockManager lockManager;

    public static void main(String[] args) {
        SpringApplication.run(DistributedLockDemoApplication.class, args);
    }



    public void run(String... args) throws Exception {
        Thread thread1 = new Thread(() -> {
            lockManager.lockTest();
        });

        Thread thread2 = new Thread(() -> {
            lockManager.lockTest();
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();


    }
}
