package backtraceio.library.services;

import backtraceio.library.events.OnServerErrorEventListener;

/* loaded from: classes.dex */
public abstract class BacktraceHandlerInput {
    public OnServerErrorEventListener serverErrorEventListener;

    /* JADX INFO: Access modifiers changed from: protected */
    public BacktraceHandlerInput(OnServerErrorEventListener serverErrorEventListener) {
        this.serverErrorEventListener = serverErrorEventListener;
    }
}
