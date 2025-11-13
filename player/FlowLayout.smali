.class public Lcom/mylrc/mymusic/tool/FlowLayout;
.super Landroid/view/ViewGroup;
.source "SourceFile"


# instance fields
.field private a:I

.field private b:I

.field private c:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/util/List<",
            "Landroid/view/View;",
            ">;>;"
        }
    .end annotation
.end field

.field private d:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/lang/Integer;",
            ">;"
        }
    .end annotation
.end field

.field e:Z


# direct methods
.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 0

    invoke-direct {p0, p1, p2}, Landroid/view/ViewGroup;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;)V

    const/16 p1, 0x8

    invoke-static {p1}, Lcom/mylrc/mymusic/tool/FlowLayout;->a(I)I

    move-result p1

    iput p1, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->a:I

    const/16 p1, 0xd

    invoke-static {p1}, Lcom/mylrc/mymusic/tool/FlowLayout;->a(I)I

    move-result p1

    iput p1, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->b:I

    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->d:Ljava/util/List;

    const/4 p1, 0x0

    iput-boolean p1, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->e:Z

    return-void
.end method

.method public static a(I)I
    .locals 2

    int-to-float p0, p0

    invoke-static {}, Landroid/content/res/Resources;->getSystem()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v0

    const/4 v1, 0x1

    invoke-static {v1, p0, v0}, Landroid/util/TypedValue;->applyDimension(IFLandroid/util/DisplayMetrics;)F

    move-result p0

    float-to-int p0, p0

    return p0
.end method

.method private b()V
    .locals 1

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->c:Ljava/util/List;

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->d:Ljava/util/List;

    return-void
.end method


