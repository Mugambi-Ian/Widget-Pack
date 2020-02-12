package nenecorp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;


public class TextPad extends LinearLayout implements View.OnClickListener {
    private String text = "";
    private InputListener inputListener;
    private ArrayList<Character> chars;
    private int Id = -1;
    private char aChar;
    private Drawable itemDrawable;
    private int textColor;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private int itemHeight, itemWidth, itemMargin, textSize;
    private final float DEF_VAL = -20;
    private int itemElevation;

    public void setInputListener(InputListener inputListener) {
        this.inputListener = inputListener;
    }

    public TextPad(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttrs(attrs);
    }

    private void loadAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextPad);
        itemDrawable = a.getDrawable(R.styleable.TextPad_ptDrawable);
        if (itemDrawable == null) {
            itemDrawable = getContext().getDrawable(R.drawable.box_textpad);
        }
        textColor = a.getColor(R.styleable.TextPad_ptTextColor, Color.BLACK);
        itemHeight = (int) a.getDimension(R.styleable.TextPad_ptItemHeight, DEF_VAL);
        itemWidth = (int) a.getDimension(R.styleable.TextPad_ptItemWidth, DEF_VAL);
        itemMargin = (int) a.getDimension(R.styleable.TextPad_ptItemMargin, DEF_VAL);
        itemElevation = (int) a.getDimension(R.styleable.TextPad_ptItemElevation, DEF_VAL);
        textSize = (int) a.getDimension(R.styleable.TextPad_ptTextSize, DEF_VAL);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_textpad, this);
        ArrayList<TextView> pads = new ArrayList<>();
        pads.add((TextView) findViewById(R.id.VT_1));
        pads.add((TextView) findViewById(R.id.VT_2));
        pads.add((TextView) findViewById(R.id.VT_3));
        pads.add((TextView) findViewById(R.id.VT_4));
        pads.add((TextView) findViewById(R.id.VT_5));
        pads.add((TextView) findViewById(R.id.VT_6));
        pads.add((TextView) findViewById(R.id.VT_7));
        pads.add((TextView) findViewById(R.id.VT_8));
        for (TextView p : pads) {
            p.setBackground(itemDrawable);
            p.setTextColor(textColor);
            p.setOnClickListener(this);
            updateView(p);
        }
        TextView specialChar = findViewById(R.id.VT_0);
        specialChar.setTextColor(textColor);
        specialChar.setBackground(itemDrawable);
        specialChar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View x) {
                String c = ",.!@=<>/\"#$%&:;?\'()*+-[\\]^_`{|}~";
                onItemClick(c, (TextView) x);
            }
        });
        updateView(specialChar);
        TextView space = findViewById(R.id.VT_space);
        space.setBackground(itemDrawable);
        space.setTextColor(textColor);
        space.setPaintFlags(space.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        space.setText("   ");
        space.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Id = -1;
                text = text + " ";
                textChanged(text);
            }
        });
        updateView(space);
        TextView del = findViewById(R.id.VT_del);
        del.setBackground(itemDrawable);
        del.setTextColor(textColor);
        del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Id = -1;
                if (text.length() >= 1) {
                    text = removeLastChar(text);
                }
                textChanged(text);
            }
        });
        updateView(del);
        TextView clear = findViewById(R.id.VT_c);
        clear.setBackground(itemDrawable);
        clear.setTextColor(textColor);
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Id = -1;
                text = "";
                textChanged(text);
            }
        });
        updateView(clear);
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

    private void onItemClick(String c, TextView x) {
        if (x.getId() != Id) {
            chars = getChars(c);
            text = text + chars.get(0);
            Id = x.getId();
            aChar = chars.get(0);
            stringManager();
        } else {
            Id = x.getId();
            stringManager();
            int iTB = chars.indexOf(aChar);
            if (iTB + 1 < chars.size()) {
                text = removeLastChar(text);
                text = text + chars.get(iTB + 1);
                aChar = chars.get(iTB + 1);
            } else {
                text = removeLastChar(text);
                text = text + chars.get(0);
                aChar = chars.get(0);
            }
        }
        textChanged(text);
    }

    @Override
    public void onClick(View v) {
        TextView x = ((TextView) v);
        String c = x.getText().toString();
        onItemClick(c, (TextView) v);
    }

    private void stringManager() {
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
            timerHandler = null;
            timerRunnable = null;
        }
        timerHandler = new Handler();
        int timerDelay = 1200;
        timerRunnable = new Runnable() {
            public void run() {
                Id = -1;
            }
        };
        timerHandler.postDelayed(timerRunnable, timerDelay);
    }


    private String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }


    private void textChanged(String text) {
        if (inputListener != null) {
            inputListener.onInputChanged(text);
        }
    }

    private ArrayList<Character> getChars(CharSequence text) {
        ArrayList<Character> characters = new ArrayList<>();
        for (int x = 0; x < text.length(); x++) {
            characters.add(text.charAt(x));
        }
        return characters;
    }

    public String getText() {
        return text;
    }

    public void clearText() {
        text = "";
        inputListener.onInputChanged(text);
    }

    public interface InputListener {
        void onInputChanged(String number);
    }
}
