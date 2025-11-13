.class Lcom/mylrc/mymusic/activity/p$j$a;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Lcom/mylrc/mymusic/tool/LrcView$h;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/activity/p$j;->handleMessage(Landroid/os/Message;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/activity/p$j;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/activity/p$j;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$j$a;->a:Lcom/mylrc/mymusic/activity/p$j;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public a(J)Z
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$j$a;->a:Lcom/mylrc/mymusic/activity/p$j;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$j;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    if-eqz v0, :cond_0

    long-to-int p2, p1

    invoke-virtual {v0, p2}, Landroid/media/MediaPlayer;->seekTo(I)V

    :cond_0
    const/4 p1, 0x1

    return p1
.end method
