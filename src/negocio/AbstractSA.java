package negocio;

public abstract class AbstractSA {

    protected final BusinessDelegate businessDelegate;

    public AbstractSA(BusinessDelegate businessDelegate) {
        this.businessDelegate = businessDelegate;
    }
}
