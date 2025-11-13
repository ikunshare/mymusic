.class Lcom/mylrc/mymusic/service/player$i;
.super Ljava/lang/Thread;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/service/player;->r()V
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

    iput-object p1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    invoke-direct {p0}, Ljava/lang/Thread;-><init>()V

    return-void
.end method


# virtual methods
.method public run()V
    .locals 12

    new-instance v0, Ljava/util/Random;

    invoke-direct {v0}, Ljava/util/Random;-><init>()V

    sget-object v1, Lq0/a0;->b:Ljava/util/List;

    invoke-interface {v1}, Ljava/util/List;->size()I

    move-result v1

    invoke-virtual {v0, v1}, Ljava/util/Random;->nextInt(I)I

    move-result v0

    sget-object v1, Lq0/a0;->b:Ljava/util/List;

    sget-object v2, Lq0/a0;->d:Ljava/lang/String;

    invoke-interface {v1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    const-string v4, "name"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    sput-object v3, Lq0/a0;->e:Ljava/lang/String;

    invoke-interface {v1, v0}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    const-string v4, "singer"

    invoke-interface {v3, v4}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    sput-object v3, Lq0/a0;->f:Ljava/lang/String;

    sput v0, Lq0/a0;->c:I

    const-string v0, "kugou"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    const-string v4, "\uff1a\u65e0\u7248\u6743\u6216\u8005\u4e3a\u6570\u5b57\u4e13\u8f91\uff0c\u4e0d\u80fd\u64ad\u653e\uff01\u4e0b\u4e00\u66f2"

    const-string v5, "cy"

    const-string v6, "pay"

    const/4 v7, 0x1

    const-string v8, "utf-8"

    const-string v9, "mp3"

    if-eqz v3, :cond_2

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    const-string v3, "filehash"

    invoke-interface {v2, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    const-string v3, "\u4f4e\u9ad8"

    invoke-virtual {v2, v3}, Ljava/lang/String;->indexOf(Ljava/lang/String;)I

    move-result v3

    const/4 v10, 0x0

    invoke-virtual {v2, v10, v3}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v2

    sget v3, Lq0/a0;->c:I

    invoke-interface {v1, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    sget v10, Lq0/a0;->c:I

    invoke-interface {v1, v10}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v3, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_1

    const-string v3, "5"

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    goto :goto_0

    :cond_0
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v1, v0, v2, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v2, v1, v8}, Lq0/l;->b(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    goto/16 :goto_3

    :cond_1
    :goto_0
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    :goto_1
    sget-object v2, Lq0/a0;->e:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, v1}, Lcom/mylrc/mymusic/service/player;->a(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    invoke-static {v0}, Lcom/mylrc/mymusic/service/player;->b(Lcom/mylrc/mymusic/service/player;)V

    goto/16 :goto_7

    :cond_2
    const-string v0, "wyy"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    const/4 v10, 0x2

    const-string v11, "id"

    if-eqz v3, :cond_5

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    sget v3, Lq0/a0;->c:I

    invoke-interface {v1, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v3

    check-cast v3, Ljava/util/Map;

    invoke-interface {v3, v6}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v3

    sget v6, Lq0/a0;->c:I

    invoke-interface {v1, v6}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1, v5}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    const-string v5, "4"

    invoke-virtual {v3, v5}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-nez v3, :cond_4

    const-string v3, "-200"

    invoke-virtual {v1, v3}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_3

    goto :goto_2

    :cond_3
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v1, v0, v2, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v1, v1, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v2, v1, v8}, Lq0/l;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_7

    goto :goto_5

    :cond_4
    :goto_2
    iget-object v0, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    goto :goto_1

    :cond_5
    const-string v0, "migu"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_6

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v2

    check-cast v2, Ljava/util/Map;

    invoke-interface {v2, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v2

    sget v3, Lq0/a0;->c:I

    invoke-interface {v1, v3}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    const-string v3, "lrc"

    invoke-interface {v1, v3}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v3, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v3, v3, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v3, v0, v2, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v1, v2, v8}, Lq0/l;->d(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    :goto_3
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iput v7, v1, Lcom/mylrc/mymusic/service/player;->o:I

    :goto_4
    invoke-static {v1, v0}, Lcom/mylrc/mymusic/service/player;->c(Lcom/mylrc/mymusic/service/player;Ljava/lang/String;)V

    goto :goto_7

    :cond_6
    const-string v0, "qq"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v3

    if-eqz v3, :cond_8

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v2, v0, v1, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v1, v2, v8}, Lq0/l;->f(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    move-result v1

    if-eqz v1, :cond_7

    :goto_5
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iput v10, v1, Lcom/mylrc/mymusic/service/player;->o:I

    goto :goto_6

    :cond_7
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iput v7, v1, Lcom/mylrc/mymusic/service/player;->o:I

    :goto_6
    iget-object v1, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    goto :goto_4

    :cond_8
    const-string v0, "kuwo"

    invoke-virtual {v2, v0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v2

    if-eqz v2, :cond_9

    sget v2, Lq0/a0;->c:I

    invoke-interface {v1, v2}, Ljava/util/List;->get(I)Ljava/lang/Object;

    move-result-object v1

    check-cast v1, Ljava/util/Map;

    invoke-interface {v1, v11}, Ljava/util/Map;->get(Ljava/lang/Object;)Ljava/lang/Object;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v1

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->f:Lcom/mylrc/mymusic/tool/musicurl;

    invoke-virtual {v2, v0, v1, v9}, Lcom/mylrc/mymusic/tool/musicurl;->h(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iget-object v2, p0, Lcom/mylrc/mymusic/service/player$i;->a:Lcom/mylrc/mymusic/service/player;

    iget-object v2, v2, Lcom/mylrc/mymusic/service/player;->b:Ljava/lang/String;

    invoke-static {v1, v2, v8}, Lq0/l;->c(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z

    goto :goto_3

    :cond_9
    :goto_7
    return-void
.end method
