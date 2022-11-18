package App.Window.Panel;

import java.util.ArrayList;
import javax.swing.*;

public abstract class TransitionablePanel extends JPanel {

    public void addObserver(final TransitionPanelObserver observer)
    {
        this.observer = observer;
    }

    public void removeObserver()
    {
        observer = null;
    }

    protected void notifyEvent(TransitionEvent event)
    {
        if (event != null && observer != null) {
                observer.onEvent(event);
        }
    }

    public abstract void onExit();
    public abstract void onEnter();
    
    protected TransitionPanelObserver observer = null;    
}
