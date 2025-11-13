.class Lcom/mylrc/mymusic/tool/LrcView$a;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/tool/LrcView;->setLabel(Ljava/lang/String;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Ljava/lang/String;

.field final synthetic b:Lcom/mylrc/mymusic/tool/LrcView;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/String;)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$a;->b:Lcom/mylrc/mymusic/tool/LrcView;

    iput-object p2, p0, Lcom/mylrc/mymusic/tool/LrcView$a;->a:Ljava/lang/String;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$a;->b:Lcom/mylrc/mymusic/tool/LrcView;

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$a;->a:Ljava/lang/String;

    invoke-static {v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->n(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$a;->b:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v0}, Landroid/view/View;->invalidate()V

    return-void
.end method
