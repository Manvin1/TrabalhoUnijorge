package App.Window.Panel;

import javax.swing.*;

import App.Core.Categoria;
import App.Core.Pessoa;
import App.Core.PessoaDTO;
import App.Core.Projeto;
import App.Core.ProjetoDTO;
import App.Managers.DataBaseManager;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.*;
import java.awt.*;

public class ProjCadastroPanel extends TransitionablePanel {

    public ProjCadastroPanel(Dimension dimension, TransitionPanelObserver observer, Pessoa pessoa) {
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

        ArrayList<Categoria>  catList = DataBaseManager.get().getCategorias();
        for (Categoria cat : catList) {
            cat_cbox.addItem(cat);
        }

        this.add(titulo_label);
        this.add(titulo_field);
        this.add(autor_label);
        this.add(autor_field);
        this.add(resumo_label);
        this.add(resumo_area);
        this.add(cidade_label);
        this.add(cidade_field);
        this.add(estado_label);
        this.add(estado_field);
        this.add(dption_label);
        this.add(dpction_area);
        this.add(cat_label);
        this.add(cat_cbox);
        this.add(criar_button);
        this.add(return_button);
        this.add(add_button);
    }

    private void setConstraints(SpringLayout layout_manager) {
        // titulo
        layout_manager.putConstraint(SpringLayout.WEST, titulo_label, (int) (bounds.getWidth() / 2.0 - 150),
                SpringLayout.WEST, this);
        layout_manager.putConstraint(SpringLayout.NORTH, titulo_label, (int) (bounds.getHeight() * 0.2),
                SpringLayout.NORTH, this);
        titulo_label.setLabelFor(titulo_field);

        layout_manager.putConstraint(SpringLayout.WEST, titulo_field, 30,
                SpringLayout.EAST, titulo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, titulo_field, 0,
                SpringLayout.NORTH, titulo_label);

        // autor
        layout_manager.putConstraint(SpringLayout.WEST, autor_label, 0,
                SpringLayout.WEST, titulo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, autor_label, 10,
                SpringLayout.SOUTH, titulo_field);
        autor_label.setLabelFor(autor_field);

        layout_manager.putConstraint(SpringLayout.WEST, autor_field, 30,
                SpringLayout.EAST, titulo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, autor_field, 10,
                SpringLayout.SOUTH, titulo_field);

        // resumo
        layout_manager.putConstraint(SpringLayout.WEST, resumo_label, 0,
                SpringLayout.WEST, autor_label);
        layout_manager.putConstraint(SpringLayout.NORTH, resumo_label, 10,
                SpringLayout.SOUTH, autor_field);
        resumo_label.setLabelFor(resumo_area);

        layout_manager.putConstraint(SpringLayout.WEST, resumo_area, 30,
                SpringLayout.EAST, titulo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, resumo_area, 10,
                SpringLayout.SOUTH, autor_field);
        resumo_area.setPreferredSize(resumo_area.getSize());

        // cidade
        layout_manager.putConstraint(SpringLayout.WEST, cidade_label, 0,
                SpringLayout.WEST, resumo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, cidade_label, 5,
                SpringLayout.SOUTH, resumo_area);
        cidade_label.setLabelFor(cidade_field);

        layout_manager.putConstraint(SpringLayout.WEST, cidade_field, 30,
                SpringLayout.EAST, titulo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, cidade_field, 10,
                SpringLayout.SOUTH, resumo_area);

        // estado
        layout_manager.putConstraint(SpringLayout.WEST, estado_label, 0,
                SpringLayout.WEST, cidade_label);
        layout_manager.putConstraint(SpringLayout.NORTH, estado_label, 10,
                SpringLayout.SOUTH, cidade_field);
        estado_label.setLabelFor(estado_field);

        layout_manager.putConstraint(SpringLayout.WEST, estado_field, 30,
                SpringLayout.EAST, titulo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, estado_field, 10,
                SpringLayout.SOUTH, cidade_field);

        // description
        layout_manager.putConstraint(SpringLayout.WEST, dption_label, 0,
                SpringLayout.WEST, estado_label);
        layout_manager.putConstraint(SpringLayout.NORTH, dption_label, 5,
                SpringLayout.SOUTH, estado_field);
        dption_label.setLabelFor(dpction_area);

        layout_manager.putConstraint(SpringLayout.WEST, dpction_area, 30,
                SpringLayout.EAST, titulo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, dpction_area, 10,
                SpringLayout.SOUTH, estado_field);
        dpction_area.setPreferredSize(dpction_area.getSize());

        // categories
        layout_manager.putConstraint(SpringLayout.WEST, cat_label, 0,
                SpringLayout.WEST, dption_label);
        layout_manager.putConstraint(SpringLayout.NORTH, cat_label, 5,
                SpringLayout.SOUTH, dpction_area);
        cat_label.setLabelFor(cat_cbox);

        layout_manager.putConstraint(SpringLayout.WEST, cat_cbox, 30,
                SpringLayout.EAST, titulo_label);
        layout_manager.putConstraint(SpringLayout.NORTH, cat_cbox, 10,
                SpringLayout.SOUTH, dpction_area);
        // cat_cbox.setPreferredSize(cat_cbox.getSize());

        // button
        layout_manager.putConstraint(SpringLayout.WEST, return_button, 0,
                SpringLayout.WEST, cat_label); // return_button
        layout_manager.putConstraint(SpringLayout.NORTH, return_button, 10,
                SpringLayout.SOUTH, cat_cbox);

        layout_manager.putConstraint(SpringLayout.EAST, criar_button, 0,
                SpringLayout.EAST, dpction_area); // criar_button
        layout_manager.putConstraint(SpringLayout.NORTH, criar_button, 10,
                SpringLayout.SOUTH, cat_cbox);

        layout_manager.putConstraint(SpringLayout.WEST, add_button, 5,
                SpringLayout.EAST, autor_field); // criar_button
        layout_manager.putConstraint(SpringLayout.NORTH, add_button, 0,
                SpringLayout.NORTH, autor_field);

    }

