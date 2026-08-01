.class final Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;
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
    name = "CreditLinkClick"
.end annotation


# instance fields
.field private final act:Landroid/app/Activity;

.field private final url:Ljava/lang/String;


# direct methods
.method constructor <init>(Landroid/app/Activity;Ljava/lang/String;)V
    .locals 0

    .line 757
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    iput-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->act:Landroid/app/Activity;

    iput-object p2, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 6

    .line 760
    invoke-static {}, Ljava/lang/System;->currentTimeMillis()J

    move-result-wide v0

    .line 761
    iget-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$300()Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    .line 762
    invoke-static {}, Lio/kamihama/magianative/CNCNDownloadUI;->access$400()J

    move-result-wide v2

    sub-long v2, v0, v2

    const-wide/16 v4, 0x1770

    cmp-long p1, v2, v4

    if-gtz p1, :cond_0

    const/4 p1, 0x1

    goto :goto_0

    :cond_0
    const/4 p1, 0x0

    .line 763
    :goto_0
    const-string v2, "\u754c\u9762"

    if-nez p1, :cond_1

    .line 764
    iget-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    invoke-static {p1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$302(Ljava/lang/String;)Ljava/lang/String;

    .line 765
    invoke-static {v0, v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$402(J)J

    .line 766
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "\u5916\u94fe\u5f85\u786e\u8ba4: "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v2, p1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 767
    iget-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->act:Landroid/app/Activity;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u5373\u5c06\u79bb\u5f00\u6e38\u620f\u6253\u5f00\uff1a"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    const-string v1, "\n\u518d\u70b9\u4e00\u6b21\u7ee7\u7eed"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$500(Landroid/app/Activity;Ljava/lang/String;)V

    .line 768
    return-void

    .line 770
    :cond_1
    const/4 p1, 0x0

    invoke-static {p1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$302(Ljava/lang/String;)Ljava/lang/String;

    .line 771
    const-wide/16 v0, 0x0

    invoke-static {v0, v1}, Lio/kamihama/magianative/CNCNDownloadUI;->access$402(J)J

    .line 772
    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    const-string v0, "\u5916\u94fe\u5df2\u786e\u8ba4\uff0c\u8c03\u8d77\u7cfb\u7edf\u6d4f\u89c8\u5668: "

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    invoke-virtual {p1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object p1

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-static {v2, p1}, Lio/kamihama/magianative/CNLog;->i(Ljava/lang/String;Ljava/lang/String;)V

    .line 774
    :try_start_0
    new-instance p1, Landroid/content/Intent;

    const-string v0, "android.intent.action.VIEW"

    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    invoke-static {v1}, Landroid/net/Uri;->parse(Ljava/lang/String;)Landroid/net/Uri;

    move-result-object v1

    invoke-direct {p1, v0, v1}, Landroid/content/Intent;-><init>(Ljava/lang/String;Landroid/net/Uri;)V

    .line 775
    const/high16 v0, 0x10000000

    invoke-virtual {p1, v0}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;

    .line 776
    iget-object v0, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->act:Landroid/app/Activity;

    invoke-virtual {v0, p1}, Landroid/app/Activity;->startActivity(Landroid/content/Intent;)V
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 780
    goto :goto_1

    .line 777
    :catchall_0
    move-exception p1

    .line 778
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u6253\u5f00\u5916\u94fe\u5931\u8d25: "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v2, v0, p1}, Lio/kamihama/magianative/CNLog;->w(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V

    .line 779
    iget-object p1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->act:Landroid/app/Activity;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    const-string v1, "\u65e0\u6cd5\u6253\u5f00\u94fe\u63a5\uff1a"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    iget-object v1, p0, Lio/kamihama/magianative/CNCNDownloadUI$CreditLinkClick;->url:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {p1, v0}, Lio/kamihama/magianative/CNCNDownloadUI;->access$500(Landroid/app/Activity;Ljava/lang/String;)V

    .line 781
    :goto_1
    return-void
.end method
