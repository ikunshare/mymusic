.class Lcom/mylrc/mymusic/activity/p$a;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mylrc/mymusic/activity/p;
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

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 4

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    if-eqz v0, :cond_3

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v1, v0, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->getDuration()I

    move-result v1

    iput v1, v0, Lcom/mylrc/mymusic/activity/p;->g:I

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v1, v0, Lcom/mylrc/mymusic/activity/p;->e:Landroid/widget/SeekBar;

    iget v0, v0, Lcom/mylrc/mymusic/activity/p;->g:I

    invoke-virtual {v1, v0}, Landroid/widget/ProgressBar;->setMax(I)V

    :cond_0
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v1, v0, Lcom/mylrc/mymusic/activity/p;->d:Landroid/widget/TextView;

    iget v0, v0, Lcom/mylrc/mymusic/activity/p;->g:I

    div-int/lit16 v0, v0, 0x3e8

    invoke-static {v0}, Lq0/s;->i(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v1, v0, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->getCurrentPosition()I

    move-result v1

    iput v1, v0, Lcom/mylrc/mymusic/activity/p;->f:I

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v1, v0, Lcom/mylrc/mymusic/activity/p;->e:Landroid/widget/SeekBar;

    iget v0, v0, Lcom/mylrc/mymusic/activity/p;->f:I

    invoke-virtual {v1, v0}, Landroid/widget/ProgressBar;->setProgress(I)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v1, v0, Lcom/mylrc/mymusic/activity/p;->u:Lcom/mylrc/mymusic/tool/LrcView;

    iget v0, v0, Lcom/mylrc/mymusic/activity/p;->f:I

    add-int/lit16 v0, v0, 0x96

    int-to-long v2, v0

    invoke-virtual {v1, v2, v3}, Lcom/mylrc/mymusic/tool/LrcView;->Q(J)V

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->o:Landroid/widget/ImageView;

    const v1, 0x7f070105

    goto :goto_0

    :cond_2
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->o:Landroid/widget/ImageView;

    const v1, 0x7f0700f1

    :goto_0
    invoke-virtual {v0, v1}, Landroid/view/View;->setBackgroundResource(I)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v1, v0, Lcom/mylrc/mymusic/activity/p;->c:Landroid/widget/TextView;

    iget v0, v0, Lcom/mylrc/mymusic/activity/p;->f:I

    div-int/lit16 v0, v0, 0x3e8

    invoke-static {v0}, Lq0/s;->i(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v1, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_3
    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$a;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->E:Landroid/os/Handler;

    const-wide/16 v1, 0x64

    invoke-virtual {v0, p0, v1, v2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    return-void
.end method
