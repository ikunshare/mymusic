.class Lcom/mylrc/mymusic/service/player$k;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/media/MediaPlayer$OnPreparedListener;


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

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$k;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onPrepared(Landroid/media/MediaPlayer;)V
    .locals 1

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$k;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/service/player;->t()V

    new-instance p1, Landroid/content/Intent;

    invoke-direct {p1}, Landroid/content/Intent;-><init>()V

    const-string v0, "com.music.upview"

    invoke-virtual {p1, v0}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$k;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {v0, p1}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    return-void
.end method
