package com.ralph.pmgr;

import com.ralph.pmgr.exception.ClientException;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcAhcTransportFactory;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcClientException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.ralph.pmgr.utils.Utils.printJson;


public class ConfluenceApi {

    private XmlRpcClient client = null;
    private String token;

    public ConfluenceApi(String endpoint) throws MalformedURLException {

        if (endpoint.endsWith("/")) {
            endpoint = endpoint.substring(0, endpoint.length() - 1);
        }

        if (!endpoint.endsWith("/rpc/xmlrpc")) {
            endpoint += "/rpc/xmlrpc";
        }

        XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
        clientConfig.setServerURL(new URL(endpoint));
        clientConfig.setEnabledForExtensions(true);
        client = new XmlRpcClient();
        client.setTransportFactory(new XmlRpcAhcTransportFactory(client));
        client.setConfig(clientConfig);
    }

    public void login(String username, String password) throws ConfluenceException, ClientException {
        token = (String) call("login", username, password);
    }

    /**
     * remove this token from the list of logged in tokens. Returns true if the user was logged out, false if they
     * were not logged in in the first place.
     */
    public boolean logout() throws ConfluenceException, ClientException {
        return (Boolean) call("logout");
    }

    /**
     * returns all the permissions of the current space.
     */
    public Object[] getSpacePermissionSets(String spaceName) throws ConfluenceException, ClientException {
        return (Object[]) call("getSpacePermissionSets", spaceName);
    }

    /**
     * Give the entity named {{remoteEntityName}} (either a group or a user) the permissions {{permissions}} on the
     * space with the key {{spaceKey}}.
     */
    public boolean addPermissionsToSpace(List<ConfluencePermissionKeys> permissions, String remoteEntityName, String spaceKey) throws ConfluenceException, ClientException {
        String[] permissionArr = new String[permissions.size()];
        int index = 0;
        for (ConfluencePermissionKeys value : permissions) {
            permissionArr[index] = value.name();
            index++;
        }
        Boolean value = (Boolean) call("addPermissionsToSpace", permissionArr, remoteEntityName, spaceKey);
        return value.booleanValue();
    }

    /**
     * Remove the permission {{permission} from the entity named {{remoteEntityName}} (either a group or a user) on
     * the space with the key {{spaceKey}}.
     */
    public boolean removePermissionFromSpace(String permission, String remoteEntityName, String spaceKey) throws ConfluenceException, ClientException {
        Boolean value = (Boolean) call("removePermissionFromSpace", permission, remoteEntityName, spaceKey);
        return value.booleanValue();
    }


    private Object call(String command, Object... args) throws ConfluenceException, ClientException {
        Object[] vector;
        if (!command.equals("login")) {
            vector = new Object[args.length + 1];
            vector[0] = token;
            System.arraycopy(args, 0, vector, 1, args.length);
        } else {
            vector = args;
        }
        printJson(vector);
        try {
            return client.execute("confluence2." + command, vector);
        } catch (XmlRpcClientException e) {
            throw new ClientException(e.getMessage(), e.linkedException);
        } catch (XmlRpcException e) {
            throw new ConfluenceException(e.getMessage(), e.linkedException);
        }
    }
}
