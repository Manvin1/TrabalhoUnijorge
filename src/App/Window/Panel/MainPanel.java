package App.Window.Panel;

import javax.swing.*;

import App.Core.Authenticate;
import App.Core.Pessoa;
import App.Core.Projeto;
import App.Managers.DataBaseManager;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class MainPanel extends TransitionablePanel {

    public MainPanel(Dimension dimension, TransitionPanelObserver observer, Pessoa pessoa) {
        bounds = dimension;
        this.pessoa = pessoa;
        this.setPreferredSize(dimension);

        init();
        addObserver(observer);
    }

    private void init() {
        SpringLayout layout_manager = new SpringLayout();

        this.setLayout(layout_manager);
        setConstraints(layout_manager);
        setObservers();

        this.add(all_button);
        this.add(cadastrar_button);
        this.add(return_button);
    }

    private void setConstraints(SpringLayout layout_manager) {
        // login
        layout_manager.putConstraint(SpringLayout.WEST, all_button, (int) (bounds.getWidth() / 2.0 - 150),
                SpringLayout.WEST, this);
        layout_manager.putConstraint(SpringLayout.NORTH, all_button, (int) (bounds.getHeight() * 0.2),
                SpringLayout.NORTH, this); // login_label

        // password
        layout_manager.putConstraint(SpringLayout.WEST, cadastrar_button, 0,
                SpringLayout.WEST, all_button);
        layout_manager.putConstraint(SpringLayout.NORTH, cadastrar_button, 20,
                SpringLayout.SOUTH, all_button); // pwd_label

        layout_manager.putConstraint(SpringLayout.WEST, return_button, 0,
                SpringLayout.WEST, all_button);
        layout_manager.putConstraint(SpringLayout.NORTH, return_button, 20,
                SpringLayout.SOUTH, cadastrar_button); // pwd_field

    }

    void setObservers() {
        return_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        notifyEvent(new TransitionEvent(new LoginPanel(bounds, observer)));
                    }
                });

        cadastrar_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        notifyEvent(new TransitionEvent(new ProjCadastroPanel(bounds, observer, pessoa)));
                    }
                });

        all_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        notifyEvent(new TransitionEvent(new ProjetoListPanel(bounds,observer, pessoa)));
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
    private JButton all_button = new JButton("Ver Todos os Projetos");
    private JButton cadastrar_button = new JButton("Cadastrar um Projeto");
    private JButton return_button = new JButton("Sair");

}
