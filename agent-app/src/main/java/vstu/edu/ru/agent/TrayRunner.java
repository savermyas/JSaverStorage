package vstu.edu.ru.agent;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import saver.galoismath.GF8;

import javax.swing.*;


public class TrayRunner 
{
	/*static JNKServer serv;
	static class ShowMessageListener implements ActionListener {
	    TrayIcon trayIcon;
	    String title;
	    String message;
	    TrayIcon.MessageType messageType;
	    ShowMessageListener(
	        TrayIcon trayIcon,
	        String title,
	        String message,
	        TrayIcon.MessageType messageType) 
	        {
	    		this.trayIcon = trayIcon;
	    		this.title = title;
	    		this.message = message;
	    		this.messageType = messageType;
	        }
	    public void actionPerformed(ActionEvent e) {
	      trayIcon.displayMessage(title, message, messageType);
	    }
	  }*/
	public static void main(String args[]) 
	{
	//	serv = new JNKServer();
		Runnable runner = new Runnable() {
	    public void run() 
	    {
	    			    	
	    	  if (SystemTray.isSupported()) {
	          final SystemTray tray = SystemTray.getSystemTray();
	          ImageIcon image = new ImageIcon(getClass().getClassLoader().getResource("TrayIcon.gif"));
	          //Image image = Toolkit.getDefaultToolkit().getImage("TrayIcon.gif");

	          PopupMenu popup = new PopupMenu();
	          final TrayIcon trayIcon = new TrayIcon(image.getImage(), "JSaverStorage agent is running...", popup);
	          trayIcon.setImageAutoSize(true);
	 
	          MenuItem options = new MenuItem("Options");
	          options.addActionListener(new ActionListener()
	        		  {	          			
						public void actionPerformed(ActionEvent arg0) 
						{
							OptionsDialog.main(null);	 
						}; 
	          		  });
	          popup.add(options);	          
	      //    MenuItem item = new MenuItem("Error");
	       //   item = new MenuItem("Info");
	      //    item.addActionListener(new ShowMessageListener(trayIcon,
	      //      "Java distributed failover storage service", "Running", TrayIcon.MessageType.INFO));
	      //    popup.add(item);
	          MenuItem exit = new MenuItem("Exit");
	          exit.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	             // tray.remove(trayIcon);
	      //        serv.stopServer();
	            	AdminPass.main(null);
	              
	            }
	          });
	          popup.add(exit);
	          try {
	            tray.add(trayIcon);	          
	          } catch (AWTException e) {
	            System.err.println("Can't add to tray");
	          }
	        } else {
	          System.err.println("Tray unavailable");
	        }
	      }
	    };
	    EventQueue.invokeLater(runner);
	   // serv.startServer();	 
	    
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());
		
		try 
		{
			//JStorageImpl s = new JStorageImpl(); http://ru.wikipedia.org/wiki/%D0%90%D0%BD%D0%BE%D0%BD%D0%B8%D0%BC%D0%BD%D1%8B%D0%B5_%D1%81%D0%B5%D1%82%D0%B8
			GF8.generateGf8();
			JDisperserImpl disperser = new JDisperserImpl(); 
			JSaverImpl saver = new JSaverImpl(); 
			Registry r =  LocateRegistry.createRegistry(1099);
			String thisAddr = InetAddress.getLocalHost().toString();
			//Registry r =  LocateRegistry. 
			//r.rebind("storage", s);
			r.rebind("disperser", disperser);
			r.rebind("saver", saver);
			System.out.println("Server started at "+thisAddr);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	  }
}
