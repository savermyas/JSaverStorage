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
	Icon onIcon = new ImageIcon("./icons/node_on.gif");
	Icon offIcon = new ImageIcon("./icons/node_off1.gif");

	
	Icon disperserOnIcon = new ImageIcon("./icons/disperser_on.gif");
	Icon disperserOffIcon = new ImageIcon("./icons/disperser_off.gif");
	Icon nodeOnIcon = new ImageIcon("./icons/node_on.gif");
	Icon nodeOffIcon = new ImageIcon("./icons/node_off.gif");
	Icon saverOnIcon = new ImageIcon("./icons/saver_on.gif");
	Icon saverOffIcon = new ImageIcon("./icons/saver_off.gif");
	Icon comboOnIcon = new ImageIcon("./icons/combo_on.gif");
	Icon comboOffIcon = new ImageIcon("./icons/combo_off.gif");
	Icon sumIcon = new ImageIcon("s.gif"); 
	Icon infoIcon = new ImageIcon("i.gif");
	Icon b_onIcon = new ImageIcon("b_on.gif");
	Icon b_offIcon = new ImageIcon("a_off.gif");
	
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	private JSaverCoordinator c;;	    

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
