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

public class DayPicker extends LinearLayout implements View.OnClickListener {
    private ArrayList<TextView> days = new ArrayList<>();
    private TextView titleTextView;
    private String titleText;
    private boolean showTitle;
    private static final float DEF_VAL = -20;
    private int itemHeight;
    private int itemWidth;
    private int itemMargin;
    private int itemElevation;
    private int textSize;
    private int selectedTextColor, unselectedTextColor;
    private Drawable selectedDrawable, unselectedDrawable;
    private int pickerOrientation;

    public enum PickerOrientation {
        HORIZONTAL(1), VERTICAL(2);

        PickerOrientation(int i) {
        }

        static int horizontal() {
            return 1;
        }

        public static PickerOrientation valueOf(int pickerOrientation) {
            switch (pickerOrientation) {
                case 1:
                    return HORIZONTAL;
                case 2:
                    return VERTICAL;
            }
            return null;
        }
    }

    public DayPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        loadAttrs(attrs);
    }

    private void loadAttrs(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DayPicker);
        showTitle = a.getBoolean(R.styleable.DayPicker_showTitle, true);
        titleText = a.getString(R.styleable.DayPicker_titleText);
        selectedTextColor = a.getColor(R.styleable.DayPicker_selectedTextColor, Color.WHITE);
        unselectedTextColor = a.getColor(R.styleable.DayPicker_unselectedTextColor, Color.BLACK);
        selectedDrawable = a.getDrawable(R.styleable.DayPicker_selectedDrawable);
        unselectedDrawable = a.getDrawable(R.styleable.DayPicker_unselectedDrawable);
        if (selectedDrawable == null) {
            selectedDrawable = getContext().getDrawable(R.drawable.box_daypicker_selected);
        }
        if (unselectedDrawable == null) {
            unselectedDrawable = getContext().getDrawable(R.drawable.box_daypicker_unselected);
        }
        pickerOrientation = a.getInt(R.styleable.DayPicker_pickerOrientation, PickerOrientation.horizontal());
        itemHeight = (int) a.getDimension(R.styleable.DayPicker_dpItemHeight, DEF_VAL);
        itemWidth = (int) a.getDimension(R.styleable.DayPicker_dpItemWidth, DEF_VAL);
        itemMargin = (int) a.getDimension(R.styleable.DayPicker_dpItemMargin, DEF_VAL);
        itemElevation = (int) a.getDimension(R.styleable.DayPicker_dpItemElevation, DEF_VAL);
        textSize = (int) a.getDimension(R.styleable.DayPicker_dpTextSize, DEF_VAL);
        setPickerOrientation(PickerOrientation.valueOf(pickerOrientation));
    }

    private void setPickerOrientation(PickerOrientation pickerOrientation) {
        if (pickerOrientation == PickerOrientation.HORIZONTAL) {
            horizontalDayPicker();
        }
        if (pickerOrientation == PickerOrientation.VERTICAL) {
            verticalDayPicker();
        }
    }

    private void horizontalDayPicker() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_day_picker_horizontal, this);
        initView();
    }

    private void verticalDayPicker() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_day_picker_vertical, this);
        initView();
    }

    private void hideTitle() {
        titleTextView.setVisibility(GONE);
    }

    private void showTitle() {
        titleTextView.setVisibility(VISIBLE);
    }

    private void initView() {
        days.add(((TextView) findViewById(R.id.VDP_btnSun)));
        days.add(((TextView) findViewById(R.id.VDP_btnMon)));
        days.add(((TextView) findViewById(R.id.VDP_btnTue)));
        days.add(((TextView) findViewById(R.id.VDP_btnWen)));
        days.add(((TextView) findViewById(R.id.VDP_btnThur)));
        days.add(((TextView) findViewById(R.id.VDP_btnFri)));
        days.add(((TextView) findViewById(R.id.VDP_btnSat)));
        for (TextView x : days) {
            x.setOnClickListener(this);
            x.setTextColor(unselectedTextColor);
            x.setBackground(unselectedDrawable);
            LayoutParams params = (LayoutParams) x.getLayoutParams();
            if (itemHeight != DEF_VAL) {
                params.height = itemHeight;
            }
            if (itemWidth != DEF_VAL) {
                params.width = itemWidth;
            }
            if (itemMargin != DEF_VAL) {
                if (pickerOrientation == 1) {
                    params.setMarginStart(itemMargin);
                    params.setMarginEnd(itemMargin);
                }
                if (pickerOrientation == 2) {
                    params.setMargins(0, itemMargin, 0, itemMargin);
                }
            }
            x.setLayoutParams(params);
            if (itemElevation != DEF_VAL) {
                ViewCompat.setElevation(x, itemElevation);
            }
            if (textSize != DEF_VAL) {
                x.setTextSize(textSize);
            }
        }
        titleTextView = findViewById(R.id.VDP_title);
        if (titleText != null && !titleText.equals("")) {
            setTitle(titleText);
        } else {
            showTitle = false;
        }
        if (showTitle) {
            showTitle();
        } else {
            hideTitle();
        }
    }

    public void setTitle(String title) {
        showTitle();
        titleTextView.setText(title);
    }

    public void displayDays(String dayS) {
        String[] ds = dayS.split(",");
        for (TextView v : days) {
            for (String y : ds) {
                if (y.equals("" + days.indexOf(v))) {
                    v.setBackground(unselectedDrawable);
                    v.setTextColor(unselectedTextColor);
                } else {
                    v.setBackground(selectedDrawable);
                    v.setTextColor(selectedTextColor);
                }
            }
        }
    }

    public String getDays() {
        StringBuilder dayS = new StringBuilder();
        for (TextView x : days) {
            if (x.getBackground() == selectedDrawable) {
                if (dayS.toString().equals("")) {
                    dayS = new StringBuilder((days.indexOf(x) + 1) + ",");
                } else {
                    dayS.append((days.indexOf(x) + 1)).append(",");
                }
            }
        }
        if (dayS.length() != 0) {
            if (dayS.charAt(dayS.length() - 1) == ',') {
                dayS = new StringBuilder(removeLastChar(dayS.toString()));
            }
        }
        return dayS.toString();
    }

    private String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

    @Override
    public void onClick(View v) {
        if (v.getBackground() == selectedDrawable) {
            v.setBackground(unselectedDrawable);
            ((TextView) v).setTextColor(unselectedTextColor);
        } else {
            v.setBackground(selectedDrawable);
            ((TextView) v).setTextColor(selectedTextColor);
        }
    }
}
