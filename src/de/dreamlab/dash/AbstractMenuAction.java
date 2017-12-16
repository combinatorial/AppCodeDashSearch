package de.dreamlab.dash;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.util.IconLoader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.*;

abstract public class AbstractMenuAction extends AnAction {
    protected @Nonnull String title = "";
    protected @Nonnull String description = "";
    protected @Nullable String iconFilename = null;


    private boolean isPresentationInitialized = false;

    private void initPresentation()
    {
        String docAppName = "";

        if ( SystemUtil.isIsOSMac() ) {
            docAppName = " in Dash";
        }

        Presentation presentation = this.getTemplatePresentation();
        presentation.setText(title + docAppName);
        presentation.setDescription(description + docAppName);

        if ( iconFilename != null ) {
            Icon icon = IconLoader.getIcon("/" + iconFilename, AbstractMenuAction.class);
            presentation.setIcon(icon);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);

        Presentation presentation = e.getPresentation();

        if ( !isPresentationInitialized ) {
            isPresentationInitialized = true;
            initPresentation();

            Presentation templatePresentation = getTemplatePresentation();
            presentation.setText(templatePresentation.getText());
            presentation.setDescription(templatePresentation.getDescription());
            presentation.setIcon(templatePresentation.getIcon());
        }

        presentation.setEnabled(PlatformDataKeys.EDITOR.getData(e.getDataContext()) != null);
    }
}
