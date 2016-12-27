package com.sodino.ripple;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/12/27.
 */

public class RippleTextView extends TextView {
    public RippleTextView(Context context) {
        super(context);
    }

    public RippleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RippleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RippleTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MovementMethod movement = getMovementMethod();
        if ((movement != null || onCheckIsTextEditor()) && isEnabled()
                && getText() instanceof Spannable && getLayout() != null) {

            Spannable buffer = (Spannable) getText();

            if (movement != null) {
                // 这个if的作用域内的代码就是movement.onTouchEvent()的代码实现.所以不再需要调用movement.onTouchEvent()
                // 或者再继承自LinkMovementMethod重写一个onTouchEvent()方法也行.
//                handled |= movement.onTouchEvent(this, (Spannable) getText(), event);
                int action = event.getAction();

                if (action == MotionEvent.ACTION_UP ||
                        action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= getTotalPaddingLeft();
                    y -= getTotalPaddingTop();

                    x += getScrollX();
                    y += getScrollY();

                    Layout layout = getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);

                    ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(this);
                        } else if (action == MotionEvent.ACTION_DOWN) {
                            Selection.setSelection(buffer,
                                    buffer.getSpanStart(link[0]),
                                    buffer.getSpanEnd(link[0]));
                        }

                        return true;
                    } else {
                        Selection.removeSelection(buffer);
                    }
                }
            }
        }

        return false;
    }
}
