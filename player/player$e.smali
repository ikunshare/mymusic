.class Lcom/mylrc/mymusic/service/player$e;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Runnable;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lcom/mylrc/mymusic/service/player;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/service/player;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/service/player;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$e;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 5

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    if-eqz v0, :cond_2

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result v0

    if-eqz v0, :cond_2

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->getCurrentPosition()I

    move-result v0

    add-int/lit16 v0, v0, 0x96

    int-to-long v0, v0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$e;->a:Lcom/mylrc/mymusic/service/player;

    iget v3, v2, Lcom/mylrc/mymusic/service/player;->o:I

    const/4 v4, 0x2

    if-ne v3, v4, :cond_1

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->d:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v2, v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->A(J)I

    move-result v2

    if-nez v2, :cond_0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$e;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v2, Lcom/mylrc/mymusic/service/player;->l:Landroid/widget/TextView;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->d:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v2, v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->A(J)I

    move-result v4

    goto :goto_0

    :cond_0
    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$e;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v2, Lcom/mylrc/mymusic/service/player;->l:Landroid/widget/TextView;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->d:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v2, v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->A(J)I

    move-result v4

    add-int/lit8 v4, v4, -0x1

    :goto_0
    invoke-virtual {v2, v4}, Lcom/mylrc/mymusic/tool/LrcView;->P(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$e;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v2, Lcom/mylrc/mymusic/service/player;->m:Landroid/widget/TextView;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->d:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v2, v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->A(J)I

    move-result v0

    goto :goto_1

    :cond_1
    iget-object v3, v2, Lcom/mylrc/mymusic/service/player;->l:Landroid/widget/TextView;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->d:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v2, v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->A(J)I

    move-result v4

    invoke-virtual {v2, v4}, Lcom/mylrc/mymusic/tool/LrcView;->P(I)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v3, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$e;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v2, Lcom/mylrc/mymusic/service/player;->m:Landroid/widget/TextView;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->d:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-virtual {v2, v0, v1}, Lcom/mylrc/mymusic/tool/LrcView;->A(J)I

    move-result v0

    add-int/lit8 v0, v0, 0x1

    :goto_1
    invoke-virtual {v2, v0}, Lcom/mylrc/mymusic/tool/LrcView;->P(I)Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v3, v0}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    :cond_2
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$e;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    const-wide/16 v1, 0x64

    invoke-virtual {v0, p0, v1, v2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z

    return-void
.end method
