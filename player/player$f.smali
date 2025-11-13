.class Lcom/mylrc/mymusic/service/player$f;
.super Ljava/lang/Thread;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player;->onStartCommand(Landroid/content/Intent;II)I
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

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$f;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$f;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {v0}, Lcom/mylrc/mymusic/service/player;->e(Lcom/mylrc/mymusic/service/player;)V

    return-void
.end method
