.class Lcom/mylrc/mymusic/activity/p$h;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/widget/SeekBar$OnSeekBarChangeListener;


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

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$h;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onProgressChanged(Landroid/widget/SeekBar;IZ)V
    .locals 0

    return-void
.end method

.method public onStartTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 0

    return-void
.end method

.method public onStopTrackingTouch(Landroid/widget/SeekBar;)V
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p$h;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object v0, v0, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    if-eqz v0, :cond_0

    invoke-virtual {p1}, Landroid/widget/ProgressBar;->getProgress()I

    move-result p1

    invoke-virtual {v0, p1}, Landroid/media/MediaPlayer;->seekTo(I)V

    :cond_0
    return-void
.end method
