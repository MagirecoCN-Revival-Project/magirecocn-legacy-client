package backtraceio.library.events;

import backtraceio.library.models.BacktraceData;

/* loaded from: classes.dex */
public interface OnBeforeSendEventListener {
    BacktraceData onEvent(BacktraceData data);
}
