package work.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import work.service.DistributedLockService;

@Component
public class LockManager {

    @Autowired
    private DistributedLockService distributedLockService;

    private static final String LOCK_KEY = "A_LOCK";

    private static int LOCK_VALUE = 100;

    public void lockTest() {
        System.out.println("start lock");


        String requestId = "req" + System.currentTimeMillis();

        if (!distributedLockService.tryLock(LOCK_KEY, requestId, 30)) {
            System.out.println("加锁操作失败");
            return;
        }

        try {
            Thread.sleep(50000);
            LOCK_VALUE -= LOCK_VALUE;
            System.out.println("VLAUE -1 = " + LOCK_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        distributedLockService.release(LOCK_KEY, requestId);
        System.out.println("end lock");
    }

}
