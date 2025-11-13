.class public Lcom/mylrc/mymusic/tool/LrcView;
.super Landroid/view/View;
.source "SourceFile"


# annotations
.annotation build Landroid/annotation/SuppressLint;
    value = {
        "StaticFieldLeak"
    }
.end annotation

.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mylrc/mymusic/tool/LrcView$h;
    }
.end annotation


# instance fields
.field private A:I

.field private B:Ljava/lang/String;

.field private C:Landroid/graphics/drawable/Drawable;

.field private D:Landroid/view/GestureDetector$SimpleOnGestureListener;

.field private E:Ljava/lang/Runnable;

.field private a:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/mylrc/mymusic/tool/a;",
            ">;"
        }
    .end annotation
.end field

.field private b:Landroid/text/TextPaint;

.field private c:Landroid/text/TextPaint;

.field private d:Landroid/graphics/Paint$FontMetrics;

.field private e:F

.field private f:J

.field private g:I

.field private h:F

.field private i:I

.field private j:F

.field private k:I

.field private l:I

.field private m:I

.field private n:I

.field private o:I

.field private p:F

.field private q:Lcom/mylrc/mymusic/tool/LrcView$h;

.field private r:Landroid/animation/ValueAnimator;

.field private s:Landroid/view/GestureDetector;

.field private t:Landroid/widget/Scroller;

.field private u:F

.field private v:I

.field private w:Ljava/lang/Object;

.field private x:Z

.field private y:Z

.field private z:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, p1, v0}, Lcom/mylrc/mymusic/tool/LrcView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1

    const/4 v0, 0x0

    invoke-direct {p0, p1, p2, v0}, Lcom/mylrc/mymusic/tool/LrcView;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 0

    invoke-direct {p0, p1, p2, p3}, Landroid/view/View;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    new-instance p1, Landroid/text/TextPaint;

    invoke-direct {p1}, Landroid/text/TextPaint;-><init>()V

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    new-instance p1, Landroid/text/TextPaint;

    invoke-direct {p1}, Landroid/text/TextPaint;-><init>()V

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    new-instance p1, Lcom/mylrc/mymusic/tool/LrcView$e;

    invoke-direct {p1, p0}, Lcom/mylrc/mymusic/tool/LrcView$e;-><init>(Lcom/mylrc/mymusic/tool/LrcView;)V

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->D:Landroid/view/GestureDetector$SimpleOnGestureListener;

    new-instance p1, Lcom/mylrc/mymusic/tool/LrcView$f;

    invoke-direct {p1, p0}, Lcom/mylrc/mymusic/tool/LrcView$f;-><init>(Lcom/mylrc/mymusic/tool/LrcView;)V

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->E:Ljava/lang/Runnable;

    invoke-direct {p0, p2}, Lcom/mylrc/mymusic/tool/LrcView;->D(Landroid/util/AttributeSet;)V

    return-void
.end method

.method private B(I)F
    .locals 5

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v0}, Lcom/mylrc/mymusic/tool/a;->c()F

    move-result v0

    const/4 v1, 0x1

    cmpl-float v0, v0, v1

    if-nez v0, :cond_1

    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    move-result v0

    div-int/lit8 v0, v0, 0x2

    int-to-float v0, v0

    const/4 v1, 0x1

    const/4 v2, 0x1

    :goto_0
    if-gt v2, p1, :cond_0

    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    add-int/lit8 v4, v2, -0x1

    invoke-interface {v3, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v3}, Lcom/mylrc/mymusic/tool/a;->b()I

    move-result v3

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v4, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v4}, Lcom/mylrc/mymusic/tool/a;->b()I

    move-result v4

    add-int/2addr v3, v4

    shr-int/2addr v3, v1

    int-to-float v3, v3

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->e:F

    add-float/2addr v3, v4

    sub-float/2addr v0, v3

    add-int/lit8 v2, v2, 0x1

    goto :goto_0

    :cond_0
    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v1, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v1, v0}, Lcom/mylrc/mymusic/tool/a;->i(F)V

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/tool/a;->c()F

    move-result p1

    return p1
.end method

