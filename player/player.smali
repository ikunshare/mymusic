.class public Lcom/mylrc/mymusic/service/player;
.super Landroid/app/Service;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mylrc/mymusic/service/player$n;
    }
.end annotation


# static fields
.field public static v:Landroid/media/MediaPlayer;


# instance fields
.field a:Landroid/view/View;

.field b:Ljava/lang/String;

.field c:Ljava/lang/String;

.field d:Lcom/mylrc/mymusic/tool/LrcView;

.field e:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Ljava/util/Map<",
            "Ljava/lang/String;",
            "Ljava/lang/Object;",
            ">;>;"
        }
    .end annotation
.end field

.field f:Lcom/mylrc/mymusic/tool/musicurl;

.field g:Landroid/content/SharedPreferences;

.field h:I

.field i:Landroid/view/WindowManager;

.field j:Landroid/media/AudioManager;

.field k:Ljava/lang/String;

.field l:Landroid/widget/TextView;

.field m:Landroid/widget/TextView;

.field n:I

.field o:I

.field p:Ljava/lang/String;

.field private q:Landroid/view/WindowManager$LayoutParams;

.field private r:Landroid/net/wifi/WifiManager$WifiLock;

.field s:Ljava/lang/Runnable;

.field t:Landroid/os/Handler;

.field private u:Landroid/media/AudioManager$OnAudioFocusChangeListener;


# direct methods
.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Landroid/app/Service;-><init>()V

    const/4 v0, -0x1

    iput v0, p0, Lcom/mylrc/mymusic/service/player;->h:I

    new-instance v0, Lcom/mylrc/mymusic/service/player$e;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$e;-><init>(Lcom/mylrc/mymusic/service/player;)V

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->s:Ljava/lang/Runnable;

    new-instance v0, Lcom/mylrc/mymusic/service/player$g;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, p0, v1}, Lcom/mylrc/mymusic/service/player$g;-><init>(Lcom/mylrc/mymusic/service/player;Landroid/os/Looper;)V

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    new-instance v0, Lcom/mylrc/mymusic/service/player$d;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$d;-><init>(Lcom/mylrc/mymusic/service/player;)V

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->u:Landroid/media/AudioManager$OnAudioFocusChangeListener;

    return-void
.end method

