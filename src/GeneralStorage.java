public class GeneralStorage {
    private static final int ENGINE_CAPACITY = 1;
    private static final int WHEEL_CAPACITY = 4;
    private static final int GLASS_CAPACITY = 6;

    public static Storage engineStorage = new Storage(
            ENGINE_CAPACITY,
            Actions::startEngineProductionLine,
            Actions::stopEngineProductionLine,
            Actions::produceEngine
    );
    public static Storage wheelStorage = new Storage(
            WHEEL_CAPACITY,
            Actions::startWheelProductionLine,
            Actions::stopWheelProductionLine,
            Actions::produceWheel
    );
    public static Storage glassStorage = new Storage(
            GLASS_CAPACITY,
            Actions::startGlassProductionLine,
            Actions::stopGlassProductionLine,
            Actions::produceGlass
    );

    public static void addEngine() {
        engineStorage.addPart();
    }

    public static void addWheel() {
        wheelStorage.addPart();
    }

    public static void addGlass() {
        glassStorage.addPart();
    }

    public static void emptyStorage() {
        engineStorage.lock.lock();
        wheelStorage.lock.lock();
        glassStorage.lock.lock();
        try {
            while (engineStorage.partsNumber != engineStorage.CAPACITY) {
                engineStorage.full.await();
            }

            while (wheelStorage.partsNumber != wheelStorage.CAPACITY) {
                wheelStorage.full.await();
            }

            while (glassStorage.partsNumber != glassStorage.CAPACITY) {
                glassStorage.full.await();
            }

            Actions.startAssemblyLine();

            engineStorage.partsNumber = 0;
            wheelStorage.partsNumber = 0;
            glassStorage.partsNumber = 0;

            Actions.assemble();

            engineStorage.notFull.signal();
            wheelStorage.notFull.signal();
            glassStorage.notFull.signal();

            Actions.stopAssemblyLine();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            engineStorage.lock.unlock();
            wheelStorage.lock.unlock();
            glassStorage.lock.unlock();
        }

    }
}
