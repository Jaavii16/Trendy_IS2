package negocio;

import launcher.SAFactory;

public class SAFacade {

    private AbstractSA testSA;
    private AbstractSA test2SA;

    public SAFacade(BusinessDelegate businessDelegate, SAFactory saFactory) {
        testSA = saFactory.getTestSA(businessDelegate);
    }

    public void registerObserver(Observer observer) {
        //TODO Preguntar si registrar un observer hay que hacerlo a traves de businessDelegate o de los sa
    }

}