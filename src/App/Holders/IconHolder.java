package App.Holders;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.HashMap;

public class IconHolder {
    
    static public ImageIcon getIcon(String path)
    {
        if (iconMap.containsKey(path))
        {
            return iconMap.get(path);
        }
        else 
        {
            ImageIcon icon = new ImageIcon(path);
            iconMap.put(path, icon);
            return icon;
        }
    }

    static private Map<String, ImageIcon> iconMap = new HashMap<String, ImageIcon>();
}
