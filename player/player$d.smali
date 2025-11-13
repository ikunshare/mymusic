.class Lcom/mylrc/mymusic/service/player$d;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/media/AudioManager$OnAudioFocusChangeListener;


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

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$d;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onAudioFocusChange(I)V
    .locals 3

    const/4 v0, -0x3

    const-string v1, "pm"

    const/4 v2, 0x1

    if-eq p1, v0, :cond_3

    const/4 v0, -0x2

    if-eq p1, v0, :cond_2

    const/4 v0, -0x1

    if-eq p1, v0, :cond_1

    if-eq p1, v2, :cond_0

    goto :goto_1

    :cond_0
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$d;->a:Lcom/mylrc/mymusic/service/player;

    iget v0, p1, Lcom/mylrc/mymusic/service/player;->h:I

    if-nez v0, :cond_4

    invoke-virtual {p1}, Lcom/mylrc/mymusic/service/player;->t()V

    goto :goto_1

    :cond_1
    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_4

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result p1

    if-eqz p1, :cond_4

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$d;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->g:Landroid/content/SharedPreferences;

    invoke-interface {p1, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result p1

    if-ne p1, v2, :cond_4

    goto :goto_0

    :cond_2
    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_4

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result p1

    if-eqz p1, :cond_4

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$d;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->g:Landroid/content/SharedPreferences;

    invoke-interface {p1, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result p1

    if-ne p1, v2, :cond_4

    goto :goto_0

    :cond_3
    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    if-eqz p1, :cond_4

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result p1

    if-eqz p1, :cond_4

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$d;->a:Lcom/mylrc/mymusic/service/player;

    iget-object p1, p1, Lcom/mylrc/mymusic/service/player;->g:Landroid/content/SharedPreferences;

    invoke-interface {p1, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result p1

    if-ne p1, v2, :cond_4

    :goto_0
    iget-object p1, p0, Lcom/mylrc/mymusic/service/player$d;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/service/player;->u()V

    :cond_4
    :goto_1
    return-void
.end method
