.class Lcom/mylrc/mymusic/activity/p$k;
.super Landroid/content/BroadcastReceiver;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mylrc/mymusic/activity/p;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "k"
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/activity/p;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/activity/p;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$k;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "com.music.upview"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$k;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-static {p1}, Lcom/mylrc/mymusic/activity/p;->a(Lcom/mylrc/mymusic/activity/p;)V

    goto :goto_0

    :cond_0
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "com.music.upview2"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$k;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-static {p1}, Lcom/mylrc/mymusic/activity/p;->b(Lcom/mylrc/mymusic/activity/p;)V

    goto :goto_0

    :cond_1
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string p2, "com.music.exit"

    invoke-virtual {p1, p2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$k;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/activity/p;->finish()V

    :cond_2
    :goto_0
    return-void
.end method
