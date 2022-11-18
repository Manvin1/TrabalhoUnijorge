package App.Window.Panel;

import javax.swing.*;

import App.Core.Pessoa;
import App.Core.PessoaDTO;
import App.Core.Projeto;
import App.Managers.DataBaseManager;

import java.awt.event.*;
import java.util.Optional;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends TransitionablePanel {

    public RegisterPanel(Dimension dimension, TransitionPanelObserver observer) {
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
        this.add(email_label);
        this.add(email_field);
        this.add(tel_label);
        this.add(tel_field);
        this.add(return_button);
        this.add(criar_button);
    }

    private void setConstraints(SpringLayout layout_manager) {
        // login
        layout_manager.putConstraint(SpringLayout.WEST, login_label, (int) (bounds.getWidth() / 2.0 - 150),
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

        // email
        layout_manager.putConstraint(SpringLayout.WEST, email_label, 0,
                SpringLayout.WEST, login_label);
        layout_manager.putConstraint(SpringLayout.NORTH, email_label, 10,
                SpringLayout.SOUTH, pwd_field); // email_label

        layout_manager.putConstraint(SpringLayout.WEST, email_field, 0,
                SpringLayout.WEST, login_label);
        layout_manager.putConstraint(SpringLayout.NORTH, email_field, 5,
                SpringLayout.SOUTH, email_label); // email_field

        // telefone
        layout_manager.putConstraint(SpringLayout.WEST, tel_label, 0,
                SpringLayout.WEST, login_label);
        layout_manager.putConstraint(SpringLayout.NORTH, tel_label, 10,
                SpringLayout.SOUTH, email_field); // tel_label

        layout_manager.putConstraint(SpringLayout.WEST, tel_field, 0,
                SpringLayout.WEST, login_label);
        layout_manager.putConstraint(SpringLayout.NORTH, tel_field, 5,
                SpringLayout.SOUTH, tel_label); // teL_field

        // button
        layout_manager.putConstraint(SpringLayout.EAST, criar_button, 0,
                SpringLayout.EAST, login_field); // criar_button
        layout_manager.putConstraint(SpringLayout.NORTH, criar_button, 10,
                SpringLayout.SOUTH, tel_field);

        layout_manager.putConstraint(SpringLayout.WEST, return_button, 0,
                SpringLayout.WEST, login_label); // return_button
        layout_manager.putConstraint(SpringLayout.NORTH, return_button, 10,
                SpringLayout.SOUTH, tel_field);

    }

    void setObservers() {

        criar_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String login = login_field.getText();
                        String pwd = new String(pwd_field.getPassword());
                        String email = email_field.getText();
                        String tel = tel_field.getText();

                        PessoaDTO pessoa = new PessoaDTO(login, pwd, email, tel);

                        Optional<Pessoa> opt = DataBaseManager.get().registerUser(pessoa);
                        if (opt.isPresent()) {
                            System.out.println("RegisterPanel: Usuário registrado com sucesso");
                            notifyEvent(new TransitionEvent(new LoginPanel(bounds, observer)));
                        }
                        else
                        {
                            System.out.println("RegisterPanel: Não foi possível registrar o usuário");
                        }
                    }
                });

        return_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        notifyEvent(new TransitionEvent(new LoginPanel(bounds, observer)));
                    }
                });
    }

    @Override
    public void onExit() {
        System.out.println("RegisterPanel: Exiting");
    }

    @Override
    public void onEnter() {
        System.out.println("RegisterPanel: Entring");
    }

    // Id
    private static final long serialVersionUID = 1L;

    // Runtime Data
    private Dimension bounds;

    // Labels
    private JLabel login_label = new JLabel("Login");
    private JLabel pwd_label = new JLabel("Senha");
    private JLabel email_label = new JLabel("Email");
    private JLabel tel_label = new JLabel("Telefone");

    // Fields
    private JTextField login_field = new JTextField("", 30);
    private JPasswordField pwd_field = new JPasswordField("", 30);
    private JTextField email_field = new JTextField("", 30);
    private JTextField tel_field = new JTextField("", 30);

    // Buttons
    private JButton return_button = new JButton("Retornar");
    private JButton criar_button = new JButton("Criar Conta");

}
