.class Lcom/mylrc/mymusic/activity/p$e$b;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/widget/AdapterView$OnItemClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/activity/p$e;->onClick(Landroid/view/View;)V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic a:Lcom/mylrc/mymusic/activity/p$e;


# direct methods
.method constructor <init>(Lcom/mylrc/mymusic/activity/p$e;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$b;->a:Lcom/mylrc/mymusic/activity/p$e;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onItemClick(Landroid/widget/AdapterView;Landroid/view/View;IJ)V
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Landroid/widget/AdapterView<",
            "*>;",
            "Landroid/view/View;",
            "IJ)V"
        }
    .end annotation

    sput p3, Lq0/a0;->c:I

    new-instance p1, Landroid/content/Intent;

    iget-object p2, p0, Lcom/mylrc/mymusic/activity/p$e$b;->a:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p2, p2, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {p2}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object p2

    const-class p3, Lcom/mylrc/mymusic/service/player;

    invoke-direct {p1, p2, p3}, Landroid/content/Intent;-><init>(Landroid/content/Context;Ljava/lang/Class;)V

    sget p2, Landroid/os/Build$VERSION;->SDK_INT:I

    const/16 p3, 0x1a

    if-lt p2, p3, :cond_0

    iget-object p2, p0, Lcom/mylrc/mymusic/activity/p$e$b;->a:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p2, p2, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {p2, p1}, Landroid/app/Activity;->startForegroundService(Landroid/content/Intent;)Landroid/content/ComponentName;

    goto :goto_0

    :cond_0
    iget-object p2, p0, Lcom/mylrc/mymusic/activity/p$e$b;->a:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p2, p2, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {p2, p1}, Landroid/content/Context;->startService(Landroid/content/Intent;)Landroid/content/ComponentName;

    :goto_0
    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$e$b;->a:Lcom/mylrc/mymusic/activity/p$e;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p$e;->a:Lcom/mylrc/mymusic/activity/p;

    iget-object p1, p1, Lcom/mylrc/mymusic/activity/p;->y:Landroid/widget/SimpleAdapter;

    invoke-virtual {p1}, Landroid/widget/BaseAdapter;->notifyDataSetChanged()V

    return-void
.end method
