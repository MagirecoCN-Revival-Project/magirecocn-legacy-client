.class final Lio/kamihama/magianative/CNLog$Entry;
.super Ljava/lang/Object;
.source "CNLog.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNLog;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "Entry"
.end annotation


# instance fields
.field final line:Ljava/lang/String;

.field final src:I


# direct methods
.method constructor <init>(ILjava/lang/String;)V
    .locals 0

    .line 94
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput p1, p0, Lio/kamihama/magianative/CNLog$Entry;->src:I

    iput-object p2, p0, Lio/kamihama/magianative/CNLog$Entry;->line:Ljava/lang/String;

    return-void
.end method
