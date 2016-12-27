package com.sodino.ripple;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.RippleDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/27.
 */
public class RippleAdapter extends RecyclerView.Adapter<RippleHolder> implements View.OnClickListener {
    public static final int RIPPLE_NO_CHILD_LAYERS_OR_MASK = 0;
    public static final int RIPPLE_HARDWARE_OFF = RIPPLE_NO_CHILD_LAYERS_OR_MASK + 1;
    public static final int RIPPLE_WITH_CHILD_LAYER_NO_MASK = RIPPLE_HARDWARE_OFF + 1;
    public static final int RIPPLE_WITH_CHILD_LAYER_and_MASK = RIPPLE_WITH_CHILD_LAYER_NO_MASK + 1;
    public static final int RIPPLE_WITH_SELECTOR = RIPPLE_WITH_CHILD_LAYER_and_MASK + 1;
    public static final int RIPPLE_WITH_PICTURE_MASK = RIPPLE_WITH_SELECTOR + 1;
    public static final int RIPPLE_WITH_SHAPE_MASK = RIPPLE_WITH_PICTURE_MASK + 1;
    public static final int RIPPLE_by_FOREGROUND = RIPPLE_WITH_SHAPE_MASK + 1;
    public static final int RIPPLE_COMPATIBLE_WITH_ClickableSpan = RIPPLE_by_FOREGROUND + 1;
    public static final int RIPPLE_END = RIPPLE_COMPATIBLE_WITH_ClickableSpan + 1;

    @Override
    public int getItemCount() {
        return RIPPLE_END;
    }

    @Override
    public RippleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        int rippleID = R.drawable.ripple_with_no_child_layers_or_mask;
        CharSequence charsequence = "";
        switch(viewType) {
            case RIPPLE_NO_CHILD_LAYERS_OR_MASK:{
                rippleID = R.drawable.ripple_with_no_child_layers_or_mask;
                charsequence = "Ripple NO Child Layers or Mask \nthe drawing region may extend outside of the Drawable bounds";
            }break;
            case RIPPLE_HARDWARE_OFF: {
                rippleID = R.drawable.ripple_with_no_child_layers_or_mask;
                charsequence = "Ripple NO Child Layers or Mask \nbut HARDWARE OFF";
                v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }break;
            case RIPPLE_WITH_CHILD_LAYER_NO_MASK:{
                rippleID = R.drawable.ripple_with_color_red_no_mask;
                charsequence = "Ripple With Child Layer(Color Red) NO Mask";
            }break;
            case RIPPLE_WITH_CHILD_LAYER_and_MASK: {
                rippleID = R.drawable.ripple_with_color_red_and_mask;
                charsequence = "Ripple With Child Layer(Color Red) and Mask";
            }break;
            case RIPPLE_WITH_PICTURE_MASK: {
                rippleID = R.drawable.ripple_with_picture_and_mask;
                charsequence = "Ripple With Picture and Mask";
            }break;
            case RIPPLE_WITH_SHAPE_MASK: {
                rippleID = R.drawable.ripple_with_shape_and_mask;
                charsequence = "Ripple With Shape and Mask";
            }break;
            case RIPPLE_WITH_SELECTOR: {
                rippleID = R.drawable.ripple_with_selector;
                charsequence = "Ripple With Selector \nthe drawing region will be drawn from RED gradient to GREEN.";
            }break;
            case RIPPLE_by_FOREGROUND:{
                rippleID = R.drawable.ripple_by_foreground;
                charsequence = "Ripple by 'foreground'\nOnly FrameLayout support.";
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_framelayout, parent, false);
                ((TextView)v.findViewById(R.id.txt)).setTextColor(Color.YELLOW);
            }break;
            case RIPPLE_COMPATIBLE_WITH_ClickableSpan:{
                rippleID = R.drawable.ripple_with_color_red_and_mask;
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                String str = "Ripple Compatible With ClickableSpan";
                SpannableStringBuilder ssBuilder = new SpannableStringBuilder(str);
                ssBuilder.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        Toast.makeText(widget.getContext(), "U click me!", Toast.LENGTH_SHORT).show();
                    }
                }, str.length() - 13, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                charsequence = ssBuilder;
            }break;
        }

        // add onClickListener and ripple do active.
        v.setOnClickListener(this);
        RippleDrawable rippleDrawable = (RippleDrawable) parent.getResources().getDrawable(rippleID, null);
        if (v instanceof FrameLayout) {
            ((FrameLayout)v).setForeground(rippleDrawable);
        } else {
            v.setBackground(rippleDrawable);
        }
        RippleHolder holder = new RippleHolder(v, charsequence);
        v.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(RippleHolder holder, int position) {
        // DO NOTHING
    }

    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
    }
}

class RippleHolder extends RecyclerView.ViewHolder {
    int viewType;
    public RippleHolder(View itemView, CharSequence chars) {
        super(itemView);
        TextView txt = (TextView) itemView.findViewById(R.id.txt);
        if (txt != null) {
            txt.setText(chars);
            if (chars instanceof SpannableStringBuilder) {
                txt.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }
}
