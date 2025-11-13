.class Lcom/mylrc/mymusic/service/player$m$a;
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

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$m$a;->a:Lcom/mylrc/mymusic/service/player$m;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$m$a;->a:Lcom/mylrc/mymusic/service/player$m;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player$m;->a:Lcom/mylrc/mymusic/service/player;

    const-string v1, "\u64ad\u653e\u51fa\u9519\uff0c\u51c6\u5907\u4e0b\u4e00\u66f2"

    invoke-static {v0, v1}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method
