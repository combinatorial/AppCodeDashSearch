/**
 * Created by gerard on 04.04.14.
 */

package de.dreamlab.dash;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DashLauncher {

    public DashLauncher() {
    }

    public void search(Collection<String> keywords, String query)
    {
        try {
            if ( System.getProperty("os.name").equals("Linux") ) {
                List<String> command = Arrays.asList("zeal", "--query");
                StringBuilder queryString = new StringBuilder();
                if (keywords.size() > 0) {
                    // Zeal only supports searches with one keyword. Until that
                    // limitation is fixed, we can only search for the first keyword.
                    String keyword = keywords.iterator().next();
                    queryString.append(keyword);
                    queryString.append(":");
                }
                queryString.append(query);
                ProcessBuilder pb = new ProcessBuilder("zeal", "--query", queryString.toString());
                pb.start();
            } else {
                String request = "dash-plugin://";

                // keywords
                if (keywords.size() > 0) {
                    request += "keys=";
                    int i = 0;

                    for (String keyword : keywords) {
                        if (i > 0) {
                            request += ',';
                        }

                        request += urlEncode(keyword);

                        i++;
                    }

                    request += "&";
                }

                // query
                request += "query=" + urlEncode(query);

                openUri(request);
            }
        }
        catch ( IOException e ) {
            Notifications.Bus.notify(new Notification("Dash Plugin Error", "Dash Plugin Error", e.getMessage(), NotificationType.ERROR));
            return;
        }
    }

    private void openUri(String uriStr)
    {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI uri = new URI(uriStr);
            desktop.browse(uri);
        }
        catch (Throwable e) {
            Notifications.Bus.notify(new Notification("Dash Plugin Error", "Dash Plugin Error", e.getMessage(), NotificationType.ERROR));
        }
    }

    private String urlEncode(String s) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
    }
}