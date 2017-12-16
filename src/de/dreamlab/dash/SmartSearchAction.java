package de.dreamlab.dash;


import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import javax.swing.*;

public class SmartSearchAction extends AnAction {
    private boolean isPresentationInitialized = false;

    public SmartSearchAction()
    {
    }

    private void initPresentation()
    {
        String docAppName = "";

        if ( SystemUtil.isIsOSMac() ) {
            docAppName = " in Dash";
        }

        Presentation presentation = this.getTemplatePresentation();
        presentation.setText("Search Documentation" + docAppName);
        presentation.setDescription("Searches documentation for word under caret or selected text" + docAppName);

        Icon icon = IconLoader.getIcon("/dash.png", SmartSearchAction.class);
        presentation.setIcon(icon);
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

    public void actionPerformed(AnActionEvent e) {
        final Editor editor = PlatformDataKeys.EDITOR.getData(e.getDataContext());
        if ( editor == null ) {
            return;
        }

        final Project project = e.getProject();
        final PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        final VirtualFile virtualFile = e.getData(LangDataKeys.VIRTUAL_FILE);

        SearchService.getInstance().smartSearch(editor, project, psiFile, virtualFile);
    }
}
