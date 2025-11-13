.class Lcom/mylrc/mymusic/service/player$h$a;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player$h;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/service/player$h;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/service/player$h;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$h$a;->a:Lcom/mylrc/mymusic/service/player$h;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h$a;->a:Lcom/mylrc/mymusic/service/player$h;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    const-string v1, "\u5217\u8868\u64ad\u653e\u5b8c\u6bd5\uff5e"

    invoke-static {v0, v1}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    return-void
.end method
