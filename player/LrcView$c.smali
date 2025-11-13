.class Lcom/mylrc/mymusic/tool/LrcView$c;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/tool/LrcView;->I(Ljava/lang/String;Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Ljava/lang/String;

.field final synthetic b:Ljava/lang/String;

.field final synthetic c:Lcom/mylrc/mymusic/tool/LrcView;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/String;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->c:Lcom/mylrc/mymusic/tool/LrcView;

    iput-object p2, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->a:Ljava/lang/String;

    iput-object p3, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->b:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->c:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v0}, Lcom/mylrc/mymusic/tool/LrcView;->t(Lcom/mylrc/mymusic/tool/LrcView;)V

    new-instance v0, Ljava/lang/StringBuilder;

    const-string v1, "file://"

    invoke-direct {v0, v1}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->a:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->b:Ljava/lang/String;

    if-eqz v1, :cond_0

    const-string v1, "#"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->b:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    :cond_0
    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->c:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v1, v0}, Lcom/mylrc/mymusic/tool/LrcView;->u(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/Object;)V

    new-instance v1, Lcom/mylrc/mymusic/tool/LrcView$c$a;

    invoke-direct {v1, p0, v0}, Lcom/mylrc/mymusic/tool/LrcView$c$a;-><init>(Lcom/mylrc/mymusic/tool/LrcView$c;Ljava/lang/String;)V

    const/4 v0, 0x2

    new-array v0, v0, [Ljava/lang/String;

    const/4 v2, 0x0

    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->a:Ljava/lang/String;

    aput-object v3, v0, v2

    const/4 v2, 0x1

    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView$c;->b:Ljava/lang/String;

    aput-object v3, v0, v2

    invoke-virtual {v1, v0}, Landroid/os/AsyncTask;->execute([Ljava/lang/Object;)Landroid/os/AsyncTask;

    return-void
.end method
