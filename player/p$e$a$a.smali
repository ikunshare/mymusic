.class Lcom/mylrc/mymusic/activity/p$e$a$a;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/activity/p$e$a;->getView(ILandroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:I

.field final synthetic b:Lcom/mylrc/mymusic/activity/p$e$a;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/activity/p$e$a;I)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "()V"
        }
    .end annotation

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->b:Lcom/mylrc/mymusic/activity/p$e$a;

    iput p2, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->a:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 2

    sget p1, Lq0/a0;->c:I

    iget v0, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->a:I

    if-ne p1, v0, :cond_0

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->b:Lcom/mylrc/mymusic/activity/p$e$a;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    const-string v0, "\u4e0d\u80fd\u79fb\u9664\u6b63\u5728\u64ad\u653e\u7684\u6b4c\u66f2"

    invoke-static {p1, v0}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    goto :goto_2

    :cond_0
    sget-object p1, Lq0/a0;->b:Ljava/util/List;

    invoke-interface {p1, v0}, Ljava/util/List;->remove(I)Ljava/lang/Object;

    sget p1, Lq0/a0;->c:I

    iget v0, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->a:I

    const/4 v1, 0x1

    if-le p1, v0, :cond_1

    sub-int/2addr p1, v1

    sput p1, Lq0/a0;->c:I

    :cond_1
    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->b:Lcom/mylrc/mymusic/activity/p$e$a;

    iget v0, p1, Lcom/mylrc/mymusic/activity/p$e$a;->a:I

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e$a;->b:Landroid/widget/TextView;

    if-nez v0, :cond_2

    const-string v0, "\u987a\u5e8f\u64ad\u653e"

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->b:Lcom/mylrc/mymusic/activity/p$e$a;

    iget-object v0, p1, Lcom/mylrc/mymusic/activity/p$e$a;->c:Landroid/widget/ImageView;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    const v1, 0x7f07005d

    :goto_0
    invoke-virtual {p1, v1}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    :cond_2
    if-ne v0, v1, :cond_3

    const-string v0, "\u5355\u66f2\u5faa\u73af"

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->b:Lcom/mylrc/mymusic/activity/p$e$a;

    iget-object v0, p1, Lcom/mylrc/mymusic/activity/p$e$a;->c:Landroid/widget/ImageView;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    const v1, 0x7f0700ed

    goto :goto_0

    :cond_3
    const-string v0, "\u968f\u673a\u64ad\u653e"

    invoke-virtual {p1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->b:Lcom/mylrc/mymusic/activity/p$e$a;

    iget-object v0, p1, Lcom/mylrc/mymusic/activity/p$e$a;->c:Landroid/widget/ImageView;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    const v1, 0x7f070102

    goto :goto_0

    :goto_1
    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$a$a;->b:Lcom/mylrc/mymusic/activity/p$e$a;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e$a;->d:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p;->y:Landroid/widget/SimpleAdapter;

    invoke-virtual {p1}, Landroid/widget/BaseAdapter;->notifyDataSetChanged()V

    :goto_2
    return-void
.end method
