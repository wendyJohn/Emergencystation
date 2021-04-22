package com.sanleng.emergencystation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.sanleng.emergencystation.R;

/**
 * 使用说明
 *
 * @author qiaoshi
 */
public class InstructionsDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView notice;
    private ImageView image_instructions;
    private String image_type;


    public InstructionsDialog(Context context, String image_type) {
        super(context);
        this.context = context;
        this.image_type = image_type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        this.setContentView(R.layout.instructionsdialog);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失

        notice = findViewById(R.id.notice);
        image_instructions = findViewById(R.id.image_instructions);
        notice.setOnClickListener(this);

        if(image_type.equals("a")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.a_icon));
        }
        if(image_type.equals("b")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.b));
        }
        if(image_type.equals("c")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.c_icon));
        }
        if(image_type.equals("d")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.d));
        }
        if(image_type.equals("e")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.e));
        }
        if(image_type.equals("f")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.f));
        }
        if(image_type.equals("g")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.g));
        }
        if(image_type.equals("h")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.h_icon));
        }
        if(image_type.equals("i")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.i_icon));
        }
        if(image_type.equals("j")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.j_icon));
        }if(image_type.equals("k")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.k));
        }
        if(image_type.equals("l")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.l_icon));
        }
        if(image_type.equals("m")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.m));
        }
        if(image_type.equals("n")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.n));
        }if(image_type.equals("o")){
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.o_icon));
        }
        if(image_type.equals("p")) {
            image_instructions.setBackground(context.getResources().getDrawable(R.drawable.p));
        }

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            // 知道了
            case R.id.notice:
                dismiss();
                break;
            default:
                break;
        }
    }

}
