.class Lcom/mylrc/mymusic/activity/p$f;
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

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$f;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 2

    sget p1, Lq0/a0;->g:I

    const/4 v0, 0x1

    if-nez p1, :cond_0

    sput v0, Lq0/a0;->g:I

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$f;->a:Lcom/mylrc/mymusic/activity/p;

    const-string v0, "\u5355\u66f2\u5faa\u73af"

    invoke-static {p1, v0}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$f;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, p1, Lcom/mylrc/mymusic/activity/p;->p:Landroid/widget/ImageView;

    const v1, 0x7f0700ed

    :goto_0
    invoke-virtual {p1, v1}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_1

    :cond_0
    if-ne p1, v0, :cond_1

    const/4 p1, 0x2

    sput p1, Lq0/a0;->g:I

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$f;->a:Lcom/mylrc/mymusic/activity/p;

    const-string v0, "\u968f\u673a\u64ad\u653e"

    invoke-static {p1, v0}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$f;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, p1, Lcom/mylrc/mymusic/activity/p;->p:Landroid/widget/ImageView;

    const v1, 0x7f070102

    goto :goto_0

    :cond_1
    const/4 p1, 0x0

    sput p1, Lq0/a0;->g:I

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$f;->a:Lcom/mylrc/mymusic/activity/p;

    const-string v0, "\u987a\u5e8f\u64ad\u653e"

    invoke-static {p1, v0}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$f;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, p1, Lcom/mylrc/mymusic/activity/p;->p:Landroid/widget/ImageView;

    const v1, 0x7f07005d

    goto :goto_0

    :goto_1
    return-void
.end method
