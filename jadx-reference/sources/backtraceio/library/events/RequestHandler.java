package backtraceio.library.events;

import backtraceio.library.models.BacktraceData;
import backtraceio.library.models.BacktraceResult;

/* loaded from: classes.dex */
public interface RequestHandler {
    BacktraceResult onRequest(BacktraceData data);
}
