package com.kejiyuanren.lint_jar;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

public class KJYRIssueRegistry extends IssueRegistry {
    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(
                KJYRLogUtilDetector.ISSUE
//                HandlerDetector.ISSUE
        );
    }
}
