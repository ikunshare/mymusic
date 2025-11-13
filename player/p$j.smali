.class Lcom/mylrc/mymusic/activity/p$j;
.super Landroid/os/Handler;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mylrc/mymusic/activity/p;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/activity/p;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/activity/p;Landroid/os/Looper;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 5

    iget v0, p1, Landroid/os/Message;->what:I

    if-eqz v0, :cond_3

    const/4 v1, 0x1

    if-eq v0, v1, :cond_0

    goto :goto_2

    :cond_0
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->s:Ljava/lang/String;

    invoke-static {v0}, Lq0/d;->f(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    if-eqz v0, :cond_2

    const-string v2, "GCSP_Have_Translation"

    invoke-virtual {v0, v2}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v3

    const/4 v4, -0x1

    if-eq v3, v4, :cond_2

    invoke-virtual {v0, v2}, Ljava/lang/String;->split(Ljava/lang/String;)[Ljava/lang/String;

    move-result-object v0

    array-length v2, v0

    const/4 v3, 0x2

    if-ne v2, v3, :cond_1

    const/4 v2, 0x0

    aget-object v2, v0, v2

    aget-object v0, v0, v1

    iget-object v3, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v3, v3, Lcom/mylrc/mymusic/activity/p;->u:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v3, v2, v0}, Lcom/mylrc/mymusic/tool/LrcView;->I(Ljava/lang/String;Ljava/lang/String;)V

    goto :goto_1

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->u:Lcom/mylrc/mymusic/tool/LrcView;

    new-instance v2, Ljava/io/File;

    iget-object v3, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v3, v3, Lcom/mylrc/mymusic/activity/p;->s:Ljava/lang/String;

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->u:Lcom/mylrc/mymusic/tool/LrcView;

    new-instance v2, Ljava/io/File;

    iget-object v3, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v3, v3, Lcom/mylrc/mymusic/activity/p;->s:Ljava/lang/String;

    invoke-direct {v2, v3}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    :goto_0
    invoke-virtual {v0, v2}, Lcom/mylrc/mymusic/tool/LrcView;->G(Ljava/io/File;)V

    :goto_1
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->u:Lcom/mylrc/mymusic/tool/LrcView;

    new-instance v2, Lcom/mylrc/mymusic/activity/p$j$a;

    invoke-direct {v2, p0}, Lcom/mylrc/mymusic/activity/p$j$a;-><init>(Lcom/mylrc/mymusic/activity/p$j;)V

    invoke-virtual {v0, v1, v2}, Lcom/mylrc/mymusic/tool/LrcView;->M(ZLcom/mylrc/mymusic/tool/LrcView$h;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v1, v0, Lcom/mylrc/mymusic/activity/p;->E:Landroid/os/Handler;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->F:Ljava/lang/Runnable;

    invoke-virtual {v1, v0}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    goto :goto_2

    :cond_3
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {v0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    iget-object v1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    :goto_2
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    return-void
.end method
