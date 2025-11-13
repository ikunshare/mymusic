.class Lcom/mylrc/mymusic/service/player$l;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/media/MediaPlayer$OnCompletionListener;


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

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$l;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onCompletion(Landroid/media/MediaPlayer;)V
    .locals 1

    sget p1, Lq0/a0;->g:I

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$l;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {p1}, Lq0/d;->c(Ljava/lang/String;)Z

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$l;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {p1}, Lq0/d;->c(Ljava/lang/String;)Z

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$l;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {p1}, Lcom/mylrc/mymusic/service/player;->b(Lcom/mylrc/mymusic/service/player;)V

    goto :goto_0

    :cond_0
    const/4 v0, 0x1

    if-ne p1, v0, :cond_1

    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->start()V

    goto :goto_0

    :cond_1
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$l;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {p1}, Lq0/d;->c(Ljava/lang/String;)Z

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$l;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {p1}, Lq0/d;->c(Ljava/lang/String;)Z

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$l;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {p1}, Lcom/mylrc/mymusic/service/player;->d(Lcom/mylrc/mymusic/service/player;)V

    :goto_0
    return-void
.end method
