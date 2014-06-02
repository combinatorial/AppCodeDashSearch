package de.dreamlab.dash;


import com.intellij.lang.Language;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.impl.status.StatusBarUtil;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class DashLauncherAction extends AnAction {
    private static final String XML_LANGUAGE_ID = "XML";

    private KeywordLookup keywordLookup;
    private DashLauncher dashLauncher;

    public DashLauncherAction()
    {
        keywordLookup = new KeywordLookup();
        dashLauncher = new DashLauncher();
    }

    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(PlatformDataKeys.EDITOR.getData(e.getDataContext()) != null);
    }

    public void actionPerformed(AnActionEvent e) {
        final Editor editor = PlatformDataKeys.EDITOR.getData(e.getDataContext());
        if ( editor == null ) {
            return;
        }

        final PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
        PsiElement psiElement = null;
        Language language = null;

        if ( psiFile != null ) {
            psiElement = psiFile.findElementAt(editor.getCaretModel().getOffset());
            language = elementLanguage(psiElement);
        }

        String query;

        SelectionModel selectionModel = editor.getSelectionModel();
        if ( selectionModel.hasSelection() ) {
            query = selectionModel.getSelectedText();
        }
        else {
            if ( psiElement == null || psiElement instanceof PsiComment ) {
                query = getWordAtCursor(editor);
            }
            else {
                query = psiElement.getText();
            }
        }

        if ( query != null ) {
            // show status message for potential troubleshooting
            String resolvedLanguage = keywordLookup.findLanguageName(language);

            final StringBuilder messageBuilder = new StringBuilder();

            if ( resolvedLanguage == null ) {
                messageBuilder.append("Searching all docsets in Dash");
            }
            else {
                messageBuilder.append(String.format("Searching \"%s\" docsets in Dash", resolvedLanguage));
            }

            if ( language != null && !language.getID().equals(resolvedLanguage) ) {
                messageBuilder.append(String.format(" Based on \"%s\" context.", language.getID()));
            }

            Project project = e.getProject();

            if ( project != null ) {
                StatusBarUtil.setStatusBarInfo(project, messageBuilder.toString());
            }

            // open dash
            dashLauncher.search(keywordLookup.findKeywords(language, getSdk(e, psiElement)), query);

            /*
            use the following command to display information about the sdk in use in the event log. intended for development purposes.
            showSdkDebug(getSdk(e, psiElement));
            */
        }
    }

    private Sdk getSdk(AnActionEvent actionEvent, PsiElement psiElement)
    {
        final PsiFile psiFile = actionEvent.getData(LangDataKeys.PSI_FILE);
        final Project project = actionEvent.getProject();
        Module module = null;

        if ( psiFile != null ) {
            module = ModuleUtil.findModuleForPsiElement(psiFile);

            // Get the module associated with the PsiElement, if one is present.
            if (module == null && psiElement != null) {
                module = ModuleUtil.findModuleForPsiElement(psiElement);
            }
        }

        // Get the module associated with the VIRTUAL_FILE, if one is present.
        if ( module == null ) {
            final VirtualFile virtualFile = actionEvent.getData(LangDataKeys.VIRTUAL_FILE);

            if ( virtualFile != null && project != null ) {
                module = ModuleUtil.findModuleForFile(virtualFile, project);
            }
        }

        // Get the SDK associated with the previously found module, or use the project-wide SDK if no module has been found.
        if ( module != null ) {
            return ModuleRootManager.getInstance(module).getSdk();
        }
        else if ( project != null) {
            return ProjectRootManager.getInstance(project).getProjectSdk();
        }
        else {
            return null;
        }
    }

    private void showSdkDebug(Sdk sdk)
    {
        StringBuilder sdkMessage = new StringBuilder();

        if ( sdk != null ) {
            sdkMessage.append(String.format("Name: %s\n", sdk.getName()));
            sdkMessage.append(String.format("SdkType: %s\n", sdk.getSdkType().getName()));
            sdkMessage.append(String.format("VersionString: %s\n", sdk.getVersionString()));

        }
        else {
            sdkMessage.append("not available");
        }

        Notifications.Bus.notify(new Notification("Dash", "Dash SDK: ", sdkMessage.toString(), NotificationType.INFORMATION));
    }

    private Language elementLanguage(PsiElement element)
    {
        if ( element == null ) {
            return null;
        }

        try {
            if ( XML_LANGUAGE_ID.equals(element.getLanguage().getID()) ) {
                PsiElement parent = element.getParent();

                if ( !XML_LANGUAGE_ID.equals(parent.getLanguage().getID()) && XML_LANGUAGE_ID.equals(parent.getLanguage().getBaseLanguage().getID()) ) {
                    return parent.getLanguage();
                }
            }

            return element.getLanguage();
        }
        catch ( NullPointerException e ) {
            return null;
        }
    }

    private String getWordAtCursor(Editor editor) {
        CharSequence editorText = editor.getDocument().getCharsSequence();
        int cursorOffset = editor.getCaretModel().getOffset();
        int editorTextLength = editorText.length();

        if ( editorTextLength == 0 ) {
            return null;
        }

        if ( (cursorOffset >= editorTextLength) || (cursorOffset > 1 && !isIdentifierPart(editorText.charAt(cursorOffset) ) && isIdentifierPart(editorText.charAt(cursorOffset - 1))) ) {
            cursorOffset--;
        }

        if ( isIdentifierPart(editorText.charAt(cursorOffset)) ) {
            int start = cursorOffset;
            int end = cursorOffset;

            while ( start > 0 && isIdentifierPart(editorText.charAt(start-1)) ) {
                start--;
            }

            while ( end < editorTextLength && isIdentifierPart(editorText.charAt(end)) ) {
                end++;
            }

            return editorText.subSequence(start, end).toString();
        }
        return null;
    }

    private boolean isIdentifierPart(char ch) {
        return Character.isJavaIdentifierPart(ch);
    }
}
