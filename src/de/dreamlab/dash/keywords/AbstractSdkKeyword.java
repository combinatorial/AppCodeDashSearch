package de.dreamlab.dash.keywords;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import de.dreamlab.dash.LookupInfoDictionary;

public abstract class AbstractSdkKeyword {

    protected Sdk getSdk(LookupInfoDictionary dict)
    {
        if ( !dict.isSdk() ) {
            dict.setSdk(findSdk(dict.getPsiElement(), dict.getProject(), dict.getPsiFile(), dict.getVirtualFile()));
        }

        return dict.getSdk();
    }

    public static Sdk findSdk(final PsiElement psiElement, final Project project, final PsiFile psiFile, final VirtualFile virtualFile)
    {
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
}
