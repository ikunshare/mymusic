.class Lcom/mylrc/mymusic/activity/p$b;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Landroid/view/View$OnClickListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/mylrc/mymusic/activity/p;->onCreate(Landroid/os/Bundle;)V
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

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p$b;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onClick(Landroid/view/View;)V
    .locals 0

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p$b;->a:Lcom/mylrc/mymusic/activity/p;

    invoke-virtual {p1}, Lcom/mylrc/mymusic/activity/p;->finish()V

    return-void
.end method
