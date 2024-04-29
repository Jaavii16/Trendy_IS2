package negocio;


public class SACestaimp implements SACesta {

    private final BusinessDelegate businessDelegate;

    public SACestaimp(BusinessDelegate businessDelegate) {
        this.businessDelegate = businessDelegate;
    }

    @Override
    public void addArticuloACesta(TOArticuloEnCesta toArticuloEnCesta) {
        businessDelegate.addArticuloACesta(toArticuloEnCesta);
    }

    @Override
    public void actualizarArticuloEnCesta(TOArticuloEnCesta toArticuloEnCesta) {
        businessDelegate.actualizarArticuloEnCesta(toArticuloEnCesta);
    }

    @Override
    public void removeArticuloDeCesta(TOArticuloEnCesta toArticuloEnCesta) {
        businessDelegate.removeArticuloDeCesta(toArticuloEnCesta);
    }
}
