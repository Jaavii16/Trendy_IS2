package launcher;

import negocio.BusinessDelegate;
import negocio.SAFacade;

public class BusinessFacade {
    public static <ImpSA extends SAFactory, ImpDAO extends DAOFactory> SAFacade initBusiness(ImpSA saFactory, ImpDAO daoFactory) {
        BusinessDelegate businessDelegate = new BusinessDelegate(daoFactory);
        saFactory.setBusinessDelegate(businessDelegate);
        return new SAFacade(saFactory);
    }
}

