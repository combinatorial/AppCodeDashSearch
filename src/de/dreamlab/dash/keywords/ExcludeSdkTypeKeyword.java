package de.dreamlab.dash.keywords;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

public class ExcludeSdkTypeKeyword implements IKeyword {
    private String keyword;
    private String sdkType;

    public ExcludeSdkTypeKeyword(String keyword, String sdkType) {
        this.keyword = keyword;
        this.sdkType = sdkType;
    }

    public String getName(Sdk sdk, final Project project, final PsiFile psiFile, final VirtualFile virtualFile) {
        if ( sdkType == null || !sdkType.equals(sdk.getSdkType().getName()) ) {
            return keyword;
        }
        else {
            return null;
        }
    }
}
