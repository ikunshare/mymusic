.class Lcom/mylrc/mymusic/service/player$a;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player;->n(Ljava/lang/String;)V
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
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$a;->b:Lcom/mylrc/mymusic/service/player;

    iput-object p2, p0, Lcom/mylrc/mymusic/service/player$a;->a:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$a;->a:Ljava/lang/String;

    if-eqz v0, :cond_0

    const-string v1, "\u7248\u672c"

    invoke-virtual {v0, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v0

    const/4 v1, -0x1

    if-eq v0, v1, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$a;->b:Lcom/mylrc/mymusic/service/player;

    const-string v1, "\u5f53\u524d\u7248\u672c\u8fc7\u4f4e\uff0c\u8bf7\u5148\u5347\u7ea7\u7248\u672c"

    invoke-static {v0, v1}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    :cond_0
    return-void
.end method
