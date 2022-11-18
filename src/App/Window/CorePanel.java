package App.Window;

import App.Window.Panel.LoginPanel;
import App.Window.Panel.StatusPanel;
import App.Window.Panel.TransitionEvent;
import App.Window.Panel.TransitionPanelObserver;
import App.Window.Panel.TransitionablePanel;

import javax.swing.*;
import java.awt.*;

/**
 *  < Panel based on SpringLayout
 */
public class CorePanel extends JPanel implements TransitionPanelObserver {
    
    public CorePanel(Dimension bounds)
    {
        this.bounds = bounds;
        this.setSize(bounds);
        this.setBackground(Color.GRAY);

        this.panelTopBounds = new Dimension((int) (bounds.width * 0.90), (int) (bounds.height * 0.75));
        this.panelBottomBounds = new Dimension((int) (bounds.width * 0.90), (int) (bounds.height * 0.05));

        paddingHor = (int)(bounds.width * 0.05);
        paddingVert = (int) (bounds.height * 0.05);

        panelTop = new LoginPanel(panelTopBounds, this);
        panelBottom = new StatusPanel(panelBottomBounds, this);
        //panelBottom = new LoginPanel(panelTopBounds, this);
        init();
    }

    private void init() {
        this.setLayout(layoutManager);
        configureLayout(layoutManager);

        this.add(panelTop);
        this.add(panelBottom);
    }

    private void configureLayout(SpringLayout layout_manager) {
        
        //panelTop
        layout_manager.putConstraint(SpringLayout.WEST, panelTop, paddingHor,
                                        SpringLayout.WEST, this);  
        layout_manager.putConstraint(SpringLayout.NORTH, panelTop, paddingVert,
                                        SpringLayout.NORTH, this);  

        //panelBottom
        layout_manager.putConstraint(SpringLayout.WEST, panelBottom, paddingHor,
                                        SpringLayout.WEST, this);  
        layout_manager.putConstraint(SpringLayout.NORTH, panelBottom, paddingVert,
                                        SpringLayout.SOUTH, panelTop);  

        SwingUtilities.updateComponentTreeUI(this); // atualiza a janela
    }

    @Override
    public void onEvent(TransitionEvent event) {
        panelTop.onExit();
        this.remove(panelTop);

        panelTop = event.next;

        panelTop.onEnter();
        this.add(panelTop);

        panelBottom.update();
        configureLayout(layoutManager);
    }

    //Properties Data
    private Dimension bounds;
    private Dimension panelTopBounds;
    private Dimension panelBottomBounds;
    private int paddingHor;
    private int paddingVert;


    //Layout Manager
    SpringLayout layoutManager = new SpringLayout();

    //Panel Data
    private TransitionablePanel panelTop;
    private StatusPanel panelBottom;



}
