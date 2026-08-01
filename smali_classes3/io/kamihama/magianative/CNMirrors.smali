.class public final Lio/kamihama/magianative/CNMirrors;
.super Ljava/lang/Object;
.source "CNMirrors.java"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lio/kamihama/magianative/CNMirrors$Mirror;
    }
.end annotation


# static fields
.field private static final CONNECT_TIMEOUT_MS:I = 0x3a98

.field public static final DEFAULT_BASE:Ljava/lang/String; = "https://assets.magireco.top/"

.field private static final MAX_JSON_BYTES:I = 0x40000

.field public static final MIRRORS_URL:Ljava/lang/String; = "https://assets.magireco.top/mirrors.json"

.field private static final READ_TIMEOUT_MS:I = 0x7530

.field private static final TAG:Ljava/lang/String; = "MagiaCNMirrors"

.field private static volatile cfgChunks:I

.field private static volatile cfgCooldownMs:J

.field private static volatile cfgMinChunkBytes:J

.field private static volatile cfgMinSpeedKbps:I

.field private static volatile cfgStallSeconds:I

.field private static volatile cfgSwitchAfterFail:I

.field private static volatile loaded:Z

.field private static volatile mirrors:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lio/kamihama/magianative/CNMirrors$Mirror;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method static constructor <clinit>()V
    .locals 2

    .line 49
    const/4 v0, 0x4

    sput v0, Lio/kamihama/magianative/CNMirrors;->cfgChunks:I

    .line 50
    const-wide/32 v0, 0x800000

    sput-wide v0, Lio/kamihama/magianative/CNMirrors;->cfgMinChunkBytes:J

    .line 51
    const/4 v0, 0x1

    sput v0, Lio/kamihama/magianative/CNMirrors;->cfgSwitchAfterFail:I

    .line 52
    const/16 v0, 0x19

    sput v0, Lio/kamihama/magianative/CNMirrors;->cfgStallSeconds:I

    .line 53
    const/16 v0, 0x20

    sput v0, Lio/kamihama/magianative/CNMirrors;->cfgMinSpeedKbps:I

    .line 54
    const-wide/32 v0, 0xea60

    sput-wide v0, Lio/kamihama/magianative/CNMirrors;->cfgCooldownMs:J

    .line 100
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->defaultList()Ljava/util/List;

    move-result-object v0

    sput-object v0, Lio/kamihama/magianative/CNMirrors;->mirrors:Ljava/util/List;

    .line 101
    const/4 v0, 0x0

    sput-boolean v0, Lio/kamihama/magianative/CNMirrors;->loaded:Z

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .line 103
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method static synthetic access$000()I
    .locals 1

    .line 33
    sget v0, Lio/kamihama/magianative/CNMirrors;->cfgChunks:I

    return v0
.end method

.method public static chunks()I
    .locals 1

    .line 56
    sget v0, Lio/kamihama/magianative/CNMirrors;->cfgChunks:I

    return v0
.end method

.method private static clampInt(III)I
    .locals 0

    .line 228
    if-ge p0, p1, :cond_0

    move p0, p1

    goto :goto_0

    :cond_0
    if-le p0, p2, :cond_1

    move p0, p2

    :cond_1
    :goto_0
    return p0
.end method

.method private static defaultList()Ljava/util/List;
    .locals 8
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lio/kamihama/magianative/CNMirrors$Mirror;",
            ">;"
        }
    .end annotation

    .line 106
    new-instance v0, Ljava/util/ArrayList;

    const/4 v1, 0x1

    invoke-direct {v0, v1}, Ljava/util/ArrayList;-><init>(I)V

    .line 107
    new-instance v1, Lio/kamihama/magianative/CNMirrors$Mirror;

    const-string v3, "\u9ed8\u8ba4\u7ebf\u8def"

    const-string v4, "https://assets.magireco.top/"

    const/16 v5, 0x64

    const/4 v6, 0x0

    const/4 v7, 0x1

    move-object v2, v1

    invoke-direct/range {v2 .. v7}, Lio/kamihama/magianative/CNMirrors$Mirror;-><init>(Ljava/lang/String;Ljava/lang/String;IIZ)V

    invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 108
    return-object v0
