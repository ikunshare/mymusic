.class Lcom/mylrc/mymusic/service/player$h;
.super Ljava/lang/Thread;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player;->l()V
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

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 12

    sget v0, Lq0/a0;->c:I

    sget-object v1, Lq0/a0;->b:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    const/4 v2, 0x1

    sub-int/2addr v1, v2

    if-ge v0, v1, :cond_a

    sget-object v0, Lq0/a0;->b:Ljava/util/List;

    sget v1, Lq0/a0;->c:I

    add-int/2addr v1, v2

    sget-object v3, Lq0/a0;->d:Ljava/lang/String;

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map;

    const-string v5, "name"

    invoke-interface {v4, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    sput-object v4, Lq0/a0;->e:Ljava/lang/String;

    invoke-interface {v0, v1}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map;

    const-string v5, "singer"

    invoke-interface {v4, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    sput-object v4, Lq0/a0;->f:Ljava/lang/String;

    sput v1, Lq0/a0;->c:I

    const-string v1, "kugou"

    invoke-virtual {v3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    const-string v5, "\uff1a\u65e0\u7248\u6743\u6216\u8005\u4e3a\u6570\u5b57\u4e13\u8f91\uff0c\u4e0d\u80fd\u64ad\u653e\uff01\u4e0b\u4e00\u66f2"

    const-string v6, "cy"

    const/4 v7, 0x2

    const-string v8, "pay"

    const-string v9, "utf-8"

    const-string v10, "mp3"

    if-eqz v4, :cond_3

    sget v3, Lq0/a0;->c:I

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    invoke-interface {v3, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    sget v4, Lq0/a0;->c:I

    invoke-interface {v0, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map;

    invoke-interface {v4, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    invoke-virtual {v3, v8}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_2

    const-string v3, "5"

    invoke-virtual {v4, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_0

    goto :goto_3

    :cond_0
    sget v3, Lq0/a0;->c:I

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    const-string v3, "filehash"

    invoke-interface {v0, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v3, "\u4f4e\u9ad8"

    invoke-virtual {v0, v3}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v3

    const/4 v4, 0x0

    invoke-virtual {v0, v4, v3}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v0

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v3, v1, v0, v10}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v0, v3, v9}, Lq0/l;->b(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_1

    :goto_0
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iput v7, v0, Lcom/mylrc/mymusic/service/player;->o:I

    goto :goto_1

    :cond_1
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iput v2, v0, Lcom/mylrc/mymusic/service/player;->o:I

    :goto_1
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    :goto_2
    invoke-static {v0, v1}, Lcom/mylrc/mymusic/service/player;->c(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V

    goto/16 :goto_8

    :cond_2
    :goto_3
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    :goto_4
    sget-object v2, Lq0/a0;->e:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v5}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mylrc/mymusic/service/player;->a(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {v0}, Lcom/mylrc/mymusic/service/player;->b(Lcom/mylrc/mymusic/service/player;)V

    goto/16 :goto_8

    :cond_3
    const-string v1, "wyy"

    invoke-virtual {v3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    const-string v11, "id"

    if-eqz v4, :cond_7

    sget v3, Lq0/a0;->c:I

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    invoke-interface {v3, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    sget v4, Lq0/a0;->c:I

    invoke-interface {v0, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v4

    check-cast v4, Ljava/util/Map;

    invoke-interface {v4, v8}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v4

    invoke-virtual {v4}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v4

    sget v8, Lq0/a0;->c:I

    invoke-interface {v0, v8}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    invoke-interface {v0, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    const-string v6, "4"

    invoke-virtual {v4, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-nez v4, :cond_6

    const-string v4, "-200"

    invoke-virtual {v0, v4}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v0

    if-eqz v0, :cond_4

    goto :goto_6

    :cond_4
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v0, v1, v3, v10}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v3, v1, v9}, Lq0/l;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_5

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iput v7, v1, Lcom/mylrc/mymusic/service/player;->o:I

    goto :goto_5

    :cond_5
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iput v2, v1, Lcom/mylrc/mymusic/service/player;->o:I

    :goto_5
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {v1, v0}, Lcom/mylrc/mymusic/service/player;->c(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V

    goto/16 :goto_8

    :cond_6
    :goto_6
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    goto/16 :goto_4

    :cond_7
    const-string v1, "migu"

    invoke-virtual {v3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_8

    sget v3, Lq0/a0;->c:I

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    invoke-interface {v3, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    sget v4, Lq0/a0;->c:I

    invoke-interface {v0, v4}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    const-string v4, "lrc"

    invoke-interface {v0, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v4, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v4, v4, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v4, v1, v3, v10}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v0, v3, v9}, Lq0/l;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    :goto_7
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iput v2, v0, Lcom/mylrc/mymusic/service/player;->o:I

    goto/16 :goto_2

    :cond_8
    const-string v1, "qq"

    invoke-virtual {v3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v4

    if-eqz v4, :cond_9

    sget v3, Lq0/a0;->c:I

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v3, v1, v0, v10}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v0, v3, v9}, Lq0/l;->f(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_1

    goto/16 :goto_0

    :cond_9
    const-string v1, "kuwo"

    invoke-virtual {v3, v1}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_b

    sget v3, Lq0/a0;->c:I

    invoke-interface {v0, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/util/Map;

    invoke-interface {v0, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v3, v1, v0, v10}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v0, v3, v9}, Lq0/l;->c(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    goto :goto_7

    :cond_a
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$h;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v0, v0, Lcom/mylrc/mymusic/service/player;->t:Landroid/os/Handler;

    new-instance v1, Lcom/mylrc/mymusic/service/player$h$a;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/service/player$h$a;-><init>(Lcom/mylrc/mymusic/service/player$h;)V

    invoke-virtual {v0, v1}, Landroid/os/Handler;->post(Ljava/lang/Runnable;)Z

    :cond_b
    :goto_8
    return-void
.end method
