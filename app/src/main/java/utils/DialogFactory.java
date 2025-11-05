package utils;


import android.app.Dialog;
import android.content.Context;
import com.mylrc.mymusic.R;
import java.util.Objects;

public class DialogFactory {

  public Dialog createDialog(Context context) {
    Dialog dialog = new Dialog(context, R.style.dialog);
    Objects.requireNonNull(dialog.getWindow()).setGravity(80);
    dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
    dialog.requestWindowFeature(1);
    return dialog;
  }
}