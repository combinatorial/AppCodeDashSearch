package de.dreamlab.dash.launcher;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.util.List;

public class ZealLauncher extends AbstractLauncher {

    public void search(List<String> keywords, String query)
    {
        try {
            String queryStr = "";

            // keywords
            if ( keywords.size() > 0 ) {
                queryStr += keywordString(keywords) + ":";
            }

            // query
            queryStr += query;

            openCmd(queryStr);
        }
        catch ( Throwable e ) {
            Notifications.Bus.notify(new Notification("Dash Plugin Error", "Dash Plugin Error", e.getMessage(), NotificationType.ERROR));
        }
    }

    private void openCmd(final String queryStr) throws ExecutionException
    {
        final GeneralCommandLine commandLine = new GeneralCommandLine("zeal");
        commandLine.addParameter("--query");
        commandLine.addParameter(queryStr);
        commandLine.createProcess();
    }

}
