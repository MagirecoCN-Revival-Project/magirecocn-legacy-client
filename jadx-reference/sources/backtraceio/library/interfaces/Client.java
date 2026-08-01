package backtraceio.library.interfaces;

import backtraceio.library.models.json.BacktraceReport;

/* loaded from: classes.dex */
public interface Client {
    void enableNativeIntegration();

    void send(BacktraceReport report);
}
