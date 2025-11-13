.class public Lcom/mylrc/mymusic/activity/p;
.super Landroid/app/Activity;
.source "SourceFile"


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/mylrc/mymusic/activity/p$k;
    }
.end annotation


# instance fields
.field A:Ljava/util/Map;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/Map<",
            "Ljava/lang/Integer;",
            "Ljava/lang/Boolean;",
            ">;"
        }
    .end annotation
.end field

.field B:Landroid/widget/RelativeLayout;

.field C:Landroid/widget/ImageView;

.field D:Ljava/lang/String;

.field E:Landroid/os/Handler;

.field F:Ljava/lang/Runnable;

.field a:Landroid/widget/TextView;

.field b:Landroid/widget/TextView;

.field c:Landroid/widget/TextView;

.field d:Landroid/widget/TextView;

.field e:Landroid/widget/SeekBar;

.field f:I

.field g:I

.field h:Landroid/media/MediaPlayer;

.field i:Landroid/widget/RelativeLayout;

.field j:Landroid/widget/RelativeLayout;

.field k:Landroid/widget/RelativeLayout;

.field l:Landroid/widget/RelativeLayout;

.field m:Landroid/widget/RelativeLayout;

.field n:Landroid/widget/RelativeLayout;

.field o:Landroid/widget/ImageView;

.field p:Landroid/widget/ImageView;

.field q:Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/List<",
            "Lcom/mylrc/mymusic/tool/a;",
            ">;"
        }
    .end annotation
.end field

.field r:Ljava/lang/String;

.field s:Ljava/lang/String;

.field t:Lq0/b0;

.field u:Lcom/mylrc/mymusic/tool/LrcView;

.field v:Landroid/widget/LinearLayout;

.field w:Landroid/content/SharedPreferences;

.field x:I

.field y:Landroid/widget/SimpleAdapter;

.field z:Landroid/graphics/Bitmap;


# direct methods
.method public constructor <init>()V
    .locals 2

    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    new-instance v0, Ljava/util/ArrayList;

    invoke-direct {v0}, Ljava/util/ArrayList;-><init>()V

    iput-object v0, p0, Lcom/mylrc/mymusic/activity/p;->q:Ljava/util/List;

    const/4 v0, 0x0

    iput-object v0, p0, Lcom/mylrc/mymusic/activity/p;->z:Landroid/graphics/Bitmap;

    new-instance v0, Lcom/mylrc/mymusic/activity/p$j;

    invoke-static {}, Landroid/os/Looper;->getMainLooper()Landroid/os/Looper;

    move-result-object v1

    invoke-direct {v0, p0, v1}, Lcom/mylrc/mymusic/activity/p$j;-><init>(Lcom/mylrc/mymusic/activity/p;Landroid/os/Looper;)V

    iput-object v0, p0, Lcom/mylrc/mymusic/activity/p;->E:Landroid/os/Handler;

    new-instance v0, Lcom/mylrc/mymusic/activity/p$a;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/activity/p$a;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    iput-object v0, p0, Lcom/mylrc/mymusic/activity/p;->F:Ljava/lang/Runnable;

    return-void
.end method

.method static bridge synthetic a(Lcom/mylrc/mymusic/activity/p;)V
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/activity/p;->e()V

    return-void
.end method

.method static bridge synthetic b(Lcom/mylrc/mymusic/activity/p;)V
    .locals 0

    invoke-direct {p0}, Lcom/mylrc/mymusic/activity/p;->h()V

    return-void
.end method

