package presentacion;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;
import negocio.SAFacade;

public class GUIPpalCategorias extends JPanel, MainGUIPanel  implements ActionListener {
    //Lo que es mostrar la lista de categorías

    private List<JButton> categorias;
    private List<String> lcat;
    private SAFacade sa;

    GUIPpalCategorias(SAFacade sa){
        this.sa = sa;
        lcat = this.sa.getCategorias();
        initGUI();
    }

    private void initGUI(){
        this.setLayout(new BoxLayout( this, BoxLayout.Y_AXIS) );
        this.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        for(int i = 0; i < this.lcat.size(); i++){
            JButton cat = new JButton(lcat.get(i));
            cat.addActionListener(this);
            categorias.add(cat);
            this.add(cat);
        }
    }

    public void actionPerformed(ActionEvent e){
        int i = 0;
        while( i < this.categorias.size() && e.getSource() != this.categorias.get(i)) i++;
        if(i < this.categorias.size()){
            GUICategoria guicat = new GUICategoria(sa, this.categorias.get(i).getName(), this);
            this.setVisible(false);
        }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }
}
