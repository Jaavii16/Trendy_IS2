package presentacion;


import negocio.SAFacade;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GUIPpalCategorias extends MainGUIPanel implements ActionListener {
    //Lo que es mostrar la lista de categorías

    private List<JButton> categorias;
    private List<String> lcat;
    private SAFacade sa;

    GUIPpalCategorias(SAFacade sa) {
        super();
        this.sa = sa;
        lcat = this.sa.getCategorias();
        initGUI();
    }

    private void initGUI() {

        JPanel categorias = new JPanel();

        categorias.setLayout(new BoxLayout(categorias, BoxLayout.Y_AXIS));
        categorias.setAlignmentX(JComponent.LEFT_ALIGNMENT);

        for (String s : this.lcat) {
            JButton cat = new JButton(s);
            cat.addActionListener(this);
            categorias.add(cat);
            categorias.add(cat);
        }

        this.setViewportView(categorias);
    }

    public void actionPerformed(ActionEvent e) {
        int i = 0;
        while (i < this.categorias.size() && e.getSource() != this.categorias.get(i)) i++;
        if (i < this.categorias.size()) {
            GUICategoria guicat = new GUICategoria(sa, this.categorias.get(i).getName(), this);
            this.setVisible(false);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void reset() {

    }
}
