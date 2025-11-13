.class Lcom/mylrc/mymusic/service/player$n;
.super Landroid/content/BroadcastReceiver;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mylrc/mymusic/service/player;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = "n"
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/service/player;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/service/player;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Landroid/content/BroadcastReceiver;-><init>()V

    return-void
.end method


# virtual methods
.method public onReceive(Landroid/content/Context;Landroid/content/Intent;)V
    .locals 1

    const-string p1, "state"

    invoke-virtual {p2, p1}, Landroid/content/Intent;->hasExtra(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x0

    invoke-virtual {p2, p1, v0}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/service/player;->u()V

    :cond_0
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "com.music.remove"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_1

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/service/player;->o()V

    goto :goto_0

    :cond_1
    invoke-virtual {p2}, Landroid/content/Intent;->getAction()Ljava/lang/String;

    move-result-object p1

    const-string v0, "com.music.add"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-eqz p1, :cond_2

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/service/player;->h()V

    :cond_2
    :goto_0
    const-string p1, "control"

    const/4 v0, -0x1

    invoke-virtual {p2, p1, v0}, Landroid/content/Intent;->getIntExtra(Ljava/lang/String;I)I

    move-result p1

    const/4 p2, 0x1

    packed-switch p1, :pswitch_data_0

    goto/16 :goto_4

    :pswitch_0
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    const-string p2, "com.music.exit"

    invoke-virtual {p1, p2}, Lcom/mylrc/mymusic/service/player;->q(Ljava/lang/String;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {p1}, Landroid/app/Service;->stopSelf()V

    goto :goto_4

    :pswitch_1
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {p1}, Lq0/d;->c(Ljava/lang/String;)Z

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {p1}, Lq0/d;->c(Ljava/lang/String;)Z

    sget p1, Lq0/a0;->g:I

    if-nez p1, :cond_3

    :goto_1
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {p1}, Lcom/mylrc/mymusic/service/player;->b(Lcom/mylrc/mymusic/service/player;)V

    goto :goto_4

    :cond_3
    if-ne p1, p2, :cond_5

    goto :goto_1

    :pswitch_2
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {p1}, Lq0/d;->c(Ljava/lang/String;)Z

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {p1}, Lq0/d;->c(Ljava/lang/String;)Z

    sget p1, Lq0/a0;->g:I

    if-nez p1, :cond_4

    :goto_2
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {p1}, Lcom/mylrc/mymusic/service/player;->f(Lcom/mylrc/mymusic/service/player;)V

    goto :goto_4

    :cond_4
    if-ne p1, p2, :cond_5

    goto :goto_2

    :cond_5
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {p1}, Lcom/mylrc/mymusic/service/player;->d(Lcom/mylrc/mymusic/service/player;)V

    goto :goto_4

    :pswitch_3
    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->stop()V

    goto :goto_4

    :pswitch_4
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    iput p2, p1, Lcom/mylrc/mymusic/service/player;->h:I

    invoke-virtual {p1}, Lcom/mylrc/mymusic/service/player;->u()V

    new-instance p1, Landroid/os/Message;

    invoke-direct {p1}, Landroid/os/Message;-><init>()V

    goto :goto_3

    :pswitch_5
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/service/player;->t()V

    new-instance p1, Landroid/os/Message;

    invoke-direct {p1}, Landroid/os/Message;-><init>()V

    :goto_3
    iput p2, p1, Landroid/os/Message;->what:I

    iget-object p2, p0, Lcom/mylrc/mymusic/service/player$n;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p2, p2, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    invoke-virtual {p2, p1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    :goto_4
    return-void

    :pswitch_data_0
    .packed-switch 0x123
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method
