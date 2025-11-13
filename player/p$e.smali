.class Lcom/mylrc/mymusic/activity/p$e;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/activity/p;->onCreate(Landroid/os/Bundle;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/activity/p;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/activity/p;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 12

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    const/4 v0, 0x0

    iput v0, p1, Lcom/mylrc/mymusic/activity/p;->x:I

    new-instance p1, Lq0/m;

    invoke-direct {p1}, Lq0/m;-><init>()V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {p1, v0}, Lq0/m;->a(Landroid/content/Context;)Landroid/app/Dialog;

    move-result-object p1

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-static {v0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    const v1, 0x7f0b0058

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    const v1, 0x7f08014f

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    move-object v10, v1

    check-cast v10, Landroid/widget/TextView;

    const v1, 0x7f08014d

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    move-object v11, v1

    check-cast v11, Landroid/widget/ImageView;

    sget v9, Lq0/a0;->g:I

    if-nez v9, :cond_0

    const-string v1, "\u987a\u5e8f\u64ad\u653e"

    invoke-virtual {v10, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    const v2, 0x7f07005d

    :goto_0
    invoke-virtual {v1, v2}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v11, v1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    :cond_0
    const/4 v1, 0x1

    if-ne v9, v1, :cond_1

    const-string v1, "\u5355\u66f2\u5faa\u73af"

    invoke-virtual {v10, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    const v2, 0x7f0700ed

    goto :goto_0

    :cond_1
    const-string v1, "\u968f\u673a\u64ad\u653e"

    invoke-virtual {v10, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    const v2, 0x7f070102

    goto :goto_0

    :goto_1
    invoke-virtual {p1, v0}, Landroid/app/Dialog;->setContentView(Landroid/view/View;)V

    invoke-virtual {p1}, Landroid/app/Dialog;->show()V

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {v1}, Landroid/app/Activity;->getWindowManager()Landroid/view/WindowManager;

    move-result-object v1

    invoke-interface {v1}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v1

    invoke-virtual {p1}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object v2

    invoke-virtual {v2}, Landroid/view/Window;->getAttributes()Landroid/view/WindowManager$LayoutParams;

    move-result-object v2

    invoke-virtual {v1}, Landroid/view/Display;->getWidth()I

    move-result v1

    iput v1, v2, Landroid/view/WindowManager$LayoutParams;->width:I

    invoke-virtual {p1}, Landroid/app/Dialog;->getWindow()Landroid/view/Window;

    move-result-object p1

    invoke-virtual {p1, v2}, Landroid/view/Window;->setAttributes(Landroid/view/WindowManager$LayoutParams;)V

    const p1, 0x7f08014e

    invoke-virtual {v0, p1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ListView;

    const-string v0, "name"

    const-string v1, "singer"

    filled-new-array {v0, v1}, [Ljava/lang/String;

    move-result-object v7

    const/4 v0, 0x2

    new-array v8, v0, [I

    fill-array-data v8, :array_0

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    new-instance v1, Ljava/util/HashMap;

    invoke-direct {v1}, Ljava/util/HashMap;-><init>()V

    iput-object v1, v0, Lcom/mylrc/mymusic/activity/p;->A:Ljava/util/Map;

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->A:Ljava/util/Map;

    sget v1, Lq0/a0;->c:I

    invoke-static {v1}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v1

    sget-object v2, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$e$a;

    iget-object v4, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    sget-object v5, Lq0/a0;->b:Ljava/util/List;

    const v6, 0x7f0b0059

    move-object v2, v1

    move-object v3, p0

    invoke-direct/range {v2 .. v11}, Lcom/mylrc/mymusic/activity/p$e$a;-><init>(Lcom/mylrc/mymusic/activity/p$e;Landroid/content/Context;Ljava/util/List;I[Ljava/lang/String;[IILandroid/widget/TextView;Landroid/widget/ImageView;)V

    iput-object v1, v0, Lcom/mylrc/mymusic/activity/p;->y:Landroid/widget/SimpleAdapter;

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->y:Landroid/widget/SimpleAdapter;

    invoke-virtual {p1, v0}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    sget v0, Lq0/a0;->c:I

    const/4 v1, 0x3

    invoke-virtual {p1, v0, v1}, Landroid/widget/AbsListView;->setSelectionFromTop(II)V

    new-instance v0, Lcom/mylrc/mymusic/activity/p$e$b;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/activity/p$e$b;-><init>(Lcom/mylrc/mymusic/activity/p$e;)V

    invoke-virtual {p1, v0}, Landroid/widget/AdapterView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    return-void

    nop

    :array_0
    .array-data 4
        0x7f080157
        0x7f080158
    .end array-data
.end method