.end method

.method private static fetch(Ljava/lang/String;Z)Ljava/lang/String;
    .locals 6
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .line 138
    new-instance v0, Ljava/net/URL;

    invoke-direct {v0, p0}, Ljava/net/URL;-><init>(Ljava/lang/String;)V

    .line 140
    if-eqz p1, :cond_0

    sget-object p0, Ljava/net/Proxy;->NO_PROXY:Ljava/net/Proxy;

    invoke-virtual {v0, p0}, Ljava/net/URL;->openConnection(Ljava/net/Proxy;)Ljava/net/URLConnection;

    move-result-object p0

    goto :goto_0

    :cond_0
    invoke-virtual {v0}, Ljava/net/URL;->openConnection()Ljava/net/URLConnection;

    move-result-object p0

    :goto_0
    check-cast p0, Ljava/net/HttpURLConnection;

    .line 141
    const/16 p1, 0x3a98

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setConnectTimeout(I)V

    .line 142
    const/16 p1, 0x7530

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setReadTimeout(I)V

    .line 143
    const/4 p1, 0x0

    invoke-virtual {p0, p1}, Ljava/net/HttpURLConnection;->setUseCaches(Z)V

    .line 144
    const/4 v0, 0x1

    invoke-virtual {p0, v0}, Ljava/net/HttpURLConnection;->setInstanceFollowRedirects(Z)V

    .line 145
    const-string v0, "Accept"

    const-string v1, "application/json"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 146
    const-string v0, "Accept-Encoding"

    const-string v1, "identity"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 147
    const-string v0, "Connection"

    const-string v1, "close"

    invoke-virtual {p0, v0, v1}, Ljava/net/HttpURLConnection;->setRequestProperty(Ljava/lang/String;Ljava/lang/String;)V

    .line 148
    nop

    .line 150
    const/4 v0, 0x0

    :try_start_0
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->getResponseCode()I

    move-result v1

    .line 151
    const/16 v2, 0xc8

    if-lt v1, v2, :cond_4

    const/16 v2, 0x12c

    if-ge v1, v2, :cond_4

    .line 154
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->getInputStream()Ljava/io/InputStream;

    move-result-object v0

    .line 155
    new-instance v1, Ljava/io/ByteArrayOutputStream;

    invoke-direct {v1}, Ljava/io/ByteArrayOutputStream;-><init>()V

    .line 156
    const/16 v2, 0x2000

    new-array v2, v2, [B

    .line 157
    const/4 v3, 0x0

    .line 158
    :goto_1
    invoke-virtual {v0, v2}, Ljava/io/InputStream;->read([B)I

    move-result v4

    if-ltz v4, :cond_2

    .line 159
    add-int/2addr v3, v4

    .line 160
    const/high16 v5, 0x40000

    if-gt v3, v5, :cond_1

    .line 161
    invoke-virtual {v1, v2, p1, v4}, Ljava/io/ByteArrayOutputStream;->write([BII)V

    goto :goto_1

    .line 160
    :cond_1
    new-instance p1, Ljava/io/IOException;

    const-string v1, "mirrors.json \u8fc7\u5927"

    invoke-direct {p1, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p1

    .line 163
    :cond_2
    new-instance p1, Ljava/lang/String;

    invoke-virtual {v1}, Ljava/io/ByteArrayOutputStream;->toByteArray()[B

    move-result-object v1

    sget-object v2, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    invoke-direct {p1, v1, v2}, Ljava/lang/String;-><init>([BLjava/nio/charset/Charset;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 165
    if-eqz v0, :cond_3

    :try_start_1
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_1
    .catch Ljava/io/IOException; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_2

    :catch_0
    move-exception v0

    .line 166
    :cond_3
    :goto_2
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 163
    return-object p1

    .line 152
    :cond_4
    :try_start_2
    new-instance p1, Ljava/io/IOException;

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "mirrors.json HTTP "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-direct {p1, v1}, Ljava/io/IOException;-><init>(Ljava/lang/String;)V

    throw p1
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    .line 165
    :catchall_0
    move-exception p1

    if-eqz v0, :cond_5

    :try_start_3
    invoke-virtual {v0}, Ljava/io/InputStream;->close()V
    :try_end_3
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_1

    goto :goto_3

    :catch_1
    move-exception v0

    .line 166
    :cond_5
    :goto_3
    invoke-virtual {p0}, Ljava/net/HttpURLConnection;->disconnect()V

    .line 167
    throw p1
.end method

.method public static healthy()Ljava/util/List;
    .locals 9
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()",
            "Ljava/util/List<",
            "Lio/kamihama/magianative/CNMirrors$Mirror;",
            ">;"
        }
    .end annotation

    .line 236
    sget-object v0, Lio/kamihama/magianative/CNMirrors;->mirrors:Ljava/util/List;

    .line 237
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v1

    .line 238
    new-instance v3, Ljava/util/ArrayList;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v4

    invoke-direct {v3, v4}, Ljava/util/ArrayList;-><init>(I)V

    .line 239
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v4

    :goto_0
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    move-result v5

    if-eqz v5, :cond_2

    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v5

    check-cast v5, Lio/kamihama/magianative/CNMirrors$Mirror;

    .line 240
    iget-boolean v6, v5, Lio/kamihama/magianative/CNMirrors$Mirror;->enabled:Z

    if-nez v6, :cond_0

    goto :goto_0

    .line 241
    :cond_0
    iget-wide v6, v5, Lio/kamihama/magianative/CNMirrors$Mirror;->cooldownUntilNs:J

    cmp-long v8, v6, v1

    if-lez v8, :cond_1

    goto :goto_0

    .line 242
    :cond_1
    invoke-interface {v3, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 243
    goto :goto_0

    .line 244
    :cond_2
    invoke-interface {v3}, Ljava/util/List;->isEmpty()Z

    move-result v1

    if-nez v1, :cond_3

    return-object v3

    .line 245
    :cond_3
    new-instance v1, Ljava/util/ArrayList;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v2

    invoke-direct {v1, v2}, Ljava/util/ArrayList;-><init>(I)V

    .line 246
    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :cond_4
    :goto_1
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v2

    if-eqz v2, :cond_5

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lio/kamihama/magianative/CNMirrors$Mirror;

    iget-boolean v3, v2, Lio/kamihama/magianative/CNMirrors$Mirror;->enabled:Z

    if-eqz v3, :cond_4

    invoke-interface {v1, v2}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    goto :goto_1

    .line 247
    :cond_5
    invoke-interface {v1}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-eqz v0, :cond_6

    new-instance v0, Lio/kamihama/magianative/CNMirrors$Mirror;

    const-string v3, "\u9ed8\u8ba4\u7ebf\u8def"

    const-string v4, "https://assets.magireco.top/"

    const/4 v5, 0x0

    const/4 v6, 0x0

    const/4 v7, 0x1

    move-object v2, v0

    invoke-direct/range {v2 .. v7}, Lio/kamihama/magianative/CNMirrors$Mirror;-><init>(Ljava/lang/String;Ljava/lang/String;IIZ)V

    invoke-interface {v1, v0}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 248
    :cond_6
    return-object v1
.end method

.method public static isLoaded()Z
    .locals 1

    .line 135
    sget-boolean v0, Lio/kamihama/magianative/CNMirrors;->loaded:Z

    return v0
.end method

.method public static minChunkBytes()J
    .locals 2

    .line 57
    sget-wide v0, Lio/kamihama/magianative/CNMirrors;->cfgMinChunkBytes:J

    return-wide v0
.end method

.method public static minSpeedKbps()I
    .locals 1

    .line 60
    sget v0, Lio/kamihama/magianative/CNMirrors;->cfgMinSpeedKbps:I

    return v0
.end method

.method private static parse(Ljava/lang/String;)Ljava/util/List;
    .locals 13
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/String;",
            ")",
            "Ljava/util/List<",
            "Lio/kamihama/magianative/CNMirrors$Mirror;",
            ">;"
        }
    .end annotation

    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/lang/Exception;
        }
    .end annotation

    .line 171
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0, p0}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    .line 173
    const-string p0, "settings"

    invoke-virtual {v0, p0}, Lorg/json/JSONObject;->optJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p0

    .line 174
    const-string v1, "chunks"

    const/4 v2, 0x0

    const/4 v3, 0x1

    if-eqz p0, :cond_0

    .line 175
    sget v4, Lio/kamihama/magianative/CNMirrors;->cfgChunks:I

    invoke-virtual {p0, v1, v4}, Lorg/json/JSONObject;->optInt(Ljava/lang/String;I)I

    move-result v4

    const/16 v5, 0x10

    invoke-static {v4, v3, v5}, Lio/kamihama/magianative/CNMirrors;->clampInt(III)I

    move-result v4

    sput v4, Lio/kamihama/magianative/CNMirrors;->cfgChunks:I

    .line 176
    sget-wide v4, Lio/kamihama/magianative/CNMirrors;->cfgMinChunkBytes:J

    .line 177
    const-string v6, "min_chunk_bytes"

    invoke-virtual {p0, v6, v4, v5}, Lorg/json/JSONObject;->optLong(Ljava/lang/String;J)J

    move-result-wide v4

    .line 176
    const-wide/32 v6, 0x100000

    invoke-static {v6, v7, v4, v5}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v4

    sput-wide v4, Lio/kamihama/magianative/CNMirrors;->cfgMinChunkBytes:J

    .line 178
    const-string v4, "switch_after_failures"

    sget v5, Lio/kamihama/magianative/CNMirrors;->cfgSwitchAfterFail:I

    invoke-virtual {p0, v4, v5}, Lorg/json/JSONObject;->optInt(Ljava/lang/String;I)I

    move-result v4

    const/16 v5, 0xa

    invoke-static {v4, v3, v5}, Lio/kamihama/magianative/CNMirrors;->clampInt(III)I

    move-result v4

    sput v4, Lio/kamihama/magianative/CNMirrors;->cfgSwitchAfterFail:I

    .line 179
    const-string v4, "stall_seconds"

    sget v5, Lio/kamihama/magianative/CNMirrors;->cfgStallSeconds:I

    invoke-virtual {p0, v4, v5}, Lorg/json/JSONObject;->optInt(Ljava/lang/String;I)I

    move-result v4

    const/4 v5, 0x5

    const/16 v6, 0x12c

    invoke-static {v4, v5, v6}, Lio/kamihama/magianative/CNMirrors;->clampInt(III)I

    move-result v4

    sput v4, Lio/kamihama/magianative/CNMirrors;->cfgStallSeconds:I

    .line 180
    const-string v4, "min_speed_kbps"

    sget v5, Lio/kamihama/magianative/CNMirrors;->cfgMinSpeedKbps:I

    invoke-virtual {p0, v4, v5}, Lorg/json/JSONObject;->optInt(Ljava/lang/String;I)I

    move-result v4

    const v5, 0xf4240

    invoke-static {v4, v2, v5}, Lio/kamihama/magianative/CNMirrors;->clampInt(III)I

    move-result v4

    sput v4, Lio/kamihama/magianative/CNMirrors;->cfgMinSpeedKbps:I

    .line 181
    const-string v4, "cooldown_ms"

    sget-wide v5, Lio/kamihama/magianative/CNMirrors;->cfgCooldownMs:J

    invoke-virtual {p0, v4, v5, v6}, Lorg/json/JSONObject;->optLong(Ljava/lang/String;J)J

    move-result-wide v4

    const-wide/16 v6, 0x3e8

    invoke-static {v6, v7, v4, v5}, Ljava/lang/Math;->max(JJ)J

    move-result-wide v4

    sput-wide v4, Lio/kamihama/magianative/CNMirrors;->cfgCooldownMs:J

    .line 184
    :cond_0
    const-string p0, "mirrors"

    invoke-virtual {v0, p0}, Lorg/json/JSONObject;->optJSONArray(Ljava/lang/String;)Lorg/json/JSONArray;

    move-result-object p0

    .line 185
    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    .line 186
    if-nez p0, :cond_1

    return-object v0

    .line 187
    :cond_1
    const/4 v4, 0x0

    :goto_0
    invoke-virtual {p0}, Lorg/json/JSONArray;->length()I

    move-result v5

    if-ge v4, v5, :cond_6

    .line 188
    invoke-virtual {p0, v4}, Lorg/json/JSONArray;->optJSONObject(I)Lorg/json/JSONObject;

    move-result-object v5

    .line 189
    if-nez v5, :cond_2

    goto/16 :goto_1

    .line 190
    :cond_2
    const-string v6, "base"

    const-string v7, ""

    invoke-virtual {v5, v6, v7}, Lorg/json/JSONObject;->optString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v6

    .line 191
    invoke-virtual {v6}, Ljava/lang/String;->isEmpty()Z

    move-result v7

    if-eqz v7, :cond_3

    goto :goto_1

    .line 193
    :cond_3
    sget-object v7, Ljava/util/Locale;->US:Ljava/util/Locale;

    invoke-virtual {v6, v7}, Ljava/lang/String;->toLowerCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object v7

    .line 194
    const-string v8, "http://"

    invoke-virtual {v7, v8}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v8

    if-nez v8, :cond_4

    const-string v8, "https://"

    invoke-virtual {v7, v8}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v7

    if-nez v7, :cond_4

    .line 195
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    const-string v7, "\u5ffd\u7565\u975e http(s) \u7ebf\u8def: "

    invoke-virtual {v5, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v5

    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v5

    const-string v6, "MagiaCNMirrors"

    invoke-static {v6, v5}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 196
    goto :goto_1

    .line 198
    :cond_4
    const-string v7, "/"

    invoke-virtual {v6, v7}, Ljava/lang/String;->endsWith(Ljava/lang/String;)Z

    move-result v8

    if-nez v8, :cond_5

    new-instance v8, Ljava/lang/StringBuilder;

    invoke-direct {v8}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {v8, v6}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6, v7}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v6

    invoke-virtual {v6}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    :cond_5
    move-object v9, v6

    .line 199
    const-string v6, "name"

    invoke-virtual {v5, v6, v9}, Lorg/json/JSONObject;->optString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    .line 200
    const-string v6, "weight"

    invoke-virtual {v5, v6, v2}, Lorg/json/JSONObject;->optInt(Ljava/lang/String;I)I

    move-result v10

    .line 201
    invoke-virtual {v5, v1, v2}, Lorg/json/JSONObject;->optInt(Ljava/lang/String;I)I

    move-result v11

    .line 202
    const-string v6, "enabled"

    invoke-virtual {v5, v6, v3}, Lorg/json/JSONObject;->optBoolean(Ljava/lang/String;Z)Z

    move-result v12

    .line 203
    new-instance v5, Lio/kamihama/magianative/CNMirrors$Mirror;

    move-object v7, v5

    invoke-direct/range {v7 .. v12}, Lio/kamihama/magianative/CNMirrors$Mirror;-><init>(Ljava/lang/String;Ljava/lang/String;IIZ)V

    invoke-interface {v0, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 187
    :goto_1
    add-int/lit8 v4, v4, 0x1

    goto/16 :goto_0

    .line 205
    :cond_6
    invoke-static {v0}, Lio/kamihama/magianative/CNMirrors;->sortByWeightDesc(Ljava/util/List;)V

    .line 206
    return-object v0
.end method

.method public static pick(I)Lio/kamihama/magianative/CNMirrors$Mirror;
    .locals 2

    .line 256
    invoke-static {}, Lio/kamihama/magianative/CNMirrors;->healthy()Ljava/util/List;

    move-result-object v0

    .line 257
    add-int/lit8 p0, p0, -0x1

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v1

    rem-int/2addr p0, v1

    .line 258
    if-gez p0, :cond_0

    const/4 p0, 0x0

    .line 259
    :cond_0
    invoke-interface {v0, p0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p0

    check-cast p0, Lio/kamihama/magianative/CNMirrors$Mirror;

    return-object p0
.end method

.method public static declared-synchronized refresh(Z)V
    .locals 6

    const-class v0, Lio/kamihama/magianative/CNMirrors;

    monitor-enter v0

    .line 118
    :try_start_0
    const-string v1, "https://assets.magireco.top/mirrors.json"

    invoke-static {v1, p0}, Lio/kamihama/magianative/CNMirrors;->fetch(Ljava/lang/String;Z)Ljava/lang/String;

    move-result-object p0

    .line 119
    invoke-static {p0}, Lio/kamihama/magianative/CNMirrors;->parse(Ljava/lang/String;)Ljava/util/List;

    move-result-object p0

    .line 120
    invoke-interface {p0}, Ljava/util/List;->isEmpty()Z

    move-result v1

    if-eqz v1, :cond_0

    .line 121
    const-string p0, "MagiaCNMirrors"

    const-string v1, "mirrors.json \u672a\u5305\u542b\u4efb\u4f55\u53ef\u7528\u7ebf\u8def\uff0c\u6cbf\u7528\u9ed8\u8ba4\u7ebf\u8def"

    invoke-static {p0, v1}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 122
    monitor-exit v0

    return-void

    .line 124
    :cond_0
    :try_start_1
    sput-object p0, Lio/kamihama/magianative/CNMirrors;->mirrors:Ljava/util/List;

    .line 125
    const/4 v1, 0x1

    sput-boolean v1, Lio/kamihama/magianative/CNMirrors;->loaded:Z

    .line 126
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    .line 127
    invoke-interface {p0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :goto_0
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lio/kamihama/magianative/CNMirrors$Mirror;

    const/16 v4, 0x20

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-object v5, v3, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v4

    const/16 v5, 0x3d

    invoke-virtual {v4, v5}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    move-result-object v4

    iget-object v3, v3, Lio/kamihama/magianative/CNMirrors$Mirror;->base:Ljava/lang/String;

    invoke-virtual {v4, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    goto :goto_0

    .line 128
    :cond_1
    const-string v2, "MagiaCNMirrors"

    new-instance v3, Ljava/lang/StringBuilder;

    invoke-direct {v3}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "\u7ebf\u8def\u5217\u8868\u5df2\u52a0\u8f7d count="

    invoke-virtual {v3, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v3

    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result p0

    invoke-virtual {v3, p0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v2, p0}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 131
    goto :goto_1

    .line 129
    :catchall_0
    move-exception p0

    .line 130
    :try_start_2
    const-string v1, "MagiaCNMirrors"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "\u62c9\u53d6\u7ebf\u8def\u5217\u8868\u5931\u8d25\uff0c\u6cbf\u7528\u9ed8\u8ba4\u7ebf\u8def: "

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v1, p0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_1

    .line 132
    :goto_1
    monitor-exit v0

    return-void

    .line 117
    :catchall_1
    move-exception p0

    monitor-exit v0

    throw p0
.end method

.method public static reportFailure(Lio/kamihama/magianative/CNMirrors$Mirror;Ljava/lang/String;)V
    .locals 8

    .line 264
    if-nez p0, :cond_0

    return-void

    .line 265
    :cond_0
    iget-object v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->failures:Ljava/util/concurrent/atomic/AtomicInteger;

    invoke-virtual {v0}, Ljava/util/concurrent/atomic/AtomicInteger;->incrementAndGet()I

    move-result v0

    .line 266
    sget v1, Lio/kamihama/magianative/CNMirrors;->cfgSwitchAfterFail:I

    const-string v2, " reason="

    const-string v3, "MagiaCNMirrors"

    if-lt v0, v1, :cond_1

    .line 267
    invoke-static {}, Ljava/lang/System;->nanoTime()J

    move-result-wide v0

    sget-wide v4, Lio/kamihama/magianative/CNMirrors;->cfgCooldownMs:J

    const-wide/32 v6, 0xf4240

    mul-long v4, v4, v6

    add-long/2addr v0, v4

    iput-wide v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->cooldownUntilNs:J

    .line 268
    iget-object v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->failures:Ljava/util/concurrent/atomic/AtomicInteger;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicInteger;->set(I)V

    .line 269
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u7ebf\u8def\u8fdb\u5165\u51b7\u5374 mirror="

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object p0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v0, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, " cooldown_ms="

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    sget-wide v0, Lio/kamihama/magianative/CNMirrors;->cfgCooldownMs:J

    invoke-virtual {p0, v0, v1}, Ljava/lang/StringBuilder;->append(J)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v3, p0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_0

    .line 272
    :cond_1
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v4, "\u7ebf\u8def\u5931\u8d25 mirror="

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    iget-object p0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->name:Ljava/lang/String;

    invoke-virtual {v1, p0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    const-string p1, " count="

    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0, v0}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p0

    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p0

    invoke-static {v3, p0}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;)V

    .line 274
    :goto_0
    return-void
.end method

.method public static reportSuccess(Lio/kamihama/magianative/CNMirrors$Mirror;)V
    .locals 2

    .line 278
    if-nez p0, :cond_0

    return-void

    .line 279
    :cond_0
    iget-object v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->failures:Ljava/util/concurrent/atomic/AtomicInteger;

    const/4 v1, 0x0

    invoke-virtual {v0, v1}, Ljava/util/concurrent/atomic/AtomicInteger;->set(I)V

    .line 280
    const-wide/16 v0, 0x0

    iput-wide v0, p0, Lio/kamihama/magianative/CNMirrors$Mirror;->cooldownUntilNs:J

    .line 281
    return-void
.end method

.method private static sortByWeightDesc(Ljava/util/List;)V
    .locals 5
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lio/kamihama/magianative/CNMirrors$Mirror;",
            ">;)V"
        }
    .end annotation

    .line 216
    const/4 v0, 0x1

    :goto_0
    invoke-interface {p0}, Ljava/util/List;->size()I

    move-result v1

    if-ge v0, v1, :cond_1

    .line 217
    invoke-interface {p0, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lio/kamihama/magianative/CNMirrors$Mirror;

    .line 218
    add-int/lit8 v2, v0, -0x1

    .line 219
    :goto_1
    if-ltz v2, :cond_0

    invoke-interface {p0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lio/kamihama/magianative/CNMirrors$Mirror;

    iget v3, v3, Lio/kamihama/magianative/CNMirrors$Mirror;->weight:I

    iget v4, v1, Lio/kamihama/magianative/CNMirrors$Mirror;->weight:I

    if-ge v3, v4, :cond_0

    .line 220
    add-int/lit8 v3, v2, 0x1

    invoke-interface {p0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lio/kamihama/magianative/CNMirrors$Mirror;

    invoke-interface {p0, v3, v4}, Ljava/util/List;->set(ILjava/lang/Object;)Ljava/lang/Object;

    .line 221
    add-int/lit8 v2, v2, -0x1

    goto :goto_1

    .line 223
    :cond_0
    add-int/lit8 v2, v2, 0x1

    invoke-interface {p0, v2, v1}, Ljava/util/List;->set(ILjava/lang/Object;)Ljava/lang/Object;

    .line 216
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    .line 225
    :cond_1
    return-void
.end method

.method public static stallSeconds()I
    .locals 1

    .line 59
    sget v0, Lio/kamihama/magianative/CNMirrors;->cfgStallSeconds:I

    return v0
.end method

.method public static switchAfterFail()I
    .locals 1

    .line 58
    sget v0, Lio/kamihama/magianative/CNMirrors;->cfgSwitchAfterFail:I

    return v0
.end method
