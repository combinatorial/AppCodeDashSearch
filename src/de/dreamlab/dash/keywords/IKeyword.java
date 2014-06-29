package de.dreamlab.dash.keywords;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.sun.istack.internal.Nullable;

public interface IKeyword {
    @Nullable
    public String getName(Sdk sdk, final Project project, final PsiFile psiFile, final VirtualFile virtualFile);
}
