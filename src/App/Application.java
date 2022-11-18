package App;

import App.Holders.*;
import App.Managers.*;
import App.Core.*;
import App.Window.*;

import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Application {
    
    public Application() 
    {
        //nothing
    }

    /**
     * < Inicia a aplicação
     */
    public void start() 
    {
        window.setVisible(true);
    }

    //Window
    private App.Window.Window window = new App.Window.Window("MyApp", new Dimension(1080, 720));

}
