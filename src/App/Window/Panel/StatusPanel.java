package App.Window.Panel;

import App.Holders.*;
import App.Managers.ConnectionManager;
import App.Managers.DataBaseManager;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends TransitionablePanel {
    
    enum Status 
    {
        connected, 
        offline,
    };

    public StatusPanel(Dimension dimension, TransitionPanelObserver observer)
    {
        bounds = dimension;
        this.setPreferredSize(dimension);

        init();

        addObserver(observer);
    }

    @Override
    public void onExit() {
        System.out.println("StatusPanel: Exiting");
    }

    @Override
    public void onEnter() {
        System.out.println("StatusPanel: Entring");
    }
   
    public void update()
    {
        if (ConnectionManager.ping())
        {
            removeAll();
            statusLabel = new JLabel ("Connected", greenIcon, JLabel.CENTER);
            projetosLabel = new JLabel(String.valueOf(DataBaseManager.get().getProjectCount()) + " Projetos", paperIcon, JLabel.CENTER);
            usuariosLabel = new JLabel(String.valueOf(DataBaseManager.get().getUserCount()) + " Usuários", peopleIcon, JLabel.CENTER);

            this.add(statusLabel);
            this.add(projetosLabel);
            this.add(usuariosLabel);

            status = Status.connected;
        }
        else {
                removeAll();
                statusLabel = new JLabel ("Offline", redIcon, JLabel.CENTER);
                projetosLabel = new JLabel("??? Projetos", paperIcon, JLabel.CENTER);
                usuariosLabel = new JLabel("??? Usuários", peopleIcon, JLabel.CENTER);                
                this.add(statusLabel);
                this.add(projetosLabel);
                this.add(usuariosLabel);

                status = Status.offline;
        }
    }

    private void init()
    {
        FlowLayout layoutManager = new FlowLayout();
        layoutManager.setAlignment(FlowLayout.CENTER);
        layoutManager.setHgap(20);
        
        this.setLayout(layoutManager);
        update();

        this.add(statusLabel);
        this.add(projetosLabel);
        this.add(usuariosLabel);
    }

    //Id
    private static final long serialVersionUID = 1L;

    //Runtime Data
    private Dimension bounds;
    private Status status = Status.offline;

    //Icons
    private ImageIcon greenIcon = IconHolder.getIcon("graphics/greenCircle.png");
    private ImageIcon redIcon = IconHolder.getIcon("graphics/redCircle.png");
    private ImageIcon peopleIcon = IconHolder.getIcon("graphics/people.png");
    private ImageIcon paperIcon = IconHolder.getIcon("graphics/paper.png");

    //Labels
    private JLabel statusLabel = new JLabel("Offline", redIcon, JLabel.CENTER);
    private JLabel projetosLabel = new JLabel("??? Projetos", paperIcon, JLabel.CENTER);
    private JLabel usuariosLabel = new JLabel("??? Usuários", peopleIcon, JLabel.CENTER);




}
