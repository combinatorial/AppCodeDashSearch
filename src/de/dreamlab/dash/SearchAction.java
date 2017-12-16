package de.dreamlab.dash;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;


public class SearchAction extends AbstractMenuAction {
    public SearchAction()
    {
        title = "Search all Documentation";
        description = "Searches word under caret or selection in documentation";
    }

    public void actionPerformed(AnActionEvent e) {
        final Editor editor = PlatformDataKeys.EDITOR.getData(e.getDataContext());
        if ( editor == null ) {
            return;
        }

        final Project project = e.getProject();
        final PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);

        SearchService.getInstance().search(editor, project, psiFile);
    }
}
