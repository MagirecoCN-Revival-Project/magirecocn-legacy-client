package com.loopj.android.http;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes.dex */
public abstract class SaxAsyncHttpResponseHandler<T extends DefaultHandler> extends AsyncHttpResponseHandler {
    private static final String LOG_TAG = "SaxAsyncHttpRH";
    private T handler;

    public abstract void onFailure(int i, Header[] headerArr, T t);

    public abstract void onSuccess(int i, Header[] headerArr, T t);

    public SaxAsyncHttpResponseHandler(T t) {
        this.handler = null;
        if (t == null) {
            throw new Error("null instance of <T extends DefaultHandler> passed to constructor");
        }
        this.handler = t;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0046, code lost:
    
        if (r4 == null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0053, code lost:
    
        if (r4 == null) goto L35;
     */
    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected byte[] getResponseData(HttpEntity httpEntity) throws IOException {
        InputStreamReader inputStreamReader;
        InputStreamReader inputStreamReader2 = null;
        if (httpEntity != null) {
            InputStream content = httpEntity.getContent();
            try {
                if (content != null) {
                    try {
                        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                        xMLReader.setContentHandler(this.handler);
                        inputStreamReader = new InputStreamReader(content, getCharset());
                        try {
                            xMLReader.parse(new InputSource(inputStreamReader));
                            AsyncHttpClient.silentCloseInputStream(content);
                        } catch (ParserConfigurationException e) {
                            e = e;
                            AsyncHttpClient.log.e(LOG_TAG, "getResponseData exception", e);
                            AsyncHttpClient.silentCloseInputStream(content);
                        } catch (SAXException e2) {
                            e = e2;
                            AsyncHttpClient.log.e(LOG_TAG, "getResponseData exception", e);
                            AsyncHttpClient.silentCloseInputStream(content);
                        }
                    } catch (ParserConfigurationException e3) {
                        e = e3;
                        inputStreamReader = null;
                    } catch (SAXException e4) {
                        e = e4;
                        inputStreamReader = null;
                    } catch (Throwable th) {
                        th = th;
                        AsyncHttpClient.silentCloseInputStream(content);
                        if (inputStreamReader2 != null) {
                            try {
                                inputStreamReader2.close();
                            } catch (IOException unused) {
                            }
                        }
                        throw th;
                    }
                    try {
                        inputStreamReader.close();
                    } catch (IOException unused2) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                inputStreamReader2 = inputStreamReader;
            }
        }
        return null;
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onSuccess(int i, Header[] headerArr, byte[] bArr) {
        onSuccess(i, headerArr, (Header[]) this.handler);
    }

    @Override // com.loopj.android.http.AsyncHttpResponseHandler
    public void onFailure(int i, Header[] headerArr, byte[] bArr, Throwable th) {
        onFailure(i, headerArr, this.handler);
    }
}
