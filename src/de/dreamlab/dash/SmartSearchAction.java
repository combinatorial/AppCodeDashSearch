package de.dreamlab.dash;


import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

public class SmartSearchAction extends AbstractMenuAction {

    public SmartSearchAction()
    {
        title = "Smart-Search Documentation";
        description = "Searches word under caret or selection in documentation filtered by currently used language";
        iconFilename = "dash.png";
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
