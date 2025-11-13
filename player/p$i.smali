.class Lcom/mylrc/mymusic/activity/p$i;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/activity/p;->e()V
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

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$i;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 1

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$i;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_0

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result p1

    if-eqz p1, :cond_0

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$i;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p;->o:Landroid/widget/ImageView;

    const v0, 0x7f0700f1

    invoke-virtual {p1, v0}, Landroid/view/View;->setBackgroundResource(I)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$i;->a:Lcom/mylrc/mymusic/activity/p;

    const/16 v0, 0x124

    goto :goto_0

    :cond_0
    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$i;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p;->o:Landroid/widget/ImageView;

    const v0, 0x7f070105

    invoke-virtual {p1, v0}, Landroid/view/View;->setBackgroundResource(I)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$i;->a:Lcom/mylrc/mymusic/activity/p;

    const/16 v0, 0x123

    :goto_0
    invoke-virtual {p1, v0}, Lcom/mylrc/mymusic/activity/p;->g(I)V

    return-void
.end method
