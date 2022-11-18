package App.Window.Panel;

import javax.swing.*;

import App.Core.Authenticate;
import App.Core.Pessoa;
import App.Managers.DataBaseManager;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginPanel extends TransitionablePanel {

    public LoginPanel(Dimension dimension, TransitionPanelObserver observer) {
        bounds = dimension;
        this.setPreferredSize(dimension);

        init();
        addObserver(observer);
    }

    private void init() {
        SpringLayout layout_manager = new SpringLayout();

        this.setLayout(layout_manager);
        setConstraints(layout_manager);
        setObservers();

        this.add(login_label);
        this.add(login_field);
        this.add(pwd_label);
        this.add(pwd_field);
        this.add(login_button);
        this.add(registrar_button);


    }

    private void setConstraints(SpringLayout layout_manager) {
        //login
        layout_manager.putConstraint(SpringLayout.WEST, login_label, (int)(bounds.getWidth() / 2.0 - 150),
                SpringLayout.WEST, this);
        layout_manager.putConstraint(SpringLayout.NORTH, login_label, (int) (bounds.getHeight() * 0.2),
                SpringLayout.NORTH, this); // login_label

        layout_manager.putConstraint(SpringLayout.WEST, login_field, 0,
                SpringLayout.WEST, login_label);
        layout_manager.putConstraint(SpringLayout.NORTH, login_field, 5,
                SpringLayout.SOUTH, login_label); // login_field

        // password
        layout_manager.putConstraint(SpringLayout.WEST, pwd_label, 0,
                SpringLayout.WEST, login_label);
        layout_manager.putConstraint(SpringLayout.NORTH, pwd_label, 10,
                SpringLayout.SOUTH, login_field); // pwd_label

                layout_manager.putConstraint(SpringLayout.WEST, pwd_field, 0,
                SpringLayout.WEST, login_label);
        layout_manager.putConstraint(SpringLayout.NORTH, pwd_field, 5,
                SpringLayout.SOUTH, pwd_label); // pwd_field

        // button
        layout_manager.putConstraint(SpringLayout.NORTH, login_button, 10,
                SpringLayout.SOUTH, pwd_field); // login_button
        layout_manager.putConstraint(SpringLayout.EAST, login_button, 0,
                SpringLayout.EAST, pwd_field); // login_button

        layout_manager.putConstraint(SpringLayout.NORTH, registrar_button, 10,
                SpringLayout.SOUTH, pwd_field); // regitrar_button
        layout_manager.putConstraint(SpringLayout.WEST, registrar_button, 0,
                SpringLayout.WEST, pwd_field); // regitrar_button
    }

    void setObservers() {

        registrar_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    System.out.println("LoginPanel: Registrar Button Clicked");
                    notifyEvent(new TransitionEvent(new RegisterPanel(bounds, observer)));
                    }
                });

        login_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String login = login_field.getText();
                        String pwd = new String(pwd_field.getPassword());
                        
                        Optional<Pessoa>  opt = DataBaseManager.get().checkUser(new Authenticate(login, pwd));
                        if (opt.isPresent())
                        {
                                System.out.println("LoginPanel: Usuário encontrado");
                                notifyEvent(new TransitionEvent(new MainPanel(bounds, observer, opt.get())));
                        }
                        else 
                        {
                                System.out.println("LoginPanel: Usuário não encontrado");

                        }
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

    // Labels
    private JLabel login_label = new JLabel("Login");
    private JLabel pwd_label = new JLabel("Senha");

    // Fields
    private JTextField login_field = new JTextField("", 30);
    private JPasswordField pwd_field = new JPasswordField("", 30);

    // Buttons
    private JButton login_button = new JButton("Entrar");
    private JButton registrar_button = new JButton("Registrar-se");

}