    void setObservers() {
        return_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        notifyEvent(new TransitionEvent(
                                new MainPanel(bounds, observer, pessoa)));
                    }
                });

        criar_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String titulo = titulo_field.getText();

                        String resumo = resumo_area.getText();
                        String cidade = cidade_field.getText();
                        String estado = estado_field.getText();
                        String dption = dpction_area.getText();

                        ArrayList<Categoria> catList = new ArrayList<Categoria>();
                        catList.add((Categoria) cat_cbox.getSelectedItem());

                        ProjetoDTO projetoDto = new ProjetoDTO(titulo, pessoa.login, resumo,
                                dption, cidade, estado, catList);
                        
                                Optional<Projeto>  opt = DataBaseManager.get().insertProject(projetoDto, nomeList);

                          if (opt.isPresent()) {
                            System.out.println("ProjCadastroPanel: Projeto Cadastrado.");
                          notifyEvent(new TransitionEvent(new MainPanel(bounds, observer, pessoa)));
                          }
                          else
                          {
                            System.out.println("ProjCadastroPanel: Não foi possivel cadastrar o projeto.");
                         }
                         
                    }
                });

        add_button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        nomeList.add(autor_field.getText());
                        autor_field.setText("");
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
    private Pessoa pessoa;
    ArrayList<String> nomeList = new ArrayList<String>();

    // Labels
    private JLabel titulo_label = new JLabel("Título");
    private JLabel autor_label = new JLabel("Autor");
    private JLabel resumo_label = new JLabel("Resumo");
    private JLabel cidade_label = new JLabel("Cidade");
    private JLabel estado_label = new JLabel("Estado");
    private JLabel dption_label = new JLabel("Descrição");
    private JLabel cat_label = new JLabel("Categoria");

    // Fields
    private JTextField titulo_field = new JTextField("", 30);
    private JTextField autor_field = new JTextField("", 30);
    private JTextField cidade_field = new JTextField("", 30);
    private JTextField estado_field = new JTextField("", 30);

    // Area
    private JTextArea resumo_area = new JTextArea(2, 30);
    private JTextArea dpction_area = new JTextArea(5, 30);

    // ComboBox
    private JComboBox<Categoria> cat_cbox = new JComboBox<Categoria>();

    // Buttons
    private JButton return_button = new JButton("Retornar");
    private JButton criar_button = new JButton("Criar Projeto");
    private JButton add_button = new JButton("Adicionar");

}
