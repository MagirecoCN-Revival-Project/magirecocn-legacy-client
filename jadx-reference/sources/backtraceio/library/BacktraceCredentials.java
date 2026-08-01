package backtraceio.library;

import android.net.Uri;
import cn.thinkingdata.core.router.TRouterMap;

/* loaded from: classes.dex */
public class BacktraceCredentials {
    private Uri backtraceHostUri;
    private String endpointUrl;
    private final String format;
    private String submissionToken;

    public BacktraceCredentials(String endpointUrl, String submissionToken) {
        this.format = "json";
        this.endpointUrl = endpointUrl;
        this.submissionToken = submissionToken;
    }

    public BacktraceCredentials(String backtraceHostUri) {
        this(Uri.parse(backtraceHostUri));
    }

    public BacktraceCredentials(Uri backtraceHostUri) {
        this.format = "json";
        this.backtraceHostUri = backtraceHostUri;
    }

    private String getEndpointUrl() {
        return this.endpointUrl;
    }

    private Uri getBacktraceHostUri() {
        return this.backtraceHostUri;
    }

    private Uri getServerUrl() {
        String endpointUrl = getEndpointUrl();
        return Uri.parse(String.format("%s%spost?format=%s&token=%s", endpointUrl, endpointUrl.endsWith("/") ? "" : "/", "json", getSubmissionToken()));
    }

    public Uri getSubmissionUrl() {
        Uri backtraceHostUri = getBacktraceHostUri();
        return backtraceHostUri != null ? backtraceHostUri : getServerUrl();
    }

    public Uri getMinidumpSubmissionUrl() {
        String replace;
        String uri = getSubmissionUrl().toString();
        if (uri.contains("format=json")) {
            replace = uri.replace("format=json", "format=minidump");
        } else {
            if (!uri.contains("/json")) {
                return null;
            }
            replace = uri.replace("/json", "/minidump");
        }
        return Uri.parse(replace);
    }

    public String getUniverseName() {
        String uri = getSubmissionUrl().toString();
        if (uri.startsWith("https://submit.backtrace.io/")) {
            int indexOf = uri.indexOf(47, 28);
            if (indexOf == -1) {
                throw new IllegalArgumentException("Invalid Backtrace URL");
            }
            return uri.substring(28, indexOf);
        }
        if (uri.indexOf("backtrace.io") == -1) {
            throw new IllegalArgumentException("Invalid Backtrace URL");
        }
        Uri parse = Uri.parse(uri);
        return parse.getHost().substring(0, parse.getHost().indexOf(TRouterMap.DOT));
    }

    public String getSubmissionToken() {
        String str = this.submissionToken;
        if (str != null) {
            return str;
        }
        String uri = getSubmissionUrl().toString();
        if (uri.contains("submit.backtrace.io")) {
            return uri.substring((uri.lastIndexOf("/") - 64) + 1, uri.lastIndexOf("/"));
        }
        return uri.substring(uri.indexOf("token=") + 6, ((uri.indexOf("token=") + 6) + 64) - 1);
    }
}
