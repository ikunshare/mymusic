.class Lcom/mylrc/mymusic/tool/LrcView$d;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/tool/LrcView;->Q(J)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:J

.field final synthetic b:Lcom/mylrc/mymusic/tool/LrcView;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/tool/LrcView;J)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->b:Lcom/mylrc/mymusic/tool/LrcView;

    iput-wide p2, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->a:J

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 3

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->b:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v0}, Lcom/mylrc/mymusic/tool/LrcView;->C()Z

    move-result v0

    if-nez v0, :cond_0

    return-void

    :cond_0
    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->b:Lcom/mylrc/mymusic/tool/LrcView;

    iget-wide v1, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->a:J

    invoke-virtual {v0, v1, v2}, Lcom/mylrc/mymusic/tool/LrcView;->z(J)I

    move-result v0

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->b:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v1}, Lcom/mylrc/mymusic/tool/LrcView;->d(Lcom/mylrc/mymusic/tool/LrcView;)I

    move-result v1

    if-eq v0, v1, :cond_2

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->b:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v1, v0}, Lcom/mylrc/mymusic/tool/LrcView;->m(Lcom/mylrc/mymusic/tool/LrcView;I)V

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->b:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v1}, Lcom/mylrc/mymusic/tool/LrcView;->b(Lcom/mylrc/mymusic/tool/LrcView;)Z

    move-result v1

    if-nez v1, :cond_1

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->b:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v1, v0}, Lcom/mylrc/mymusic/tool/LrcView;->v(Lcom/mylrc/mymusic/tool/LrcView;I)V

    goto :goto_0

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$d;->b:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v0}, Landroid/view/View;->invalidate()V

    :cond_2
    :goto_0
    return-void
.end method
