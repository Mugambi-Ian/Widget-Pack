package nenecorp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;

public class NumPad extends LinearLayout implements View.OnClickListener {
    private String num = "";
    private InputListener inputListener;
    private Drawable itemDrawable;
    private int textColor;
    private int itemHeight, itemWidth, itemMargin;
    private final float DEF_VAL = -20;
    private int itemElevation;
    private int textSize;

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    public NumPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttrs(attrs);
    }

    private void loadAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NumPad);
        itemDrawable = a.getDrawable(R.styleable.NumPad_pnDrawable);
        if (itemDrawable == null) {
            itemDrawable = getContext().getDrawable(R.drawable.box_numpad);
        }
        textColor = a.getColor(R.styleable.NumPad_pnColor, Color.BLACK);
        itemHeight = (int) a.getDimension(R.styleable.NumPad_pnItemHeight, DEF_VAL);
        itemWidth = (int) a.getDimension(R.styleable.NumPad_pnItemWidth, DEF_VAL);
        itemMargin = (int) a.getDimension(R.styleable.NumPad_pnItemMargin, DEF_VAL);
        itemElevation = (int) a.getDimension(R.styleable.NumPad_pnItemElevation, DEF_VAL);
        textSize = (int) a.getDimension(R.styleable.NumPad_pnTextSize, DEF_VAL);
        initView();
    }

    private void updateView(TextView v) {
        LayoutParams l = (LayoutParams) v.getLayoutParams();
        if (itemMargin != DEF_VAL) {
            l.setMargins(itemMargin, itemMargin, itemMargin, itemMargin);
        }
        if (itemWidth != DEF_VAL) {
            l.width = itemWidth;
        }
        if (itemHeight != DEF_VAL) {
            l.height = itemHeight;
        }
        v.setLayoutParams(l);
        if (itemElevation != DEF_VAL) {
            ViewCompat.setElevation(v, itemElevation);
        }
        if (textSize != DEF_VAL) {
            v.setTextSize(textSize);
        }
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_numpad, this);
        ArrayList<TextView> pads = new ArrayList<>();
        pads.add((TextView) findViewById(R.id.VN_0));
        pads.add((TextView) findViewById(R.id.VN_1));
        pads.add((TextView) findViewById(R.id.VN_2));
        pads.add((TextView) findViewById(R.id.VN_3));
        pads.add((TextView) findViewById(R.id.VN_4));
        pads.add((TextView) findViewById(R.id.VN_5));
        pads.add((TextView) findViewById(R.id.VN_6));
        pads.add((TextView) findViewById(R.id.VN_7));
        pads.add((TextView) findViewById(R.id.VN_8));
        pads.add((TextView) findViewById(R.id.VN_9));
        pads.add((TextView) findViewById(R.id.VN_del));
        pads.add((TextView) findViewById(R.id.VN_c));
        for (TextView p : pads) {
            p.setBackground(itemDrawable);
            p.setTextColor(textColor);
            p.setOnClickListener(this);
            updateView(p);
        }
    }


    private String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

    @Override
    public void onClick(View v) {
        TextView x = ((TextView) v);
        if (x.getText().equals("del")) {
            if (num.length() >= 1) {
                num = removeLastChar(num);
            }
        } else if (x.getText().equals("c")) {
            num = "";
        } else {
            int limit = -1;
            if (num.length() == limit) {
                num = "";
            }
            num = num + x.getText();
        }
        if (inputListener != null) {
            inputListener.onInputChanged(num);
        }
    }

    public String getNum() {
        return num;
    }

    public void clear() {
        num = "";
        inputListener.onInputChanged(num);
    }


    public interface InputListener {
        void onInputChanged(String number);
    }
}
