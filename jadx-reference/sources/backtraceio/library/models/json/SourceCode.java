package backtraceio.library.models.json;

import backtraceio.library.models.BacktraceStackFrame;
import com.google.gson.annotations.SerializedName;
import cz.msebera.android.httpclient.cookie.ClientCookie;

/* loaded from: classes.dex */
public class SourceCode {

    @SerializedName(ClientCookie.PATH_ATTR)
    public String sourceCodeFileName;

    @SerializedName("startLine")
    public Integer startLine;

    public SourceCode(BacktraceStackFrame stackFrame) {
        this.sourceCodeFileName = stackFrame.sourceCodeFileName;
        this.startLine = stackFrame.line;
    }
}
