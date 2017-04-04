package vstu.edu.ru.expert.xml;

import java.io.IOException;

import org.xml.sax.SAXException;

import vstu.edu.ru.expert.agents.JSaverCoordinator;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;


/**
 * @author  saver
 */
public class JSaverConfigParser 
{
	String uri;
	/**
	 * @uml.property  name="c"
	 * @uml.associationEnd  
	 */
	JSaverCoordinator c;
	
	public void bindCoordinator(JSaverCoordinator coord)
	{
		c = coord;
	}
	
	public void initScheme() throws SAXException, IOException 
	{
		SAXParser parser = new SAXParser();
		SaverHandler sh = new SaverHandler();
		sh.bindCoordinator(c);
		parser.setContentHandler(sh);
		parser.parse(uri);
	}
	
	public JSaverConfigParser(String filename) 
	{
		uri = filename;
	}

}
