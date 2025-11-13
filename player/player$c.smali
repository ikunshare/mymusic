.class Lcom/mylrc/mymusic/service/player$c;
.super Ljava/lang/Thread;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player;->i()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Ljava/lang/String;

.field final synthetic b:Lcom/mylrc/mymusic/service/player;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$c;->b:Lcom/mylrc/mymusic/service/player;

    iput-object p2, p0, Lcom/mylrc/mymusic/service/player$c;->a:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$c;->a:Ljava/lang/String;

    const-string v1, "kugou"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_0

    sget-object v0, Lq0/a0;->b:Ljava/util/List;

    sget v1, Lq0/a0;->c:I

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    const-string v1, "filehash"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v1, "\u4f4e\u9ad8"

    invoke-virtual {v0, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v1

    const/4 v2, 0x0

    invoke-virtual {v0, v2, v1}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$c;->b:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v0, v1}, Lq0/h;->e(Ljava/lang/String;Ljava/lang/String;)Z

    goto/16 :goto_0

    :cond_0
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$c;->a:Ljava/lang/String;

    const-string v1, "wyy"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    const-string v1, "id"

    if-eqz v0, :cond_1

    sget-object v0, Lq0/a0;->b:Ljava/util/List;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$c;->b:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v0, v1}, Lq0/h;->o(Ljava/lang/String;Ljava/lang/String;)Z

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$c;->a:Ljava/lang/String;

    const-string v2, "migu"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_2

    sget-object v0, Lq0/a0;->b:Ljava/util/List;

    sget v1, Lq0/a0;->c:I

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    const-string v1, "imgurl"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$c;->b:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v0, v1}, Lq0/h;->i(Ljava/lang/String;Ljava/lang/String;)Z

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$c;->a:Ljava/lang/String;

    const-string v2, "qq"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_3

    sget-object v0, Lq0/a0;->b:Ljava/util/List;

    sget v1, Lq0/a0;->c:I

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    const-string v1, "albumid"

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$c;->b:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v0, v1}, Lq0/h;->k(Ljava/lang/String;Ljava/lang/String;)Z

    goto :goto_0

    :cond_3
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$c;->a:Ljava/lang/String;

    const-string v2, "kuwo"

    invoke-virtual {v0, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    sget-object v0, Lq0/a0;->b:Ljava/util/List;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    invoke-interface {v0, v1}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$c;->b:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v0, v1}, Lq0/h;->f(Ljava/lang/String;Ljava/lang/String;)Z

    :cond_4
    :goto_0
    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "com.music.upview2"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$c;->b:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {v1, v0}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    new-instance v0, Landroid/os/Message;

    invoke-direct {v0}, Landroid/os/Message;-><init>()V

    const/4 v1, 0x1

    iput v1, v0, Landroid/os/Message;->what:I

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$c;->b:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    invoke-virtual {v1, v0}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method
