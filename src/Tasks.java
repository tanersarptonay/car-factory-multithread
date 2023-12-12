public class Tasks {
    public static class AssembleCarTask implements Runnable{
        @Override
        public void run() {
            while (true) {
                GeneralStorage.emptyStorage();
            }
        }
    }

    public static class ProduceEngineTask implements Runnable{
        @Override
        public void run() {
            while (true) {
                GeneralStorage.addEngine();
            }
        }
    }

    public static class ProduceGlassTask implements Runnable{
        @Override
        public void run() {
            while (true) {
                GeneralStorage.addGlass();
            }
        }
    }

    public static class ProduceWheelTask implements Runnable{
        @Override
        public void run() {
            while (true) {
                GeneralStorage.addWheel();
            }
        }
    }
}
