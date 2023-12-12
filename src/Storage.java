import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    public int CAPACITY;
    public int partsNumber;
    public Lock lock;

    public Condition notFull;

    public Condition full;
    public ActionFunction startLineAction;
    public ActionFunction stopLineAction;
    public ActionFunction produceAction;

    Storage() { }

    Storage(int capacity, ActionFunction startLineAction, ActionFunction stopLineAction, ActionFunction produceAction) {
        CAPACITY = capacity;
        partsNumber = 0;
        this.startLineAction = startLineAction;
        this.stopLineAction = stopLineAction;
        this.produceAction = produceAction;
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        full = lock.newCondition();
    }

    public void addPart() {
        lock.lock();
        try {
            while (partsNumber == CAPACITY) {
                notFull.await();
            }
            if (partsNumber == 0) {
                startLineAction.apply();
            }

            produceAction.apply();
            partsNumber += 1;

            if (partsNumber == CAPACITY) {
                full.signal();
                stopLineAction.apply();
            }
            else {
                notFull.signal();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();
        }
    }
}
