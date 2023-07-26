package com.reason.plugin.common.utils

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.searches.AllClassesSearch

/**
 * @author impassive
 */
object PsiUtils {

    /**
     * 根据类 的完全限定名 查找 项目内的 类信息（不包括 依赖包）
     */
    fun findPisClassByName(
        fullyQualifiedName: String,
        project: Project,
        withLibrary: Boolean = false
    ): PsiClass? {
        val projectScope = if (withLibrary) GlobalSearchScope.allScope(project)
        else GlobalSearchScope.projectScope(project)

        val search = AllClassesSearch.search(projectScope, project)
        return search.first {
            val name = it.qualifiedName
            if (name == fullyQualifiedName) {
                // 获取类上的注解
                it.annotations[0].findDeclaredAttributeValue("value")?.text

                // 获取每一个字段的注解
                it.allFields[0].name
                // 获取对应注解的信息
                it.allFields[0].annotations[0].findDeclaredAttributeValue("message")?.text
            }
            name == fullyQualifiedName
        }
    }


}