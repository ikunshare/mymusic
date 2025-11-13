.class Lcom/mylrc/mymusic/service/player$g;
.super Landroid/os/Handler;
.source "SourceFile"


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
.method constructor <init>(Lcom/mylrc/mymusic/service/player;Landroid/os/Looper;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0, p2}, Landroid/os/Handler;-><init>(Landroid/os/Looper;)V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 11

    const-string v0, "channel_8"

    const-string v1, "control"

    const-string v2, "com.mylrc.mymusic.ac"

    iget v3, p1, Landroid/os/Message;->what:I

    if-eqz v3, :cond_5

    const/4 v4, 0x1

    if-eq v3, v4, :cond_0

    goto/16 :goto_4

    :cond_0
    :try_start_0
    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->d:Lcom/mylrc/mymusic/tool/LrcView;

    new-instance v4, Ljava/io/File;

    iget-object v5, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v5, v5, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-direct {v4, v5}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v3, v4}, Lcom/mylrc/mymusic/tool/LrcView;->G(Ljava/io/File;)V

    new-instance v3, Landroid/widget/RemoteViews;

    iget-object v4, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {v4}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v4

    const v5, 0x7f0b0056

    invoke-direct {v3, v4, v5}, Landroid/widget/RemoteViews;-><init>(Ljava/lang/String;I)V

    new-instance v4, Landroid/widget/RemoteViews;

    iget-object v5, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {v5}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v5

    const v6, 0x7f0b001d

    invoke-direct {v4, v5, v6}, Landroid/widget/RemoteViews;-><init>(Ljava/lang/String;I)V

    sget-object v5, Lq0/a0;->e:Ljava/lang/String;

    const v6, 0x7f08002f

    invoke-virtual {v4, v6, v5}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    sget-object v5, Lq0/a0;->f:Ljava/lang/String;

    const v6, 0x7f080030

    invoke-virtual {v4, v6, v5}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    sget-object v5, Lq0/a0;->e:Ljava/lang/String;

    const v6, 0x7f080137

    invoke-virtual {v3, v6, v5}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    sget-object v5, Lq0/a0;->f:Ljava/lang/String;

    const v6, 0x7f080138

    invoke-virtual {v3, v6, v5}, Landroid/widget/RemoteViews;->setTextViewText(ILjava/lang/CharSequence;)V

    sget-object v5, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    invoke-virtual {v5}, Landroid/media/MediaPlayer;->isPlaying()Z

    move-result v5

    const v6, 0x7f080135

    const v7, 0x7f08002c

    const/high16 v8, 0x8000000

    if-eqz v5, :cond_1

    const v5, 0x7f070105

    invoke-virtual {v4, v7, v5}, Landroid/widget/RemoteViews;->setImageViewResource(II)V

    invoke-virtual {v3, v6, v5}, Landroid/widget/RemoteViews;->setImageViewResource(II)V

    iget-object v5, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    const/16 v9, 0x124

    invoke-virtual {v5, v9}, Lcom/mylrc/mymusic/service/player;->g(I)Landroid/content/Intent;

    move-result-object v9

    const/16 v10, 0x67

    invoke-static {v5, v10, v9, v8}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v5

    invoke-virtual {v4, v7, v5}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    :goto_0
    invoke-virtual {v3, v6, v5}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    goto :goto_1

    :cond_1
    const v5, 0x7f0700f1

    invoke-virtual {v4, v7, v5}, Landroid/widget/RemoteViews;->setImageViewResource(II)V

    invoke-virtual {v3, v6, v5}, Landroid/widget/RemoteViews;->setImageViewResource(II)V

    iget-object v5, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    const/16 v9, 0x123

    invoke-virtual {v5, v9}, Lcom/mylrc/mymusic/service/player;->g(I)Landroid/content/Intent;

    move-result-object v9

    const/16 v10, 0x68

    invoke-static {v5, v10, v9, v8}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v5

    invoke-virtual {v4, v7, v5}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    goto :goto_0

    :goto_1
    new-instance v5, Landroid/content/Intent;

    invoke-direct {v5}, Landroid/content/Intent;-><init>()V

    invoke-virtual {v5, v2}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    const/16 v6, 0x126

    invoke-virtual {v5, v1, v6}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    iget-object v6, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    const/16 v7, 0x64

    invoke-static {v6, v7, v5, v8}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v5

    const v6, 0x7f08002b

    invoke-virtual {v4, v6, v5}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    const v6, 0x7f080134

    invoke-virtual {v3, v6, v5}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    new-instance v5, Landroid/content/Intent;

    invoke-direct {v5}, Landroid/content/Intent;-><init>()V

    invoke-virtual {v5, v2}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    const/16 v6, 0x127

    invoke-virtual {v5, v1, v6}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    iget-object v6, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    const/16 v7, 0x65

    invoke-static {v6, v7, v5, v8}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v5

    const v6, 0x7f08002d

    invoke-virtual {v4, v6, v5}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    const v6, 0x7f080136

    invoke-virtual {v3, v6, v5}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    new-instance v5, Landroid/content/Intent;

    invoke-direct {v5}, Landroid/content/Intent;-><init>()V

    invoke-virtual {v5, v2}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    const/16 v2, 0x128

    invoke-virtual {v5, v1, v2}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    const/16 v2, 0x66

    invoke-static {v1, v2, v5, v8}, Landroid/app/PendingIntent;->getBroadcast(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v1

    const v2, 0x7f08002a

    invoke-virtual {v4, v2, v1}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    const v2, 0x7f080133

    invoke-virtual {v3, v2, v1}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    new-instance v1, Landroid/content/Intent;

    invoke-direct {v1}, Landroid/content/Intent;-><init>()V

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    const-class v5, Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {v1, v2, v5}, Landroid/content/Intent;->setClass(Landroid/content/Context;Ljava/lang/Class;)Landroid/content/Intent;

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    const/16 v5, 0x69

    invoke-static {v2, v5, v1, v8}, Landroid/app/PendingIntent;->getActivity(Landroid/content/Context;ILandroid/content/Intent;I)Landroid/app/PendingIntent;

    move-result-object v1

    const v2, 0x7f080029

    invoke-virtual {v4, v2, v1}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    const v5, 0x7f080132

    invoke-virtual {v3, v5, v1}, Landroid/widget/RemoteViews;->setOnClickPendingIntent(ILandroid/app/PendingIntent;)V

    new-instance v1, Ljava/io/File;

    iget-object v6, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v6, v6, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-direct {v1, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v1

    if-eqz v1, :cond_2

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v1}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v1

    invoke-virtual {v4, v2, v1}, Landroid/widget/RemoteViews;->setImageViewBitmap(ILandroid/graphics/Bitmap;)V

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->c:Ljava/lang/String;

    invoke-static {v1}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v1

    invoke-virtual {v3, v5, v1}, Landroid/widget/RemoteViews;->setImageViewBitmap(ILandroid/graphics/Bitmap;)V

    goto :goto_2

    :cond_2
    const v1, 0x7f0700ae

    invoke-virtual {v4, v2, v1}, Landroid/widget/RemoteViews;->setImageViewResource(II)V

    invoke-virtual {v3, v5, v1}, Landroid/widget/RemoteViews;->setImageViewResource(II)V

    :goto_2
    sget v1, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 v2, 0x1a

    const/16 v5, 0x2b67

    const/4 v6, 0x0

    const v7, 0x7f0700aa

    if-lt v1, v2, :cond_3

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    const-string v2, "notification"

    invoke-virtual {v1, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Landroid/app/NotificationManager;

    new-instance v2, Landroid/app/NotificationChannel;

    const-string v8, "\u540e\u53f0\u64ad\u653e\u670d\u52a1"

    const/4 v9, 0x2

    invoke-direct {v2, v0, v8, v9}, Landroid/app/NotificationChannel;-><init>(Ljava/lang/String;Ljava/lang/CharSequence;I)V

    invoke-static {v1, v2}, Lp0/a;->a(Landroid/app/NotificationManager;Landroid/app/NotificationChannel;)V

    new-instance v1, Landroid/app/Notification$Builder;

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {v1, v2, v0}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;Ljava/lang/String;)V

    invoke-static {v1, v4}, Lj/f;->a(Landroid/app/Notification$Builder;Landroid/widget/RemoteViews;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-static {v0, v3}, Lj/e;->a(Landroid/app/Notification$Builder;Landroid/widget/RemoteViews;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v7}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v6}, Landroid/app/Notification$Builder;->setSound(Landroid/net/Uri;)Landroid/app/Notification$Builder;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {v1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-static {v1, v7}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/app/Notification$Builder;->setLargeIcon(Landroid/graphics/Bitmap;)Landroid/app/Notification$Builder;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {v0}, Landroid/app/Notification$Builder;->build()Landroid/app/Notification;

    move-result-object v0

    :goto_3
    invoke-virtual {v1, v5, v0}, Landroid/app/Service;->startForeground(ILandroid/app/Notification;)V

    goto :goto_4

    :cond_3
    const/16 v0, 0x18

    if-lt v1, v0, :cond_4

    new-instance v0, Lj/b;

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {v0, v1}, Lj/b;-><init>(Landroid/content/Context;)V

    invoke-virtual {v0, v3}, Lj/b;->d(Landroid/widget/RemoteViews;)Lj/b;

    move-result-object v0

    invoke-virtual {v0, v4}, Lj/b;->c(Landroid/widget/RemoteViews;)Lj/b;

    move-result-object v0

    invoke-virtual {v0, v7}, Lj/b;->e(I)Lj/b;

    move-result-object v0

    invoke-virtual {v0, v6}, Lj/b;->f(Landroid/net/Uri;)Lj/b;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {v0}, Lj/b;->a()Landroid/app/Notification;

    move-result-object v0

    goto :goto_3

    :cond_4
    new-instance v0, Landroid/app/Notification$Builder;

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {v0, v1}, Landroid/app/Notification$Builder;-><init>(Landroid/content/Context;)V

    invoke-virtual {v0, v3}, Landroid/app/Notification$Builder;->setContent(Landroid/widget/RemoteViews;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v7}, Landroid/app/Notification$Builder;->setSmallIcon(I)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0, v6}, Landroid/app/Notification$Builder;->setSound(Landroid/net/Uri;)Landroid/app/Notification$Builder;

    move-result-object v0

    invoke-virtual {v0}, Landroid/app/Notification$Builder;->build()Landroid/app/Notification;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_3

    :catch_0
    move-exception v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-static {v1, v0}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    goto :goto_4

    :cond_5
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$g;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v1, p1, Landroid/os/Message;->obj:Ljava/lang/Object;

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lq0/r;->c(Landroid/content/Context;Ljava/lang/String;)V

    :goto_4
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    return-void
.end method
