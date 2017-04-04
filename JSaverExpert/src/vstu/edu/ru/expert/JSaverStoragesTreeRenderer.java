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
import saver.common.StorageInfo;
import vstu.edu.ru.expert.agents.JSaverCoordinator;

/**
 * @author  saver
 */
public class JSaverStoragesTreeRenderer extends DefaultTreeCellRenderer 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -92999669652631537L;
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
	Icon saversIcon = new ImageIcon("./icons/savers.gif");
	Icon dispersersIcon = new ImageIcon("./icons/dispersers.gif");
	Icon storageOnIcon = new ImageIcon("./icons/storage_on.gif");
	Icon storageOffIcon = new ImageIcon("./icons/storage_off.gif");
	Icon storageWarnIcon = new ImageIcon("./icons/storage_w.gif");
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	private JSaverCoordinator c;    

    public JSaverStoragesTreeRenderer() 
    {
    	
	}
    
    public JSaverStoragesTreeRenderer(JSaverCoordinator coord) 
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
			else if(!node.isLeaf())
			{
				if(node.getUserObject().equals("Storage agents"))
				{
					setIcon(saversIcon);
				}
				else if(node.getUserObject().equals("Dispersion agents"))
				{
					setIcon(dispersersIcon);
				}
				else
				{
					if(((StorageInfo) node.getUserObject()).canDisperse())
						setIcon(storageOnIcon);
					else
						if(((StorageInfo) node.getUserObject()).storageIsOn())
							setIcon(storageWarnIcon);
						else
							setIcon(storageOffIcon);
				}
				
			}
			else if(((NodeInfo) node.getUserObject()).timeout>=0)//JSaverCoordinator.nodeIsOn(node.getRoot().getIndex(node)))  
			{		
				setIcon(nodeOnIcon);
			}
			else 
			{
				setIcon(nodeOffIcon);
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
        return this;
    }
};
