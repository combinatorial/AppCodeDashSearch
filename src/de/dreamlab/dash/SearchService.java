package de.dreamlab.dash;

import com.intellij.lang.Language;
import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.impl.status.StatusBarUtil;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.dreamlab.dash.launcher.AbstractLauncher;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class SearchService {
    // singleton
    private static SearchService ourInstance = new SearchService();

    public static SearchService getInstance()
    {
        return ourInstance;
    }

    // class instance
    private static final String XML_LANGUAGE_ID = "XML";
    private KeywordLookup keywordLookup;
    private AbstractLauncher launcher;


    private SearchService() {
        keywordLookup = new KeywordLookup();
        launcher = AbstractLauncher.createInstance();
    }

    private @Nullable PsiElement getPsiElementAtCursor(@Nonnull Editor editor, @Nullable Project project, @Nullable PsiFile psiFile)
    {
        if ( psiFile == null ) {
            return null;
        }

        int caretOffset = editor.getCaretModel().getOffset();

        if ( project != null ) {
            InjectedLanguageManager injectedLanguageManager = InjectedLanguageManager.getInstance(project);

            if ( injectedLanguageManager != null ) {
                PsiElement injectedPsiElement = injectedLanguageManager.findInjectedElementAt(psiFile, caretOffset);

                if ( injectedPsiElement != null ) {
                    return injectedPsiElement;
                }
            }
        }

        return psiFile.findElementAt(editor.getCaretModel().getOffset());
    }

    private @Nullable String getSearchQueryFromEditor(@Nonnull Editor editor, @Nullable PsiElement psiElement)
    {
        SelectionModel selectionModel = editor.getSelectionModel();
        if ( selectionModel.hasSelection() ) {
            return selectionModel.getSelectedText();
        }

        if ( psiElement == null || psiElement instanceof PsiComment ) {
            return getWordAtCursor(editor);
        }

        return psiElement.getText();
    }

    public void search(@Nonnull Editor editor, @Nullable Project project, @Nullable PsiFile psiFile)
    {
        PsiElement psiElement = getPsiElementAtCursor(editor, project, psiFile);
        String query = getSearchQueryFromEditor(editor, psiElement);

        if ( query == null ) {
            return;
        }

        // open dash
        showStatusMessage(project, null, null);
        launcher.search(new ArrayList<>(), query);
    }

    public void smartSearch(@Nonnull Editor editor, @Nullable Project project, @Nullable PsiFile psiFile, @Nullable VirtualFile virtualFile)
    {
        PsiElement psiElement = getPsiElementAtCursor(editor, project, psiFile);
        Language language = elementLanguage(psiElement);
        String resolvedLanguage = keywordLookup.findLanguageName(language);

        if ( resolvedLanguage == null ) {
            psiElement = null;
        }

        String query = getSearchQueryFromEditor(editor, psiElement);

        if ( query == null ) {
            return;
        }

        // open dash
        showStatusMessage(project, resolvedLanguage, language);
        launcher.search(keywordLookup.findKeywords(new LookupInfoDictionary(language, psiElement, project, psiFile, virtualFile)), query);

        /*
        use the following command to display information about the sdk in use in the event log. intended for development purposes.
        showSdkDebug(AbstractSdkKeyword.findSdk(psiElement, project, psiFile, virtualFile));
        */
    }

    private void showStatusMessage(final @Nullable Project project, final @Nullable String resolvedLanguage, final @Nullable Language language)
    {
        if ( project == null ) {
            return;
        }

        // show status message for potential troubleshooting
        final StringBuilder messageBuilder = new StringBuilder();

        if ( resolvedLanguage == null ) {
            messageBuilder.append("Searching all documentation");
        }
        else {
            messageBuilder.append(String.format("Smart-Searching \"%s\" documentation", resolvedLanguage));
        }

        if ( language != null && !language.getID().equals(resolvedLanguage) ) {
            messageBuilder.append(String.format(". Based on \"%s\" context.", language.getID()));
        }

        StatusBarUtil.setStatusBarInfo(project, messageBuilder.toString());
    }

    private void showSdkDebug(@Nullable Sdk sdk)
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

    private @Nullable Language elementLanguage(final @Nullable PsiElement element)
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

    private @Nullable String getWordAtCursor(final @Nonnull Editor editor)
    {
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

    private boolean isIdentifierPart(final char ch)
    {
        return Character.isJavaIdentifierPart(ch);
    }
}
