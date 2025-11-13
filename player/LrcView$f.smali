.class Lcom/mylrc/mymusic/tool/LrcView$f;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mylrc/mymusic/tool/LrcView;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/tool/LrcView;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/tool/LrcView;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$f;->a:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$f;->a:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v0}, Lcom/mylrc/mymusic/tool/LrcView;->C()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$f;->a:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v0}, Lcom/mylrc/mymusic/tool/LrcView;->b(Lcom/mylrc/mymusic/tool/LrcView;)Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$f;->a:Lcom/mylrc/mymusic/tool/LrcView;

    const/4 v1, 0x0

    invoke-static {v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->k(Lcom/mylrc/mymusic/tool/LrcView;Z)V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$f;->a:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v0}, Lcom/mylrc/mymusic/tool/LrcView;->d(Lcom/mylrc/mymusic/tool/LrcView;)I

    move-result v1

    invoke-static {v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->v(Lcom/mylrc/mymusic/tool/LrcView;I)V

    :cond_0
    return-void
.end method