.method public static d(Landroid/graphics/Bitmap;IIII)Landroid/graphics/Bitmap;
    .locals 7

    if-nez p0, :cond_0

    const/4 p0, 0x0

    return-object p0

    :cond_0
    invoke-virtual {p0}, Landroid/graphics/Bitmap;->getHeight()I

    move-result v0

    invoke-virtual {p0}, Landroid/graphics/Bitmap;->getWidth()I

    move-result v1

    int-to-float v2, p1

    const/high16 v3, 0x3f800000    # 1.0f

    mul-float v2, v2, v3

    int-to-float v1, v1

    div-float/2addr v2, v1

    int-to-float v1, p2

    mul-float v1, v1, v3

    int-to-float v0, v0

    div-float/2addr v1, v0

    new-instance v0, Landroid/graphics/Matrix;

    invoke-direct {v0}, Landroid/graphics/Matrix;-><init>()V

    invoke-virtual {v0, v2, v1}, Landroid/graphics/Matrix;->setScale(FF)V

    sget-object v1, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {p1, p2, v1}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v1

    new-instance v2, Landroid/graphics/Canvas;

    invoke-direct {v2, v1}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    new-instance v3, Landroid/graphics/Paint;

    const/4 v4, 0x1

    invoke-direct {v3, v4}, Landroid/graphics/Paint;-><init>(I)V

    new-instance v5, Landroid/graphics/BitmapShader;

    sget-object v6, Landroid/graphics/Shader$TileMode;->CLAMP:Landroid/graphics/Shader$TileMode;

    invoke-direct {v5, p0, v6, v6}, Landroid/graphics/BitmapShader;-><init>(Landroid/graphics/Bitmap;Landroid/graphics/Shader$TileMode;Landroid/graphics/Shader$TileMode;)V

    invoke-virtual {v5, v0}, Landroid/graphics/Shader;->setLocalMatrix(Landroid/graphics/Matrix;)V

    invoke-virtual {v3, v5}, Landroid/graphics/Paint;->setShader(Landroid/graphics/Shader;)Landroid/graphics/Shader;

    new-instance p0, Landroid/graphics/RectF;

    int-to-float v0, p4

    sub-int/2addr p1, p4

    int-to-float p1, p1

    sub-int/2addr p2, p4

    int-to-float p2, p2

    invoke-direct {p0, v0, v0, p1, p2}, Landroid/graphics/RectF;-><init>(FFFF)V

    int-to-float p1, p3

    invoke-virtual {v2, p0, p1, p1, v3}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    if-lez p4, :cond_1

    new-instance p2, Landroid/graphics/Paint;

    invoke-direct {p2, v4}, Landroid/graphics/Paint;-><init>(I)V

    const p3, -0xff0100

    invoke-virtual {p2, p3}, Landroid/graphics/Paint;->setColor(I)V

    sget-object p3, Landroid/graphics/Paint$Style;->STROKE:Landroid/graphics/Paint$Style;

    invoke-virtual {p2, p3}, Landroid/graphics/Paint;->setStyle(Landroid/graphics/Paint$Style;)V

    invoke-virtual {p2, v0}, Landroid/graphics/Paint;->setStrokeWidth(F)V

    invoke-virtual {v2, p0, p1, p1, p2}, Landroid/graphics/Canvas;->drawRoundRect(Landroid/graphics/RectF;FFLandroid/graphics/Paint;)V

    :cond_1
    return-object v1
.end method

