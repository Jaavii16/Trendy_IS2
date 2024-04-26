package launcher;

import negocio.AbstractSA;
import negocio.BusinessDelegate;

public interface SAFactory {
    void setBusinessDelegate(BusinessDelegate businessDelegate);

    AbstractSA getTestSA();
    //añadir get<nombre>SA() para cada SA
}
