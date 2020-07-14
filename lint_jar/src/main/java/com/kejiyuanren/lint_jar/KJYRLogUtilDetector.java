package com.kejiyuanren.lint_jar;

import com.android.tools.lint.client.api.JavaEvaluator;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiMethod;

import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

public class KJYRLogUtilDetector extends Detector implements Detector.UastScanner {
    public static final Issue ISSUE = Issue.create(
            "KJYRLogUtilCase",
            "科技猿人-避免使用Log",
            "使用LogUtil，LogUtil对系统的Log类进行了逻辑封装",
            Category.SECURITY, 5, Severity.WARNING,
            new Implementation(KJYRLogUtilDetector.class, Scope.JAVA_FILE_SCOPE));

    public List<String> getApplicableMethodNames() {
        return Arrays.asList("d", "e", "i", "v", "w");
    }

    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        JavaEvaluator evaluator = context.getEvaluator();
        if (evaluator.isMemberInClass(method, "android.util.Log")) {
            String message = "kejiyuanren：请使用LogUtil封装类";
            context.report(ISSUE, node, context.getLocation(node), message);
        }
    }
}