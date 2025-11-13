.class Lcom/mylrc/mymusic/activity/p$g;
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

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$g;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$g;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p;->C:Landroid/widget/ImageView;

    invoke-virtual {p1}, Landroid/view/View;->getVisibility()I

    move-result p1

    if-nez p1, :cond_0

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$g;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p;->C:Landroid/widget/ImageView;

    const/16 v0, 0x8

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$g;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p;->C:Landroid/widget/ImageView;

    const/4 v0, 0x0

    :goto_0
    invoke-virtual {p1, v0}, Landroid/widget/ImageView;->setVisibility(I)V

    return-void
.end method