# virtual methods
.method protected onLayout(ZIIII)V
    .locals 6

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->c:Ljava/util/List;

    invoke-interface {p1}, Ljava/util/List;->size()I

    move-result p1

    const/4 p2, 0x0

    const/4 p3, 0x0

    const/4 p4, 0x0

    :goto_0
    if-ge p3, p1, :cond_1

    iget-object p5, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->c:Ljava/util/List;

    invoke-interface {p5, p3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object p5

    check-cast p5, Ljava/util/List;

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->d:Ljava/util/List;

    invoke-interface {v0, p3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    const/4 v1, 0x0

    const/4 v2, 0x0

    :goto_1
    invoke-interface {p5}, Ljava/util/List;->size()I

    move-result v3

    if-ge v1, v3, :cond_0

    invoke-interface {p5, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Landroid/view/View;

    invoke-virtual {v3}, Landroid/view/View;->getMeasuredHeight()I

    move-result v4

    add-int/2addr v4, p4

    invoke-virtual {v3}, Landroid/view/View;->getMeasuredWidth()I

    move-result v5

    add-int/2addr v5, v2

    invoke-virtual {v3, v2, p4, v5, v4}, Landroid/view/View;->layout(IIII)V

    iget v2, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->a:I

    add-int/2addr v2, v5

    add-int/lit8 v1, v1, 0x1

    goto :goto_1

    :cond_0
    add-int/2addr p4, v0

    iget p5, p0, Lcom/mylrc/mymusic/tool/FlowLayout;->b:I

    add-int/2addr p4, p5

    add-int/lit8 p3, p3, 0x1

    goto :goto_0

    :cond_1
    return-void
.end method

.method protected onMeasure(II)V
    .locals 19

    move-object/from16 v0, p0

    invoke-direct/range {p0 .. p0}, Lcom/mylrc/mymusic/tool/FlowLayout;->b()V

    invoke-virtual/range {p0 .. p0}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v1

    invoke-virtual/range {p0 .. p0}, Landroid/view/View;->getPaddingLeft()I

    move-result v2

    invoke-virtual/range {p0 .. p0}, Landroid/view/View;->getPaddingRight()I

    move-result v3

    invoke-virtual/range {p0 .. p0}, Landroid/view/View;->getPaddingTop()I

    move-result v4

    invoke-virtual/range {p0 .. p0}, Landroid/view/View;->getPaddingBottom()I

    move-result v5

    invoke-static/range {p1 .. p1}, Landroid/view/View$MeasureSpec;->getSize(I)I

    move-result v6

    invoke-static/range {p2 .. p2}, Landroid/view/View$MeasureSpec;->getSize(I)I

    move-result v7

    new-instance v8, Ljava/util/ArrayList;

    invoke-direct {v8}, Ljava/util/ArrayList;-><init>()V

    const/4 v10, 0x0

    const/4 v11, 0x0

    const/4 v12, 0x0

    const/4 v13, 0x0

    const/4 v14, 0x0

    :goto_0
    if-ge v10, v1, :cond_4

    invoke-virtual {v0, v10}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v15

    invoke-virtual {v15}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v9

    move/from16 v16, v7

    add-int v7, v2, v3

    move/from16 v17, v2

    iget v2, v9, Landroid/view/ViewGroup$LayoutParams;->width:I

    move/from16 v18, v3

    move/from16 v3, p1

    invoke-static {v3, v7, v2}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    move-result v2

    add-int v7, v5, v4

    iget v9, v9, Landroid/view/ViewGroup$LayoutParams;->height:I

    move/from16 v3, p2

    invoke-static {v3, v7, v9}, Landroid/view/ViewGroup;->getChildMeasureSpec(III)I

    move-result v7

    invoke-virtual {v15, v2, v7}, Landroid/view/View;->measure(II)V

    invoke-virtual {v15}, Landroid/view/View;->getMeasuredWidth()I

    move-result v2

    invoke-virtual {v15}, Landroid/view/View;->getMeasuredHeight()I

    move-result v7

    add-int v9, v2, v11

    iget v3, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->a:I

    add-int/2addr v9, v3

    if-le v9, v6, :cond_0

    iget-object v3, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->c:Ljava/util/List;

    invoke-interface {v3, v8}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v3, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->d:Ljava/util/List;

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v8

    invoke-interface {v3, v8}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget v3, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->a:I

    add-int/2addr v11, v3

    invoke-static {v13, v11}, Ljava/lang/Math;->max(II)I

    move-result v13

    add-int/2addr v14, v12

    iget v3, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->b:I

    add-int/2addr v14, v3

    new-instance v3, Ljava/util/ArrayList;

    invoke-direct {v3}, Ljava/util/ArrayList;-><init>()V

    move-object v8, v3

    const/4 v11, 0x0

    const/4 v12, 0x0

    :cond_0
    invoke-interface {v8, v15}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    add-int/2addr v11, v2

    iget v2, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->a:I

    add-int/2addr v11, v2

    invoke-static {v12, v7}, Ljava/lang/Math;->max(II)I

    move-result v12

    add-int/lit8 v2, v1, -0x1

    if-ne v10, v2, :cond_1

    iget-object v2, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->d:Ljava/util/List;

    invoke-static {v12}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v3

    invoke-interface {v2, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    iget-object v2, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->c:Ljava/util/List;

    invoke-interface {v2, v8}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    invoke-static {v13, v11}, Ljava/lang/Math;->max(II)I

    move-result v2

    add-int/2addr v14, v12

    move v13, v2

    :cond_1
    invoke-static/range {p1 .. p1}, Landroid/view/View$MeasureSpec;->getMode(I)I

    move-result v2

    invoke-static/range {p2 .. p2}, Landroid/view/View$MeasureSpec;->getMode(I)I

    move-result v3

    const/high16 v7, 0x40000000    # 2.0f

    if-ne v2, v7, :cond_2

    move v2, v6

    goto :goto_1

    :cond_2
    move v2, v13

    :goto_1
    if-ne v3, v7, :cond_3

    move/from16 v3, v16

    goto :goto_2

    :cond_3
    move v3, v14

    :goto_2
    invoke-virtual {v0, v2, v3}, Landroid/view/View;->setMeasuredDimension(II)V

    const/4 v2, 0x1

    iput-boolean v2, v0, Lcom/mylrc/mymusic/tool/FlowLayout;->e:Z

    add-int/lit8 v10, v10, 0x1

    move/from16 v7, v16

    move/from16 v2, v17

    move/from16 v3, v18

    goto/16 :goto_0

    :cond_4
    return-void
.end method
