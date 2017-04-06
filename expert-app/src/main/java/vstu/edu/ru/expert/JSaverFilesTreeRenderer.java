/**
 * 
 */
package vstu.edu.ru.expert;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author saver
 *
 */
public class JSaverFilesTreeRenderer extends DefaultTreeCellRenderer 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -92999669652631537L;
	Icon fileIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/file.gif"));

    public JSaverFilesTreeRenderer() 
    {
    	
	}
    
	public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
        try 
        {
        	setIcon(fileIcon);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
        return this;
    }
};
