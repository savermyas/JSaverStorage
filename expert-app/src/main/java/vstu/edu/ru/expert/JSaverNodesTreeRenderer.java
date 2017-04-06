/**
 * 
 */
package vstu.edu.ru.expert;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import saver.common.NodeInfo;
import vstu.edu.ru.expert.agents.JSaverCoordinator;

/**
 * @author  saver
 */
public class JSaverNodesTreeRenderer extends DefaultTreeCellRenderer 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -92999669652631537L;
	Icon onIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/node_on.gif"));
	Icon offIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/node_off.gif"));

	Icon disperserOnIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/disperser_on.gif"));
	Icon disperserOffIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/disperser_off.gif"));
	Icon nodeOnIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/node_on.gif"));
	Icon nodeOffIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/node_off.gif"));
	Icon saverOnIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/saver_on.gif"));
	Icon saverOffIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/saver_off.gif"));
	Icon comboOnIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/combo_on.gif"));
	Icon comboOffIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/combo_off.gif"));

	Icon sumIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/node_on.gif"));
/*	Icon infoIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/file.gif")"i.gif");
	Icon b_onIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/file.gif")"b_on.gif");
	Icon b_offIcon = new ImageIcon(getClass().getClassLoader().getResource("icons/file.gif")"a_off.gif");
	*/
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	private JSaverCoordinator c;

    public JSaverNodesTreeRenderer() 
    {
    	
	}
    
    public JSaverNodesTreeRenderer(JSaverCoordinator coord) 
    {
    	 c = coord;
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
        DefaultMutableTreeNode node =
            (DefaultMutableTreeNode) value;
        try 
        {
			if (node.isRoot())
			{
				 setIcon(sumIcon);					
			}

			else if(((NodeInfo) node.getUserObject()).timeout>=0)//JSaverCoordinator.nodeIsOn(node.getRoot().getIndex(node)))  
			{		
				if(((NodeInfo) node.getUserObject()).disperser)
				{
					if(((NodeInfo) node.getUserObject()).saver)
					{
						setIcon(comboOnIcon);
					}
					else
					{
						setIcon(disperserOnIcon);
					}
				}
				else if(((NodeInfo) node.getUserObject()).saver)
				{
					setIcon(saverOnIcon);
				}	
				else
				{
					setIcon(nodeOnIcon);
				}
			}
			else 
			{
				if(((NodeInfo) node.getUserObject()).disperser)
				{
					if(((NodeInfo) node.getUserObject()).saver)
					{
						setIcon(comboOffIcon);
					}
					else
					{
						setIcon(disperserOffIcon);
					}
				}
				else if(((NodeInfo) node.getUserObject()).saver)
				{
					setIcon(saverOffIcon);
				}	
				else
				{
					setIcon(nodeOffIcon);
				}
			}
			
		} catch (Exception e) 
		{
			//e.printStackTrace();
		}
        return this;
    }
};
