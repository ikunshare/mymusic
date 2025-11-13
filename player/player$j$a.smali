.class Lcom/mylrc/mymusic/service/player$j$a;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player$j;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/service/player$j;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/service/player$j;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$j$a;->a:Lcom/mylrc/mymusic/service/player$j;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$j$a;->a:Lcom/mylrc/mymusic/service/player$j;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player$j;->a:Lcom/mylrc/mymusic/service/player;

    const-string v1, "\u5f53\u524d\u5df2\u7ecf\u662f\u7b2c\u4e00\u9996\u4e86\u54e6\uff5e"

    invoke-static {v0, v1}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method
