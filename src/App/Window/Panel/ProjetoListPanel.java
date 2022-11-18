package App.Window.Panel;

import javax.swing.*;

import App.Core.Authenticate;
import App.Core.Pessoa;
import App.Core.Projeto;
import App.Managers.DataBaseManager;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class ProjetoListPanel extends TransitionablePanel {

    public ProjetoListPanel(Dimension dimension, TransitionPanelObserver observer, Pessoa pessoa) {
        bounds = dimension;
        this.pessoa = pessoa;
        this.setPreferredSize(dimension);

        init();
        addObserver(observer);
    }

    private void init() {
        BoxLayout layout_manager = new BoxLayout(this,BoxLayout.PAGE_AXIS);
        
        ArrayList<Projeto>  projetosShortList = DataBaseManager.get().getAllProjectsShort();
        String attributes[] = new String[]{"Id", "Título", "Autores", "Resumo", "Categoria"};
        ArrayList<String[]> lineList = new ArrayList<String[]>();
        
        for (Projeto projeto : projetosShortList)
        {
            lineList.add(new String[]{String.valueOf(projeto.id), projeto.titulo, projeto.nomeList.toString(), projeto.resumo, projeto.categoriaList.toString()});
        }

        table = new JTable(lineList.toArray(new String[0][0]), attributes);
        table.setShowGrid(false);

        scroll = new JScrollPane(table);

        table.setFillsViewportHeight(true);
        
        this.setLayout(layout_manager);

        setObservers();

        this.add(scroll, BorderLayout.CENTER);
        this.add(ver_button, BorderLayout.LINE_END);
        this.add(return_button, BorderLayout.LINE_END);
    }

    void setObservers() {
        return_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        notifyEvent(new TransitionEvent(new MainPanel(bounds, observer, pessoa)));
                    }
                });

        ver_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Integer id = Integer.parseInt((String)table.getValueAt(table.getSelectedRow(), 0));
                        notifyEvent(new TransitionEvent(new ProjetoViewPanel(bounds, observer, pessoa, id)));
                    }
                });
    }

    @Override
    public void onExit() {
        System.out.println("LoginPanel: Exiting");
    }

    @Override
    public void onEnter() {
        System.out.println("LoginPanel: Entring");
    }

    // Id
    private static final long serialVersionUID = 1L;

    // Runtime Data
    private Dimension bounds;
    private Pessoa pessoa;

    // Buttons
    private JButton return_button = new JButton("Retornar");
    private JButton ver_button = new JButton("Ver");

    // List
    private JTable table;
    private JScrollPane scroll;
}
