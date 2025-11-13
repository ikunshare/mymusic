.class Lcom/mylrc/mymusic/service/player$m$b;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player$m;->onError(Landroid/media/MediaPlayer;II)Z
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/service/player$m;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/service/player$m;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$m$b;->a:Lcom/mylrc/mymusic/service/player$m;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$m$b;->a:Lcom/mylrc/mymusic/service/player$m;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player$m;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v0}, Lq0/d;->c(Ljava/lang/String;)Z

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$m$b;->a:Lcom/mylrc/mymusic/service/player$m;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player$m;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v0}, Lq0/d;->c(Ljava/lang/String;)Z

    sget v0, Lq0/a0;->g:I

    if-nez v0, :cond_0

    :goto_0
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$m$b;->a:Lcom/mylrc/mymusic/service/player$m;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player$m;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {v0}, Lcom/mylrc/mymusic/service/player;->b(Lcom/mylrc/mymusic/service/player;)V

    goto :goto_1

    :cond_0
    const/4 v1, 0x1

    if-ne v0, v1, :cond_1

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$m$b;->a:Lcom/mylrc/mymusic/service/player$m;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player$m;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {v0}, Lcom/mylrc/mymusic/service/player;->d(Lcom/mylrc/mymusic/service/player;)V

    :goto_1
    return-void
.end method
