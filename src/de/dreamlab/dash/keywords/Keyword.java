package de.dreamlab.dash.keywords;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

public class Keyword implements IKeyword {
    private String keyword;

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName(Sdk sdk, final Project project, final PsiFile psiFile, final VirtualFile virtualFile) {
        return keyword;
    }
}
