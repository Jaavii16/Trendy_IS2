package launcher;

import negocio.BusinessDelegate;

public class SAFactoryImp implements SAFactory {

    private BusinessDelegate businessDelegate;

    @Override
    public void setBusinessDelegate(BusinessDelegate businessDelegate) {
        this.businessDelegate = businessDelegate;
    }
}
