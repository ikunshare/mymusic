.class public Lcom/mylrc/mymusic/tool/a;
.super Ljava/lang/Object;
.source "SourceFile"

# interfaces
.implements Ljava/lang/Comparable;


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Object;",
        "Ljava/lang/Comparable<",
        "Lcom/mylrc/mymusic/tool/a;",
        ">;"
    }
.end annotation


# instance fields
.field private a:J

.field private b:Ljava/lang/String;

.field private c:Ljava/lang/String;

.field private d:Landroid/text/StaticLayout;

.field private e:F


# direct methods
.method constructor <init>(JLjava/lang/String;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/4 v0, 0x1

    iput v0, p0, Lcom/mylrc/mymusic/tool/a;->e:F

    iput-wide p1, p0, Lcom/mylrc/mymusic/tool/a;->a:J

    iput-object p3, p0, Lcom/mylrc/mymusic/tool/a;->b:Ljava/lang/String;

    return-void
.end method

.method private d()Ljava/lang/String;
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/a;->c:Ljava/lang/String;

    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result v0

    if-nez v0, :cond_0

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/a;->b:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "\n"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    iget-object v1, p0, Lcom/mylrc/mymusic/tool/a;->c:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    return-object v0

    :cond_0
    iget-object v0, p0, Lcom/mylrc/mymusic/tool/a;->b:Ljava/lang/String;

    return-object v0
.end method


# virtual methods
.method public a(Lcom/mylrc/mymusic/tool/a;)I
    .locals 4

    if-nez p1, :cond_0

    const/4 p1, -0x1

    return p1

    :cond_0
    iget-wide v0, p0, Lcom/mylrc/mymusic/tool/a;->a:J

    invoke-virtual {p1}, Lcom/mylrc/mymusic/tool/a;->g()J

    move-result-wide v2

    sub-long/2addr v0, v2

    long-to-int p1, v0

    return p1
.end method

.method b()I
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/a;->d:Landroid/text/StaticLayout;

    if-nez v0, :cond_0

    const/4 v0, 0x0

    return v0

    :cond_0
    invoke-virtual {v0}, Landroid/text/Layout;->getHeight()I

    move-result v0

    return v0
.end method

.method public c()F
    .locals 1

    iget v0, p0, Lcom/mylrc/mymusic/tool/a;->e:F

    return v0
.end method

.method public bridge synthetic compareTo(Ljava/lang/Object;)I
    .locals 0

    check-cast p1, Lcom/mylrc/mymusic/tool/a;

    invoke-virtual {p0, p1}, Lcom/mylrc/mymusic/tool/a;->a(Lcom/mylrc/mymusic/tool/a;)I

    move-result p1

    return p1
.end method

.method e()Landroid/text/StaticLayout;
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/a;->d:Landroid/text/StaticLayout;

    return-object v0
.end method

.method f()Ljava/lang/String;
    .locals 1

    iget-object v0, p0, Lcom/mylrc/mymusic/tool/a;->b:Ljava/lang/String;

    return-object v0
.end method

.method public g()J
    .locals 2

    iget-wide v0, p0, Lcom/mylrc/mymusic/tool/a;->a:J

    return-wide v0
.end method

.method h(Landroid/text/TextPaint;II)V
    .locals 8

    const/4 v0, 0x1

    if-eq p3, v0, :cond_1

    const/4 v0, 0x2

    if-eq p3, v0, :cond_0

    sget-object p3, Landroid/text/Layout$Alignment;->ALIGN_CENTER:Landroid/text/Layout$Alignment;

    :goto_0
    move-object v4, p3

    goto :goto_1

    :cond_0
    sget-object p3, Landroid/text/Layout$Alignment;->ALIGN_OPPOSITE:Landroid/text/Layout$Alignment;

    goto :goto_0

    :cond_1
    sget-object p3, Landroid/text/Layout$Alignment;->ALIGN_NORMAL:Landroid/text/Layout$Alignment;

    goto :goto_0

    :goto_1
    new-instance p3, Landroid/text/StaticLayout;

    invoke-direct {p0}, Lcom/mylrc/mymusic/tool/a;->d()Ljava/lang/String;

    move-result-object v1

    const/high16 v5, 0x3f800000    # 1.0f

    const/4 v6, 0x0

    const/4 v7, 0x0

    move-object v0, p3

    move-object v2, p1

    move v3, p2

    invoke-direct/range {v0 .. v7}, Landroid/text/StaticLayout;-><init>(Ljava/lang/CharSequence;Landroid/text/TextPaint;ILandroid/text/Layout$Alignment;FFZ)V

    iput-object p3, p0, Lcom/mylrc/mymusic/tool/a;->d:Landroid/text/StaticLayout;

    const/4 p1, 0x1

    iput p1, p0, Lcom/mylrc/mymusic/tool/a;->e:F

    return-void
.end method

.method public i(F)V
    .locals 0

    iput p1, p0, Lcom/mylrc/mymusic/tool/a;->e:F

    return-void
.end method

.method j(Ljava/lang/String;)V
    .locals 0

    iput-object p1, p0, Lcom/mylrc/mymusic/tool/a;->c:Ljava/lang/String;

    return-void
.end method
