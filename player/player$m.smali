.class Lcom/mylrc/mymusic/service/player$m;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/media/MediaPlayer$OnErrorListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player;->n(Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/service/player;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/service/player;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$m;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onError(Landroid/media/MediaPlayer;II)Z
    .locals 2

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$m;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    new-instance p2, Lcom/mylrc/mymusic/service/player$m$a;

    invoke-direct {p2, p0}, Lcom/mylrc/mymusic/service/player$m$a;-><init>(Lcom/mylrc/mymusic/service/player$m;)V

    invoke-virtual {p1, p2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$m;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    new-instance p2, Lcom/mylrc/mymusic/service/player$m$b;

    invoke-direct {p2, p0}, Lcom/mylrc/mymusic/service/player$m$b;-><init>(Lcom/mylrc/mymusic/service/player$m;)V

    const-wide/16 v0, 0x1388

    invoke-virtual {p1, p2, v0, v1}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    const/4 p1, 0x1

    return p1
.end method
