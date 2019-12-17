package com.mjc.smkguide.ui.info;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mjc.smkguide.R;

public class InfoDialog {

    private Context context;

    public InfoDialog(Context context) {
        this.context = context;
    }

    public void call(String data_title, String data_content, String data_name, String data_date) {

        final Dialog dlg = new Dialog(context);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.setContentView(R.layout.info_dialog);

        TextView title = (TextView) dlg.findViewById(R.id.title);
        TextView content = (TextView) dlg.findViewById(R.id.content);
        TextView name = (TextView) dlg.findViewById(R.id.name);
        TextView date = (TextView) dlg.findViewById(R.id.date);
        TextView exit = (TextView) dlg.findViewById(R.id.exit);

        title.setText(data_title);
        content.setText(data_content);
        name.setText(data_name);
        date.setText(data_date);

        dlg.show();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }
}
