.class final Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;
.super Ljava/lang/Object;
.source "CNCNDownloadUI.java"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lio/kamihama/magianative/CNCNDownloadUI;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x1a
    name = "RetryClick"
.end annotation


# instance fields
.field private final act:Landroid/app/Activity;

.field private final index:I


# direct methods
.method constructor <init>(Landroid/app/Activity;I)V
    .locals 0

    .line 834
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;->act:Landroid/app/Activity;

    iput p2, p0, Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;->index:I

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 3

    .line 837
    const-string v0, "\u754c\u9762"

    const/16 v1, 0x8

    :try_start_0
    invoke-virtual {p1, v1}, Landroid/view/View;->setVisibility(I)V

    .line 838
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u73a9\u5bb6\u70b9\u51fb\u91cd\u8bd5: index="

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;->index:I

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v0, p1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 839
    iget-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;->act:Landroid/app/Activity;

    const-string v1, "\u5df2\u52a0\u5165\u91cd\u8bd5\u961f\u5217"

    invoke-static {p1, v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$600(Landroid/app/Activity;Ljava/lang/String;)V

    .line 840
    iget p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$RetryClick;->index:I

    invoke-static {p1}, Lio/kamihama/magianative/CNDownloaderFix;->requestRetry(I)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 843
    goto :goto_0

    .line 841
    :catchall_0
    move-exception p1

    .line 842
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v2, "\u91cd\u8bd5\u8bf7\u6c42\u5931\u8d25: "

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1, p1}, Lio/kamihama/magianative/CNLog;->e(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 844
    :goto_0
    return-void
.end method
