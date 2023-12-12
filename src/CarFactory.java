import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class CarFactory {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new Tasks.ProduceEngineTask());
        executor.execute(new Tasks.ProduceWheelTask());
        executor.execute(new Tasks.ProduceGlassTask());
        executor.execute(new Tasks.AssembleCarTask());
        executor.shutdown();
    }
}