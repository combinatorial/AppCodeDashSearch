package de.dreamlab.dash.launcher;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import de.dreamlab.dash.SystemUtil;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public abstract class AbstractLauncher {

    public static AbstractLauncher createInstance()
    {
        if ( SystemUtil.isIsOSMac() ) {
            return new DashLauncher();
        }
        else {
            return new DashPluginSchemeLauncher();
        }
    }

    abstract protected void openUri(String uriStr) throws Exception;

    public void search(final @Nonnull List<String> keywords, final @Nonnull String query)
    {
        try {
            String request = "dash-plugin://";

            // keywords
            if ( keywords.size() > 0 ) {
                request += "keys=" + String.join(",", keywords) + "&";
            }

            // query
            request += "query=" + urlEncode(query);

            openUri(request);
        }
        catch ( Throwable e ) {
            Notifications.Bus.notify(new Notification("Dash Plugin Error", "Dash Plugin Error", e.getMessage(), NotificationType.ERROR));
        }
    }

    private String urlEncode(final @Nonnull String s) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
    }
}