.method private e()V
    .locals 4

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->e:Landroid/widget/SeekBar;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$h;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/activity/p$h;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    invoke-virtual {v0, v1}, Landroid/widget/SeekBar;->setOnSeekBarChangeListener(Landroid/widget/SeekBar$OnSeekBarChangeListener;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->m:Landroid/widget/RelativeLayout;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$i;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/activity/p$i;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    invoke-virtual {v0, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->a:Landroid/widget/TextView;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v2, Lq0/a0;->e:Ljava/lang/String;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v2, ""

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->b:Landroid/widget/TextView;

    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    sget-object v3, Lq0/a0;->f:Ljava/lang/String;

    invoke-virtual {v1, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->y:Landroid/widget/SimpleAdapter;

    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/widget/BaseAdapter;->notifyDataSetChanged()V

    :cond_0
    const/4 v0, 0x1

    invoke-direct {p0, v0}, Lcom/mylrc/mymusic/activity/p;->f(I)V

    return-void
.end method

.method private f(I)V
    .locals 1

    new-instance v0, Landroid/os/Message;

    invoke-direct {v0}, Landroid/os/Message;-><init>()V

    iput p1, v0, Landroid/os/Message;->what:I

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->E:Landroid/os/Handler;

    invoke-virtual {p1, v0}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    return-void
.end method

.method private h()V
    .locals 4

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p;->D:Ljava/lang/String;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "listen_tmp.bin"

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v0

    new-instance v1, Ljava/io/File;

    invoke-direct {v1, v0}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v1

    if-eqz v1, :cond_0

    invoke-static {v0}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object v0

    goto :goto_0

    :cond_0
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    const v1, 0x7f0700ae

    invoke-static {v0, v1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object v0

    :goto_0
    iput-object v0, p0, Lcom/mylrc/mymusic/activity/p;->z:Landroid/graphics/Bitmap;

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->z:Landroid/graphics/Bitmap;

    const/16 v1, 0x23

    const/4 v2, 0x0

    const/16 v3, 0x2d0

    invoke-static {v0, v3, v3, v1, v2}, Lcom/mylrc/mymusic/activity/p;->d(Landroid/graphics/Bitmap;IIII)Landroid/graphics/Bitmap;

    move-result-object v0

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p;->C:Landroid/widget/ImageView;

    invoke-virtual {v1, v0}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    return-void
.end method


# virtual methods
.method public c(Landroid/app/Activity;III)Landroid/graphics/drawable/BitmapDrawable;
    .locals 2

    new-instance v0, Landroid/graphics/drawable/BitmapDrawable;

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-static {v1, p2}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p2

    const/4 v1, 0x1

    invoke-static {p2, p3, p4, v1}, Landroid/graphics/Bitmap;->createScaledBitmap(Landroid/graphics/Bitmap;IIZ)Landroid/graphics/Bitmap;

    move-result-object p2

    invoke-direct {v0, p2}, Landroid/graphics/drawable/BitmapDrawable;-><init>(Landroid/graphics/Bitmap;)V

    invoke-virtual {v0}, Landroid/graphics/drawable/BitmapDrawable;->getBitmap()Landroid/graphics/Bitmap;

    move-result-object p2

    invoke-virtual {p2}, Landroid/graphics/Bitmap;->getDensity()I

    move-result p2

    if-nez p2, :cond_0

    invoke-virtual {p1}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    invoke-virtual {p1}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object p1

    invoke-virtual {v0, p1}, Landroid/graphics/drawable/BitmapDrawable;->setTargetDensity(Landroid/util/DisplayMetrics;)V

    :cond_0
    return-object v0
.end method

.method public finish()V
    .locals 2

    invoke-super {p0}, Landroid/app/Activity;->finish()V

    const/4 v0, 0x0

    const v1, 0x7f010013

    invoke-virtual {p0, v0, v1}, Landroid/app/Activity;->overridePendingTransition(II)V

    return-void
.end method

.method protected g(I)V
    .locals 2

    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "com.mylrc.mymusic.ac"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    const-string v1, "control"

    invoke-virtual {v0, v1, p1}, Landroid/content/Intent;->putExtra(Ljava/lang/String;I)Landroid/content/Intent;

    invoke-virtual {p0, v0}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    return-void
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 5

    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    const p1, 0x7f010012

    const/4 v0, 0x0

    invoke-virtual {p0, p1, v0}, Landroid/app/Activity;->overridePendingTransition(II)V

    new-instance p1, Lq0/i;

    invoke-direct {p1, p0}, Lq0/i;-><init>(Landroid/app/Activity;)V

    sget-object v1, Lq0/j;->a:Lq0/j;

    invoke-virtual {p1, v1}, Lq0/i;->a(Lq0/j;)V

    const p1, 0x7f0b0057

    invoke-virtual {p0, p1}, Landroid/app/Activity;->setContentView(I)V

    const p1, 0x7f0801e6

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/TextView;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->a:Landroid/widget/TextView;

    const p1, 0x7f0801b1

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/TextView;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->b:Landroid/widget/TextView;

    const p1, 0x7f0801e4

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/TextView;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->c:Landroid/widget/TextView;

    const p1, 0x7f0801e5

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/TextView;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->d:Landroid/widget/TextView;

    const p1, 0x7f08014b

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/LinearLayout;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->v:Landroid/widget/LinearLayout;

    const p1, 0x7f080155

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/SeekBar;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->e:Landroid/widget/SeekBar;

    const p1, 0x7f08013c

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/RelativeLayout;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->n:Landroid/widget/RelativeLayout;

    const p1, 0x7f080152

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/RelativeLayout;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->i:Landroid/widget/RelativeLayout;

    const p1, 0x7f08010e

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/RelativeLayout;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->k:Landroid/widget/RelativeLayout;

    const p1, 0x7f0801f7

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/RelativeLayout;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->l:Landroid/widget/RelativeLayout;

    const p1, 0x7f080153

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/RelativeLayout;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->m:Landroid/widget/RelativeLayout;

    const p1, 0x7f080154

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/RelativeLayout;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->B:Landroid/widget/RelativeLayout;

    const p1, 0x7f080147

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->o:Landroid/widget/ImageView;

    const p1, 0x7f080148

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->p:Landroid/widget/ImageView;

    const p1, 0x7f080159

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/RelativeLayout;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->j:Landroid/widget/RelativeLayout;

    const p1, 0x7f080149

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Landroid/widget/ImageView;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->C:Landroid/widget/ImageView;

    const p1, 0x7f080150

    invoke-virtual {p0, p1}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object p1

    check-cast p1, Lcom/mylrc/mymusic/tool/LrcView;

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->u:Lcom/mylrc/mymusic/tool/LrcView;

    const-string p1, "pms"

    invoke-virtual {p0, p1, v0}, Landroid/content/Context;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object p1

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->w:Landroid/content/SharedPreferences;

    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v1, 0x7f0700b0

    invoke-static {p1, v1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    invoke-virtual {p0}, Landroid/content/Context;->getFilesDir()Ljava/io/File;

    move-result-object v1

    invoke-virtual {v1}, Ljava/io/File;->getParent()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "/app_tmpFile/"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->D:Ljava/lang/String;

    new-instance p1, Landroid/util/DisplayMetrics;

    invoke-direct {p1}, Landroid/util/DisplayMetrics;-><init>()V

    invoke-virtual {p0}, Landroid/app/Activity;->getWindowManager()Landroid/view/WindowManager;

    move-result-object v1

    invoke-interface {v1}, Landroid/view/WindowManager;->getDefaultDisplay()Landroid/view/Display;

    move-result-object v1

    invoke-virtual {v1, p1}, Landroid/view/Display;->getMetrics(Landroid/util/DisplayMetrics;)V

    iget p1, p1, Landroid/util/DisplayMetrics;->widthPixels:I

    const/16 v1, 0x5a0

    const/16 v2, 0x23

    const v3, 0x7f0700cd

    const/16 v4, 0x2d0

    if-lt p1, v1, :cond_0

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->e:Landroid/widget/SeekBar;

    const/16 v1, 0x55

    :goto_0
    invoke-virtual {p0, p0, v3, v1, v1}, Lcom/mylrc/mymusic/activity/p;->c(Landroid/app/Activity;III)Landroid/graphics/drawable/BitmapDrawable;

    move-result-object v1

    :goto_1
    invoke-virtual {p1, v1}, Landroid/widget/AbsSeekBar;->setThumb(Landroid/graphics/drawable/Drawable;)V

    goto :goto_2

    :cond_0
    if-lt p1, v4, :cond_1

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->e:Landroid/widget/SeekBar;

    const/16 v1, 0x37

    goto :goto_0

    :cond_1
    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->e:Landroid/widget/SeekBar;

    invoke-virtual {p0, p0, v3, v2, v2}, Lcom/mylrc/mymusic/activity/p;->c(Landroid/app/Activity;III)Landroid/graphics/drawable/BitmapDrawable;

    move-result-object v1

    goto :goto_1

    :goto_2
    invoke-static {}, Landroid/os/Environment;->getExternalStorageDirectory()Ljava/io/File;

    move-result-object p1

    invoke-virtual {p1}, Ljava/io/File;->getAbsolutePath()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->r:Ljava/lang/String;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p;->D:Ljava/lang/String;

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "listen_tmp.lrc"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->s:Ljava/lang/String;

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->i:Landroid/widget/RelativeLayout;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$b;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/activity/p$b;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    invoke-virtual {p1, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->n:Landroid/widget/RelativeLayout;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$c;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/activity/p$c;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    invoke-virtual {p1, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->l:Landroid/widget/RelativeLayout;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$d;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/activity/p$d;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    invoke-virtual {p1, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->j:Landroid/widget/RelativeLayout;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$e;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/activity/p$e;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    invoke-virtual {p1, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->k:Landroid/widget/RelativeLayout;

    new-instance v1, Lcom/mylrc/mymusic/activity/p$f;

    invoke-direct {v1, p0}, Lcom/mylrc/mymusic/activity/p$f;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    invoke-virtual {p1, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    sget p1, Lq0/a0;->g:I

    if-nez p1, :cond_2

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->p:Landroid/widget/ImageView;

    const v1, 0x7f07005d

    :goto_3
    invoke-virtual {p0, v1}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {p1, v1}, Landroid/view/View;->setBackground(Landroid/graphics/drawable/Drawable;)V

    goto :goto_4

    :cond_2
    const/4 v1, 0x1

    if-ne p1, v1, :cond_3

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->p:Landroid/widget/ImageView;

    const v1, 0x7f0700ed

    goto :goto_3

    :cond_3
    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->p:Landroid/widget/ImageView;

    const v1, 0x7f070102

    goto :goto_3

    :goto_4
    new-instance p1, Lcom/mylrc/mymusic/activity/p$k;

    invoke-direct {p1, p0}, Lcom/mylrc/mymusic/activity/p$k;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    new-instance v1, Landroid/content/IntentFilter;

    invoke-direct {v1}, Landroid/content/IntentFilter;-><init>()V

    const-string v3, "com.music.upview"

    invoke-virtual {v1, v3}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string v3, "com.music.upview2"

    invoke-virtual {v1, v3}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    const-string v3, "com.music.exit"

    invoke-virtual {v1, v3}, Landroid/content/IntentFilter;->addAction(Ljava/lang/String;)V

    invoke-virtual {p0, p1, v1}, Landroid/content/Context;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;)Landroid/content/Intent;

    new-instance p1, Ljava/lang/StringBuilder;

    invoke-direct {p1}, Ljava/lang/StringBuilder;-><init>()V

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p;->D:Ljava/lang/String;

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "listen_tmp.bin"

    invoke-virtual {p1, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    sget-object v1, Lcom/mylrc/mymusic/service/player;->v:Landroid/media/MediaPlayer;

    iput-object v1, p0, Lcom/mylrc/mymusic/activity/p;->h:Landroid/media/MediaPlayer;

    new-instance v1, Ljava/io/File;

    invoke-direct {v1, p1}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    invoke-virtual {v1}, Ljava/io/File;->exists()Z

    move-result v1

    if-eqz v1, :cond_4

    invoke-static {p1}, Landroid/graphics/BitmapFactory;->decodeFile(Ljava/lang/String;)Landroid/graphics/Bitmap;

    move-result-object p1

    goto :goto_5

    :cond_4
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object p1

    const v1, 0x7f0700ae

    invoke-static {p1, v1}, Landroid/graphics/BitmapFactory;->decodeResource(Landroid/content/res/Resources;I)Landroid/graphics/Bitmap;

    move-result-object p1

    :goto_5
    iput-object p1, p0, Lcom/mylrc/mymusic/activity/p;->z:Landroid/graphics/Bitmap;

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->z:Landroid/graphics/Bitmap;

    invoke-static {p1, v4, v4, v2, v0}, Lcom/mylrc/mymusic/activity/p;->d(Landroid/graphics/Bitmap;IIII)Landroid/graphics/Bitmap;

    move-result-object p1

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->C:Landroid/widget/ImageView;

    invoke-virtual {v0, p1}, Landroid/widget/ImageView;->setImageBitmap(Landroid/graphics/Bitmap;)V

    iget-object p1, p0, Lcom/mylrc/mymusic/activity/p;->B:Landroid/widget/RelativeLayout;

    new-instance v0, Lcom/mylrc/mymusic/activity/p$g;

    invoke-direct {v0, p0}, Lcom/mylrc/mymusic/activity/p$g;-><init>(Lcom/mylrc/mymusic/activity/p;)V

    invoke-virtual {p1, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    invoke-direct {p0}, Lcom/mylrc/mymusic/activity/p;->e()V

    return-void
.end method

.method protected onDestroy()V
    .locals 2

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->E:Landroid/os/Handler;

    iget-object v1, p0, Lcom/mylrc/mymusic/activity/p;->F:Ljava/lang/Runnable;

    invoke-virtual {v0, v1}, Landroid/os/Handler;->removeCallbacks(Ljava/lang/Runnable;)V

    invoke-super {p0}, Landroid/app/Activity;->onDestroy()V

    return-void
.end method

.method public onKeyDown(ILandroid/view/KeyEvent;)Z
    .locals 1

    const/4 v0, 0x4

    if-ne p1, v0, :cond_0

    invoke-virtual {p0}, Lcom/mylrc/mymusic/activity/p;->finish()V

    const/4 p1, 0x1

    return p1

    :cond_0
    invoke-super {p0, p1, p2}, Landroid/app/Activity;->onKeyDown(ILandroid/view/KeyEvent;)Z

    move-result p1

    return p1
.end method

.method protected onPause()V
    .locals 3

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->w:Landroid/content/SharedPreferences;

    const-string v1, "win"

    const/4 v2, 0x1

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-nez v0, :cond_0

    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "com.music.add"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    invoke-virtual {p0, v0}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    :cond_0
    invoke-super {p0}, Landroid/app/Activity;->onPause()V

    return-void
.end method

.method public onPointerCaptureChanged(Z)V
    .locals 0

    return-void
.end method

.method protected onResume()V
    .locals 3

    iget-object v0, p0, Lcom/mylrc/mymusic/activity/p;->w:Landroid/content/SharedPreferences;

    const-string v1, "win"

    const/4 v2, 0x1

    invoke-interface {v0, v1, v2}, Landroid/content/SharedPreferences;->getInt(Ljava/lang/String;I)I

    move-result v0

    if-nez v0, :cond_0

    new-instance v0, Landroid/content/Intent;

    invoke-direct {v0}, Landroid/content/Intent;-><init>()V

    const-string v1, "com.music.remove"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->setAction(Ljava/lang/String;)Landroid/content/Intent;

    invoke-virtual {p0, v0}, Landroid/content/Context;->sendBroadcast(Landroid/content/Intent;)V

    :cond_0
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    return-void
.end method
