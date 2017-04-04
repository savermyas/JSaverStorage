package vstu.edu.ru.expert.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import saver.common.NodeInfo;
import saver.common.StorageInfo;
//import vstu.edu.ru.expert.Coordinator;
import vstu.edu.ru.expert.agents.JSaverCoordinator;

/**
 * @author  Saver
 */
public class SaverHandler implements ContentHandler 
{
	byte elementType = 0;
	/**
	 * @uml.property  name="ni"
	 * @uml.associationEnd  
	 */
	NodeInfo ni = null;
	/**
	 * @uml.property  name="si"
	 * @uml.associationEnd  
	 */
	StorageInfo si = null;
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	JSaverCoordinator c = null;
	
	public void bindCoordinator(JSaverCoordinator ci)
	{
		c = ci;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException 
	{
		String strVal = new String(ch, start, length);
		switch(elementType)
		{
			case XMLElementTypes.NODE_LABEL_SECTION:
				ni.label = strVal;
			break;
			case XMLElementTypes.NODE_ADDR_SECTION:
				ni.addr = strVal;
			break;
			case XMLElementTypes.NODE_ID_SECTION:
				ni.id = Integer.parseInt(strVal);
			break;
			case XMLElementTypes.NODE_TYPE:
			//	ni.bNode = Boolean.parseBoolean(strVal);
			break;
			case XMLElementTypes.STORAGE_NAME_SECTION:
				si.name = strVal;
			break;
			case XMLElementTypes.STORAGE_NODE_SECTION:
				int currNodeId = Integer.parseInt(strVal);
				NodeInfo currNode = c.getNodeInfoById(currNodeId);
				si.savers.add(currNode);
			break;
			case XMLElementTypes.DISPERSION_NODE_SECTION:
				currNodeId = Integer.parseInt(strVal);
				currNode = c.getNodeInfoById(currNodeId);
				si.dispersers.add(currNode);
			break;
			case XMLElementTypes.THREADS_NUM:
				
			//	JSaverCoordinator.setThreadsNum(Integer.parseInt(strVal));
			break;
			case XMLElementTypes.MIN_NODES_NUM:
				si.minimalnodes = Integer.parseInt(strVal);
			//	JSaverCoordinator.setMinNodesNum(Integer.parseInt(strVal));
			break;
			case XMLElementTypes.BUF_LENGTH:
				c.setBufLength(Integer.parseInt(strVal));
			//	JSaverCoordinator.setBufLength(Integer.parseInt(strVal));
			break;
			//case XMLElementTypes.REFRESH_RATE:
			//	JSaverCoordinator.maxRefreshRate = (Integer.parseInt(strVal));
			//break;
			case XMLElementTypes.REFRESH_RATE_SECTION: //Если мы в разделе установки таймера обновления
				//Coordinator.maxRefreshRate = (Integer.parseInt(strVal)); 
				c.maxRefreshRate = (Integer.parseInt(strVal)); 
			break;

		}
		//elementType = 0;
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException 
	{
	//	System.out.println("start "+elementType+" "+name);
		if(name.equalsIgnoreCase("settings"))
		{
			elementType = XMLElementTypes.ROOT_SECTION;
		} else		
		if(name.equalsIgnoreCase("application")&&elementType==XMLElementTypes.ROOT_SECTION)
		{
			elementType = XMLElementTypes.APPLICATON_SECTION;
		} else	
		if(name.equalsIgnoreCase("refreshrate")&&elementType==XMLElementTypes.APPLICATON_SECTION)
		{
			elementType = XMLElementTypes.REFRESH_RATE_SECTION;
		} else	
		if(name.equalsIgnoreCase("nodeslist")&&elementType==XMLElementTypes.ROOT_SECTION)
		{
			elementType = XMLElementTypes.NODES_LIST_SECTION;
		} else	
		if(name.equalsIgnoreCase("node")&&elementType==XMLElementTypes.NODES_LIST_SECTION)
		{
			ni = new NodeInfo();
			elementType = XMLElementTypes.NODE_INFO_SECTION;
		} else	
		if(name.equalsIgnoreCase("address")&&elementType==XMLElementTypes.NODE_INFO_SECTION)
		{
			elementType = XMLElementTypes.NODE_ADDR_SECTION;
		} else	
		if(name.equalsIgnoreCase("label")&&elementType==XMLElementTypes.NODE_INFO_SECTION)
		{
			elementType = XMLElementTypes.NODE_LABEL_SECTION;
		} else
		if(name.equalsIgnoreCase("id")&&elementType==XMLElementTypes.NODE_INFO_SECTION)
		{
			elementType = XMLElementTypes.NODE_ID_SECTION;
		} else	
		if(name.equalsIgnoreCase("storage")&&elementType==XMLElementTypes.ROOT_SECTION)
		{
			si = new StorageInfo();
			si.dispersers = new ArrayList<NodeInfo>();
			si.savers = new ArrayList<NodeInfo>();
			elementType = XMLElementTypes.STORAGE_SECTION;
		} else
		if(name.equalsIgnoreCase("name")&&elementType==XMLElementTypes.STORAGE_SECTION)
		{
			elementType = XMLElementTypes.STORAGE_NAME_SECTION;
		} else
		if(name.equalsIgnoreCase("storagesettings")&&elementType==XMLElementTypes.STORAGE_SECTION)
		{
			elementType = XMLElementTypes.STORAGE_SETTINGS_SECTION;
		} else
		if(name.equalsIgnoreCase("dispersionsettings")&&elementType==XMLElementTypes.STORAGE_SECTION)
		{
			elementType = XMLElementTypes.DISPERSION_SETTINGS_SECTION;
		} else	
		if(name.equalsIgnoreCase("nodeid")&&elementType==XMLElementTypes.STORAGE_SETTINGS_SECTION)
		{
			elementType = XMLElementTypes.STORAGE_NODE_SECTION;
		} else	
		if(name.equalsIgnoreCase("nodeid")&&elementType==XMLElementTypes.DISPERSION_SETTINGS_SECTION)
		{
			elementType = XMLElementTypes.DISPERSION_NODE_SECTION;
		} else	
		if(name.equalsIgnoreCase("scheme"))
		{
			elementType = XMLElementTypes.SCHEME_SECTION;
		} else		
		if(name.equalsIgnoreCase("node"))
		{
			elementType = XMLElementTypes.NODE_SECTION;
			ni = new NodeInfo();
		} else
		if(name.equalsIgnoreCase("label"))
		{
			elementType = XMLElementTypes.NODE_LABEL;
		} else	
		if(name.equalsIgnoreCase("address"))
		{
			elementType = XMLElementTypes.NODE_ADDR;
		} else	
		if(name.equalsIgnoreCase("minimalnodes"))
		{
			elementType = XMLElementTypes.MIN_NODES_NUM;
		}
		else	
		if(name.equalsIgnoreCase("buflength"))
		{
			elementType = XMLElementTypes.BUF_LENGTH;
		}
		else	
		if(name.equalsIgnoreCase("threadsnum"))
		{
			elementType = XMLElementTypes.THREADS_NUM;
		}
		else	
		if(name.equalsIgnoreCase("refreshrate"))
		{
			elementType = XMLElementTypes.REFRESH_RATE;
		}
	//	System.out.println("new "+elementType+" "+name);
	}
	
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException 
	{
		//System.out.println(name);
	//	System.out.println("end "+elementType+" "+name);
		if(name.equalsIgnoreCase("application")&&elementType==XMLElementTypes.APPLICATON_SECTION)
		{
			elementType=XMLElementTypes.ROOT_SECTION;
		}else	
		if(name.equalsIgnoreCase("nodeslist")&&elementType==XMLElementTypes.NODES_LIST_SECTION)
		{
			elementType=XMLElementTypes.ROOT_SECTION;
		}else	
		if(name.equalsIgnoreCase("node")&&elementType==XMLElementTypes.NODE_INFO_SECTION)
		{
			c.nodesInfo.add(ni);
			
			//Coordinator.nodesInfo.add(ni);
			
			elementType=XMLElementTypes.NODES_LIST_SECTION;
		}else	
		if(name.equalsIgnoreCase("label")&&elementType==XMLElementTypes.NODE_LABEL_SECTION)
		{
			elementType=XMLElementTypes.NODE_INFO_SECTION;
		}else
		if(name.equalsIgnoreCase("address")&&elementType==XMLElementTypes.NODE_ADDR_SECTION)
		{
			elementType=XMLElementTypes.NODE_INFO_SECTION;
		}else
		if(name.equalsIgnoreCase("id")&&elementType==XMLElementTypes.NODE_ID_SECTION)
		{
			elementType=XMLElementTypes.NODE_INFO_SECTION;
		}else
		if(name.equalsIgnoreCase("storage")&&elementType==XMLElementTypes.STORAGE_SECTION)
		{
			elementType=XMLElementTypes.ROOT_SECTION;
			c.storagesInfo.add(si);
			si.id = (c.storagesInfo.size()-1);
			//Coordinator.storagesInfo.add(si);
		}else
	
		if(name.equalsIgnoreCase("name")&&elementType==XMLElementTypes.STORAGE_NAME_SECTION)
		{
			elementType = XMLElementTypes.STORAGE_SECTION;
		} else
		if(name.equalsIgnoreCase("minimalnodes")&&elementType==XMLElementTypes.MIN_NODES_NUM)
		{
			elementType = XMLElementTypes.STORAGE_SECTION;
		} else
		if(name.equalsIgnoreCase("storagesettings")&&elementType==XMLElementTypes.STORAGE_SETTINGS_SECTION)
		{
			elementType = XMLElementTypes.STORAGE_SECTION;
		} else
		if(name.equalsIgnoreCase("dispersionsettings")&&elementType==XMLElementTypes.DISPERSION_SETTINGS_SECTION)
		{
			elementType = XMLElementTypes.STORAGE_SECTION;
		} else	
		if(name.equalsIgnoreCase("nodeid")&&elementType==XMLElementTypes.DISPERSION_NODE_SECTION)
		{
			elementType = XMLElementTypes.DISPERSION_SETTINGS_SECTION;
		} else
		if(name.equalsIgnoreCase("nodeid")&&elementType==XMLElementTypes.STORAGE_NODE_SECTION)
		{
			elementType = XMLElementTypes.STORAGE_SETTINGS_SECTION;
		} else	
		if(name.equalsIgnoreCase("refreshrate"))
		{
			elementType = XMLElementTypes.APPLICATON_SECTION; //Флаг устанавливаем в предыдущую секцию
		}
		if(name.equalsIgnoreCase("buflength"))
		{
			elementType = XMLElementTypes.APPLICATON_SECTION; //Флаг устанавливаем в предыдущую секцию
		}
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub

	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub

	}

}
