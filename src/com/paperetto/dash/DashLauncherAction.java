package com.paperetto.dash;


import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.util.ExecUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.vfs.VirtualFile;
import de.dreamlab.dash.KeywordLookup;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DashLauncherAction extends AnAction {

	private final static String RUBY_FILE_IDENTIFIER = "Ruby";

    private KeywordLookup keywordLookup;
    private String fileType;

    public DashLauncherAction()
    {
        keywordLookup = new KeywordLookup();
    }


    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabled(PlatformDataKeys.EDITOR.getData(e.getDataContext()) != null);
    }



    private String getWordAtCursor(CharSequence editorText, int cursorOffset) {
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

    public void actionPerformed(AnActionEvent e) {
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
		if (virtualFile != null) {
			fileType = keywordLookup.cleanType(virtualFile.getFileType().getName());
		}

        Editor editor = PlatformDataKeys.EDITOR.getData(e.getDataContext());

        //Editor editor = DataKeys.EDITOR.getData(e.getDataContext());
        int offset = editor.getCaretModel().getOffset();
        CharSequence editorText = editor.getDocument().getCharsSequence();

        String word = null;

        SelectionModel selectionModel = editor.getSelectionModel();
        if(selectionModel.hasSelection())
        {
            word = selectionModel.getSelectedText();
        }
        else
        {
            word = getWordAtCursor(editorText,offset);
        }

        if(word!=null) {
            // keyword
            String keyword = null;

            if (virtualFile != null) {
                keyword = keywordLookup.findKeyword(virtualFile.getFileType().getName(), virtualFile.getExtension());
            }

            // search word
            String searchWord;
            try {
                searchWord = URLEncoder.encode(word, "UTF-8");
            } catch (UnsupportedEncodingException el){
                ///where do I print an error
                return;
            }
            //URLEncoder turns spaces in to '+' we need them to be %20
            searchWord = searchWord.replace("+", "%20");

            String request = "dash://";

            if ( keyword != null ) {
                request += keyword + ":";
            }

            request += searchWord;

            //now open the URL with the 'open' command
            String[] command = new String[]{ExecUtil.getOpenCommandPath()};
            try {
                final GeneralCommandLine commandLine = new GeneralCommandLine(command);
                commandLine.addParameter(request);
                commandLine.createProcess();

            }
            catch (ExecutionException ee) {
                ///where do I print an error
                return;
            }
        }
    }

    private boolean isIdentifierPart(char ch) {
		return Character.isJavaIdentifierPart(ch) ||
				(RUBY_FILE_IDENTIFIER.equalsIgnoreCase(fileType) && ch == '?');
    }
}