.method static bridge synthetic a(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/service/player;->k(Ljava/lang/String;)V

    return-void
.end method

.method static bridge synthetic b(Lcom/mylrc/mymusic/service/player;)V
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/service/player;->l()V

    return-void
.end method

.method static bridge synthetic c(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/service/player;->n(Ljava/lang/String;)V

    return-void
.end method

.method static bridge synthetic d(Lcom/mylrc/mymusic/service/player;)V
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/service/player;->r()V

    return-void
.end method

.method static bridge synthetic e(Lcom/mylrc/mymusic/service/player;)V
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/service/player;->s()V

    return-void
.end method

.method static bridge synthetic f(Lcom/mylrc/mymusic/service/player;)V
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/service/player;->v()V

    return-void
.end method

.method private j()V
    .locals 8

    sget v0, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v1, 0x1a

    const/16 v2, 0x2b67

    const/4 v3, 0x0

    const v4, 0x7f0700aa

    const-string v5, "\u540e\u53f0\u64ad\u653e\u670d\u52a1"

    if-lt v0, v1, :cond_0

    const-string v0, "notification"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/app/NotificationManager;

    new-instance v1, Landroid/app/NotificationChannel;

    const/4 v6, 0x2

    const-string v7, "channel_8"

    invoke-direct {v1, v7, v5, v6}, Landroid/app/NotificationChannel;-><init>(Ljava/lang/String;Ljava/lang/CharSequence;I)V

    invoke-static {v0, v1}, Lp0/a;->a(Landroid/app/NotificationManager;Landroid/app/NotificationChannel;)V

    new-instance v0, Landroid/app/Notification$Builder;

    invoke-direct {v0, p0, v7}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-static {v1, v4}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/Notification$Builder;->setLargeIcon(Landroid/graphics/Bitmap;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/app/Notification$Builder;->setSound(Landroid/net/Uri;)Landroid/app/Notification$Builder;

    move-result-object v0

    const v1, 0x7f070061

    invoke-virtual {v0, v1}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v5}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v5}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v0

    goto :goto_0

    :cond_0
    new-instance v0, Landroid/app/Notification$Builder;

    invoke-direct {v0, p0}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;)V

    invoke-virtual {v0, v4}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v5}, Landroid/app/Notification$Builder;->setContentTitle(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-static {v1, v4}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/Notification$Builder;->setLargeIcon(Landroid/graphics/Bitmap;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/app/Notification$Builder;->setSound(Landroid/net/Uri;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v5}, Landroid/app/Notification$Builder;->setContentText(Ljava/lang/CharSequence;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/app/Notification$Builder;->setSound(Landroid/net/Uri;)Landroid/app/Notification$Builder;

    move-result-object v0

    :goto_0
    invoke-virtual {v0}, Landroid/app/Notification$Builder;->build()Landroid/app/Notification;

    move-result-object v0

    invoke-virtual {p0, v2, v0}, Landroid/app/Service;->startForeground(ILandroid/app/Notification;)V

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v1

    invoke-virtual {v1}, Ljava/io/File;->getParent()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "/app_tmpFile/"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->p:Ljava/lang/String;

    const/4 v0, 0x1

    iput v0, p0, Lcom/mylrc/mymusic/service/player;->o:I

    const-string v1, "wifi"

    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/net/wifi/WifiManager;

    const-string v2, "mylock"

    invoke-virtual {v1, v0, v2}, Landroid/net/wifi/WifiManager;->createWifiLock(ILjava/lang/String;)Landroid/net/wifi/WifiManager$WifiLock;

    move-result-object v1

    iput-object v1, p0, Lcom/mylrc/mymusic/service/player;->r:Landroid/net/wifi/WifiManager$WifiLock;

    if-eqz v1, :cond_1

    invoke-virtual {v1}, Landroid/net/wifi/WifiManager$WifiLock;->acquire()V

    :cond_1
    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object v1

    invoke-virtual {v1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/mylrc/mymusic/service/player;->k:Ljava/lang/String;

    new-instance v1, Lcom/mylrc/mymusic/tool/LrcView;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/tool/LrcView;-><init>(Landroid/content/Context;)V

    iput-object v1, p0, Lcom/mylrc/mymusic/service/player;->d:Lcom/mylrc/mymusic/tool/LrcView;

    const-string v1, "pms"

    const/4 v2, 0x0

    invoke-virtual {p0, v1, v2}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    iput-object v1, p0, Lcom/mylrc/mymusic/service/player;->g:Landroid/content/SharedPreferences;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->p:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "listen_tmp.lrc"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->p:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, "listen_tmp.bin"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    iput-object v1, p0, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    new-instance v1, Lcom/mylrc/mymusic/tool/musicurl;

    invoke-direct {v1}, Lcom/mylrc/mymusic/tool/musicurl;-><init>()V

    iput-object v1, p0, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    const-string v1, "audio"

    invoke-virtual {p0, v1}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/media/AudioManager;

    iput-object v1, p0, Lcom/mylrc/mymusic/service/player;->j:Landroid/media/AudioManager;

    new-instance v1, Landroid/media/MediaPlayer;

    invoke-direct {v1}, Landroid/media/MediaPlayer;-><init>()V

    sput-object v1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    new-instance v1, Lcom/mylrc/mymusic/service/player$n;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/service/player$n;-><init>(Lcom/mylrc/mymusic/service/player;)V

    new-instance v2, Landroid/content/IntentFilter;

    invoke-direct {v2}, Landroid/content/IntentFilter;-><init>()V

    const-string v3, "com.mylrc.mymusic.ac"

    invoke-virtual {v2, v3}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string v3, "android.intent.action.HEADSET_PLUG"

    invoke-virtual {v2, v3}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string v3, "com.music.remove"

    invoke-virtual {v2, v3}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string v3, "com.music.add"

    invoke-virtual {v2, v3}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    invoke-virtual {p0, v1, v2}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    new-instance v1, Landroid/content/ComponentName;

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v2

    const-class v3, Lcom/mylrc/mymusic/receiver/HeadsetButtonReceiver;

    invoke-virtual {v3}, Ljava/lang/Class;->getName()Ljava/lang/String;

    move-result-object v3

    invoke-direct {v1, v2, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->j:Landroid/media/AudioManager;

    invoke-virtual {v2, v1}, Landroid/media/AudioManager;->registerMediaButtonEventReceiver(Landroid/content/ComponentName;)V

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->g:Landroid/content/SharedPreferences;

    const-string v2, "win"

    invoke-interface {v1, v2, v0}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-nez v0, :cond_2

    invoke-direct {p0}, Lcom/mylrc/mymusic/service/player;->m()V

    invoke-virtual {p0}, Lcom/mylrc/mymusic/service/player;->w()V

    :cond_2
    return-void
.end method

.method private k(Ljava/lang/String;)V
    .locals 2

    new-instance v0, Landroid/os/Message;

    invoke-direct {v0}, Landroid/os/Message;-><init>()V

    const/4 v1, 0x0

    iput v1, v0, Landroid/os/Message;->what:I

    iput-object p1, v0, Landroid/os/Message;->obj:Ljava/lang/Object;

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    invoke-virtual {p1, v0}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method private l()V
    .locals 1

    new-instance v0, Lcom/mylrc/mymusic/service/player$h;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$h;-><init>(Lcom/mylrc/mymusic/service/player;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    return-void
.end method

.method private m()V
    .locals 3

    :try_start_0
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->i:Landroid/view/WindowManager;

    if-nez v0, :cond_1

    const-string v0, "window"

    invoke-virtual {p0, v0}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/view/WindowManager;

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->i:Landroid/view/WindowManager;

    invoke-static {p0}, Landroid/view/LayoutInflater;->from(Landroid/content/Context;)Landroid/view/LayoutInflater;

    move-result-object v0

    const v1, 0x7f0b0045

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->a:Landroid/view/View;

    const v1, 0x7f0800f6

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->l:Landroid/widget/TextView;

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->a:Landroid/view/View;

    const v1, 0x7f0800f7

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->m:Landroid/widget/TextView;

    new-instance v0, Landroid/view/WindowManager$LayoutParams;

    invoke-direct {v0}, Landroid/view/WindowManager$LayoutParams;-><init>()V

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->q:Landroid/view/WindowManager$LayoutParams;

    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1a

    if-lt v1, v2, :cond_0

    const/16 v1, 0x7f6

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->type:I

    goto :goto_0

    :cond_0
    const/16 v1, 0x7da

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->type:I

    :goto_0
    const/16 v1, 0x38

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->flags:I

    const/4 v1, -0x3

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->format:I

    const/16 v1, 0x30

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->gravity:I

    const/4 v1, -0x1

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->width:I

    const/4 v1, -0x2

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->height:I

    const v1, 0x3f4ccccd    # 0.8f

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->alpha:F

    const/16 v1, 0x14

    iput v1, v0, Landroid/view/WindowManager$LayoutParams;->y:I

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->i:Landroid/view/WindowManager;

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->a:Landroid/view/View;

    invoke-interface {v1, v2, v0}, Landroid/view/ViewManager;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_1

    :catch_0
    const-string v0, "\u8bf7\u5148\u6388\u6743\u60ac\u6d6e\u7a97\u6743\u9650\uff0c\u624d\u80fd\u663e\u793a\u684c\u9762\u6b4c\u8bcd\uff01"

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/service/player;->k(Ljava/lang/String;)V

    :cond_1
    :goto_1
    return-void
.end method

.method private n(Ljava/lang/String;)V
    .locals 4

    const/4 v0, -0x1

    iput v0, p0, Lcom/mylrc/mymusic/service/player;->n:I

    :try_start_0
    sget-object v1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->stop()V

    sget-object v1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v1}, Landroid/media/MediaPlayer;->reset()V

    new-instance v1, Landroid/os/Message;

    invoke-direct {v1}, Landroid/os/Message;-><init>()V

    const/4 v2, 0x1

    iput v2, v1, Landroid/os/Message;->what:I

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    invoke-virtual {v3, v1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    new-instance v1, Landroid/content/Intent;

    invoke-direct {v1}, Landroid/content/Intent;-><init>()V

    const-string v3, "com.music.upview"

    invoke-virtual {v1, v3}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    invoke-virtual {p0, v1}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    if-eqz p1, :cond_0

    const-string v1, "http"

    invoke-virtual {p1, v1}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p0, v2}, Landroid/media/MediaPlayer;->setWakeMode(Landroid/content/Context;I)V

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p1}, Landroid/media/MediaPlayer;->setDataSource(Ljava/lang/String;)V

    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {p1}, Landroid/media/MediaPlayer;->prepareAsync()V

    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    new-instance v0, Lcom/mylrc/mymusic/service/player$k;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$k;-><init>(Lcom/mylrc/mymusic/service/player;)V

    invoke-virtual {p1, v0}, Landroid/media/MediaPlayer;->setOnPreparedListener(Landroid/media/MediaPlayer$OnPreparedListener;)V

    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    new-instance v0, Lcom/mylrc/mymusic/service/player$l;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$l;-><init>(Lcom/mylrc/mymusic/service/player;)V

    invoke-virtual {p1, v0}, Landroid/media/MediaPlayer;->setOnCompletionListener(Landroid/media/MediaPlayer$OnCompletionListener;)V

    sget-object p1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    new-instance v0, Lcom/mylrc/mymusic/service/player$m;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$m;-><init>(Lcom/mylrc/mymusic/service/player;)V

    invoke-virtual {p1, v0}, Landroid/media/MediaPlayer;->setOnErrorListener(Landroid/media/MediaPlayer$OnErrorListener;)V

    goto :goto_0

    :cond_0
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    new-instance v2, Lcom/mylrc/mymusic/service/player$a;

    invoke-direct {v2, p0, p1}, Lcom/mylrc/mymusic/service/player$a;-><init>(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V

    invoke-virtual {v1, v2}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    const-string v1, "\u7248\u672c"

    invoke-virtual {p1, v1}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result p1

    if-ne p1, v0, :cond_1

    iget-object p1, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    new-instance v0, Lcom/mylrc/mymusic/service/player$b;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$b;-><init>(Lcom/mylrc/mymusic/service/player;)V

    const-wide/16 v1, 0x1388

    invoke-virtual {p1, v0, v1, v2}, Landroid/os/Handler;->postDelayed(Ljava/lang/Runnable;J)Z
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    :cond_1
    :goto_0
    invoke-virtual {p0}, Lcom/mylrc/mymusic/service/player;->i()V

    return-void
.end method

.method private r()V
    .locals 1

    new-instance v0, Lcom/mylrc/mymusic/service/player$i;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$i;-><init>(Lcom/mylrc/mymusic/service/player;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    return-void
.end method

.method private s()V
    .locals 12

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    if-eqz v0, :cond_9

    sget-object v1, Lq0/a0;->d:Ljava/lang/String;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    const-string v2, "name"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lq0/a0;->e:Ljava/lang/String;

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v0, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    const-string v2, "singer"

    invoke-interface {v0, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    sput-object v0, Lq0/a0;->f:Ljava/lang/String;

    const-string v0, "kugou"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    const-string v3, "\uff1a\u65e0\u7248\u6743\u6216\u8005\u4e3a\u6570\u5b57\u4e13\u8f91\uff0c\u4e0d\u80fd\u64ad\u653e\uff01\u4e0b\u4e00\u66f2"

    const-string v4, "cy"

    const/4 v5, 0x2

    const-string v6, "pay"

    const/4 v7, 0x1

    const-string v8, "utf-8"

    const-string v9, "mp3"

    if-eqz v2, :cond_2

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    const-string v2, "filehash"

    invoke-interface {v1, v2}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v2, "\u4f4e\u9ad8"

    invoke-virtual {v1, v2}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v2

    const/4 v10, 0x0

    invoke-virtual {v1, v10, v2}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v10, Lq0/a0;->c:I

    invoke-interface {v2, v10}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    invoke-interface {v2, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    iget-object v10, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v11, Lq0/a0;->c:I

    invoke-interface {v10, v11}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v10

    check-cast v10, Ljava/util/Map;

    invoke-interface {v10, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v2, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_1

    const-string v2, "5"

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_0

    goto :goto_2

    :cond_0
    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v2, v0, v1, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v1, v2, v8}, Lq0/l;->b(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_6

    :goto_0
    iput v5, p0, Lcom/mylrc/mymusic/service/player;->o:I

    :goto_1
    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/service/player;->n(Ljava/lang/String;)V

    goto/16 :goto_6

    :cond_1
    :goto_2
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    :goto_3
    sget-object v1, Lq0/a0;->e:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/service/player;->k(Ljava/lang/String;)V

    invoke-direct {p0}, Lcom/mylrc/mymusic/service/player;->l()V

    goto/16 :goto_6

    :cond_2
    const-string v0, "wyy"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    const-string v10, "id"

    if-eqz v2, :cond_5

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v10, Lq0/a0;->c:I

    invoke-interface {v2, v10}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    invoke-interface {v2, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    iget-object v6, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v10, Lq0/a0;->c:I

    invoke-interface {v6, v10}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v6

    check-cast v6, Ljava/util/Map;

    invoke-interface {v6, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    const-string v6, "4"

    invoke-virtual {v2, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-nez v2, :cond_4

    const-string v2, "-200"

    invoke-virtual {v4, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_3

    goto :goto_4

    :cond_3
    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v2, v0, v1, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v1, v2, v8}, Lq0/l;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_6

    goto :goto_0

    :cond_4
    :goto_4
    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    goto :goto_3

    :cond_5
    const-string v0, "migu"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_7

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v3, Lq0/a0;->c:I

    invoke-interface {v2, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    const-string v3, "lrc"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v3, v0, v1, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v2, v1, v8}, Lq0/l;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    :cond_6
    :goto_5
    iput v7, p0, Lcom/mylrc/mymusic/service/player;->o:I

    goto/16 :goto_1

    :cond_7
    const-string v0, "qq"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_8

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v2, v0, v1, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v1, v2, v8}, Lq0/l;->f(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_6

    goto/16 :goto_0

    :cond_8
    const-string v0, "kuwo"

    invoke-virtual {v1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_a

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1, v10}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v2, v0, v1, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v1, v2, v8}, Lq0/l;->c(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    goto :goto_5

    :cond_9
    const-string v0, "\u64ad\u653e\u5217\u8868\u4e3a\u7a7a"

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/service/player;->k(Ljava/lang/String;)V

    :cond_a
    :goto_6
    return-void
.end method

.method private v()V
    .locals 1

    new-instance v0, Lcom/mylrc/mymusic/service/player$j;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$j;-><init>(Lcom/mylrc/mymusic/service/player;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    return-void
.end method


# virtual methods
.method protected g(I)Landroid/content/Intent;
    .locals 2

    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "com.mylrc.mymusic.ac"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    const-string v1, "control"

    invoke-virtual {v0, v1, p1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    return-object v0
.end method

.method public h()V
    .locals 3

    :try_start_0
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->i:Landroid/view/WindowManager;

    if-eqz v0, :cond_0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->a:Landroid/view/View;

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->q:Landroid/view/WindowManager$LayoutParams;

    invoke-interface {v0, v1, v2}, Landroid/view/ViewManager;->addView(Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V

    :goto_0
    invoke-virtual {p0}, Lcom/mylrc/mymusic/service/player;->w()V

    goto :goto_1

    :cond_0
    invoke-direct {p0}, Lcom/mylrc/mymusic/service/player;->m()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    const-string v0, "\u8bf7\u5148\u6388\u6743\u60ac\u6d6e\u7a97\u6743\u9650\uff0c\u624d\u80fd\u663e\u793a\u684c\u9762\u6b4c\u8bcd\uff01"

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/service/player;->k(Ljava/lang/String;)V

    :goto_1
    return-void
.end method

.method public i()V
    .locals 2

    sget-object v0, Lq0/a0;->d:Ljava/lang/String;

    new-instance v1, Lcom/mylrc/mymusic/service/player$c;

    invoke-direct {v1, p0, v0}, Lcom/mylrc/mymusic/service/player$c;-><init>(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/lang/Thread;->start()V

    return-void
.end method

.method public o()V
    .locals 2

    :try_start_0
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->i:Landroid/view/WindowManager;

    if-eqz v0, :cond_0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->a:Landroid/view/View;

    invoke-interface {v0, v1}, Landroid/view/ViewManager;->removeView(Landroid/view/View;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->s:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    :catch_0
    const-string v0, "\u79fb\u9664\u684c\u9762\u6b4c\u8bcd\u51fa\u9519\uff01"

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/service/player;->k(Ljava/lang/String;)V

    :cond_0
    :goto_0
    return-void
.end method

.method public onBind(Landroid/content/Intent;)Landroid/os/IBinder;
    .locals 0

    const/4 p1, 0x0

    return-object p1
.end method

.method public onCreate()V
    .locals 0

    invoke-super {p0}, Landroid/app/Service;->onCreate()V

    invoke-direct {p0}, Lcom/mylrc/mymusic/service/player;->j()V

    return-void
.end method

.method public onDestroy()V
    .locals 3

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->r:Landroid/net/wifi/WifiManager$WifiLock;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/net/wifi/WifiManager$WifiLock;->release()V

    :cond_0
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->g:Landroid/content/SharedPreferences;

    const-string v1, "win"

    const/4 v2, 0x1

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-nez v0, :cond_1

    invoke-virtual {p0}, Lcom/mylrc/mymusic/service/player;->o()V

    :cond_1
    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->stop()V

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->j:Landroid/media/AudioManager;

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->u:Landroid/media/AudioManager$OnAudioFocusChangeListener;

    invoke-virtual {v0, v1}, Landroid/media/AudioManager;->abandonAudioFocus(Landroid/media/AudioManager$OnAudioFocusChangeListener;)I

    const/16 v0, 0x2b67

    invoke-virtual {p0, v0}, Landroid/app/Service;->stopSelf(I)V

    invoke-super {p0}, Landroid/app/Service;->onDestroy()V

    return-void
.end method

.method public onStartCommand(Landroid/content/Intent;II)I
    .locals 1

    sget-object v0, Lq0/a0;->b:Ljava/util/List;

    iput-object v0, p0, Lcom/mylrc/mymusic/service/player;->e:Ljava/util/List;

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v0}, Lq0/d;->c(Ljava/lang/String;)Z

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v0}, Lq0/d;->c(Ljava/lang/String;)Z

    new-instance v0, Lcom/mylrc/mymusic/service/player$f;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/service/player$f;-><init>(Lcom/mylrc/mymusic/service/player;)V

    invoke-virtual {v0}, Ljava/lang/Thread;->start()V

    invoke-super {p0, p1, p2, p3}, Landroid/app/Service;->onStartCommand(Landroid/content/Intent;II)I

    move-result p1

    return p1
.end method

.method public p()Z
    .locals 5

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->u:Landroid/media/AudioManager$OnAudioFocusChangeListener;

    const/4 v1, 0x0

    if-eqz v0, :cond_0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->j:Landroid/media/AudioManager;

    const/4 v3, 0x3

    const/4 v4, 0x1

    invoke-virtual {v2, v0, v3, v4}, Landroid/media/AudioManager;->requestAudioFocus(Landroid/media/AudioManager$OnAudioFocusChangeListener;II)I

    move-result v0

    if-ne v4, v0, :cond_0

    const/4 v1, 0x1

    :cond_0
    return v1
.end method

.method protected q(Ljava/lang/String;)V
    .locals 1

    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    invoke-virtual {v0, p1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    invoke-virtual {p0, v0}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    return-void
.end method

.method public t()V
    .locals 3

    const/4 v0, 0x0

    iput v0, p0, Lcom/mylrc/mymusic/service/player;->h:I

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    if-eqz v0, :cond_0

    new-instance v0, Landroid/os/Message;

    invoke-direct {v0}, Landroid/os/Message;-><init>()V

    const/4 v1, 0x1

    iput v1, v0, Landroid/os/Message;->what:I

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    invoke-virtual {v2, v0}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->start()V

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v0, p0, v1}, Landroid/media/MediaPlayer;->setWakeMode(Landroid/content/Context;I)V

    const/16 v0, 0x123

    sput v0, Lq0/a0;->a:I

    invoke-virtual {p0}, Lcom/mylrc/mymusic/service/player;->p()Z

    :cond_0
    return-void
.end method

.method public u()V
    .locals 2

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    if-eqz v0, :cond_0

    new-instance v0, Landroid/os/Message;

    invoke-direct {v0}, Landroid/os/Message;-><init>()V

    const/4 v1, 0x1

    iput v1, v0, Landroid/os/Message;->what:I

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    invoke-virtual {v1, v0}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    sget-object v0, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v0}, Landroid/media/MediaPlayer;->pause()V

    const/16 v0, 0x124

    sput v0, Lq0/a0;->a:I

    :cond_0
    return-void
.end method

.method public w()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player;->s:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    return-void
.end method
