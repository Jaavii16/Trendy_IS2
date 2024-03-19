import controller.Controller;

public class Main {
    public static void main(String[] args) {
        Productos view = new Productos();
        PantalonCargo model= new PantalonCargo();
        Controller controller = new Controller(view, model);
        controller.mostrarArticulo();
    }
}
