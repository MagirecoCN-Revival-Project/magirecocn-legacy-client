package cn.thinkingdata.core.network;

/* loaded from: classes.dex */
public interface Call {

    /* loaded from: classes.dex */
    public interface Factory {
        Call newCall(Request request);
    }

    void enqueue(TEHttpCallback tEHttpCallback);

    String execute();
}
