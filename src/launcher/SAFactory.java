package launcher;

import negocio.AbstractSA;
import negocio.BusinessDelegate;

public interface SAFactory {


    AbstractSA getTestSA(BusinessDelegate businessDelegate);

    //añadir get<nombre>SA() para cada SA
}
