package backtraceio.library.models.database;

import android.content.Context;
import backtraceio.library.common.BacktraceSerializeHelper;
import backtraceio.library.common.BacktraceStringHelper;
import backtraceio.library.common.FileHelper;
import backtraceio.library.interfaces.DatabaseRecordWriter;
import backtraceio.library.logger.BacktraceLogger;
import backtraceio.library.models.BacktraceData;
import backtraceio.library.models.json.BacktraceReport;
import com.google.gson.annotations.SerializedName;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/* loaded from: classes.dex */
public class BacktraceDatabaseRecord {
    private static final transient String LOG_TAG = "BacktraceDatabaseRecord";
    transient DatabaseRecordWriter RecordWriter;
    private final transient String _path;

    @SerializedName("DataPath")
    private String diagnosticDataPath;

    @SerializedName("Id")
    public UUID id;
    public transient boolean locked;
    private transient BacktraceData record;

    @SerializedName("RecordName")
    private String recordPath;

    @SerializedName("ReportPath")
    private String reportPath;

    @SerializedName("Size")
    private long size;

    BacktraceDatabaseRecord() {
        UUID randomUUID = UUID.randomUUID();
        this.id = randomUUID;
        this.locked = false;
        this._path = "";
        this.recordPath = String.format("%s-record.json", randomUUID);
        this.diagnosticDataPath = String.format("%s-attachment", this.id);
        this.recordPath = String.format("%s-record.json", this.id);
    }

    public BacktraceDatabaseRecord(BacktraceData data, String path) {
        this.id = UUID.randomUUID();
        this.locked = false;
        this.id = UUID.fromString(data.uuid);
        this.record = data;
        this._path = path;
        this.RecordWriter = new BacktraceDatabaseRecordWriter(path);
    }

    public static BacktraceDatabaseRecord readFromFile(File file) {
        String str = LOG_TAG;
        BacktraceLogger.d(str, "Reading JSON from passed file");
        String readFile = FileHelper.readFile(file);
        if (BacktraceStringHelper.isNullOrEmpty(readFile)) {
            BacktraceLogger.w(str, "JSON from passed file is null or empty");
            return null;
        }
        return (BacktraceDatabaseRecord) BacktraceSerializeHelper.fromJson(readFile, BacktraceDatabaseRecord.class);
    }

    public String getRecordPath() {
        return this.recordPath;
    }

    public String getDiagnosticDataPath() {
        return this.diagnosticDataPath;
    }

    public String getReportPath() {
        return this.reportPath;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public BacktraceData getBacktraceData(Context context) {
        BacktraceData backtraceData = this.record;
        if (backtraceData != null) {
            return backtraceData;
        }
        if (!valid()) {
            BacktraceLogger.w(LOG_TAG, "Database record is invalid");
            return null;
        }
        String readFile = FileHelper.readFile(new File(this.diagnosticDataPath));
        String readFile2 = FileHelper.readFile(new File(this.reportPath));
        try {
            BacktraceLogger.d(LOG_TAG, "Deserialization diagnostic data");
            BacktraceData backtraceData2 = (BacktraceData) BacktraceSerializeHelper.fromJson(readFile, BacktraceData.class);
            backtraceData2.report = (BacktraceReport) BacktraceSerializeHelper.fromJson(readFile2, BacktraceReport.class);
            backtraceData2.context = context;
            return backtraceData2;
        } catch (Exception e) {
            BacktraceLogger.e(LOG_TAG, "Exception occurs on deserialization of diagnostic data", e);
            return null;
        }
    }

    public boolean save() {
        try {
            String str = LOG_TAG;
            BacktraceLogger.d(str, "Trying saving data to internal app storage");
            this.diagnosticDataPath = save(this.record, String.format("%s-attachment", this.id));
            this.reportPath = save(this.record.report, String.format("%s-report", this.id));
            this.recordPath = new File(this._path, String.format("%s-record.json", this.id)).getAbsolutePath();
            this.size += BacktraceSerializeHelper.toJson(this).getBytes(StandardCharsets.UTF_8).length;
            this.RecordWriter.write(this, String.format("%s-record", this.id));
            BacktraceLogger.d(str, "Saving data to internal app storage successful");
            return true;
        } catch (Exception e) {
            BacktraceLogger.e(LOG_TAG, "Received IOException while saving data to database", e);
            return false;
        }
    }

    private String save(Object data, String prefix) {
        try {
            if (data == null) {
                BacktraceLogger.w(LOG_TAG, "Passed data parameter is null");
                return "";
            }
            byte[] bytes = BacktraceSerializeHelper.toJson(data).getBytes(StandardCharsets.UTF_8);
            this.size += bytes.length;
            return this.RecordWriter.write(bytes, prefix);
        } catch (Exception e) {
            BacktraceLogger.e(LOG_TAG, "Received IOException while saving data to database", e);
            return "";
        }
    }

    public boolean valid() {
        return FileHelper.isFileExists(this.diagnosticDataPath) && FileHelper.isFileExists(this.reportPath);
    }

    public void delete() {
        BacktraceLogger.d(LOG_TAG, "Trying delete files from database");
        delete(this.reportPath);
        delete(this.diagnosticDataPath);
        delete(this.recordPath);
    }

    private void delete(String path) {
        try {
            if (FileHelper.isFileExists(path)) {
                BacktraceLogger.d(LOG_TAG, "Passed path exist, trying delete file on database record");
                new File(path).delete();
            }
        } catch (Exception e) {
            BacktraceLogger.e(LOG_TAG, String.format("Cannot delete file: %s", path), e);
        }
    }

    public boolean close() {
        String str = LOG_TAG;
        BacktraceLogger.d(str, "Trying unlock database record");
        try {
            this.locked = false;
            this.record = null;
            BacktraceLogger.d(str, "Record unlocked");
            return true;
        } catch (Exception unused) {
            BacktraceLogger.e(LOG_TAG, "Can not unlock record");
            return false;
        }
    }
}
