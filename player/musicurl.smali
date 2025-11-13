.class public Lcom/mylrc/mymusic/tool/musicurl;
.super Ljava/lang/Object;
.source "SourceFile"


# instance fields
.field a:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    const-string v0, "Mverify"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const-string v0, "http://app.kzti.top/client/cgi-bin/api.fcg"

    iput-object v0, p0, Lcom/mylrc/mymusic/tool/musicurl;->a:Ljava/lang/String;

    return-void
.end method

.method private a(Ljava/lang/String;)[B
    .locals 0

    invoke-virtual {p1}, Ljava/lang/String;->getBytes()[B

    move-result-object p1

    invoke-static {p1}, Lq0/d0;->b([B)[B

    move-result-object p1

    return-object p1
.end method

.method private b(Ljava/lang/String;)Ljava/lang/String;
    .locals 4

    :try_start_0
    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0, p1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    const-string v1, "code"

    invoke-virtual {v0, v1}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    const-string v2, "data"

    invoke-virtual {v0, v2}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    const-string v3, "error_msg"

    invoke-virtual {v0, v3}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v3, "200"

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    if-eqz p1, :cond_0

    return-object v2

    :cond_0
    return-object v0

    :catch_0
    return-object p1
.end method

.method private c()Ljava/lang/String;
    .locals 11

    sget-object v0, Lcom/mylrc/mymusic/tool/APPAplication;->a:Landroid/content/SharedPreferences;

    const-string v1, "uin"

    const-string v2, ""

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v4

    sget-object v0, Lcom/mylrc/mymusic/tool/APPAplication;->a:Landroid/content/SharedPreferences;

    const-string v1, "token"

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v5

    sget-object v6, Lcom/mylrc/mymusic/tool/APPAplication;->b:Ljava/lang/String;

    sget-object v7, Lcom/mylrc/mymusic/tool/APPAplication;->c:Ljava/lang/String;

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v1, Landroid/os/Build;->BRAND:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, " "

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, Landroid/os/Build;->MODEL:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v8

    sget-object v9, Landroid/os/Build$VERSION;->RELEASE:Ljava/lang/String;

    sget-object v10, Lcom/mylrc/mymusic/tool/APPAplication;->d:Ljava/lang/String;

    move-object v3, p0

    invoke-direct/range {v3 .. v10}, Lcom/mylrc/mymusic/tool/musicurl;->i(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    return-object v0
.end method

.method private d(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    invoke-virtual {p1}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object p1

    new-instance v0, Lq0/f;

    invoke-direct {v0}, Lq0/f;-><init>()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/musicurl;->a:Ljava/lang/String;

    const-string v1, "GetMusicUrl"

    const-string v2, "kugou"

    invoke-direct {p0, v1, v2, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->j(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/musicurl;->c()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p0, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->tmc(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->a(Ljava/lang/String;)[B

    move-result-object p1

    invoke-static {v0, p1}, Lq0/f;->c(Ljava/lang/String;[B)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->b(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method private e(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    new-instance v0, Lq0/f;

    invoke-direct {v0}, Lq0/f;-><init>()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/musicurl;->a:Ljava/lang/String;

    const-string v1, "GetMusicUrl"

    const-string v2, "kuwo"

    invoke-direct {p0, v1, v2, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->j(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/musicurl;->c()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p0, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->tmc(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->a(Ljava/lang/String;)[B

    move-result-object p1

    invoke-static {v0, p1}, Lq0/f;->c(Ljava/lang/String;[B)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->b(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-static {p1}, Lq0/f;->a(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    :try_start_0
    new-instance p2, Lorg/json/JSONObject;

    invoke-direct {p2, p1}, Lorg/json/JSONObject;-><init>(Ljava/lang/String;)V

    const-string v0, "data"

    invoke-virtual {p2, v0}, Lorg/json/JSONObject;->getJSONObject(Ljava/lang/String;)Lorg/json/JSONObject;

    move-result-object p2

    const-string v0, "bitrate"

    invoke-virtual {p2, v0}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    const-string v1, "1"

    invoke-virtual {v0, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-nez v0, :cond_0

    const-string v0, "url"

    invoke-virtual {p2, v0}, Lorg/json/JSONObject;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    goto :goto_0

    :cond_0
    const-string p1, "\u5185\u90e8\u670d\u52a1\u5668\u9519\u8bef\uff0cE1"
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    :goto_0
    return-object p1
.end method

.method private f(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    new-instance v0, Lq0/f;

    invoke-direct {v0}, Lq0/f;-><init>()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/musicurl;->a:Ljava/lang/String;

    const-string v1, "GetMusicUrl"

    const-string v2, "mgu"

    invoke-direct {p0, v1, v2, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->j(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/musicurl;->c()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p0, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->tmc(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->a(Ljava/lang/String;)[B

    move-result-object p1

    invoke-static {v0, p1}, Lq0/f;->c(Ljava/lang/String;[B)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->b(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method private g(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    new-instance v0, Lq0/f;

    invoke-direct {v0}, Lq0/f;-><init>()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/musicurl;->a:Ljava/lang/String;

    const-string v1, "GetMusicUrl"

    const-string v2, "qq"

    invoke-direct {p0, v1, v2, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->j(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/musicurl;->c()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p0, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->tmc(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->a(Ljava/lang/String;)[B

    move-result-object p1

    invoke-static {v0, p1}, Lq0/f;->c(Ljava/lang/String;[B)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->b(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method private i(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0}, Lorg/json/JSONObject;-><init>()V

    :try_start_0
    const-string v1, "uid"

    invoke-virtual {v0, v1, p1}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "token"

    invoke-virtual {v0, p1, p2}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "deviceid"

    invoke-virtual {v0, p1, p3}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "appVersion"

    invoke-virtual {v0, p1, p4}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "vercode"

    invoke-virtual {v0, p1, p7}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "device"

    invoke-virtual {v0, p1, p5}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "osVersion"

    invoke-virtual {v0, p1, p6}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    invoke-virtual {v0}, Lorg/json/JSONObject;->toString()Ljava/lang/String;

    move-result-object p1
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    const-string p1, ""

    return-object p1
.end method

.method private j(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    new-instance v0, Lorg/json/JSONObject;

    invoke-direct {v0}, Lorg/json/JSONObject;-><init>()V

    :try_start_0
    const-string v1, "method"

    invoke-virtual {v0, v1, p1}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "platform"

    invoke-virtual {v0, p1, p2}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "t1"

    invoke-virtual {v0, p1, p3}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    const-string p1, "t2"

    invoke-virtual {v0, p1, p4}, Lorg/json/JSONObject;->put(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;

    invoke-virtual {v0}, Lorg/json/JSONObject;->toString()Ljava/lang/String;

    move-result-object p1
    :try_end_0
    .catch Lorg/json/JSONException; {:try_start_0 .. :try_end_0} :catch_0

    return-object p1

    :catch_0
    const-string p1, ""

    return-object p1
.end method

.method private k(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    new-instance v0, Lq0/f;

    invoke-direct {v0}, Lq0/f;-><init>()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/musicurl;->a:Ljava/lang/String;

    const-string v1, "GetMusicUrl"

    const-string v2, "wyy"

    invoke-direct {p0, v1, v2, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->j(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/musicurl;->c()Ljava/lang/String;

    move-result-object p2

    invoke-virtual {p0, p1, p2}, Lcom/mylrc/mymusic/tool/musicurl;->tmc(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->a(Ljava/lang/String;)[B

    move-result-object p1

    invoke-static {v0, p1}, Lq0/f;->c(Ljava/lang/String;[B)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->b(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method

.method private l(Ljava/lang/String;)Ljava/lang/String;
    .locals 4

    new-instance v0, Lq0/f;

    invoke-direct {v0}, Lq0/f;-><init>()V

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/musicurl;->a:Ljava/lang/String;

    const-string v1, "yun"

    const-string v2, ""

    const-string v3, "GetMusicUrl"

    invoke-direct {p0, v3, v1, p1, v2}, Lcom/mylrc/mymusic/tool/musicurl;->j(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/musicurl;->c()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p0, p1, v1}, Lcom/mylrc/mymusic/tool/musicurl;->tmc(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Lcom/mylrc/mymusic/tool/musicurl;->a(Ljava/lang/String;)[B

    move-result-object p1

    invoke-static {v0, p1}, Lq0/f;->c(Ljava/lang/String;[B)Ljava/lang/String;

    move-result-object p1

    return-object p1
.end method


# virtual methods
.method public h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    .locals 2

    invoke-virtual {p1}, Ljava/lang/String;->hashCode()I

    invoke-virtual {p1}, Ljava/lang/String;->hashCode()I

    move-result v0

    const/4 v1, -0x1

    sparse-switch v0, :sswitch_data_0

    goto :goto_0

    :sswitch_0
    const-string v0, "kugou"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_0

    goto :goto_0

    :cond_0
    const/4 v1, 0x5

    goto :goto_0

    :sswitch_1
    const-string v0, "migu"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_1

    goto :goto_0

    :cond_1
    const/4 v1, 0x4

    goto :goto_0

    :sswitch_2
    const-string v0, "kuwo"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_2

    goto :goto_0

    :cond_2
    const/4 v1, 0x3

    goto :goto_0

    :sswitch_3
    const-string v0, "yun"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_3

    goto :goto_0

    :cond_3
    const/4 v1, 0x2

    goto :goto_0

    :sswitch_4
    const-string v0, "wyy"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_4

    goto :goto_0

    :cond_4
    const/4 v1, 0x1

    goto :goto_0

    :sswitch_5
    const-string v0, "qq"

    invoke-virtual {p1, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result p1

    if-nez p1, :cond_5

    goto :goto_0

    :cond_5
    const/4 v1, 0x0

    :goto_0
    packed-switch v1, :pswitch_data_0

    const/4 p1, 0x0

    return-object p1

    :pswitch_0
    invoke-direct {p0, p2, p3}, Lcom/mylrc/mymusic/tool/musicurl;->d(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :pswitch_1
    invoke-direct {p0, p2, p3}, Lcom/mylrc/mymusic/tool/musicurl;->f(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :pswitch_2
    invoke-direct {p0, p2, p3}, Lcom/mylrc/mymusic/tool/musicurl;->e(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :pswitch_3
    invoke-direct {p0, p2}, Lcom/mylrc/mymusic/tool/musicurl;->l(Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :pswitch_4
    invoke-direct {p0, p2, p3}, Lcom/mylrc/mymusic/tool/musicurl;->k(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :pswitch_5
    invoke-direct {p0, p2, p3}, Lcom/mylrc/mymusic/tool/musicurl;->g(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    return-object p1

    :sswitch_data_0
    .sparse-switch
        0xe20 -> :sswitch_5
        0x1cdd7 -> :sswitch_4
        0x1d4d2 -> :sswitch_3
        0x3269c2 -> :sswitch_2
        0x33238a -> :sswitch_1
        0x61a92e3 -> :sswitch_0
    .end sparse-switch

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_5
        :pswitch_4
        :pswitch_3
        :pswitch_2
        :pswitch_1
        :pswitch_0
    .end packed-switch
.end method

.method public native tmc(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
.end method
