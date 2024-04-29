package launcher;

import negocio.AbstractSA;
import negocio.BusinessDelegate;

public class SAFactoryTrendy implements SAFactory {

    @Override
    public AbstractSA getTestSA(BusinessDelegate businessDelegate) {
        return new AbstractSA(businessDelegate) {
        };
    }
}
