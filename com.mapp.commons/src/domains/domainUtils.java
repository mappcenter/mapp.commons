/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domains;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

/**
 *
 * @author liempt
 */
public class domainUtils {
    public static boolean isFree(String url) {
        try
        {
            InetAddress inetAddress;
            // get Internet Address of this host name
            inetAddress = InetAddress.getByName(url);
            // get the default initial Directory Context
            InitialDirContext iDirC = new InitialDirContext();
            // get the DNS records for inetAddress
            Attributes attributes = iDirC.getAttributes("dns:/" + inetAddress.getHostName());
            // get an enumeration of the attributes and print them out
            NamingEnumeration<?> attributeEnumeration = attributes.getAll();
            if (attributeEnumeration.hasMore())
            {
                attributeEnumeration.close();
                return false;
            }
            attributeEnumeration.close();
            return false;
        }
        catch (UnknownHostException exception)
        {
        	return true;       
        }
        catch (NamingException exception)
        {
            return false;
        }
    }
}