.method private D(Landroid/util/AttributeSet;)V
    .locals 8

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    sget-object v1, Ln0/a;->S:[I

    invoke-virtual {v0, p1, v1}, Landroid/content/Context;->obtainStyledAttributes(Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;

    move-result-object p1

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f060093

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    const/16 v1, 0x9

    invoke-virtual {p1, v1, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->j:F

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f060092

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    const/4 v1, 0x5

    invoke-virtual {p1, v1, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->h:F

    const/4 v1, 0x0

    cmpl-float v0, v0, v1

    if-nez v0, :cond_0

    iget v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->j:F

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->h:F

    :cond_0
    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v2, 0x7f060090

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    const/4 v2, 0x2

    invoke-virtual {p1, v2, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->e:F

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v2, 0x7f090009

    invoke-virtual {v0, v2}, Landroid/content/res/Resources;->getInteger(I)I

    move-result v0

    const/4 v2, 0x0

    invoke-virtual {p1, v2, v0}, Landroid/content/res/TypedArray;->getInt(II)I

    move-result v3

    int-to-long v3, v3

    iput-wide v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->f:J

    const-wide/16 v5, 0x0

    cmp-long v7, v3, v5

    if-gez v7, :cond_1

    int-to-long v3, v0

    :cond_1
    iput-wide v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->f:J

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v3, 0x7f050042

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    const/4 v3, 0x4

    invoke-virtual {p1, v3, v0}, Landroid/content/res/TypedArray;->getColor(II)I

    move-result v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->g:I

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v3, 0x7f050041

    invoke-virtual {v0, v3}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    const/4 v3, 0x1

    invoke-virtual {p1, v3, v0}, Landroid/content/res/TypedArray;->getColor(II)I

    move-result v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->i:I

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v4, 0x7f050045

    invoke-virtual {v0, v4}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    const/16 v4, 0xe

    invoke-virtual {p1, v4, v0}, Landroid/content/res/TypedArray;->getColor(II)I

    move-result v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->k:I

    const/4 v0, 0x3

    invoke-virtual {p1, v0}, Landroid/content/res/TypedArray;->getString(I)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->B:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    const v4, 0x7f0d0030

    invoke-virtual {v0, v4}, Landroid/content/Context;->getString(I)Ljava/lang/String;

    move-result-object v0

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->B:Ljava/lang/String;

    :goto_0
    iput-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->B:Ljava/lang/String;

    const/4 v0, 0x6

    invoke-virtual {p1, v0, v1}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->p:F

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f050044

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getColor(I)I

    move-result v0

    const/16 v1, 0xc

    invoke-virtual {p1, v1, v0}, Landroid/content/res/TypedArray;->getColor(II)I

    move-result v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->l:I

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f060096

    invoke-virtual {v0, v1}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v0

    const/16 v1, 0xd

    invoke-virtual {p1, v1, v0}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v0

    const/4 v1, 0x7

    invoke-virtual {p1, v1}, Landroid/content/res/TypedArray;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    iput-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->C:Landroid/graphics/drawable/Drawable;

    if-nez v1, :cond_3

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v4, 0x7f0700f1

    invoke-virtual {v1, v4}, Landroid/content/res/Resources;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    :cond_3
    iput-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->C:Landroid/graphics/drawable/Drawable;

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v4, 0x7f050043

    invoke-virtual {v1, v4}, Landroid/content/res/Resources;->getColor(I)I

    move-result v1

    const/16 v4, 0xa

    invoke-virtual {p1, v4, v1}, Landroid/content/res/TypedArray;->getColor(II)I

    move-result v1

    iput v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->m:I

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    const v4, 0x7f060094

    invoke-virtual {v1, v4}, Landroid/content/res/Resources;->getDimension(I)F

    move-result v1

    const/16 v4, 0xb

    invoke-virtual {p1, v4, v1}, Landroid/content/res/TypedArray;->getDimension(IF)F

    move-result v1

    const/16 v4, 0x8

    invoke-virtual {p1, v4, v2}, Landroid/content/res/TypedArray;->getInteger(II)I

    move-result v4

    iput v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->A:I

    invoke-virtual {p1}, Landroid/content/res/TypedArray;->recycle()V

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v4, 0x7f060091

    invoke-virtual {p1, v4}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p1

    float-to-int p1, p1

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->n:I

    invoke-virtual {p0}, Landroid/view/View;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v4, 0x7f060095

    invoke-virtual {p1, v4}, Landroid/content/res/Resources;->getDimension(I)F

    move-result p1

    float-to-int p1, p1

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->o:I

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    invoke-virtual {p1, v3}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->j:F

    invoke-virtual {p1, v4}, Landroid/graphics/Paint;->setTextSize(F)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    sget-object v4, Landroid/graphics/Paint$Align;->LEFT:Landroid/graphics/Paint$Align;

    invoke-virtual {p1, v4}, Landroid/graphics/Paint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    invoke-virtual {p1, v3}, Landroid/graphics/Paint;->setAntiAlias(Z)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    invoke-virtual {p1, v1}, Landroid/graphics/Paint;->setTextSize(F)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    sget-object v1, Landroid/graphics/Paint$Align;->CENTER:Landroid/graphics/Paint$Align;

    invoke-virtual {p1, v1}, Landroid/graphics/Paint;->setTextAlign(Landroid/graphics/Paint$Align;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    invoke-virtual {p1, v0}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    sget-object v0, Landroid/graphics/Paint$Cap;->ROUND:Landroid/graphics/Paint$Cap;

    invoke-virtual {p1, v0}, Landroid/graphics/Paint;->setStrokeCap(Landroid/graphics/Paint$Cap;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    invoke-virtual {p1}, Landroid/graphics/Paint;->getFontMetrics()Landroid/graphics/Paint$FontMetrics;

    move-result-object p1

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->d:Landroid/graphics/Paint$FontMetrics;

    new-instance p1, Landroid/view/GestureDetector;

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->D:Landroid/view/GestureDetector$SimpleOnGestureListener;

    invoke-direct {p1, v0, v1}, Landroid/view/GestureDetector;-><init>(Landroid/content/Context;Landroid/view/GestureDetector$OnGestureListener;)V

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->s:Landroid/view/GestureDetector;

    invoke-virtual {p1, v2}, Landroid/view/GestureDetector;->setIsLongpressEnabled(Z)V

    new-instance p1, Landroid/widget/Scroller;

    invoke-virtual {p0}, Landroid/view/View;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-direct {p1, v0}, Landroid/widget/Scroller;-><init>(Landroid/content/Context;)V

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->t:Landroid/widget/Scroller;

    return-void
.end method

.method private E()V
    .locals 5

    invoke-virtual {p0}, Lcom/mylrc/mymusic/tool/LrcView;->C()Z

    move-result v0

    if-eqz v0, :cond_2

    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    move-result v0

    if-nez v0, :cond_0

    goto :goto_1

    :cond_0
    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->iterator()Ljava/util/Iterator;

    move-result-object v0

    :goto_0
    invoke-interface {v0}, Ljava/util/Iterator;->hasNext()Z

    move-result v1

    if-eqz v1, :cond_1

    invoke-interface {v0}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lcom/mylrc/mymusic/tool/a;

    iget-object v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->getLrcWidth()F

    move-result v3

    float-to-int v3, v3

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->A:I

    invoke-virtual {v1, v2, v3, v4}, Lcom/mylrc/mymusic/tool/a;->h(Landroid/text/TextPaint;II)V

    goto :goto_0

    :cond_1
    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    move-result v0

    div-int/lit8 v0, v0, 0x2

    int-to-float v0, v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    :cond_2
    :goto_1
    return-void
.end method

.method private F()V
    .locals 5

    iget v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->o:I

    iget v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->n:I

    sub-int/2addr v0, v1

    div-int/lit8 v0, v0, 0x2

    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    move-result v1

    div-int/lit8 v1, v1, 0x2

    iget v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->n:I

    div-int/lit8 v3, v2, 0x2

    sub-int/2addr v1, v3

    add-int v3, v0, v2

    add-int/2addr v2, v1

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->C:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v4, v0, v1, v3, v2}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    return-void
.end method

.method private J(Ljava/util/List;)V
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/mylrc/mymusic/tool/a;",
            ">;)V"
        }
    .end annotation

    if-eqz p1, :cond_0

    invoke-interface {p1}, Ljava/util/List;->isEmpty()Z

    move-result v0

    if-nez v0, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->addAll(Ljava/util/Collection;)Z

    :cond_0
    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-static {p1}, Ljava/util/Collections;->sort(Ljava/util/List;)V

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->E()V

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    return-void
.end method

.method private K()V
    .locals 2

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->y()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->t:Landroid/widget/Scroller;

    const/4 v1, 0x1

    invoke-virtual {v0, v1}, Landroid/widget/Scroller;->forceFinished(Z)V

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->x:Z

    iput-boolean v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->y:Z

    iput-boolean v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->z:Z

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->E:Ljava/lang/Runnable;

    invoke-virtual {p0, v1}, Landroid/view/View;->removeCallbacks(Ljava/lang/Runnable;)Z

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->clear()V

    const/4 v1, 0x0

    iput v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->v:I

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    return-void
.end method

.method private L(Ljava/lang/Runnable;)V
    .locals 2

    invoke-static {}, Landroid/os/Looper;->myLooper()Landroid/os/Looper;

    move-result-object v0

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    if-ne v0, v1, :cond_0

    invoke-interface {p1}, Ljava/lang/Runnable;->run()V

    goto :goto_0

    :cond_0
    invoke-virtual {p0, p1}, Landroid/view/View;->post(Ljava/lang/Runnable;)Z

    :goto_0
    return-void
.end method

.method private N(I)V
    .locals 2

    iget-wide v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->f:J

    invoke-direct {p0, p1, v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->O(IJ)V

    return-void
.end method

.method private O(IJ)V
    .locals 3

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/LrcView;->B(I)F

    move-result p1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->y()V

    const/4 v0, 0x2

    new-array v0, v0, [F

    const/4 v1, 0x0

    iget v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    aput v2, v0, v1

    const/4 v1, 0x1

    aput p1, v0, v1

    invoke-static {v0}, Landroid/animation/ValueAnimator;->ofFloat([F)Landroid/animation/ValueAnimator;

    move-result-object p1

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->r:Landroid/animation/ValueAnimator;

    invoke-virtual {p1, p2, p3}, Landroid/animation/ValueAnimator;->setDuration(J)Landroid/animation/ValueAnimator;

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->r:Landroid/animation/ValueAnimator;

    new-instance p2, Landroid/view/animation/LinearInterpolator;

    invoke-direct {p2}, Landroid/view/animation/LinearInterpolator;-><init>()V

    invoke-virtual {p1, p2}, Landroid/animation/ValueAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->r:Landroid/animation/ValueAnimator;

    new-instance p2, Lcom/mylrc/mymusic/tool/LrcView$g;

    invoke-direct {p2, p0}, Lcom/mylrc/mymusic/tool/LrcView$g;-><init>(Lcom/mylrc/mymusic/tool/LrcView;)V

    invoke-virtual {p1, p2}, Landroid/animation/ValueAnimator;->addUpdateListener(Landroid/animation/ValueAnimator$AnimatorUpdateListener;)V

    invoke-static {}, Lcom/mylrc/mymusic/tool/b;->g()V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->r:Landroid/animation/ValueAnimator;

    invoke-virtual {p1}, Landroid/animation/ValueAnimator;->start()V

    return-void
.end method

.method static bridge synthetic a(Lcom/mylrc/mymusic/tool/LrcView;)Ljava/lang/Runnable;
    .locals 0

    iget-object p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->E:Ljava/lang/Runnable;

    return-object p0
.end method

.method static bridge synthetic b(Lcom/mylrc/mymusic/tool/LrcView;)Z
    .locals 0

    iget-boolean p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->x:Z

    return p0
.end method

.method static bridge synthetic c(Lcom/mylrc/mymusic/tool/LrcView;)Landroid/animation/ValueAnimator;
    .locals 0

    iget-object p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->r:Landroid/animation/ValueAnimator;

    return-object p0
.end method

.method static bridge synthetic d(Lcom/mylrc/mymusic/tool/LrcView;)I
    .locals 0

    iget p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->v:I

    return p0
.end method

.method static bridge synthetic e(Lcom/mylrc/mymusic/tool/LrcView;)Ljava/util/List;
    .locals 0

    iget-object p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    return-object p0
.end method

.method static bridge synthetic f(Lcom/mylrc/mymusic/tool/LrcView;)F
    .locals 0

    iget p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    return p0
.end method

.method static bridge synthetic g(Lcom/mylrc/mymusic/tool/LrcView;)Lcom/mylrc/mymusic/tool/LrcView$h;
    .locals 0

    iget-object p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->q:Lcom/mylrc/mymusic/tool/LrcView$h;

    return-object p0
.end method

.method private getCenterLine()I
    .locals 5

    const/4 v0, 0x0

    const v1, 0x7f7fffff    # Float.MAX_VALUE

    const/4 v1, 0x0

    const v2, 0x7f7fffff    # Float.MAX_VALUE

    :goto_0
    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    if-ge v0, v3, :cond_1

    iget v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/tool/LrcView;->B(I)F

    move-result v4

    sub-float/2addr v3, v4

    invoke-static {v3}, Ljava/lang/Math;->abs(F)F

    move-result v3

    cmpg-float v3, v3, v2

    if-gez v3, :cond_0

    iget v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/tool/LrcView;->B(I)F

    move-result v2

    sub-float/2addr v1, v2

    invoke-static {v1}, Ljava/lang/Math;->abs(F)F

    move-result v1

    move v2, v1

    move v1, v0

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    return v1
.end method

.method private getFlag()Ljava/lang/Object;
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->w:Ljava/lang/Object;

    return-object v0
.end method

.method private getLrcWidth()F
    .locals 3

    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    move-result v0

    int-to-float v0, v0

    iget v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->p:F

    const/high16 v2, 0x40000000    # 2.0f

    mul-float v1, v1, v2

    sub-float/2addr v0, v1

    return v0
.end method

.method static bridge synthetic h(Lcom/mylrc/mymusic/tool/LrcView;)Landroid/graphics/drawable/Drawable;
    .locals 0

    iget-object p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->C:Landroid/graphics/drawable/Drawable;

    return-object p0
.end method

.method static bridge synthetic i(Lcom/mylrc/mymusic/tool/LrcView;)Landroid/widget/Scroller;
    .locals 0

    iget-object p0, p0, Lcom/mylrc/mymusic/tool/LrcView;->t:Landroid/widget/Scroller;

    return-object p0
.end method

.method static bridge synthetic j(Lcom/mylrc/mymusic/tool/LrcView;Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->z:Z

    return-void
.end method

.method static bridge synthetic k(Lcom/mylrc/mymusic/tool/LrcView;Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->x:Z

    return-void
.end method

.method static bridge synthetic l(Lcom/mylrc/mymusic/tool/LrcView;Z)V
    .locals 0

    iput-boolean p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->y:Z

    return-void
.end method

.method static bridge synthetic m(Lcom/mylrc/mymusic/tool/LrcView;I)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->v:I

    return-void
.end method

.method static bridge synthetic n(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->B:Ljava/lang/String;

    return-void
.end method

.method static bridge synthetic o(Lcom/mylrc/mymusic/tool/LrcView;F)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    return-void
.end method

.method static bridge synthetic p(Lcom/mylrc/mymusic/tool/LrcView;)I
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->getCenterLine()I

    move-result p0

    return p0
.end method

.method static bridge synthetic q(Lcom/mylrc/mymusic/tool/LrcView;)Ljava/lang/Object;
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->getFlag()Ljava/lang/Object;

    move-result-object p0

    return-object p0
.end method

.method static bridge synthetic r(Lcom/mylrc/mymusic/tool/LrcView;I)F
    .locals 0

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/LrcView;->B(I)F

    move-result p0

    return p0
.end method

.method static bridge synthetic s(Lcom/mylrc/mymusic/tool/LrcView;Ljava/util/List;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/LrcView;->J(Ljava/util/List;)V

    return-void
.end method

.method private setFlag(Ljava/lang/Object;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->w:Ljava/lang/Object;

    return-void
.end method

.method static bridge synthetic t(Lcom/mylrc/mymusic/tool/LrcView;)V
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->K()V

    return-void
.end method

.method static bridge synthetic u(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/Object;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/LrcView;->setFlag(Ljava/lang/Object;)V

    return-void
.end method

.method static bridge synthetic v(Lcom/mylrc/mymusic/tool/LrcView;I)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/LrcView;->N(I)V

    return-void
.end method

.method private w()V
    .locals 3

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->getCenterLine()I

    move-result v0

    const-wide/16 v1, 0x64

    invoke-direct {p0, v0, v1, v2}, Lcom/mylrc/mymusic/tool/LrcView;->O(IJ)V

    return-void
.end method

.method private x(Landroid/graphics/Canvas;Landroid/text/StaticLayout;F)V
    .locals 2

    invoke-virtual {p1}, Landroid/graphics/Canvas;->save()I

    iget v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->p:F

    invoke-virtual {p2}, Landroid/text/Layout;->getHeight()I

    move-result v1

    shr-int/lit8 v1, v1, 0x1

    int-to-float v1, v1

    sub-float/2addr p3, v1

    invoke-virtual {p1, v0, p3}, Landroid/graphics/Canvas;->translate(FF)V

    invoke-virtual {p2, p1}, Landroid/text/Layout;->draw(Landroid/graphics/Canvas;)V

    invoke-virtual {p1}, Landroid/graphics/Canvas;->restore()V

    return-void
.end method

.method private y()V
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->r:Landroid/animation/ValueAnimator;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->isRunning()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->r:Landroid/animation/ValueAnimator;

    invoke-virtual {v0}, Landroid/animation/ValueAnimator;->end()V

    :cond_0
    return-void
.end method


# virtual methods
.method public A(J)I
    .locals 7

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x0

    :cond_0
    :goto_0
    if-gt v2, v0, :cond_3

    if-eqz v0, :cond_3

    add-int v3, v2, v0

    div-int/lit8 v3, v3, 0x2

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v4, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v4}, Lcom/mylrc/mymusic/tool/a;->g()J

    move-result-wide v4

    cmp-long v6, p1, v4

    if-gez v6, :cond_1

    add-int/lit8 v3, v3, -0x1

    move v0, v3

    goto :goto_0

    :cond_1
    add-int/lit8 v2, v3, 0x1

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v4

    if-ge v2, v4, :cond_2

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v4, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v4}, Lcom/mylrc/mymusic/tool/a;->g()J

    move-result-wide v4

    cmp-long v6, p1, v4

    if-gez v6, :cond_0

    :cond_2
    return v3

    :cond_3
    return v1
.end method

.method public C()Z
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->isEmpty()Z

    move-result v0

    xor-int/lit8 v0, v0, 0x1

    return v0
.end method

.method public G(Ljava/io/File;)V
    .locals 1

    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Lcom/mylrc/mymusic/tool/LrcView;->H(Ljava/io/File;Ljava/io/File;)V

    return-void
.end method

.method public H(Ljava/io/File;Ljava/io/File;)V
    .locals 1

    new-instance v0, Lcom/mylrc/mymusic/tool/LrcView$b;

    invoke-direct {v0, p0, p1, p2}, Lcom/mylrc/mymusic/tool/LrcView$b;-><init>(Lcom/mylrc/mymusic/tool/LrcView;Ljava/io/File;Ljava/io/File;)V

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/tool/LrcView;->L(Ljava/lang/Runnable;)V

    return-void
.end method

.method public I(Ljava/lang/String;Ljava/lang/String;)V
    .locals 1

    new-instance v0, Lcom/mylrc/mymusic/tool/LrcView$c;

    invoke-direct {v0, p0, p1, p2}, Lcom/mylrc/mymusic/tool/LrcView$c;-><init>(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/String;Ljava/lang/String;)V

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/tool/LrcView;->L(Ljava/lang/Runnable;)V

    return-void
.end method

.method public M(ZLcom/mylrc/mymusic/tool/LrcView$h;)V
    .locals 0

    if-eqz p1, :cond_1

    if-eqz p2, :cond_0

    iput-object p2, p0, Lcom/mylrc/mymusic/tool/LrcView;->q:Lcom/mylrc/mymusic/tool/LrcView$h;

    goto :goto_0

    :cond_0
    new-instance p1, Ljava/lang/IllegalArgumentException;

    const-string p2, "if draggable == true, onPlayClickListener must not be null"

    invoke-direct {p1, p2}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p1

    :cond_1
    const/4 p1, 0x0

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->q:Lcom/mylrc/mymusic/tool/LrcView$h;

    :goto_0
    return-void
.end method

.method public P(I)Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    add-int/lit8 v0, v0, -0x1

    if-gt p1, v0, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0, p1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p1

    check-cast p1, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/tool/a;->f()Ljava/lang/String;

    move-result-object p1

    return-object p1

    :cond_0
    const-string p1, ""

    return-object p1
.end method

.method public Q(J)V
    .locals 1

    new-instance v0, Lcom/mylrc/mymusic/tool/LrcView$d;

    invoke-direct {v0, p0, p1, p2}, Lcom/mylrc/mymusic/tool/LrcView$d;-><init>(Lcom/mylrc/mymusic/tool/LrcView;J)V

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/tool/LrcView;->L(Ljava/lang/Runnable;)V

    return-void
.end method

.method public computeScroll()V
    .locals 3

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->t:Landroid/widget/Scroller;

    invoke-virtual {v0}, Landroid/widget/Scroller;->computeScrollOffset()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->t:Landroid/widget/Scroller;

    invoke-virtual {v0}, Landroid/widget/Scroller;->getCurrY()I

    move-result v0

    int-to-float v0, v0

    iput v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    invoke-virtual {p0}, Landroid/view/View;->invalidate()V

    :cond_0
    iget-boolean v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->z:Z

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->t:Landroid/widget/Scroller;

    invoke-virtual {v0}, Landroid/widget/Scroller;->isFinished()Z

    move-result v0

    if-eqz v0, :cond_1

    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->z:Z

    invoke-virtual {p0}, Lcom/mylrc/mymusic/tool/LrcView;->C()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->y:Z

    if-nez v0, :cond_1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->w()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->E:Ljava/lang/Runnable;

    const-wide/16 v1, 0xfa0

    invoke-virtual {p0, v0, v1, v2}, Landroid/view/View;->postDelayed(Ljava/lang/Runnable;J)Z

    :cond_1
    return-void
.end method

.method protected onDetachedFromWindow()V
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->E:Ljava/lang/Runnable;

    invoke-virtual {p0, v0}, Landroid/view/View;->removeCallbacks(Ljava/lang/Runnable;)Z

    invoke-super {p0}, Landroid/view/View;->onDetachedFromWindow()V

    return-void
.end method

.method protected onDraw(Landroid/graphics/Canvas;)V
    .locals 11

    invoke-super {p0, p1}, Landroid/view/View;->onDraw(Landroid/graphics/Canvas;)V

    invoke-virtual {p0}, Landroid/view/View;->getHeight()I

    move-result v0

    div-int/lit8 v0, v0, 0x2

    invoke-virtual {p0}, Lcom/mylrc/mymusic/tool/LrcView;->C()Z

    move-result v1

    if-nez v1, :cond_0

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    iget v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->i:I

    invoke-virtual {v1, v2}, Landroid/graphics/Paint;->setColor(I)V

    new-instance v1, Landroid/text/StaticLayout;

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->B:Ljava/lang/String;

    iget-object v5, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->getLrcWidth()F

    move-result v2

    float-to-int v6, v2

    sget-object v7, Landroid/text/Layout$Alignment;->ALIGN_CENTER:Landroid/text/Layout$Alignment;

    const/high16 v8, 0x3f800000    # 1.0f

    const/4 v9, 0x0

    const/4 v10, 0x0

    move-object v3, v1

    invoke-direct/range {v3 .. v10}, Landroid/text/StaticLayout;-><init>(Ljava/lang/CharSequence;Landroid/text/TextPaint;ILandroid/text/Layout$Alignment;FFZ)V

    int-to-float v0, v0

    invoke-direct {p0, p1, v1, v0}, Lcom/mylrc/mymusic/tool/LrcView;->x(Landroid/graphics/Canvas;Landroid/text/StaticLayout;F)V

    return-void

    :cond_0
    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->getCenterLine()I

    move-result v1

    iget-boolean v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->x:Z

    if-eqz v2, :cond_1

    iget-object v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->C:Landroid/graphics/drawable/Drawable;

    invoke-virtual {v2, p1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    iget-object v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    iget v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->l:I

    invoke-virtual {v2, v3}, Landroid/graphics/Paint;->setColor(I)V

    iget v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->o:I

    int-to-float v4, v2

    int-to-float v0, v0

    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    move-result v2

    iget v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->o:I

    sub-int/2addr v2, v3

    int-to-float v6, v2

    iget-object v8, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    move-object v3, p1

    move v5, v0

    move v7, v0

    invoke-virtual/range {v3 .. v8}, Landroid/graphics/Canvas;->drawLine(FFFFLandroid/graphics/Paint;)V

    iget-object v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    iget v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->m:I

    invoke-virtual {v2, v3}, Landroid/graphics/Paint;->setColor(I)V

    iget-object v2, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v2, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v2}, Lcom/mylrc/mymusic/tool/a;->g()J

    move-result-wide v2

    invoke-static {v2, v3}, Lcom/mylrc/mymusic/tool/b;->a(J)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p0}, Landroid/view/View;->getWidth()I

    move-result v3

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->o:I

    div-int/lit8 v4, v4, 0x2

    sub-int/2addr v3, v4

    int-to-float v3, v3

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->d:Landroid/graphics/Paint$FontMetrics;

    iget v5, v4, Landroid/graphics/Paint$FontMetrics;->descent:F

    iget v4, v4, Landroid/graphics/Paint$FontMetrics;->ascent:F

    add-float/2addr v5, v4

    const/high16 v4, 0x40000000    # 2.0f

    div-float/2addr v5, v4

    sub-float/2addr v0, v5

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->c:Landroid/text/TextPaint;

    invoke-virtual {p1, v2, v3, v0, v4}, Landroid/graphics/Canvas;->drawText(Ljava/lang/String;FFLandroid/graphics/Paint;)V

    :cond_1
    iget v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->u:F

    const/4 v2, 0x0

    invoke-virtual {p1, v2, v0}, Landroid/graphics/Canvas;->translate(FF)V

    const/4 v0, 0x0

    :goto_0
    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v3}, Ljava/util/List;->size()I

    move-result v3

    if-ge v0, v3, :cond_5

    if-lez v0, :cond_2

    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    add-int/lit8 v4, v0, -0x1

    invoke-interface {v3, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v3}, Lcom/mylrc/mymusic/tool/a;->b()I

    move-result v3

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v4, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v4}, Lcom/mylrc/mymusic/tool/a;->b()I

    move-result v4

    add-int/2addr v3, v4

    shr-int/lit8 v3, v3, 0x1

    int-to-float v3, v3

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->e:F

    add-float/2addr v3, v4

    add-float/2addr v2, v3

    :cond_2
    iget v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->v:I

    if-ne v0, v3, :cond_3

    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->j:F

    invoke-virtual {v3, v4}, Landroid/graphics/Paint;->setTextSize(F)V

    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->i:I

    :goto_1
    invoke-virtual {v3, v4}, Landroid/graphics/Paint;->setColor(I)V

    goto :goto_2

    :cond_3
    iget-boolean v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->x:Z

    if-eqz v3, :cond_4

    if-ne v0, v1, :cond_4

    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->k:I

    goto :goto_1

    :cond_4
    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->h:F

    invoke-virtual {v3, v4}, Landroid/graphics/Paint;->setTextSize(F)V

    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->b:Landroid/text/TextPaint;

    iget v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->g:I

    goto :goto_1

    :goto_2
    iget-object v3, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v3, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v3}, Lcom/mylrc/mymusic/tool/a;->e()Landroid/text/StaticLayout;

    move-result-object v3

    invoke-direct {p0, p1, v3, v2}, Lcom/mylrc/mymusic/tool/LrcView;->x(Landroid/graphics/Canvas;Landroid/text/StaticLayout;F)V

    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_5
    return-void
.end method

.method protected onLayout(ZIIII)V
    .locals 0

    invoke-super/range {p0 .. p5}, Landroid/view/View;->onLayout(ZIIII)V

    if-eqz p1, :cond_0

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->F()V

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->E()V

    invoke-virtual {p0}, Lcom/mylrc/mymusic/tool/LrcView;->C()Z

    move-result p1

    if-eqz p1, :cond_0

    iget p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->v:I

    const-wide/16 p2, 0x0

    invoke-direct {p0, p1, p2, p3}, Lcom/mylrc/mymusic/tool/LrcView;->O(IJ)V

    :cond_0
    return-void
.end method

.method public onTouchEvent(Landroid/view/MotionEvent;)Z
    .locals 3

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    const/4 v1, 0x1

    if-eq v0, v1, :cond_0

    invoke-virtual {p1}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    const/4 v1, 0x3

    if-ne v0, v1, :cond_1

    :cond_0
    const/4 v0, 0x0

    iput-boolean v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->y:Z

    invoke-virtual {p0}, Lcom/mylrc/mymusic/tool/LrcView;->C()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-boolean v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->z:Z

    if-nez v0, :cond_1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/LrcView;->w()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->E:Ljava/lang/Runnable;

    const-wide/16 v1, 0xfa0

    invoke-virtual {p0, v0, v1, v2}, Landroid/view/View;->postDelayed(Ljava/lang/Runnable;J)Z

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->s:Landroid/view/GestureDetector;

    invoke-virtual {v0, p1}, Landroid/view/GestureDetector;->onTouchEvent(Landroid/view/MotionEvent;)Z

    move-result p1

    return p1
.end method

.method public setCurrentColor(I)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->i:I

    invoke-virtual {p0}, Landroid/view/View;->postInvalidate()V

    return-void
.end method

.method public setCurrentTextSize(F)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->j:F

    return-void
.end method

.method public setLabel(Ljava/lang/String;)V
    .locals 1

    new-instance v0, Lcom/mylrc/mymusic/tool/LrcView$a;

    invoke-direct {v0, p0, p1}, Lcom/mylrc/mymusic/tool/LrcView$a;-><init>(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/String;)V

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/tool/LrcView;->L(Ljava/lang/Runnable;)V

    return-void
.end method

.method public setNormalColor(I)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->g:I

    invoke-virtual {p0}, Landroid/view/View;->postInvalidate()V

    return-void
.end method

.method public setNormalTextSize(F)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->h:F

    return-void
.end method

.method public setOnPlayClickListener(Lcom/mylrc/mymusic/tool/LrcView$h;)V
    .locals 0
    .annotation runtime Ljava/lang/Deprecated;
    .end annotation

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->q:Lcom/mylrc/mymusic/tool/LrcView$h;

    return-void
.end method

.method public setTimeTextColor(I)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->m:I

    invoke-virtual {p0}, Landroid/view/View;->postInvalidate()V

    return-void
.end method

.method public setTimelineColor(I)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->l:I

    invoke-virtual {p0}, Landroid/view/View;->postInvalidate()V

    return-void
.end method

.method public setTimelineTextColor(I)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/LrcView;->k:I

    invoke-virtual {p0}, Landroid/view/View;->postInvalidate()V

    return-void
.end method

.method public z(J)I
    .locals 7

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v0}, Ljava/util/List;->size()I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x0

    :cond_0
    :goto_0
    if-gt v2, v0, :cond_3

    add-int v3, v2, v0

    div-int/lit8 v3, v3, 0x2

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v4, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v4}, Lcom/mylrc/mymusic/tool/a;->g()J

    move-result-wide v4

    cmp-long v6, p1, v4

    if-gez v6, :cond_1

    add-int/lit8 v3, v3, -0x1

    move v0, v3

    goto :goto_0

    :cond_1
    add-int/lit8 v2, v3, 0x1

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v4}, Ljava/util/List;->size()I

    move-result v4

    if-ge v2, v4, :cond_2

    iget-object v4, p0, Lcom/mylrc/mymusic/tool/LrcView;->a:Ljava/util/List;

    invoke-interface {v4, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {v4}, Lcom/mylrc/mymusic/tool/a;->g()J

    move-result-wide v4

    cmp-long v6, p1, v4

    if-gez v6, :cond_0

    :cond_2
    return v3

    :cond_3
    return v1
.end method
