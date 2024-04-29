package launcher;

import presentacion.GUIWindow;

public class Main {
    public static void main(String[] args) {
        new GUIWindow(BusinessFacade.initBusiness(new SAFactoryTrendy(), new DAOFactoryMySQL()));
    }
}
