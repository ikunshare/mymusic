.class Lcom/mylrc/mymusic/tool/LrcView$g;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/animation/ValueAnimator$AnimatorUpdateListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/tool/LrcView;->O(IJ)V
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

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$g;->a:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onAnimationUpdate(Landroid/animation/ValueAnimator;)V
    .locals 1

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$g;->a:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {p1}, Lcom/mylrc/mymusic/tool/LrcView;->c(Lcom/mylrc/mymusic/tool/LrcView;)Landroid/animation/ValueAnimator;

    move-result-object v0

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->getAnimatedValue()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Float;

    invoke-virtual {v0}, Ljava/lang/Float;->floatValue()F

    move-result v0

    invoke-static {p1, v0}, Lcom/mylrc/mymusic/tool/LrcView;->o(Lcom/mylrc/mymusic/tool/LrcView;F)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$g;->a:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {p1}, Landroid/view/View;->invalidate()V

    return-void
.end method
