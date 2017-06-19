
package start;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "PrzyjazdService", targetNamespace = "http://start/", wsdlLocation = "http://localhost:8080/Przyjazd/Przyjazd?wsdl")
public class PrzyjazdService
    extends Service
{

    private final static URL PRZYJAZDSERVICE_WSDL_LOCATION;
    private final static WebServiceException PRZYJAZDSERVICE_EXCEPTION;
    private final static QName PRZYJAZDSERVICE_QNAME = new QName("http://start/", "PrzyjazdService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/Przyjazd/Przyjazd?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PRZYJAZDSERVICE_WSDL_LOCATION = url;
        PRZYJAZDSERVICE_EXCEPTION = e;
    }

    public PrzyjazdService() {
        super(__getWsdlLocation(), PRZYJAZDSERVICE_QNAME);
    }

    public PrzyjazdService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PRZYJAZDSERVICE_QNAME, features);
    }

    public PrzyjazdService(URL wsdlLocation) {
        super(wsdlLocation, PRZYJAZDSERVICE_QNAME);
    }

    public PrzyjazdService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PRZYJAZDSERVICE_QNAME, features);
    }

    public PrzyjazdService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PrzyjazdService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns Przyjazd
     */
    @WebEndpoint(name = "PrzyjazdPort")
    public Przyjazd getPrzyjazdPort() {
        return super.getPort(new QName("http://start/", "PrzyjazdPort"), Przyjazd.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Przyjazd
     */
    @WebEndpoint(name = "PrzyjazdPort")
    public Przyjazd getPrzyjazdPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://start/", "PrzyjazdPort"), Przyjazd.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PRZYJAZDSERVICE_EXCEPTION!= null) {
            throw PRZYJAZDSERVICE_EXCEPTION;
        }
        return PRZYJAZDSERVICE_WSDL_LOCATION;
    }

}