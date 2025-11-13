.class Lcom/mylrc/mymusic/activity/p$e$a;
.super Landroid/widget/SimpleAdapter;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/activity/p$e;->onClick(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:I

.field final synthetic b:Landroid/widget/TextView;

.field final synthetic c:Landroid/widget/ImageView;

.field final synthetic d:Lcom/mylrc/mymusic/activity/p$e;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/activity/p$e;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[IILandroid/widget/TextView;Landroid/widget/ImageView;)V
    .locals 6

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iput p7, p0, Lcom/mylrc/mymusic/activity/p$e$a;->a:I

    iput-object p8, p0, Lcom/mylrc/mymusic/activity/p$e$a;->b:Landroid/widget/TextView;

    iput-object p9, p0, Lcom/mylrc/mymusic/activity/p$e$a;->c:Landroid/widget/ImageView;

    move-object v0, p0

    move-object v1, p2

    move-object v2, p3

    move v3, p4

    move-object v4, p5

    move-object v5, p6

    invoke-direct/range {v0 .. v5}, Landroid/widget/SimpleAdapter;-><init>(Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[I)V

    return-void
.end method


# virtual methods
.method public getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    .locals 3

    if-nez p2, :cond_0

    iget-object p2, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p2, p2, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-static {p2}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object p2

    const v0, 0x7f0b0059

    const/4 v1, 0x0

    invoke-virtual {p2, v0, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p2

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    new-instance v1, Lq0/b0;

    invoke-direct {v1}, Lq0/b0;-><init>()V

    iput-object v1, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    const v1, 0x7f080157

    invoke-virtual {p2, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    iput-object v1, v0, Lq0/b0;->a:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    const v1, 0x7f080158

    invoke-virtual {p2, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    iput-object v1, v0, Lq0/b0;->b:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    const v1, 0x7f080156

    invoke-virtual {p2, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/RelativeLayout;

    iput-object v1, v0, Lq0/b0;->c:Landroid/widget/RelativeLayout;

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    invoke-virtual {p2, v0}, Landroid/view/View;->setTag(Ljava/lang/Object;)V

    goto :goto_0

    :cond_0
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {p2}, Landroid/view/View;->getTag()Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Lq0/b0;

    iput-object v1, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    :goto_0
    sget v0, Lq0/a0;->c:I

    if-ne v0, p1, :cond_1

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    iget-object v0, v0, Lq0/b0;->a:Landroid/widget/TextView;

    const-string v1, "#FFFE4263"

    invoke-static {v1}, Landroid/graphics/Color;->parseColor(Ljava/lang/String;)I

    move-result v2

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    iget-object v0, v0, Lq0/b0;->b:Landroid/widget/TextView;

    goto :goto_1

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    iget-object v0, v0, Lq0/b0;->a:Landroid/widget/TextView;

    const-string v1, "#FF000000"

    invoke-static {v1}, Landroid/graphics/Color;->parseColor(Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    iget-object v0, v0, Lq0/b0;->b:Landroid/widget/TextView;

    const-string v1, "#FFB2B2B2"

    :goto_1
    invoke-static {v1}, Landroid/graphics/Color;->parseColor(Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setTextColor(I)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->t:Lq0/b0;

    iget-object v0, v0, Lq0/b0;->c:Landroid/widget/RelativeLayout;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$e$a$a;

    invoke-direct {v1, p0, p1}, Lcom/mylrc/mymusic/activity/p$e$a$a;-><init>(Lcom/mylrc/mymusic/activity/p$e$a;I)V

    invoke-virtual {v0, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-super {p0, p1, p2, p3}, Landroid/widget/SimpleAdapter;->getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;

    move-result-object p1

    return-object p1
.end method
