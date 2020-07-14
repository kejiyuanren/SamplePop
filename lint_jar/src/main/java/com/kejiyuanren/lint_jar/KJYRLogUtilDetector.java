package com.kejiyuanren.lint_jar;

import com.android.tools.lint.client.api.JavaEvaluator;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.SourceCodeScanner;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiMethodCallExpression;

import java.util.Arrays;
import java.util.List;

public class KJYRLogUtilDetector extends Detector implements SourceCodeScanner {
    public static final Issue ISSUE = Issue.create(
            "LogUtilUse",
            "避免使用Log/System.out.println",
            "使用LogUtil，LogUtil对系统的Log类进行了展示格式、逻辑等封装",
            Category.SECURITY, 5, Severity.WARNING,
            new Implementation(KJYRLogUtilDetector.class, Scope.JAVA_FILE_SCOPE));

    public List<String> getApplicableMethodNames() {
        return Arrays.asList("d", "e", "i", "v", "w");
    }

    public void visitMethod(JavaContext context, JavaElementVisitor visitor, PsiMethodCallExpression node, PsiMethod method) {
        JavaEvaluator evaluator = context.getEvaluator();
        if (evaluator.isMemberInClass(method, "android.util.Log")) {
            String message = "请使用LogUtil";
            context.report(ISSUE, node, context.getLocation(node), message);
        }
    }
}