.class Lcom/mylrc/mymusic/tool/LrcView$b$a;
.super Landroid/os/AsyncTask;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/tool/LrcView$b;->run()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation

.annotation system Ldalvik/annotation/Signature;
    value = {
        "Landroid/os/AsyncTask<",
        "Ljava/io/File;",
        "Ljava/lang/Integer;",
        "Ljava/util/List<",
        "Lcom/mylrc/mymusic/tool/a;",
        ">;>;"
    }
.end annotation


# instance fields
.field final synthetic a:Ljava/lang/String;

.field final synthetic b:Lcom/mylrc/mymusic/tool/LrcView$b;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/tool/LrcView$b;Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$b$a;->b:Lcom/mylrc/mymusic/tool/LrcView$b;

    iput-object p2, p0, Lcom/mylrc/mymusic/tool/LrcView$b$a;->a:Ljava/lang/String;

    invoke-direct {p0}, Landroid/os/AsyncTask;-><init>()V

    return-void
.end method


# virtual methods
.method protected varargs a([Ljava/io/File;)Ljava/util/List;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "([",
            "Ljava/io/File;",
            ")",
            "Ljava/util/List<",
            "Lcom/mylrc/mymusic/tool/a;",
            ">;"
        }
    .end annotation

    invoke-static {p1}, Lcom/mylrc/mymusic/tool/b;->e([Ljava/io/File;)Ljava/util/List;

    move-result-object p1

    return-object p1
.end method

.method protected b(Ljava/util/List;)V
    .locals 2
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/List<",
            "Lcom/mylrc/mymusic/tool/a;",
            ">;)V"
        }
    .end annotation

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$b$a;->b:Lcom/mylrc/mymusic/tool/LrcView$b;

    iget-object v0, v0, Lcom/mylrc/mymusic/tool/LrcView$b;->c:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v0}, Lcom/mylrc/mymusic/tool/LrcView;->q(Lcom/mylrc/mymusic/tool/LrcView;)Ljava/lang/Object;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/LrcView$b$a;->a:Ljava/lang/String;

    if-ne v0, v1, :cond_0

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/LrcView$b$a;->b:Lcom/mylrc/mymusic/tool/LrcView$b;

    iget-object v0, v0, Lcom/mylrc/mymusic/tool/LrcView$b;->c:Lcom/mylrc/mymusic/tool/LrcView;

    invoke-static {v0, p1}, Lcom/mylrc/mymusic/tool/LrcView;->s(Lcom/mylrc/mymusic/tool/LrcView;Ljava/util/List;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/tool/LrcView$b$a;->b:Lcom/mylrc/mymusic/tool/LrcView$b;

    iget-object p1, p1, Lcom/mylrc/mymusic/tool/LrcView$b;->c:Lcom/mylrc/mymusic/tool/LrcView;

    const/4 v0, 0x0

    invoke-static {p1, v0}, Lcom/mylrc/mymusic/tool/LrcView;->u(Lcom/mylrc/mymusic/tool/LrcView;Ljava/lang/Object;)V

    :cond_0
    return-void
.end method

.method protected bridge synthetic doInBackground([Ljava/lang/Object;)Ljava/lang/Object;
    .locals 0

    check-cast p1, [Ljava/io/File;

    invoke-virtual {p0, p1}, Lcom/mylrc/mymusic/tool/LrcView$b$a;->a([Ljava/io/File;)Ljava/util/List;

    move-result-object p1

    return-object p1
.end method

.method protected bridge synthetic onPostExecute(Ljava/lang/Object;)V
    .locals 0

    check-cast p1, Ljava/util/List;

    invoke-virtual {p0, p1}, Lcom/mylrc/mymusic/tool/LrcView$b$a;->b(Ljava/util/List;)V

    return-void
.end method
