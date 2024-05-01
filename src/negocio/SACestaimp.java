package negocio;


public class SACestaimp extends AbstractSA implements SACesta {


    public SACestaimp(BusinessDelegate businessDelegate) {
        super(businessDelegate);
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

    @Override
    public void addArticuloAFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        businessDelegate.addArticuloAFavoritos(toArticuloEnFavoritos);
    }

    @Override
    public void removeArticuloDeFavoritos(TOArticuloEnFavoritos toArticuloEnFavoritos) {
        businessDelegate.removeArticuloDeFavoritos(toArticuloEnFavoritos);
    }
}
