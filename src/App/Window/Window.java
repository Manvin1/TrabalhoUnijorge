package App.Window;


import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(String name, Dimension bounds)
    {
        window.setSize(bounds);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setContentPane(new CorePanel(bounds));
    }

    public void setVisible(boolean visible)
    {
        window.setVisible(visible);
    }

    //window
    private JFrame window = new JFrame("MyApp");
    
}
